import { useState } from "react";
import type { FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

export function LoginPage() {
    const navigate = useNavigate();
    const { login } = useAuth();

    const [email, setEmail] = useState("admin@example.com");
    const [password, setPassword] = useState("NewPassword123!");
    const [error, setError] = useState<string | null>(null);

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setError(null);

        try {
            await login(email, password);
            navigate("/dashboard");
        } catch {
            setError("Giriş başarısız. Bilgileri kontrol edin.");
        }
    }

    return (
        <div style={{ maxWidth: 360, margin: "80px auto", fontFamily: "Arial" }}>
            <h1>Erba Admin</h1>

            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email</label>
                    <input
                        style={{ width: "100%", padding: 8, marginTop: 4, marginBottom: 12 }}
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                    />
                </div>

                <div>
                    <label>Password</label>
                    <input
                        type="password"
                        style={{ width: "100%", padding: 8, marginTop: 4, marginBottom: 12 }}
                        value={password}
                        onChange={(event) => setPassword(event.target.value)}
                    />
                </div>

                {error && <p style={{ color: "red" }}>{error}</p>}

                <button type="submit" style={{ width: "100%", padding: 10 }}>
                    Login
                </button>
            </form>
        </div>
    );
}