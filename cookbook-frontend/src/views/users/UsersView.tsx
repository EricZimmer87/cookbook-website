import { useEffect, useState } from 'react';
import { useFetch } from '../../api/useFetch';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import type { UserDTO } from '../../types/user';
import EditButton from '../../components/buttons/EditButton.tsx';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';
import { ApiError, apiFetch } from '../../api/apiFetch.ts';

function UsersView() {
  const { data, loading, error } = useFetch<UserDTO[]>('/api/users');
  const [rows, setRows] = useState<UserDTO[]>([]);
  useEffect(() => {
    if (data) setRows(data);
  }, [data]);

  useErrorRedirect(error);

  const location = useLocation();
  const navigate = useNavigate();

  if (loading) return <p>Loading users...</p>;
  if (!rows || rows.length === 0) return <p>No users found.</p>;

  const toggleBan = async (user: UserDTO) => {
    const path = `/api/users/${user.userId}/${user.banned ? 'unban' : 'ban'}`;
    try {
      await apiFetch<null>(path, 'PUT'); // no body
      setRows((prev) =>
        prev.map((u) => (u.userId === user.userId ? { ...u, banned: !user.banned } : u)),
      );
    } catch (e: unknown) {
      const err = e as ApiError;
      // swap alert for your toast UI if you have one
      alert(err.body || `Failed to ${user.banned ? 'unban' : 'ban'} user (status ${err.status})`);
    }
  };

  return (
    <div>
      <h1>All Users</h1>
      <div className="table-container">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Username</th>
              <th>Email</th>
              <th>Role</th>
              <th>Is Banned?</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {rows.map((user) => (
              <tr key={user.userId}>
                <td>{user.userId}</td>
                <td>
                  <Link to={`/users/${user.userId}`}>{user.userName}</Link>
                </td>
                <td>{user.userEmail}</td>
                <td>{user.role.roleName}</td>
                <td>{user.banned ? 'Yes' : 'No'}</td>
                <td style={{ display: 'flex', gap: 8 }}>
                  <button
                    className="button button-gray"
                    onClick={() => toggleBan(user)}
                    title={user.banned ? 'Unban user' : 'Ban user'}
                  >
                    {user.banned ? 'Unban' : 'Ban'}
                  </button>

                  <EditButton
                    onClick={() =>
                      navigate(`/users/${user.userId}/edit`, { state: { from: location.pathname } })
                    }
                  />
                  <DeleteButton
                    onClick={() =>
                      navigate(`/users/${user.userId}/delete`, {
                        state: { from: location.pathname },
                      })
                    }
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default UsersView;
