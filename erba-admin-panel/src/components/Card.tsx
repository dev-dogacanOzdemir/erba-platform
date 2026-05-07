import type { ReactNode } from "react";

export interface CardProps {
  children: ReactNode;
  className?: string;
  title?: string;
}

export function Card({ children, className, title }: CardProps) {
  return (
    <div className={`card ${className || ""}`.trim()}>
      {title && <h3 className="mb-5">{title}</h3>}
      {children}
    </div>
  );
}

