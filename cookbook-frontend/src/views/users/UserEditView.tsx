import { useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch';
import { apiFetch } from '../../api/apiFetch'; // POST/PUT helper
import type { RoleDTO, UserDTO } from '../../types/user';
import UserForm from '../../components/forms/user/UserForm';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';

function UserEditView() {
  const { recipeId } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const {
    data: user,
    loading: userLoading,
    error: userError,
  } = useFetch<UserDTO>(`/api/users/${recipeId}`);
  useErrorRedirect(userError);

  const {
    data: roles,
    loading: rolesLoading,
    error: rolesError,
  } = useFetch<RoleDTO[]>('/api/roles');
  useErrorRedirect(rolesError);

  if (userLoading || rolesLoading) return <p>Loading...</p>;
  if (!user || !roles) return <p>User or roles not found.</p>;

  const handleSave = async (data: Partial<UserDTO>) => {
    await apiFetch(`/api/users/${recipeId}`, 'PUT', data);
    navigate(-1);
  };

  return (
    <div>
      <h1>Edit User</h1>
      <UserForm defaultValues={user} onSubmit={handleSave} roles={roles} />
    </div>
  );
}

export default UserEditView;
