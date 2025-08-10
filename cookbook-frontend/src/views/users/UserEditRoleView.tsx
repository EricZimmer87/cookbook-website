import RoleForm from '../../components/forms/role/RoleForm';
import type { RoleDTO, UserDTO } from '../../types/user';
import { useFetch } from '../../api/useFetch';
import { useErrorRedirect } from '../../hooks/useErrorRedirect';
import { apiFetch } from '../../api/apiFetch';
import { useNavigate, useParams } from 'react-router-dom';

type RoleFormData = { roleId: number };

function UserEditRoleView() {
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
  if (!user || !roles) return <p>Not found.</p>;

  const handleSave = async (data: RoleFormData) => {
    await apiFetch(`/api/users/${recipeId}/role`, 'PUT', data); // payload = { roleId: number }
    navigate(-1);
  };

  return (
    <div>
      <h1>Edit Role for {user.userName}</h1>
      <RoleForm defaultValues={{ roleId: user.role.roleId }} roles={roles} onSubmit={handleSave} />
    </div>
  );
}

export default UserEditRoleView;
