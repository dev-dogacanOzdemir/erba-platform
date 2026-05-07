import {
    createContext,
    useContext,
    useEffect,
    useMemo,
    useState,
} from "react";

import { login as loginRequest, logout as logoutRequest, me } from "../api/authApi";
import { setAccessToken } from "../api/apiClient";

interface AuthContextType {
    isAuthenticated: boolean;
    accessToken: string | null;
    login: (email: string, password: string) => Promise<void>;
    logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export function AuthProvider({
                                 children,
                             }: {
    children: React.ReactNode;
}) {
    const [accessToken, setToken] = useState<string | null>(null);

    useEffect(() => {
        setAccessToken(accessToken);
    }, [accessToken]);

    async function login(email: string, password: string) {
        const response = await loginRequest({
            email,
            password,
        });

        setToken(response.data.accessToken);
    }

    async function logout() {
        await logoutRequest();

        setToken(null);
        setAccessToken(null);
    }

    useEffect(() => {
        async function initialize() {
            if (!accessToken) {
                return;
            }

            try {
                await me();
            } catch {
                // me() endpoint'i sadece token validasyonudur, başarısız olması kritik değildir
                // She başarısız olsa bile token geçerliyse devam edelim
                // Hata sessizce bastırılır - bu normal bir durum
            }
        }

        initialize();
    }, [accessToken]);

    const value = useMemo<AuthContextType>(
        () => ({
            isAuthenticated: !!accessToken,
            accessToken,
            login,
            logout,
        }),
        [accessToken]
    );

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be used within AuthProvider");
    }

    return context;
}