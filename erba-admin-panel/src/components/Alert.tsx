import { AlertCircle, CheckCircle, XCircle, Info } from "lucide-react";

export interface AlertProps {
  type?: "success" | "error" | "warning" | "info";
  message: string;
  onClose?: () => void;
}

export function Alert({ type = "info", message, onClose }: AlertProps) {
  const styles = {
    success: {
      bg: "bg-emerald-50 border-emerald-200",
      text: "text-emerald-800",
      icon: <CheckCircle className="h-5 w-5" />,
      iconColor: "text-emerald-600",
    },
    error: {
      bg: "bg-rose-50 border-rose-200",
      text: "text-rose-800",
      icon: <XCircle className="h-5 w-5" />,
      iconColor: "text-rose-600",
    },
    warning: {
      bg: "bg-amber-50 border-amber-200",
      text: "text-amber-800",
      icon: <AlertCircle className="h-5 w-5" />,
      iconColor: "text-amber-600",
    },
    info: {
      bg: "bg-sky-50 border-sky-200",
      text: "text-sky-800",
      icon: <Info className="h-5 w-5" />,
      iconColor: "text-sky-600",
    },
  };

  const style = styles[type];

  return (
    <div
      className={`flex items-center gap-3 rounded-2xl border p-4 shadow-sm ${style.bg} ${style.text}`}
      role="alert"
    >
      <div className={style.iconColor}>{style.icon}</div>
      <p className="flex-1">{message}</p>
      {onClose && (
        <button
          onClick={onClose}
          className="ml-2 rounded-full px-2 py-1 text-current opacity-60 transition hover:bg-white/50 hover:opacity-100"
          aria-label="Close"
        >
          ✕
        </button>
      )}
    </div>
  );
}

