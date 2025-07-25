import React from 'react';

type ButtonProps = {
  onClick?: () => void;
  children: React.ReactNode;
  className?: string;
  type?: 'button' | 'submit' | 'reset';
};

export default function Button({
  onClick,
  children,
  className = '',
  type = 'button',
}: ButtonProps) {
  return (
    <button onClick={onClick} type={type} className={`button ${className}`}>
      {children}
    </button>
  );
}
