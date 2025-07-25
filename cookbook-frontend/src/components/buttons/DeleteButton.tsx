import Button from './Button';
import { FaTrash } from 'react-icons/fa';

type Props = {
  onClick: () => void;
};

export default function DeleteButton({ onClick }: Props) {
  return (
    <Button onClick={onClick} className="button-red">
      <FaTrash className="button-icon" />
      Delete
    </Button>
  );
}
