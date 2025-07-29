import { useNavigate } from 'react-router-dom';
import { apiFetch } from '../../api/apiFetch.ts';
import type { CategoryDTO } from '../../types/category.ts';
import CategoryForm from '../../components/forms/category/CategoryForm.tsx';
import toast from 'react-hot-toast';

function CategoryCreateView() {
  const navigate = useNavigate();

  const handleSave = async (data: Partial<CategoryDTO>) => {
    try {
      await apiFetch('/api/categories', 'POST', data);
      toast.success('Category created!');
      navigate('/categories');
    } catch {
      toast.error('Failed to create category');
    }
  };

  return (
    <div>
      <h1>Create Category</h1>
      <CategoryForm onSubmit={handleSave} />
    </div>
  )
}

export default CategoryCreateView;