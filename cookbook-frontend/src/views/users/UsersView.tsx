import { useFetch } from '../../api/useFetch';
import { Link, useNavigate } from 'react-router-dom';
import type { UserDTO } from '../../types/user';
import EditButton from '../../components/buttons/EditButton.tsx';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';

function UsersView() {
  const navigate = useNavigate();
  const { data: users, loading, error } = useFetch<UserDTO[]>('/api/users');

  if (loading) return <p>Loading users...</p>;
  if (error) return <p style={{ color: 'red' }}>Error: {error}</p>;
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
                <EditButton onClick={() => navigate(`/users/${user.userId}/edit`)} />
                <DeleteButton onClick={() => navigate(`/users/${user.userId}/delete`)} />
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default UsersView;
