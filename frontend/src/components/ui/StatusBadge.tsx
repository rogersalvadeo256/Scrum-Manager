import type { RequestStatus, SprintStatus, TaskStatus } from '../../types/api';
import { Badge } from './Badge';

type SupportedStatus = RequestStatus | SprintStatus | TaskStatus;

const statusMap: Record<SupportedStatus, { label: string; tone: 'brand' | 'success' | 'warning' | 'danger' }> = {
  ACCEPTED: { label: 'Aceito', tone: 'success' },
  DOING: { label: 'Em andamento', tone: 'brand' },
  DONE: { label: 'Concluído', tone: 'success' },
  ON_HOLD: { label: 'Pendente', tone: 'warning' },
  REFUSED: { label: 'Recusado', tone: 'danger' },
  REMOVED: { label: 'Removido', tone: 'danger' },
  TO_DO: { label: 'A fazer', tone: 'warning' },
};

export function StatusBadge({ status }: { status: SupportedStatus }) {
  const entry = statusMap[status];

  return <Badge tone={entry.tone}>{entry.label}</Badge>;
}
