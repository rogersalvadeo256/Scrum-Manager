import type { ReactNode } from 'react';

type Props = {
  action?: ReactNode;
  description: string;
  title: string;
};

export function EmptyState({ action, description, title }: Props) {
  return (
    <div className="surface-muted flex h-full flex-col items-start gap-3 p-5 text-left">
      <p className="text-base font-semibold text-white">{title}</p>
      <p className="text-subtle">{description}</p>
      {action}
    </div>
  );
}
