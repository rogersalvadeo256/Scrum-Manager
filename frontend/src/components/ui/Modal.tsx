import type { PropsWithChildren } from 'react';
import { X } from 'lucide-react';
import { Button } from './Button';
import { Card } from './Card';

type Props = PropsWithChildren<{
  description?: string;
  onClose: () => void;
  title: string;
}>;

export function Modal({ children, description, onClose, title }: Props) {
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-950/80 p-4 backdrop-blur-sm">
      <Card className="w-full max-w-2xl space-y-5">
        <div className="flex items-start justify-between gap-4">
          <div>
            <h2 className="text-xl font-semibold text-white">{title}</h2>
            {description ? <p className="mt-1 text-sm text-slate-400">{description}</p> : null}
          </div>
          <Button aria-label="Fechar modal" className="px-3 py-3" onClick={onClose} variant="ghost">
            <X className="size-4" />
          </Button>
        </div>
        {children}
      </Card>
    </div>
  );
}
