import Button from './Button';
import { FaSave } from 'react-icons/fa';
import React from 'react';

type SaveButtonProps = React.ComponentProps<typeof Button> & {
  isSaving?: boolean;
};

export default function SaveButton({
  type = 'button',
  isSaving = false,
  children,
  ...rest
}: SaveButtonProps) {
  return (
    <Button type={type} className="button-green" disabled={isSaving} {...rest}>
      <FaSave className="button-icon" />
      {children ?? (isSaving ? 'Saving...' : 'Save')}
    </Button>
  );
}
