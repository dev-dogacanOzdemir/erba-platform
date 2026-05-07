import { useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { listIdentityUsers, type IdentityUser } from "../api/identityApi";
import {
  Alert,
  Button,
  DataTable,
  PageContainer,
  PageHeader,
  StatusBadge,
  UserTypeBadge,
} from "../components";
import { Eye, Plus } from "lucide-react";

export function IdentityUsersPage() {
  const [users, setUsers] = useState<IdentityUser[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const loadUsers = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await listIdentityUsers();
      setUsers(response);
    } catch {
      setError("Identity kullanıcıları yüklenemedi.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    void loadUsers();
  }, [loadUsers]);

  return (
    <PageContainer>
      <PageHeader
        title="Identity Users"
        description="Sistem identity kullanıcılarını yönetin"
        action={
          <Link to="/admin/identity-users/new" className="no-underline">
            <Button variant="primary">
              <Plus className="h-4 w-4" />
              Yeni Kullanıcı
            </Button>
          </Link>
        }
      />

      {error && (
        <Alert type="error" message={error} onClose={() => setError(null)} />
      )}

      <DataTable<IdentityUser>
        columns={[
          {
            key: "firstName",
            label: "Ad Soyad",
            width: "20%",
            render: (_, row) => `${row.firstName} ${row.lastName}`,
          },
          {
            key: "email",
            label: "Email",
            width: "25%",
          },
          {
            key: "userType",
            label: "Tip",
            render: (value) => <UserTypeBadge userType={value as string} />,
          },
          {
            key: "status",
            label: "Status",
            render: (value) => <StatusBadge status={value as string} />,
          },
          {
            key: "authUserId",
            label: "Auth Linked",
            render: (value) => (
              <span
                className={`text-sm font-medium ${
                  value ? "text-green-600" : "text-slate-500"
                }`}
              >
                {value ? "✓ Linked" : "-"}
              </span>
            ),
          },
          {
            key: "id",
            label: "İşlemler",
            render: (value) => (
              <Link to={`/admin/identity-users/${value}`} className="no-underline">
                <Button variant="secondary" size="sm">
                  <Eye className="h-4 w-4" />
                  Detay
                </Button>
              </Link>
            ),
          },
        ]}
        data={users}
        isLoading={loading}
        emptyMessage="Identity kullanıcısı bulunamadı."
      />
    </PageContainer>
  );
}