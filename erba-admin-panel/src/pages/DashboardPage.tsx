import type { ReactNode } from "react";
import { useEffect, useMemo, useState } from "react";
import { Link } from "react-router-dom";
import {
  BriefcaseBusiness,
  IdCard,
  ShieldCheck,
  Users,
} from "lucide-react";
import { listAuthUsers, type AuthUser } from "../api/authApi";
import { listIdentityUsers, type IdentityUser } from "../api/identityApi";
import {
  Alert,
  Badge,
  Button,
  Card,
  DataTable,
  PageContainer,
  PageHeader,
  RoleBadge,
  StatusBadge,
  UserTypeBadge,
} from "../components";

interface KPICardProps {
  title: string;
  value: string;
  description: string;
  icon: ReactNode;
}

function KPICard({ title, value, description, icon }: KPICardProps) {
  return (
    <Card className="flex items-start justify-between">
      <div className="flex-1">
        <p className="text-sm font-medium text-slate-500">{title}</p>
        <p className="mt-2 text-3xl font-semibold text-slate-900">{value}</p>
        <p className="mt-2 text-sm text-slate-500">{description}</p>
      </div>
      <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-slate-900 text-white shadow-sm">
        {icon}
      </div>
    </Card>
  );
}

function formatDateTime(value: string | null | undefined) {
  if (!value) return "-";

  return new Intl.DateTimeFormat("tr-TR", {
    dateStyle: "medium",
    timeStyle: "short",
  }).format(new Date(value));
}

