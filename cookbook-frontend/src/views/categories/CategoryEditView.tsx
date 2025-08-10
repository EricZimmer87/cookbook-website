import { useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import type { CategoryDTO } from '../../types/category.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import CategoryForm from '../../components/forms/category/CategoryForm.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';

function CategoryEditView() {
  const { recipeId } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const {
    data: category,
    loading: categoryLoading,
    error: categoryError,
  } = useFetch<CategoryDTO>(`/api/categories/${recipeId}`);
  useErrorRedirect(categoryError);

  if (categoryLoading) return <p>Loading...</p>;
  if (!category) return <p>Category not found.</p>;

  const handleSave = async (data: Partial<CategoryDTO>) => {
    await apiFetch(`/api/categories/${recipeId}`, 'PUT', data);
    navigate(`/categories`);
  };

  return (
    <div>
      <h1>Edit Category</h1>
      <CategoryForm defaultValues={category} onSubmit={handleSave} />
    </div>
  );
}

export default CategoryEditView;
