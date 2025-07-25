import { useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import type { TagDTO } from '../../types/tag.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import toast from 'react-hot-toast';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import CancelButton from '../../components/buttons/CancelButton.tsx';

function TagDeleteView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { data: tag, loading, error } = useFetch<TagDTO>(`/api/tags/${id}`);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error</p>;

  const handleDelete = async () => {
    try {
      await apiFetch(`/api/tags/${id}`, 'DELETE');
      toast.success('Tag deleted successfully.');
      navigate('/tags');
    } catch (err) {
      console.error(err);
      alert('Failed to delete tag');
    }
  };

  return (
    <div>
      <h1>Confirm Delete</h1>
      <p>
        Are you sure you want to delete <strong>{tag?.tagName}</strong>
      </p>
      <div style={{ marginTop: '1rem' }}>
        <DeleteButton onClick={handleDelete} />
        <CancelButton />
      </div>
    </div>
  );
}

export default TagDeleteView;
