import { useState } from "react";
import type { FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { createIdentityUser } from "../api/identityApi";

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

    async function handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setError(null);

        try {
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
        } catch {
            setError("Identity kullanıcı oluşturulamadı.");
        }
    }

    return (
        <div>
            <h1>Create Identity User</h1>

            {error && <p style={{ color: "red" }}>{error}</p>}

            <form
                onSubmit={handleSubmit}
                style={{ display: "grid", gap: 12, maxWidth: 520 }}
            >
                <h3>Basic Info</h3>

                <input value={email} onChange={(e) => setEmail(e.target.value)} />
                <input value={firstName} onChange={(e) => setFirstName(e.target.value)} />
                <input value={lastName} onChange={(e) => setLastName(e.target.value)} />
                <input value={phone} onChange={(e) => setPhone(e.target.value)} />

                <h3>Employee Profile</h3>

                <input
                    value={employeeNumber}
                    onChange={(e) => setEmployeeNumber(e.target.value)}
                />

                <select value={department} onChange={(e) => setDepartment(e.target.value)}>
                    <option value="IT">IT</option>
                    <option value="FINANCE">FINANCE</option>
                    <option value="HR">HR</option>
                    <option value="SALES">SALES</option>
                </select>

                <select value={position} onChange={(e) => setPosition(e.target.value)}>
                    <option value="SOFTWARE_ENGINEER">SOFTWARE_ENGINEER</option>
                    <option value="MANAGER">MANAGER</option>
                    <option value="ACCOUNTANT">ACCOUNTANT</option>
                    <option value="HR_SPECIALIST">HR_SPECIALIST</option>
                </select>

                <select
                    value={employmentType}
                    onChange={(e) => setEmploymentType(e.target.value)}
                >
                    <option value="FULL_TIME">FULL_TIME</option>
                    <option value="PART_TIME">PART_TIME</option>
                    <option value="INTERN">INTERN</option>
                </select>

                <input
                    type="date"
                    value={hireDate}
                    onChange={(e) => setHireDate(e.target.value)}
                />

                <input
                    type="date"
                    value={birthDate}
                    onChange={(e) => setBirthDate(e.target.value)}
                />

                <h3>Sensitive Info</h3>

                <input value={nationalId} onChange={(e) => setNationalId(e.target.value)} />
                <input value={sgkNumber} onChange={(e) => setSgkNumber(e.target.value)} />

                <button type="submit">Create</button>
            </form>
        </div>
    );
}