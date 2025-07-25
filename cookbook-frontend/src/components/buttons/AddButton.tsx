import Button from './Button.tsx';
import { FaPlus } from 'react-icons/fa';

type Props = {
  type?: 'button' | 'submit' | 'reset';
  onClick?: () => void;
};

export default function AddButton({ type = 'button', onClick }: Props) {
  return (
    <Button type={type} onClick={onClick} className="button-green">
      <FaPlus className="button-icon" />
      Add
    </Button>
  );
}
