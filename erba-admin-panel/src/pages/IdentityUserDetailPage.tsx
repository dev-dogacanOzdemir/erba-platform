import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getIdentityUser, type IdentityUserDetail } from "../api/identityApi";

export function IdentityUserDetailPage() {
    const { id } = useParams<{ id: string }>();

    const [user, setUser] = useState<IdentityUserDetail | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    async function loadUser() {
        if (!id) return;

        try {
            setLoading(true);
            setError(null);

            const response = await getIdentityUser(id);
            setUser(response);
        } catch {
            setError("Identity kullanıcı detayı yüklenemedi.");
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        loadUser();
    }, [id]);

    if (loading) return <p>Yükleniyor...</p>;
    if (error) return <p style={{ color: "red" }}>{error}</p>;
    if (!user) return <p>Kullanıcı bulunamadı.</p>;

    return (
        <div>
            <h1>Identity User Detail</h1>

            <section style={{ marginBottom: 24 }}>
                <h2>Basic Info</h2>

                <p><strong>ID:</strong> {user.id}</p>
                <p><strong>Auth User ID:</strong> {user.authUserId ?? "-"}</p>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>Name:</strong> {user.firstName} {user.lastName}</p>
                <p><strong>Phone:</strong> {user.phone}</p>
                <p><strong>Type:</strong> {user.userType}</p>
                <p><strong>Status:</strong> {user.status}</p>
            </section>

            <section>
                <h2>Employee Profile</h2>

                {user.employeeProfile ? (
                    <>
                        <p><strong>Employee Number:</strong> {user.employeeProfile.employeeNumber}</p>
                        <p><strong>Department:</strong> {user.employeeProfile.department}</p>
                        <p><strong>Position:</strong> {user.employeeProfile.position}</p>
                        <p><strong>Employment Type:</strong> {user.employeeProfile.employmentType}</p>
                        <p><strong>Hire Date:</strong> {user.employeeProfile.hireDate}</p>
                        <p><strong>Birth Date:</strong> {user.employeeProfile.birthDate}</p>
                    </>
                ) : (
                    <p>Profil bilgisi yok.</p>
                )}
            </section>
        </div>
    );
}