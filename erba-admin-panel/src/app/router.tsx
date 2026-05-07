import { createBrowserRouter, Navigate } from "react-router-dom";

import { ProtectedRoute } from "../auth/ProtectedRoute";

import { AdminLayout } from "../layouts/AdminLayout";

import { LoginPage } from "../pages/LoginPage";
import { DashboardPage } from "../pages/DashboardPage";
import { AuthUsersPage } from "../pages/AuthUsersPage";
import {IdentityUsersPage} from "../pages/IdentityUsersPage.tsx";
import {CreateIdentityUserPage} from "../pages/CreateIdentityUserPage.tsx";
import {IdentityUserDetailPage} from "../pages/IdentityUserDetailPage.tsx";

export const router = createBrowserRouter([
    {
        path: "/login",
        element: <LoginPage />,
    },
    {
        element: <ProtectedRoute />,
        children: [
            {
                element: <AdminLayout />,
                children: [
                    {
                        path: "/dashboard",
                        element: <DashboardPage />,
                    },
                    {
                        path: "/admin/auth-users",
                        element: <AuthUsersPage />,
                    },
                    {
                        path: "/admin/identity-users",
                        element: <IdentityUsersPage />,
                    },
                    {
                        path: "/",
                        element: <Navigate to="/dashboard" replace />,
                    },
                    {
                        path: "/admin/identity-users/new",
                        element: <CreateIdentityUserPage />,
                    },
                    {
                        path: "/admin/identity-users/:id",
                        element: <IdentityUserDetailPage />,
                    }
                ],
            },
        ],
    },
]);