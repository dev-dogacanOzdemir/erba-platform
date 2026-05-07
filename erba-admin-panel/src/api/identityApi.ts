import { identityApiClient } from "./apiClient";

export interface IdentityUser {
    id: string;
    authUserId: string | null;

    userType: string;
    userTypeLabel: string;

    status: string;
    statusLabel: string;

    email: string;
    firstName: string;
    lastName: string;
    phone: string;

    profilePhotoId: string | null;

    createdAt: string;
    updatedAt: string | null;
}

export async function listIdentityUsers() {
    const response = await identityApiClient.get<IdentityUser[]>(
        "/api/v1/identity-users"
    );

    return response.data;
}

export interface CreateIdentityUserRequest {
    userType: "EMPLOYEE";
    email: string;
    firstName: string;
    lastName: string;
    phone: string;
    employeeProfile: {
        employeeNumber: string;
        department: string;
        position: string;
        employmentType: string;
        hireDate: string;
        birthDate: string;
    };
    sensitiveInfo: {
        nationalId: string;
        sgkNumber: string;
    };
}

export interface CreateIdentityUserResponse {
    identityUserId: string;
}

export async function createIdentityUser(request: CreateIdentityUserRequest) {
    const response = await identityApiClient.post<CreateIdentityUserResponse>(
        "/api/v1/identity-users",
        request
    );

    return response.data;
}

export interface EmployeeProfile {
    id: string;
    employeeNumber: string;
    department: string;
    position: string;
    employmentType: string;
    hireDate: string;
    terminationDate: string | null;
    birthDate: string;
    createdAt: string;
    updatedAt: string | null;
}

export interface IdentityUserDetail extends IdentityUser {
    employeeProfile: EmployeeProfile | null;
}

export async function getIdentityUser(id: string) {
    const response = await identityApiClient.get<IdentityUserDetail>(
        `/api/v1/identity-users/${id}`
    );

    return response.data;
}

export interface UpdateIdentityUserRequest {
    authUserId?: string | null;
    userType?: string;
    status?: string;
    email?: string;
    firstName?: string;
    lastName?: string;
    phone?: string;
    profilePhotoId?: string | null;
    employeeProfile?: {
        employeeNumber?: string;
        department?: string;
        position?: string;
        employmentType?: string;
        hireDate?: string;
        terminationDate?: string | null;
        birthDate?: string;
    };
}

export async function updateIdentityUser(
    id: string,
    request: UpdateIdentityUserRequest
) {
    await identityApiClient.put(
        `/api/v1/identity-users/${id}`,
        request
    );
}

export async function deleteIdentityUser(id: string) {
    await identityApiClient.delete(
        `/api/v1/identity-users/${id}`
    );
}

export async function linkAuthUser(
    identityUserId: string,
    authUserId: string
) {
    await identityApiClient.patch(
        `/api/v1/identity-users/${identityUserId}/link-auth-user`,
        { authUserId }
    );
}
