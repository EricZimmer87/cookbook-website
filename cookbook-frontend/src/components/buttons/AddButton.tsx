import Button from './Button.tsx';
import { FaPlus } from 'react-icons/fa';
import React from 'react';

type Props = {
  type?: 'button' | 'submit' | 'reset';
  onClick?: () => void;
  children?: React.ReactNode;
  className?: string;
};

export default function AddButton({ type = 'button', onClick, children, className }: Props) {
  return (
    <Button type={type} onClick={onClick} className={`button-green ${className || ''}`.trim()}>
      <FaPlus className="button-icon" />
      {children || 'Add'}
    </Button>
  );
}
