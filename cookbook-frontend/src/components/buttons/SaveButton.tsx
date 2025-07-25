import Button from './Button';
import { FaSave } from 'react-icons/fa';

type Props = {
  type?: 'button' | 'submit' | 'reset';
};

export default function SaveButton({ type = 'button' }: Props) {
  return (
    <Button type={type} className="button-green">
      <FaSave className="button-icon" />
      Save
    </Button>
  );
}
