import axios from "axios";

export const authApiClient = axios.create({
    baseURL: import.meta.env.VITE_AUTH_API_BASE_URL,
    withCredentials: true,
});

export const identityApiClient = axios.create({
    baseURL: import.meta.env.VITE_IDENTITY_API_BASE_URL,
    withCredentials: true,
});

export function setAccessToken(accessToken: string | null) {
    const value = accessToken ? `Bearer ${accessToken}` : undefined;

    if (value) {
        authApiClient.defaults.headers.common.Authorization = value;
        identityApiClient.defaults.headers.common.Authorization = value;
    } else {
        delete authApiClient.defaults.headers.common.Authorization;
        delete identityApiClient.defaults.headers.common.Authorization;
    }
}