import { useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import type { ReviewDTO } from '../../types/review.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import toast from 'react-hot-toast';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import CancelButton from '../../components/buttons/CancelButton.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';

function ReviewDeleteView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { data: review, loading, error } = useFetch<ReviewDTO>(`/api/reviews/${id}`);
  useErrorRedirect(error);

  if (loading) return <p>Loading review...</p>;
  if (error || !review) return <p style={{ color: 'red' }}>Review not found.</p>;

  const handleDelete = async () => {
    try {
      await apiFetch(`/api/reviews/${id}`, 'DELETE');
      toast.success('Review deleted successfully.');
      navigate(-1);
    } catch (err) {
      console.error(err);
      alert('Failed to delete review.');
    }
  };

  return (
    <div>
      <h1>Confirm Delete</h1>
      <p>
        Are you sure you want to delete the <strong>{review.recipeName}</strong>'s review?
      </p>
      <div style={{ marginTop: '1rem' }}>
        <DeleteButton onClick={handleDelete} />
        <CancelButton />
      </div>
    </div>
  );
}

export default ReviewDeleteView;
