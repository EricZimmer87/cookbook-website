import { useFetch } from '../../api/useFetch';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import type { UserDTO } from '../../types/user';
import EditButton from '../../components/buttons/EditButton.tsx';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';

function UsersView() {
  const { data: users, loading, error } = useFetch<UserDTO[]>('/api/users');

  useErrorRedirect(error);

  const location = useLocation();
  const navigate = useNavigate();

  if (loading) return <p>Loading users...</p>;
  if (!users || users.length === 0) return <p>No users found.</p>;

  return (
    <div>
      <h1>All Users</h1>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.userId}>
              <td>{user.userId}</td>
              <td>
                <Link to={`/users/${user.userId}`}>{user.userName}</Link>
              </td>
              <td>{user.userEmail}</td>
              <td>{user.role.roleName}</td>
              <td>
                <EditButton
                  onClick={() =>
                    navigate(`/users/${user.userId}/edit`, { state: { from: location.pathname } })
                  }
                />
                <DeleteButton
                  onClick={() =>
                    navigate(`/users/${user.userId}/delete`, { state: { from: location.pathname } })
                  }
                />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default UsersView;
