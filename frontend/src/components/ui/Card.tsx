import type { HTMLAttributes, PropsWithChildren } from 'react';
import { cn } from '../../lib/cn';

export function Card({ children, className, ...props }: PropsWithChildren<HTMLAttributes<HTMLDivElement>>) {
  return (
    <div className={cn('surface p-6 shadow-soft', className)} {...props}>
      {children}
    </div>
  );
}
