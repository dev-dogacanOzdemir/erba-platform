import type { ReactNode } from "react";

export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: "primary" | "secondary" | "danger";
  size?: "sm" | "md" | "lg";
  children: ReactNode;
  isLoading?: boolean;
}

export function Button({
  variant = "primary",
  size = "md",
  children,
  isLoading,
  disabled,
  className,
  ...props
}: ButtonProps) {
  const variantClasses = {
    primary: "btn-primary",
    secondary: "btn-secondary",
    danger: "btn-danger",
  };

  const sizeClasses = {
    sm: "btn-sm",
    md: "btn",
    lg: "btn px-6 py-3 text-base",
  };

  return (
    <button
      {...props}
      disabled={disabled || isLoading}
      className={`${sizeClasses[size]} ${variantClasses[variant]} ${className || ""}`.trim()}
    >
      {isLoading ? "..." : children}
    </button>
  );
}

