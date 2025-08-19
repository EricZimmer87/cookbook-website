import React from 'react';

type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
  className?: string;
};

export default function Button({ children, className = '', ...props }: ButtonProps) {
  return (
    <button {...props} className={`button ${className}`}>
      {children}
    </button>
  );
}
