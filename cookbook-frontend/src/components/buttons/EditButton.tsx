import Button from './Button';
import { FaEdit } from 'react-icons/fa';

type Props = {
  onClick?: () => void;
};

export default function EditButton({ onClick }: Props) {
  return (
    <Button onClick={onClick} className="button-blue">
      <FaEdit className="button-icon" />
      Edit
    </Button>
  );
}
