import axios from "axios";
import type { AxiosResponse } from "axios";

export const authApiClient = axios.create({
    baseURL: import.meta.env.VITE_AUTH_API_BASE_URL,
    withCredentials: true,
});

export const identityApiClient = axios.create({
    baseURL: import.meta.env.VITE_IDENTITY_API_BASE_URL,
    withCredentials: true,
});

// Response interceptor for error handling
const setupInterceptors = (client: typeof authApiClient) => {
    client.interceptors.response.use(
        (response: AxiosResponse) => response,
        (error: unknown) => {
            if (
                error &&
                typeof error === "object" &&
                "response" in error &&
                error.response &&
                typeof error.response === "object" &&
                "status" in error.response &&
                "config" in error &&
                error.config &&
                typeof error.config === "object"
            ) {
                const status = (error.response as Record<string, unknown>).status;
                const url = String((error.config as Record<string, unknown>).url || "");

                // me() endpoint'ine spesifik handling - hata mesajısını değiştirme
                // me() başarısızlığı kritik değildir, token geçerli ise devam edebiliriz
                const isValidationEndpoint = url.includes("/me") || url.includes("me?");

                if (!isValidationEndpoint && (status === 401 || status === 403)) {
                    const err = error as Error & { response?: unknown };
                    err.message = "Yetkiniz yok veya oturum geçersiz";
                }
            }
            return Promise.reject(error);
        }
    );
};

setupInterceptors(authApiClient);
setupInterceptors(identityApiClient);

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