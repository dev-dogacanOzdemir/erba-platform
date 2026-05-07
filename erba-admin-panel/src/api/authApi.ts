import { authApiClient } from "./apiClient";

export interface LoginRequest {
    email: string;
    password: string;
}

export interface LoginResponse {
    success: boolean;
    data: {
        accessToken: string;
        tokenType: string;
        expiresIn: number;
    };
    error: unknown;
}

export interface AuthUser {
    id: string;
    email: string;
    role: string;
    roleLabel: string;
    status: string;
    statusLabel: string;
    failedLoginCount: number;
    lockedUntil: string | null;
    createdAt: string;
    updatedAt: string | null;
}

export interface ListAuthUsersResponse {
    success: boolean;
    data: AuthUser[];
    error: unknown;
}

export interface CreateAuthUserRequest {
    email: string;
    temporaryPassword: string;
    role: "ADMIN" | "EMPLOYEE";
    status: "ACTIVE" | "PASSIVE" | "LOCKED";
}

export interface CreateAuthUserResponse {
    success: boolean;
    data: {
        userId: string;
        email: string;
        role: string;
        roleLabel: string;
        status: string;
        statusLabel: string;
    };
    error: unknown;
}


export async function login(request: LoginRequest) {
    const response = await authApiClient.post<LoginResponse>(
        "/api/v1/auth/login",
        request
    );

    return response.data;
}

export async function logout() {
    await authApiClient.post("/api/v1/auth/logout");
}

export async function me() {
    const response = await authApiClient.get("/api/v1/auth/me");
    return response.data;
}

export async function listAuthUsers() {
    const response = await authApiClient.get<ListAuthUsersResponse>(
        "/api/v1/auth/users"
    );

    return response.data;
}

export async function createAuthUser(request: CreateAuthUserRequest) {
    const response = await authApiClient.post<CreateAuthUserResponse>(
        "/api/v1/auth/users",
        request
    );

    return response.data;
}

export async function changeAuthUserRole(
    userId: string,
    role: "ADMIN" | "EMPLOYEE"
) {
    await authApiClient.patch(`/api/v1/auth/users/${userId}/role`, {
        role,
    });
}

export async function changeAuthUserStatus(
    userId: string,
    status: "ACTIVE" | "PASSIVE" | "LOCKED"
) {
    await authApiClient.patch(`/api/v1/auth/users/${userId}/status`, {
        status,
    });
}