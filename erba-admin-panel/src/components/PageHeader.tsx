import type { ReactNode } from "react";

export interface PageContainerProps {
  children: ReactNode;
  className?: string;
}

export function PageContainer({ children, className }: PageContainerProps) {
  return (
    <div className={`page-container ${className || ""}`.trim()}>
      {children}
    </div>
  );
}

export interface PageHeaderProps {
  title: string;
  description?: string;
  action?: ReactNode;
}

export function PageHeader({
  title,
  description,
  action,
}: PageHeaderProps) {
  return (
    <div className="mb-8 flex flex-col gap-4 md:flex-row md:items-end md:justify-between">
      <div className="flex-1">
        <h1 className="mb-2">{title}</h1>
        {description && <p className="max-w-2xl text-slate-500">{description}</p>}
      </div>
      {action && <div className="flex shrink-0 items-center gap-3">{action}</div>}
    </div>
  );
}

