import { useEffect, useState } from "react";
import type { FormEvent } from "react";
import {
    changeAuthUserRole,
    changeAuthUserStatus,
    createAuthUser,
    listAuthUsers,
    type AuthUser,
} from "../api/authApi";

type AuthRole = "ADMIN" | "EMPLOYEE";
type AuthStatus = "ACTIVE" | "PASSIVE" | "LOCKED";

export function AuthUsersPage() {
    const [users, setUsers] = useState<AuthUser[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    const [email, setEmail] = useState("");
    const [temporaryPassword, setTemporaryPassword] = useState("TempPassword123!");
    const [role, setRole] = useState<AuthRole>("EMPLOYEE");
    const [status, setStatus] = useState<AuthStatus>("ACTIVE");

    async function loadUsers() {
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
    }

    async function handleCreate(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();

        await createAuthUser({
            email,
            temporaryPassword,
            role,
            status,
        });

        setEmail("");
        setTemporaryPassword("TempPassword123!");
        setRole("EMPLOYEE");
        setStatus("ACTIVE");

        await loadUsers();
    }

    async function handleRoleChange(userId: string, nextRole: AuthRole) {
        await changeAuthUserRole(userId, nextRole);
        await loadUsers();
    }

    async function handleStatusChange(userId: string, nextStatus: AuthStatus) {
        await changeAuthUserStatus(userId, nextStatus);
        await loadUsers();
    }

    useEffect(() => {
        loadUsers();
    }, []);

    if (loading) return <p>Yükleniyor...</p>;

    return (
        <div>
            <h1>Auth Users</h1>

            {error && <p style={{ color: "red" }}>{error}</p>}

            <section style={{ marginBottom: 24, padding: 16, border: "1px solid #ddd" }}>
                <h2>Yeni Auth Hesabı Oluştur</h2>

                <form onSubmit={handleCreate} style={{ display: "grid", gap: 12, maxWidth: 420 }}>
                    <input
                        placeholder="Email"
                        value={email}
                        onChange={(event) => setEmail(event.target.value)}
                    />

                    <input
                        placeholder="Temporary Password"
                        value={temporaryPassword}
                        onChange={(event) => setTemporaryPassword(event.target.value)}
                    />

                    <select value={role} onChange={(event) => setRole(event.target.value as AuthRole)}>
                        <option value="EMPLOYEE">EMPLOYEE</option>
                        <option value="ADMIN">ADMIN</option>
                    </select>

                    <select value={status} onChange={(event) => setStatus(event.target.value as AuthStatus)}>
                        <option value="ACTIVE">ACTIVE</option>
                        <option value="PASSIVE">PASSIVE</option>
                        <option value="LOCKED">LOCKED</option>
                    </select>

                    <button type="submit">Oluştur</button>
                </form>
            </section>

            <table border={1} cellPadding={8} style={{ borderCollapse: "collapse", width: "100%" }}>
                <thead>
                <tr>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Failed Login</th>
                    <th>Locked Until</th>
                </tr>
                </thead>

                <tbody>
                {users.map((user) => (
                    <tr key={user.id}>
                        <td>{user.email}</td>

                        <td>
                            <select
                                value={user.role}
                                onChange={(event) => handleRoleChange(user.id, event.target.value as AuthRole)}
                            >
                                <option value="EMPLOYEE">EMPLOYEE</option>
                                <option value="ADMIN">ADMIN</option>
                            </select>
                        </td>

                        <td>
                            <select
                                value={user.status}
                                onChange={(event) =>
                                    handleStatusChange(user.id, event.target.value as AuthStatus)
                                }
                            >
                                <option value="ACTIVE">ACTIVE</option>
                                <option value="PASSIVE">PASSIVE</option>
                                <option value="LOCKED">LOCKED</option>
                            </select>
                        </td>

                        <td>{user.failedLoginCount}</td>
                        <td>{user.lockedUntil ?? "-"}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}