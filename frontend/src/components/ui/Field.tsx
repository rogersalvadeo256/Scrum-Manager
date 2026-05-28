import type { PropsWithChildren, ReactNode } from 'react';

type Props = PropsWithChildren<{
  error?: string;
  hint?: string;
  label: string;
  labelAction?: ReactNode;
}>;

export function Field({ children, error, hint, label, labelAction }: Props) {
  return (
    <label className="flex flex-col gap-2 text-left">
      <span className="flex items-center justify-between gap-3 text-sm font-medium text-slate-200">
        {label}
        {labelAction}
      </span>
      {children}
      {error ? <span className="text-xs text-rose-300">{error}</span> : null}
      {!error && hint ? <span className="text-xs text-slate-500">{hint}</span> : null}
    </label>
  );
}
