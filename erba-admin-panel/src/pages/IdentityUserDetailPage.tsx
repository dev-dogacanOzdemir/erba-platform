import type { FormEvent } from "react";
import { useCallback, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import {
  deleteIdentityUser,
  getIdentityUser,
  linkAuthUser,
  updateIdentityUser,
  type IdentityUserDetail,
} from "../api/identityApi";
import { listAuthUsers, type AuthUser } from "../api/authApi";
import {
  Alert,
  Button,
  Card,
  FormField,
  FormSection,
  PageContainer,
  PageHeader,
  StatusBadge,
} from "../components";
import { Trash2, Link2, ArrowLeft } from "lucide-react";

export function IdentityUserDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [user, setUser] = useState<IdentityUserDetail | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  // Update form state
  const [isEditing, setIsEditing] = useState(false);
  const [updateForm, setUpdateForm] = useState({
    email: "",
    firstName: "",
    lastName: "",
    phone: "",
    status: "",
    department: "",
    position: "",
    employmentType: "",
    hireDate: "",
    birthDate: "",
  });
  const [updateLoading, setUpdateLoading] = useState(false);

  // Link auth user form state
  const [authUserId, setAuthUserId] = useState("");
  const [linkLoading, setLinkLoading] = useState(false);
  const [authUsers, setAuthUsers] = useState<AuthUser[]>([]);
  const [loadingAuthUsers, setLoadingAuthUsers] = useState(false);

  const loadUser = useCallback(async () => {
    if (!id) return;

    try {
      setLoading(true);
      setError(null);

      const response = await getIdentityUser(id);
      setUser(response);
    } catch (err) {
      const errorMsg =
        err instanceof Error ? err.message : "Identity kullanıcı detayı yüklenemedi.";
      setError(errorMsg);
    } finally {
      setLoading(false);
    }
  }, [id]);

  const loadAuthUsers = useCallback(async () => {
    try {
      setLoadingAuthUsers(true);
      const response = await listAuthUsers();
      setAuthUsers(response.data || []);
    } catch (err) {
      const errorMsg = err instanceof Error ? err.message : "Auth kullanıcıları yüklenemedi.";
      setError(errorMsg);
    } finally {
      setLoadingAuthUsers(false);
    }
  }, []);

  async function handleUpdate(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    if (!id || !user) return;

    try {
      setUpdateLoading(true);
      setError(null);

      const updateRequest = {
        email: updateForm.email,
        firstName: updateForm.firstName,
        lastName: updateForm.lastName,
        phone: updateForm.phone,
        status: updateForm.status,
        employeeProfile: user.employeeProfile
          ? {
              employeeNumber: user.employeeProfile.employeeNumber,
              department: updateForm.department,
              position: updateForm.position,
              employmentType: updateForm.employmentType,
              hireDate: updateForm.hireDate,
              birthDate: updateForm.birthDate,
            }
          : undefined,
      };

      await updateIdentityUser(id, updateRequest);
      setSuccessMessage("Identity kullanıcı başarıyla güncellendi.");
      setIsEditing(false);

      // Reload user data
      const updatedUser = await getIdentityUser(id);
      setUser(updatedUser);

      setTimeout(() => setSuccessMessage(null), 3000);
    } catch (err) {
      const errorMsg = err instanceof Error ? err.message : "Güncelleme başarısız oldu.";
      setError(errorMsg);
    } finally {
      setUpdateLoading(false);
    }
  }

  async function handleDelete() {
    if (!id) return;

    if (!confirm("Bu kullanıcıyı silmek istediğinizden emin misiniz?")) {
      return;
    }

    try {
      setError(null);
      await deleteIdentityUser(id);
      navigate("/admin/identity-users");
    } catch (err) {
      const errorMsg = err instanceof Error ? err.message : "Silme işlemi başarısız oldu.";
      setError(errorMsg);
    }
  }

  async function handleLinkAuthUser(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    if (!id || !authUserId) return;

    try {
      setLinkLoading(true);
      setError(null);

      await linkAuthUser(id, authUserId);
      setSuccessMessage("Auth kullanıcısı başarıyla linked.");
      setAuthUserId("");

      // Reload user data
      const updatedUser = await getIdentityUser(id);
      setUser(updatedUser);

      setTimeout(() => setSuccessMessage(null), 3000);
    } catch (err) {
      const errorMsg = err instanceof Error ? err.message : "Linking başarısız oldu.";
      setError(errorMsg);
    } finally {
      setLinkLoading(false);
    }
  }

  useEffect(() => {
    loadUser();
  }, [loadUser]);

  useEffect(() => {
    loadAuthUsers();
  }, [loadAuthUsers]);

  if (loading) {
    return (
      <div className="flex h-screen items-center justify-center">
        <p className="text-slate-600">Yükleniyor...</p>
      </div>
    );
  }

  if (!user) {
    return (
      <div className="flex h-screen items-center justify-center">
        <p className="text-slate-600">Kullanıcı bulunamadı.</p>
      </div>
    );
  }

  return (
    <PageContainer>
      <div className="mb-6">
        <button
          onClick={() => navigate("/admin/identity-users")}
          className="mb-4 flex items-center gap-2 text-primary-600 hover:text-primary-700"
        >
          <ArrowLeft className="h-4 w-4" />
          Geri Dön
        </button>
      </div>

      <PageHeader
        title={`${user.firstName} ${user.lastName}`}
        description={user.email}
        action={
          <div className="flex gap-2">
            <Button
              variant="secondary"
              onClick={() => {
                setIsEditing(true);
                if (user) {
                  setUpdateForm({
                    email: user.email,
                    firstName: user.firstName,
                    lastName: user.lastName,
                    phone: user.phone,
                    status: user.status,
                    department: user.employeeProfile?.department ?? "",
                    position: user.employeeProfile?.position ?? "",
                    employmentType: user.employeeProfile?.employmentType ?? "",
                    hireDate: user.employeeProfile?.hireDate ?? "",
                    birthDate: user.employeeProfile?.birthDate ?? "",
                  });
                }
              }}
            >
              Düzenle
            </Button>
            <Button variant="danger" onClick={handleDelete}>
              <Trash2 className="h-4 w-4" />
            </Button>
          </div>
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

      <div className="grid gap-6 lg:grid-cols-3">
        {/* Main Content */}
        <div className="space-y-6 lg:col-span-2">
          {/* Basic Info */}
          <Card title="Temel Bilgiler">
            <div className="space-y-4">
              <div className="grid gap-4 sm:grid-cols-2">
                <div>
                  <p className="text-xs font-semibold text-slate-600">ID</p>
                  <p className="mt-1 font-mono text-sm text-slate-900">{user.id}</p>
                </div>
                <div>
                  <p className="text-xs font-semibold text-slate-600">Status</p>
                  <p className="mt-1">
                    <StatusBadge status={user.status} />
                  </p>
                </div>
              </div>

              <div className="grid gap-4 sm:grid-cols-2">
                <div>
                  <p className="text-xs font-semibold text-slate-600">Email</p>
                  <p className="mt-1 text-sm text-slate-900">{user.email}</p>
                </div>
                <div>
                  <p className="text-xs font-semibold text-slate-600">Telefon</p>
                  <p className="mt-1 text-sm text-slate-900">{user.phone || "-"}</p>
                </div>
              </div>

              <div className="grid gap-4 sm:grid-cols-2">
                <div>
                  <p className="text-xs font-semibold text-slate-600">Ad</p>
                  <p className="mt-1 text-sm text-slate-900">{user.firstName}</p>
                </div>
                <div>
                  <p className="text-xs font-semibold text-slate-600">Soyadı</p>
                  <p className="mt-1 text-sm text-slate-900">{user.lastName}</p>
                </div>
              </div>
            </div>
          </Card>

          {/* Employee Profile */}
          {user.employeeProfile && (
            <Card title="Çalışan Profili">
              <div className="space-y-4">
                <div className="grid gap-4 sm:grid-cols-2">
                  <div>
                    <p className="text-xs font-semibold text-slate-600">Çalışan No</p>
                    <p className="mt-1 text-sm text-slate-900">
                      {user.employeeProfile.employeeNumber}
                    </p>
                  </div>
                  <div>
                    <p className="text-xs font-semibold text-slate-600">Departman</p>
                    <p className="mt-1 text-sm text-slate-900">
                      {user.employeeProfile.department}
                    </p>
                  </div>
                </div>

                <div className="grid gap-4 sm:grid-cols-2">
                  <div>
                    <p className="text-xs font-semibold text-slate-600">Pozisyon</p>
                    <p className="mt-1 text-sm text-slate-900">
                      {user.employeeProfile.position}
                    </p>
                  </div>
                  <div>
                    <p className="text-xs font-semibold text-slate-600">İstihdam Türü</p>
                    <p className="mt-1 text-sm text-slate-900">
                      {user.employeeProfile.employmentType}
                    </p>
                  </div>
                </div>

                <div className="grid gap-4 sm:grid-cols-2">
                  <div>
                    <p className="text-xs font-semibold text-slate-600">İşe Başlama</p>
                    <p className="mt-1 text-sm text-slate-900">
                      {user.employeeProfile.hireDate}
                    </p>
                  </div>
                  <div>
                    <p className="text-xs font-semibold text-slate-600">Doğum Tarihi</p>
                    <p className="mt-1 text-sm text-slate-900">
                      {user.employeeProfile.birthDate}
                    </p>
                  </div>
                </div>
              </div>
            </Card>
          )}

          {/* Edit Form */}
          {isEditing && (
            <Card title="Bilgileri Güncelle">
              <form onSubmit={handleUpdate} className="space-y-6">
                <FormSection title="Kişisel Bilgiler">
                  <div className="grid gap-4 sm:grid-cols-2">
                    <FormField
                      label="Email"
                      type="email"
                      value={updateForm.email}
                      onChange={(e) =>
                        setUpdateForm({
                          ...updateForm,
                          email: e.target.value,
                        })
                      }
                      required
                    />
                    <FormField
                      label="Telefon"
                      type="tel"
                      value={updateForm.phone}
                      onChange={(e) =>
                        setUpdateForm({
                          ...updateForm,
                          phone: e.target.value,
                        })
                      }
                    />
                  </div>

                  <div className="grid gap-4 sm:grid-cols-2">
                    <FormField
                      label="Ad"
                      type="text"
                      value={updateForm.firstName}
                      onChange={(e) =>
                        setUpdateForm({
                          ...updateForm,
                          firstName: e.target.value,
                        })
                      }
                      required
                    />
                    <FormField
                      label="Soyadı"
                      type="text"
                      value={updateForm.lastName}
                      onChange={(e) =>
                        setUpdateForm({
                          ...updateForm,
                          lastName: e.target.value,
                        })
                      }
                      required
                    />
                  </div>

                  <FormField
                    label="Status"
                    isSelect
                    value={updateForm.status}
                    onChange={(e) =>
                      setUpdateForm({
                        ...updateForm,
                        status: e.target.value,
                      })
                    }
                  >
                    <option value="ACTIVE">Aktif</option>
                    <option value="PASSIVE">Pasif</option>
                    <option value="LOCKED">Kilitli</option>
                  </FormField>
                </FormSection>

                {user.employeeProfile && (
                  <FormSection title="Çalışan Profili">
                    <div className="grid gap-4 sm:grid-cols-2">
                      <FormField
                        label="Departman"
                        type="text"
                        value={updateForm.department}
                        onChange={(e) =>
                          setUpdateForm({
                            ...updateForm,
                            department: e.target.value,
                          })
                        }
                      />
                      <FormField
                        label="Pozisyon"
                        type="text"
                        value={updateForm.position}
                        onChange={(e) =>
                          setUpdateForm({
                            ...updateForm,
                            position: e.target.value,
                          })
                        }
                      />
                    </div>

                    <div className="grid gap-4 sm:grid-cols-2">
                      <FormField
                        label="İstihdam Türü"
                        type="text"
                        value={updateForm.employmentType}
                        onChange={(e) =>
                          setUpdateForm({
                            ...updateForm,
                            employmentType: e.target.value,
                          })
                        }
                      />
                    </div>

                    <div className="grid gap-4 sm:grid-cols-2">
                      <FormField
                        label="İşe Başlama Tarihi"
                        type="date"
                        value={updateForm.hireDate}
                        onChange={(e) =>
                          setUpdateForm({
                            ...updateForm,
                            hireDate: e.target.value,
                          })
                        }
                      />
                      <FormField
                        label="Doğum Tarihi"
                        type="date"
                        value={updateForm.birthDate}
                        onChange={(e) =>
                          setUpdateForm({
                            ...updateForm,
                            birthDate: e.target.value,
                          })
                        }
                      />
                    </div>
                  </FormSection>
                )}

                <div className="flex gap-3">
                  <Button variant="primary" type="submit" isLoading={updateLoading}>
                    Kaydet
                  </Button>
                  <Button
                    variant="secondary"
                    type="button"
                    onClick={() => setIsEditing(false)}
                  >
                    İptal
                  </Button>
                </div>
              </form>
            </Card>
          )}
        </div>

        {/* Sidebar */}
        <div className="space-y-6">
          {/* Auth User Status */}
          <Card title="Auth Hesabı">
            <div className="space-y-4">
              {user.authUserId ? (
                <>
                  <div>
                    <p className="text-xs font-semibold text-slate-600">Bağlantılı User ID</p>
                    <p className="mt-1 break-all font-mono text-xs text-slate-900">
                      {user.authUserId}
                    </p>
                  </div>
                  <p className="text-sm text-green-600 font-medium">✓ Bağlantılandı</p>
                </>
              ) : (
                <p className="text-sm text-slate-500">Henüz auth user bağlantılanmadı.</p>
              )}
            </div>
          </Card>

          {/* Link Auth User Form */}
          <Card title="Auth User Bağla">
            <form onSubmit={handleLinkAuthUser} className="space-y-4">
              <FormField
                label="Auth User ID"
                type="text"
                value={authUserId}
                onChange={(e) => setAuthUserId(e.target.value)}
                placeholder="UUID seçin..."
              />

              {loadingAuthUsers ? (
                <p className="text-sm text-slate-500">Yükleniyor...</p>
              ) : authUsers.length > 0 ? (
                <div className="max-h-48 space-y-2 overflow-y-auto rounded-lg border border-slate-200 p-2">
                  {authUsers.map((au) => (
                    <button
                      key={au.id}
                      type="button"
                      onClick={() => setAuthUserId(au.id)}
                      className={`w-full rounded px-3 py-2 text-left text-sm transition-colors ${
                        authUserId === au.id
                          ? "bg-primary-100 text-primary-700"
                          : "hover:bg-slate-100 text-slate-700"
                      }`}
                    >
                      <p className="font-medium">{au.email}</p>
                      <p className="text-xs opacity-75 font-mono">{au.id}</p>
                    </button>
                  ))}
                </div>
              ) : (
                <p className="text-sm text-slate-500">Kullanıcı bulunamadı.</p>
              )}

              <Button
                variant="primary"
                type="submit"
                isLoading={linkLoading}
                disabled={!authUserId}
                className="w-full"
              >
                <Link2 className="h-4 w-4" />
                Bağla
              </Button>
            </form>
          </Card>
        </div>
      </div>
    </PageContainer>
  );
}

