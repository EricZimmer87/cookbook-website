import Button from './Button.tsx';
import { FaPlus } from 'react-icons/fa';
import React from 'react';

type Props = {
  type?: 'button' | 'submit' | 'reset';
  onClick?: () => void;
  children?: React.ReactNode;
};

export default function AddButton({ type = 'button', onClick, children }: Props) {
  return (
    <Button type={type} onClick={onClick} className="button-green">
      <FaPlus className="button-icon" />
      {children || 'Add'}
    </Button>
  );
}
