import { useNavigate } from 'react-router-dom';
import Button from './Button.tsx';
import { FaXmark } from 'react-icons/fa6';

type Props = {
  type?: 'button' | 'submit' | 'reset';
  onClick?: () => void;
};

export default function CancelButton({ type = 'button', onClick }: Props) {
  const navigate = useNavigate();

  const handleClick = () => {
    if (onClick) {
      onClick();
    } else {
      navigate(-1);
    }
  };

  return (
    <Button type={type} onClick={handleClick} className="button-gray">
      <FaXmark className="button-icon" />
      Cancel
    </Button>
  );
}
