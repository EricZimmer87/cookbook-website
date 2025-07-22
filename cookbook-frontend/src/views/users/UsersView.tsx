import { useFetch } from '../../api/useFetch';
import type { UserDTO } from '../../types/user';

function UsersView() {
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
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.userId}>
              <td>{user.userId}</td>
              <td>{user.userName}</td>
              <td>{user.email}</td>
              <td>{user.role.roleName}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default UsersView;
