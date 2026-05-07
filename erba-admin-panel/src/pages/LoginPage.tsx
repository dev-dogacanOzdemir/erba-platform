import type { FormEvent } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";
import { Alert, Button, Card, FormField } from "../components";

export function LoginPage() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    setError(null);

    try {
      setIsLoading(true);
      await login(email, password);
      navigate("/dashboard");
    } catch (err) {
      const message = err instanceof Error ? err.message : "Giriş başarısız.";
      setError(message || "Giriş başarısız. Email ve şifrenizi kontrol edin.");
    } finally {
      setIsLoading(false);
    }
  }

  return (
    <div className="flex min-h-screen items-center justify-center bg-gradient-to-br from-slate-50 to-slate-100 px-4">
      <div className="w-full max-w-md">
        {/* Logo & Title */}
        <div className="mb-8 text-center">
          <div className="mx-auto mb-4 flex h-16 w-16 items-center justify-center rounded-2xl bg-primary-600 shadow-lg">
            <span className="text-2xl font-bold text-white">E</span>
          </div>
          <h1 className="mb-2 text-3xl font-bold text-slate-900">
            Erba Admin
          </h1>
          <p className="text-slate-500">
            Kurumsal yönetim paneline hoşgeldiniz
          </p>
        </div>

        {/* Card */}
        <Card className="shadow-lg">
          <form onSubmit={handleSubmit} className="space-y-6">
            {error && (
              <Alert type="error" message={error} onClose={() => setError(null)} />
            )}

            <div className="rounded-lg border border-slate-200 bg-slate-50 p-4">
              <p className="text-sm font-medium text-slate-900">Yerel giriş notu</p>
              <p className="mt-1 text-sm text-slate-600">
                Auth servisinde hazır bir kullanıcı yoksa önce geçerli bir hesap oluşturmanız gerekir.
              </p>
            </div>

            <FormField
              label="Email Adresi"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="admin@example.com"
              required
            />

            <FormField
              label="Şifre"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
              required
            />

            <Button
              variant="primary"
              type="submit"
              isLoading={isLoading}
              className="w-full"
            >
              {isLoading ? "Giriş yapılıyor..." : "Giriş Yap"}
            </Button>
          </form>
        </Card>
      </div>
    </div>
  );
}