import { useNavigate, useParams } from 'react-router-dom';
import { apiFetch } from '../../api/apiFetch';
import { useFetch } from '../../api/useFetch';
import type { UserDTO } from '../../types/user';
import toast from 'react-hot-toast';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import CancelButton from '../../components/buttons/CancelButton.tsx';

function UserDeleteView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { data: user, loading, error } = useFetch<UserDTO>(`/api/users/${id}`);

  if (loading) return <p>Loading user...</p>;
  if (error || !user) return <p style={{ color: 'red' }}>User not found.</p>;

  const handleDelete = async () => {
    try {
      await apiFetch(`/api/users/${id}`, 'DELETE');
      toast.success('User deleted successfully!');
      navigate('/users'); // go back to users list
    } catch (err) {
      console.error(err);
      alert('Failed to delete user.');
    }
  };

  return (
    <div>
      <h1>Confirm Delete</h1>
      <p>
        Are you sure you want to delete <strong>{user.userName}</strong>?
      </p>
      <div style={{ marginTop: '1rem' }}>
        <DeleteButton onClick={handleDelete} />
        <CancelButton />
      </div>
    </div>
  );
}

export default UserDeleteView;
