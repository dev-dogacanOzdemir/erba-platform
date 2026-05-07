import type { ReactNode } from "react";

export interface FormFieldProps
  extends React.InputHTMLAttributes<HTMLInputElement | HTMLSelectElement> {
  label?: string;
  error?: string;
  hint?: string;
  children?: ReactNode;
  isSelect?: boolean;
}

export function FormField({
  label,
  error,
  hint,
  children,
  isSelect,
  className,
  ...props
}: FormFieldProps) {
  return (
    <div className="form-group">
      {label && <label className="form-label">{label}</label>}
      {isSelect ? (
        <select className={`select ${className || ""}`} {...(props as any)}>
          {children}
        </select>
      ) : (
        <input className={`input ${className || ""}`} {...(props as any)} />
      )}
      {error && <p className="form-error">{error}</p>}
      {hint && !error && <p className="form-hint">{hint}</p>}
    </div>
  );
}

export interface FormSectionProps {
  title: string;
  children: ReactNode;
}

export function FormSection({ title, children }: FormSectionProps) {
  return (
    <div className="mb-6">
      <h4 className="mb-4">{title}</h4>
      <div className="space-y-4">{children}</div>
    </div>
  );
}

