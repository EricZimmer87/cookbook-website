import { useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import type { CategoryDTO } from '../../types/category.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import toast from 'react-hot-toast';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import CancelButton from '../../components/buttons/CancelButton.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';

function CategoryDeleteView() {
  const { recipeId } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const {
    data: category,
    loading,
    error: categoryError,
  } = useFetch<CategoryDTO>(`/api/categories/${recipeId}`);
  useErrorRedirect(categoryError);

  if (loading) return <p>Loading...</p>;
  if (categoryError) return <p>Error</p>;

  const handleDelete = async () => {
    try {
      await apiFetch(`/api/categories/${recipeId}`, 'DELETE');
      toast.success('Category deleted successfully.');
      navigate('/categories');
    } catch (err) {
      console.error(err);
      alert('Failed to delete category');
    }
  };

  return (
    <div>
      <h1>Confirm Delete</h1>
      <p>
        Are you sure you want to delete <strong>{category?.categoryName}</strong>
      </p>
      <div style={{ marginTop: '1rem' }}>
        <DeleteButton onClick={handleDelete} />
        <CancelButton />
      </div>
    </div>
  );
}

export default CategoryDeleteView;
