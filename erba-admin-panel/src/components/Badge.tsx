import type { ReactNode } from "react";

export interface BadgeProps {
  children: ReactNode;
  variant?: "default" | "success" | "warning" | "danger" | "info";
  className?: string;
}

export function Badge({ children, variant = "default", className }: BadgeProps) {
  const variantClasses = {
    default: "border-slate-200 bg-slate-100 text-slate-700",
    success: "border-emerald-200 bg-emerald-50 text-emerald-700",
    warning: "border-amber-200 bg-amber-50 text-amber-700",
    danger: "border-rose-200 bg-rose-50 text-rose-700",
    info: "border-sky-200 bg-sky-50 text-sky-700",
  };

  return (
    <span
      className={`inline-flex items-center rounded-full border px-3 py-1 text-xs font-semibold ${variantClasses[variant]} ${className || ""}`.trim()}
    >
      {children}
    </span>
  );
}

export interface StatusBadgeProps {
  status: "ACTIVE" | "PASSIVE" | "LOCKED" | string;
}

export function StatusBadge({ status }: StatusBadgeProps) {
  const statusStyles = {
    ACTIVE: { variant: "success" as const, label: "Aktif" },
    PASSIVE: { variant: "warning" as const, label: "Pasif" },
    LOCKED: { variant: "danger" as const, label: "Kilitli" },
  };

  const config = statusStyles[status as keyof typeof statusStyles] || {
    variant: "default" as const,
    label: status,
  };

  return <Badge variant={config.variant}>{config.label}</Badge>;
}

export interface RoleBadgeProps {
  role: "ADMIN" | "EMPLOYEE" | string;
}

export function RoleBadge({ role }: RoleBadgeProps) {
  const roleStyles = {
    ADMIN: { variant: "danger" as const, label: "Admin" },
    EMPLOYEE: { variant: "info" as const, label: "Çalışan" },
  };

  const config = roleStyles[role as keyof typeof roleStyles] || {
    variant: "default" as const,
    label: role,
  };

  return <Badge variant={config.variant}>{config.label}</Badge>;
}

export interface UserTypeBadgeProps {
  userType: "EMPLOYEE" | string;
}

export function UserTypeBadge({ userType }: UserTypeBadgeProps) {
  const typeStyles = {
    EMPLOYEE: { variant: "info" as const, label: "Çalışan" },
  };

  const config = typeStyles[userType as keyof typeof typeStyles] || {
    variant: "default" as const,
    label: userType,
  };

  return <Badge variant={config.variant}>{config.label}</Badge>;
}

