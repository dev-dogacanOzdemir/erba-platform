import { Link, Outlet, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";

export function AdminLayout() {
    const navigate = useNavigate();
    const { logout } = useAuth();

    async function handleLogout() {
        await logout();
        navigate("/login");
    }

    return (
        <div style={{ display: "flex", minHeight: "100vh", fontFamily: "Arial" }}>
            <aside style={{ width: 240, padding: 20, borderRight: "1px solid #ddd" }}>
                <h2>Erba Admin</h2>

                <nav style={{ display: "flex", flexDirection: "column", gap: 12 }}>
                    <Link to="/dashboard">Dashboard</Link>
                    <Link to="/admin/auth-users">Auth Users</Link>
                    <Link to="/admin/identity-users">Identity Users</Link>
                    <Link to="/admin/identity-users/new">Create Identity User</Link>
                </nav>

                <button onClick={handleLogout} style={{ marginTop: 32 }}>
                    Logout
                </button>
            </aside>

            <main style={{ flex: 1, padding: 24 }}>
                <Outlet />
            </main>
        </div>
    );
}