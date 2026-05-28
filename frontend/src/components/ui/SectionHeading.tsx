type Props = {
  description: string;
  title: string;
};

export function SectionHeading({ description, title }: Props) {
  return (
    <div className="space-y-1">
      <h2 className="text-xl font-semibold text-white">{title}</h2>
      <p className="text-subtle">{description}</p>
    </div>
  );
}
