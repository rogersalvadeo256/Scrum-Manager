import { cn } from '../../lib/cn';

type Props = {
  children: string;
  tone?: 'brand' | 'success' | 'warning' | 'danger' | 'neutral';
};

const tones = {
  brand: 'bg-brand-500/15 text-brand-200 border-brand-400/30',
  success: 'bg-emerald-500/15 text-emerald-200 border-emerald-400/30',
  warning: 'bg-amber-500/15 text-amber-100 border-amber-400/30',
  danger: 'bg-rose-500/15 text-rose-100 border-rose-400/30',
  neutral: 'bg-white/10 text-slate-200 border-white/10',
};

export function Badge({ children, tone = 'neutral' }: Props) {
  return (
    <span
      className={cn(
        'inline-flex items-center rounded-full border px-3 py-1 text-xs font-medium uppercase tracking-[0.2em]',
        tones[tone],
      )}
    >
      {children}
    </span>
  );
}
