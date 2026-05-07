import { useEffect, useState } from "react";
import { listIdentityUsers, type IdentityUser } from "../api/identityApi";
import {Link} from "react-router-dom";

export function IdentityUsersPage() {
    const [users, setUsers] = useState<IdentityUser[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    async function loadUsers() {
        try {
            setLoading(true);
            const response = await listIdentityUsers();
            setUsers(response);
        } catch {
            setError("Identity kullanıcıları yüklenemedi.");
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        loadUsers();
    }, []);

    if (loading) return <p>Yükleniyor...</p>;
    if (error) return <p style={{ color: "red" }}>{error}</p>;

    return (
        <div>
            <h1>Identity Users</h1>

            <table border={1} cellPadding={8} style={{ borderCollapse: "collapse", width: "100%" }}>
                <thead>
                <tr>
                    <th>Ad Soyad</th>
                    <th>Email</th>
                    <th>Department</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Auth Linked</th>
                    <th>Actions</th>
                </tr>
                </thead>

                <tbody>
                {users.map((user) => (
                    <tr key={user.id}>
                        <td>{user.firstName} {user.lastName}</td>
                        <td>{user.email}</td>
                        <td>-</td>
                        <td>{user.userTypeLabel} ({user.userType})</td>
                        <td>{user.statusLabel} ({user.status})</td>
                        <td>{user.authUserId ? "Evet" : "Hayır"}</td>
                        <td>
                            <Link to={`/admin/identity-users/${user.id}`}>Detay</Link>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}