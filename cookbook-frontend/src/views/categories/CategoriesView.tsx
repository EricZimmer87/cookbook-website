import AddButton from '../../components/buttons/AddButton.tsx';
import { useFetch } from '../../api/useFetch.ts';
import type { CategoryDTO } from '../../types/category.ts';
import EditButton from '../../components/buttons/EditButton.tsx';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import { useNavigate, useLocation } from 'react-router-dom';

function CategoriesView() {
  const {
    data: categories,
    loading: categoriesLoading,
    error,
  } = useFetch<CategoryDTO[]>('/api/categories');

  const navigate = useNavigate();
  const location = useLocation();

  if (categoriesLoading) return <p>Loading...</p>;
  if (error) return <p>Error</p>;
  if (!categories || categories.length === 0) return <p>No categories found.</p>;

  return (
    <div>
      <h1>All Categories</h1>
      <AddButton onClick={() => navigate('/categories/new', { state: { from: location.pathname } })} />
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
            .sort((a, b) => {
              const nameA = a.categoryName || '';
              const nameB = b.categoryName || '';
              return nameA.localeCompare(nameB);
            })
            .map((category) => (
              <tr key={category.categoryId}>
                <td>{category.categoryId}</td>
                <td>{category.categoryName}</td>
                <td>{category.recipeCount}</td>
                <td>
                  <EditButton onClick={() => navigate(`/categories/${category.categoryId}/edit`, { state: { from: location.pathname } })} />
                  <DeleteButton
                    onClick={() => navigate(`/categories/${category.categoryId}/delete`, { state: { from: location.pathname } })}
                  />
                </td>
              </tr>
            ))}
        </tbody>
      </table>
    </div>
  );
}

export default CategoriesView;
