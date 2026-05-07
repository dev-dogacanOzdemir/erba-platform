import type { FormEvent } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createIdentityUser } from "../api/identityApi";
import {
  Alert,
  Button,
  Card,
  FormField,
  FormSection,
  PageContainer,
  PageHeader,
} from "../components";
import { ArrowLeft } from "lucide-react";

export function CreateIdentityUserPage() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("employee.frontend@example.com");
  const [firstName, setFirstName] = useState("Ali");
  const [lastName, setLastName] = useState("Yilmaz");
  const [phone, setPhone] = useState("05550000000");

  const [employeeNumber, setEmployeeNumber] = useState("EMP-frontend-001");
  const [department, setDepartment] = useState("IT");
  const [position, setPosition] = useState("SOFTWARE_ENGINEER");
  const [employmentType, setEmploymentType] = useState("FULL_TIME");
  const [hireDate, setHireDate] = useState("2026-05-07");
  const [birthDate, setBirthDate] = useState("1998-05-10");

  const [nationalId, setNationalId] = useState("11111111111");
  const [sgkNumber, setSgkNumber] = useState("SGK-FRONTEND-001");

  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setError(null);

    try {
      setIsSubmitting(true);
      await createIdentityUser({
        userType: "EMPLOYEE",
        email,
        firstName,
        lastName,
        phone,
        employeeProfile: {
          employeeNumber,
          department,
          position,
          employmentType,
          hireDate,
          birthDate,
        },
        sensitiveInfo: {
          nationalId,
          sgkNumber,
        },
      });

      navigate("/admin/identity-users");
    } catch (err) {
      const errorMsg =
        err instanceof Error ? err.message : "Identity kullanıcı oluşturulamadı.";
      setError(errorMsg);
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <PageContainer>
      <div className="mb-6">
        <button
          onClick={() => navigate("/admin/identity-users")}
          className="flex items-center gap-2 text-primary-600 hover:text-primary-700"
        >
          <ArrowLeft className="h-4 w-4" />
          Geri Dön
        </button>
      </div>

      <PageHeader
        title="Yeni Identity Kullanıcı"
        description="Sistem identity kullanıcısı oluşturun"
      />

      <div className="mx-auto max-w-2xl">
        <Card>
          <form onSubmit={handleSubmit} className="space-y-8">
            {error && (
              <Alert type="error" message={error} onClose={() => setError(null)} />
            )}

            {/* Basic Info */}
            <FormSection title="Kişisel Bilgiler">
              <div className="grid gap-4 sm:grid-cols-2">
                <FormField
                  label="Email Adresi"
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
                <FormField
                  label="Telefon"
                  type="tel"
                  value={phone}
                  onChange={(e) => setPhone(e.target.value)}
                  required
                />
              </div>

              <div className="grid gap-4 sm:grid-cols-2">
                <FormField
                  label="Ad"
                  type="text"
                  value={firstName}
                  onChange={(e) => setFirstName(e.target.value)}
                  required
                />
                <FormField
                  label="Soyadı"
                  type="text"
                  value={lastName}
                  onChange={(e) => setLastName(e.target.value)}
                  required
                />
              </div>
            </FormSection>

            {/* Employee Profile */}
            <FormSection title="Çalışan Profili">
              <FormField
                label="Çalışan No"
                type="text"
                value={employeeNumber}
                onChange={(e) => setEmployeeNumber(e.target.value)}
                required
              />

              <div className="grid gap-4 sm:grid-cols-2">
                <FormField
                  label="Departman"
                  isSelect
                  value={department}
                  onChange={(e) => setDepartment(e.target.value)}
                  required
                >
                  <option value="IT">IT</option>
                  <option value="FINANCE">Finans</option>
                  <option value="HR">İnsan Kaynakları</option>
                  <option value="SALES">Satış</option>
                </FormField>

                <FormField
                  label="Pozisyon"
                  isSelect
                  value={position}
                  onChange={(e) => setPosition(e.target.value)}
                  required
                >
                  <option value="SOFTWARE_ENGINEER">Yazılım Mühendisi</option>
                  <option value="MANAGER">Müdür</option>
                  <option value="ACCOUNTANT">Muhasebeci</option>
                  <option value="HR_SPECIALIST">HR Uzmanı</option>
                </FormField>
              </div>

              <FormField
                label="İstihdam Türü"
                isSelect
                value={employmentType}
                onChange={(e) => setEmploymentType(e.target.value)}
                required
              >
                <option value="FULL_TIME">Tam Zamanlı</option>
                <option value="PART_TIME">Yarı Zamanlı</option>
                <option value="INTERN">Stajyer</option>
              </FormField>

              <div className="grid gap-4 sm:grid-cols-2">
                <FormField
                  label="İşe Başlama Tarihi"
                  type="date"
                  value={hireDate}
                  onChange={(e) => setHireDate(e.target.value)}
                  required
                />
                <FormField
                  label="Doğum Tarihi"
                  type="date"
                  value={birthDate}
                  onChange={(e) => setBirthDate(e.target.value)}
                  required
                />
              </div>
            </FormSection>

            {/* Sensitive Info */}
            <FormSection title="Hassas Bilgiler">
              <div className="grid gap-4 sm:grid-cols-2">
                <FormField
                  label="Kimlik No"
                  type="text"
                  value={nationalId}
                  onChange={(e) => setNationalId(e.target.value)}
                  required
                />
                <FormField
                  label="SGK No"
                  type="text"
                  value={sgkNumber}
                  onChange={(e) => setSgkNumber(e.target.value)}
                  required
                />
              </div>
            </FormSection>

            {/* Actions */}
            <div className="flex gap-3 border-t border-slate-200 pt-6">
              <Button
                variant="primary"
                type="submit"
                isLoading={isSubmitting}
              >
                Oluştur
              </Button>
              <Button
                variant="secondary"
                type="button"
                onClick={() => navigate("/admin/identity-users")}
              >
                İptal
              </Button>
            </div>
          </form>
        </Card>
      </div>
    </PageContainer>
  );
}