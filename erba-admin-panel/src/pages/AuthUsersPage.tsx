import type { FormEvent } from "react";
import { useCallback, useEffect, useState } from "react";
import {
  changeAuthUserRole,
  changeAuthUserStatus,
  createAuthUser,
  listAuthUsers,
  type AuthUser,
} from "../api/authApi";
import {
  Alert,
  Button,
  Card,
  DataTable,
  FormField,
  PageContainer,
  PageHeader,
  RoleBadge,
  StatusBadge,
} from "../components";

type AuthRole = "ADMIN" | "EMPLOYEE";
type AuthStatus = "ACTIVE" | "PASSIVE" | "LOCKED";

export function AuthUsersPage() {
  const [users, setUsers] = useState<AuthUser[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  const [email, setEmail] = useState("");
  const [temporaryPassword, setTemporaryPassword] = useState("TempPassword123!");
  const [role, setRole] = useState<AuthRole>("EMPLOYEE");
  const [status, setStatus] = useState<AuthStatus>("ACTIVE");
  const [isCreating, setIsCreating] = useState(false);
  const [showCreateForm, setShowCreateForm] = useState(false);

  const loadUsers = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);

      const response = await listAuthUsers();
      setUsers(response.data);
    } catch {
      setError("Auth kullanıcıları yüklenemedi.");
    } finally {
      setLoading(false);
    }
  }, []);

  async function handleCreate(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setError(null);

    try {
      setIsCreating(true);

      await createAuthUser({
        email,
        temporaryPassword,
        role,
        status,
      });

      setSuccessMessage("Kullanıcı başarıyla oluşturuldu.");
      setEmail("");
      setTemporaryPassword("TempPassword123!");
      setRole("EMPLOYEE");
      setStatus("ACTIVE");
      setShowCreateForm(false);

      setTimeout(() => setSuccessMessage(null), 3000);

      await loadUsers();
    } catch (err) {
      const errorMsg = err instanceof Error ? err.message : "Oluşturma başarısız oldu.";
      setError(errorMsg);
    } finally {
      setIsCreating(false);
    }
  }

  async function handleRoleChange(userId: string, nextRole: AuthRole) {
    try {
      setError(null);
      await changeAuthUserRole(userId, nextRole);
      await loadUsers();
    } catch (err) {
      const errorMsg = err instanceof Error ? err.message : "Rol değişimi başarısız oldu.";
      setError(errorMsg);
    }
  }

  async function handleStatusChange(userId: string, nextStatus: AuthStatus) {
    try {
      setError(null);
      await changeAuthUserStatus(userId, nextStatus);
      await loadUsers();
    } catch (err) {
      const errorMsg = err instanceof Error ? err.message : "Status değişimi başarısız oldu.";
      setError(errorMsg);
    }
  }

  useEffect(() => {
    void loadUsers();
  }, [loadUsers]);

  return (
    <PageContainer>
      <PageHeader
        title="Auth Users"
        description="Sistem kullanıcı hesaplarını yönetin"
        action={
          <Button
            variant={showCreateForm ? "secondary" : "primary"}
            onClick={() => setShowCreateForm(!showCreateForm)}
          >
            {showCreateForm ? "İptal Et" : "+ Yeni Kullanıcı"}
          </Button>
        }
      />

      {error && (
        <Alert type="error" message={error} onClose={() => setError(null)} />
      )}

      {successMessage && (
        <Alert
          type="success"
          message={successMessage}
          onClose={() => setSuccessMessage(null)}
        />
      )}

      {/* Create Form */}
      {showCreateForm && (
        <Card className="mb-8" title="Yeni Auth Hesabı Oluştur">
          <form onSubmit={handleCreate} className="space-y-4">
            <FormField
              label="Email Adresi"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="user@example.com"
              required
            />

            <FormField
              label="Geçici Şifre"
              type="password"
              value={temporaryPassword}
              onChange={(e) => setTemporaryPassword(e.target.value)}
              placeholder="TempPassword123!"
              required
            />

            <div className="grid gap-4 md:grid-cols-2">
              <FormField
                label="Rol"
                isSelect
                value={role}
                onChange={(e) => setRole(e.target.value as AuthRole)}
              >
                <option value="EMPLOYEE">Çalışan</option>
                <option value="ADMIN">Admin</option>
              </FormField>

              <FormField
                label="Status"
                isSelect
                value={status}
                onChange={(e) => setStatus(e.target.value as AuthStatus)}
              >
                <option value="ACTIVE">Aktif</option>
                <option value="PASSIVE">Pasif</option>
                <option value="LOCKED">Kilitli</option>
              </FormField>
            </div>

            <div className="flex gap-3">
              <Button variant="primary" type="submit" isLoading={isCreating}>
                Oluştur
              </Button>
              <Button
                variant="secondary"
                type="button"
                onClick={() => setShowCreateForm(false)}
              >
                İptal
              </Button>
            </div>
          </form>
        </Card>
      )}

      {/* Users Table */}
      <DataTable<AuthUser>
        columns={[
          {
            key: "email",
            label: "Email",
            width: "30%",
          },
          {
            key: "role",
            label: "Rol",
            render: (value) => <RoleBadge role={value as string} />,
          },
          {
            key: "status",
            label: "Status",
            render: (value) => <StatusBadge status={value as string} />,
          },
          {
            key: "failedLoginCount",
            label: "Başarısız Giriş",
          },
          {
            key: "id",
            label: "İşlemler",
            render: (_, row) => (
              <div className="flex flex-wrap gap-2">
                <select
                  value={row.role}
                  onChange={(e) =>
                    handleRoleChange(row.id, e.target.value as AuthRole)
                  }
                  className="select min-w-32 px-3 py-2 text-sm"
                >
                  <option value="EMPLOYEE">Çalışan</option>
                  <option value="ADMIN">Admin</option>
                </select>
                <select
                  value={row.status}
                  onChange={(e) =>
                    handleStatusChange(row.id, e.target.value as AuthStatus)
                  }
                  className="select min-w-28 px-3 py-2 text-sm"
                >
                  <option value="ACTIVE">Aktif</option>
                  <option value="PASSIVE">Pasif</option>
                  <option value="LOCKED">Kilitli</option>
                </select>
              </div>
            ),
          },
        ]}
        data={users}
        isLoading={loading}
        emptyMessage="Kullanıcı bulunamadı."
      />
    </PageContainer>
  );
}