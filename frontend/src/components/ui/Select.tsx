import type { SelectHTMLAttributes } from 'react';
import { cn } from '../../lib/cn';

export function Select({ className, ...props }: SelectHTMLAttributes<HTMLSelectElement>) {
  return (
    <select
      className={cn(
        'w-full rounded-2xl border border-white/10 bg-slate-900/70 px-4 py-3 text-sm text-slate-100 outline-none transition focus:border-brand-400 focus:ring-2 focus:ring-brand-400/20',
        className,
      )}
      {...props}
    />
  );
}