export function DashboardPage() {
  const [authUsers, setAuthUsers] = useState<AuthUser[]>([]);
  const [identityUsers, setIdentityUsers] = useState<IdentityUser[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

   async function loadDashboard() {
     try {
       setLoading(true);
       setError(null);

       const [authResponse, identityResponse] = await Promise.all([
         listAuthUsers(),
         listIdentityUsers(),
       ]);

       setAuthUsers(authResponse.data);
       setIdentityUsers(identityResponse);
     } catch (err) {
       // Hata alındığında, genellikle yetki sorunu ise sessiz geç
       // UI'da error gösterme, boş durumla devam et
       if (err instanceof Error && err.message.includes("Yetkiniz")) {
         // Yetki hatası sessiz kalıp boş state göster
         console.debug("Authorization error on dashboard load:", err.message);
         setAuthUsers([]);
         setIdentityUsers([]);
       } else {
         // Diğer hatalar için kullanıcıya bildir
         const message =
           err instanceof Error ? err.message : "Dashboard verileri yüklenemedi.";
         setError(message);
       }
     } finally {
       setLoading(false);
     }
   }

  useEffect(() => {
    loadDashboard();
  }, []);

  const stats = useMemo(() => {
    const totalAuth = authUsers.length;
    const activeAuth = authUsers.filter((user) => user.status === "ACTIVE").length;
    const totalIdentity = identityUsers.length;
    const linkedIdentity = identityUsers.filter((user) => !!user.authUserId).length;

    return [
      {
        title: "Toplam Auth Kullanıcısı",
        value: totalAuth.toString(),
        description: "Auth servisindeki toplam hesap sayısı",
        icon: <Users className="h-5 w-5" />,
      },
      {
        title: "Aktif Auth Kullanıcısı",
        value: activeAuth.toString(),
        description: "Aktif durumda olan kullanıcılar",
        icon: <ShieldCheck className="h-5 w-5" />,
      },
      {
        title: "Identity Kayıtları",
        value: totalIdentity.toString(),
        description: "Identity servisindeki toplam kayıt",
        icon: <IdCard className="h-5 w-5" />,
      },
      {
        title: "Bağlı Identity Kayıtları",
        value: linkedIdentity.toString(),
        description: "Auth hesabı bağlanmış identity kullanıcıları",
        icon: <BriefcaseBusiness className="h-5 w-5" />,
      },
    ];
  }, [authUsers, identityUsers]);

  const linkedIdentityCount = useMemo(
    () => identityUsers.filter((user) => !!user.authUserId).length,
    [identityUsers]
  );

  const recentIdentityUsers = useMemo(() => {
    return [...identityUsers]
      .sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime())
      .slice(0, 5);
  }, [identityUsers]);

  const authRoleStats = useMemo(() => {
    const adminCount = authUsers.filter((user) => user.role === "ADMIN").length;
    const employeeCount = authUsers.filter((user) => user.role === "EMPLOYEE").length;
    const lockedCount = authUsers.filter((user) => user.status === "LOCKED").length;

    return { adminCount, employeeCount, lockedCount };
  }, [authUsers]);

  if (loading) {
    return (
      <PageContainer>
        <Card className="p-10 text-center">
          <p className="text-slate-500">Dashboard verileri yükleniyor...</p>
        </Card>
      </PageContainer>
    );
  }

  return (
    <PageContainer>
      <PageHeader
        title="Dashboard"
        description="Gerçek sistem verileriyle güncellenen kurumsal yönetim özeti"
        action={
          <Link to="/admin/identity-users/new">
            <Button variant="primary">Yeni Identity Kullanıcı</Button>
          </Link>
        }
      />

      {error && (
        <Alert type="error" message={error} onClose={() => setError(null)} />
      )}

      <div className="grid gap-6 md:grid-cols-2 xl:grid-cols-4">
        {stats.map((stat) => (
          <KPICard
            key={stat.title}
            title={stat.title}
            value={stat.value}
            description={stat.description}
            icon={stat.icon}
          />
        ))}
      </div>

      <div className="mt-8 grid gap-6 xl:grid-cols-3">
        <Card className="xl:col-span-2" title="Son Identity Kayıtları">
          <DataTable<IdentityUser>
            columns={[
              {
                key: "firstName",
                label: "Ad Soyad",
                render: (_, row) => `${row.firstName} ${row.lastName}`,
              },
              { key: "email", label: "Email" },
              {
                key: "userType",
                label: "Tip",
                render: (value) => <UserTypeBadge userType={String(value)} />,
              },
              {
                key: "status",
                label: "Status",
                render: (value) => <StatusBadge status={String(value)} />,
              },
              {
                key: "authUserId",
                label: "Auth",
                render: (value) => (
                  <Badge variant={value ? "success" : "warning"}>
                    {value ? "Bağlı" : "Bağsız"}
                  </Badge>
                ),
              },
              {
                key: "createdAt",
                label: "Kayıt Tarihi",
                render: (value) => formatDateTime(String(value)),
              },
              {
                key: "id",
                label: "İşlem",
                render: (_, row) => (
                  <Link to={`/admin/identity-users/${row.id}`}>
                    <Button variant="secondary" size="sm">
                      Detay
                    </Button>
                  </Link>
                ),
              },
            ]}
            data={recentIdentityUsers}
            isLoading={false}
            emptyMessage="Identity kaydı bulunamadı."
          />
        </Card>

        <div className="space-y-6">
          <Card title="Auth Kullanıcı Özeti">
            <div className="space-y-4">
              <div className="flex items-center justify-between rounded-xl bg-slate-50 px-4 py-3">
                <span className="text-sm font-medium text-slate-600">Admin</span>
                <div className="flex items-center gap-3">
                  <RoleBadge role="ADMIN" />
                  <strong className="text-slate-900">{authRoleStats.adminCount}</strong>
                </div>
              </div>
              <div className="flex items-center justify-between rounded-xl bg-slate-50 px-4 py-3">
                <span className="text-sm font-medium text-slate-600">Employee</span>
                <div className="flex items-center gap-3">
                  <RoleBadge role="EMPLOYEE" />
                  <strong className="text-slate-900">{authRoleStats.employeeCount}</strong>
                </div>
              </div>
              <div className="flex items-center justify-between rounded-xl bg-slate-50 px-4 py-3">
                <span className="text-sm font-medium text-slate-600">Locked</span>
                <div className="flex items-center gap-3">
                  <StatusBadge status="LOCKED" />
                  <strong className="text-slate-900">{authRoleStats.lockedCount}</strong>
                </div>
              </div>
            </div>
          </Card>

          <Card title="Kullanıcı Sağlığı">
            <div className="space-y-4 text-sm text-slate-600">
              <div className="flex items-center justify-between">
                <span>Bağlı Identity Oranı</span>
                <strong className="text-slate-900">
                  {identityUsers.length === 0
                    ? "0%"
                    : `${Math.round((linkedIdentityCount / identityUsers.length) * 100)}%`}
                </strong>
              </div>
              <div className="flex items-center justify-between">
                <span>Bağsız Kayıt</span>
                <strong className="text-slate-900">
                  {identityUsers.filter((user) => !user.authUserId).length}
                </strong>
              </div>
              <div className="flex items-center justify-between">
                <span>Toplam Sistem Kaydı</span>
                <strong className="text-slate-900">
                  {authUsers.length + identityUsers.length}
                </strong>
              </div>
            </div>
          </Card>
        </div>
      </div>
    </PageContainer>
  );
}