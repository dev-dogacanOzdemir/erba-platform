import type { ReactNode } from "react";
import { Link, Outlet, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";
import {
  Home,
  Users,
  UserCheck,
  LogOut,
  ChevronRight,
} from "lucide-react";
import { Button } from "../components";

interface NavItem {
  to: string;
  label: string;
  icon: ReactNode;
}

export function AdminLayout() {
  const navigate = useNavigate();
  const location = useLocation();
  const { logout } = useAuth();

  const navItems: NavItem[] = [
    { to: "/dashboard", label: "Dashboard", icon: <Home className="h-5 w-5" /> },
    {
      to: "/admin/auth-users",
      label: "Auth Users",
      icon: <UserCheck className="h-5 w-5" />,
    },
    {
      to: "/admin/identity-users",
      label: "Identity Users",
      icon: <Users className="h-5 w-5" />,
    },
  ];

  async function handleLogout() {
    await logout();
    navigate("/login");
  }

  const isActive = (path: string) => {
    return location.pathname === path || location.pathname.startsWith(path + "/");
  };

  return (
    <div className="flex h-screen w-full bg-slate-50">
      {/* Sidebar */}
      <aside className="flex w-64 flex-col border-r border-slate-200 bg-white">
        {/* Logo Section */}
        <div className="border-b border-slate-200 px-6 py-6">
          <Link to="/dashboard" className="flex items-center gap-2 no-underline">
            <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-primary-600">
              <span className="text-sm font-bold text-white">E</span>
            </div>
            <div>
              <p className="text-sm font-semibold text-slate-900">Erba</p>
              <p className="text-xs text-slate-500">Admin Panel</p>
            </div>
          </Link>
        </div>

        {/* Navigation */}
        <nav className="flex-1 space-y-1 px-4 py-6">
          {navItems.map((item) => (
            <Link
              key={item.to}
              to={item.to}
              className={`flex items-center gap-3 rounded-lg px-4 py-3 text-sm font-medium no-underline transition-all ${
                isActive(item.to)
                  ? "bg-primary-50 text-primary-700"
                  : "text-slate-600 hover:bg-slate-50 hover:text-slate-900"
              }`}
            >
              {item.icon}
              <span className="flex-1">{item.label}</span>
              {isActive(item.to) && (
                <ChevronRight className="h-4 w-4" />
              )}
            </Link>
          ))}
        </nav>

        {/* Logout Button */}
        <div className="border-t border-slate-200 px-4 py-4">
          <Button
            variant="secondary"
            size="sm"
            onClick={handleLogout}
            className="w-full justify-center gap-2"
          >
            <LogOut className="h-4 w-4" />
            Logout
          </Button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="flex-1 overflow-auto">
        <Outlet />
      </main>
    </div>
  );
}