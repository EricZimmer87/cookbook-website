import { useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import type { DifficultyLevelDTO } from '../../types/difficulty-level.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import toast from 'react-hot-toast';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import CancelButton from '../../components/buttons/CancelButton.tsx';

function DifficultyLevelDeleteView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const {
    data: difficultyLevel,
    loading: difficultyLevelLoading,
    error
  } = useFetch<DifficultyLevelDTO>(`/api/difficulty-levels/${id}`);

  if (difficultyLevelLoading) return <p>Loading...</p>;
  if (error) return <p>Error</p>;

  const handleDelete = async () => {
    try {
      await apiFetch(`/api/difficulty-levels/${id}`, 'DELETE');
      toast.success('Difficulty level deleted successfully.');
      navigate('/difficulty-levels');
    } catch (err) {
      console.error(err);
      alert('Failed to delete difficulty level.');
    }
  };

  return (
    <div>
      <h1>Confirm Delete</h1>
      <p>
        Are you sure you want to delete <strong>{difficultyLevel?.difficultyLevelName}</strong>
      </p>
      <div style={{marginTop: '1rem'}}>
        <DeleteButton onClick={handleDelete} />
        <CancelButton />
      </div>
    </div>
  )
}

export default DifficultyLevelDeleteView;