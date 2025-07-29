import AddButton from '../../components/buttons/AddButton.tsx';
import { useFetch } from '../../api/useFetch.ts';
import type { CategoryDTO } from '../../types/category.ts';
import EditButton from '../../components/buttons/EditButton.tsx';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import { useNavigate } from 'react-router-dom';

function CategoriesView() {
  const { data: categories, loading: categoriesLoading, error } = useFetch<CategoryDTO[]>('/api/categories');
  const navigate = useNavigate();

  if (categoriesLoading) return <p>Loading...</p>;
  if (error) return <p>Error</p>;
  if (!categories || categories.length === 0) return <p>No categories found.</p>;

  return (
    <div>
      <h1>All Categories</h1>
      <AddButton onClick={() => navigate('/categories/new')} />
      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Recipe Count</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        {[...categories]
          .sort((a, b) => a.categoryName.localeCompare(b.categoryName))
          .map((category) => (
            <tr key={category.categoryId}>
              <td>{category.categoryId}</td>
              <td>{category.categoryName}</td>
              <td>{category.recipeCount}</td>
              <td>
                <EditButton onClick={() => navigate(`/categories/${category.categoryId}/edit`)} />
                <DeleteButton onClick={() => navigate(`/categories/${category.categoryId}/delete`)} />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

export default CategoriesView;