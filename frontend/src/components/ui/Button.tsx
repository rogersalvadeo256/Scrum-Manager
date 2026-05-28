import type { ButtonHTMLAttributes, PropsWithChildren } from 'react';
import { LoaderCircle } from 'lucide-react';
import { cn } from '../../lib/cn';

type ButtonVariant = 'primary' | 'secondary' | 'ghost' | 'danger';

type Props = PropsWithChildren<
  ButtonHTMLAttributes<HTMLButtonElement> & {
    isLoading?: boolean;
    variant?: ButtonVariant;
  }
>;

const variants: Record<ButtonVariant, string> = {
  primary:
    'bg-brand-500 text-white shadow-lg shadow-brand-500/20 hover:bg-brand-400 disabled:bg-brand-500/50',
  secondary:
    'border border-white/10 bg-white/5 text-slate-100 hover:border-brand-400/60 hover:bg-brand-500/10',
  ghost: 'text-slate-300 hover:bg-white/5 hover:text-white',
  danger: 'bg-rose-500/90 text-white hover:bg-rose-400 disabled:bg-rose-500/50',
};

export function Button({
  children,
  className,
  disabled,
  isLoading = false,
  type = 'button',
  variant = 'primary',
  ...props
}: Props) {
  return (
    <button
      className={cn(
        'inline-flex items-center justify-center gap-2 rounded-2xl px-4 py-2.5 text-sm font-semibold transition focus:outline-none focus:ring-2 focus:ring-brand-400/70 disabled:cursor-not-allowed',
        variants[variant],
        className,
      )}
      disabled={disabled || isLoading}
      type={type}
      {...props}
    >
      {isLoading ? <LoaderCircle className="size-4 animate-spin" /> : null}
      {children}
    </button>
  );
}
