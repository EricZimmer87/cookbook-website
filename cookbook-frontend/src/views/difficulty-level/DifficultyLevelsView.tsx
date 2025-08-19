import { useFetch } from '../../api/useFetch.ts';
import type { DifficultyLevelDTO } from '../../types/difficulty-level.ts';
import { useLocation, useNavigate } from 'react-router-dom';
import AddButton from '../../components/buttons/AddButton.tsx';
import EditButton from '../../components/buttons/EditButton.tsx';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';

function DifficultyLevelsView() {
  const {
    data: difficultyLevels,
    loading: difficultyLevelsLoading,
    error: difficultyLevelsError,
  } = useFetch<DifficultyLevelDTO[]>('/api/difficulty-levels');
  useErrorRedirect(difficultyLevelsError);

  const navigate = useNavigate();
  const location = useLocation();

  if (difficultyLevelsLoading) return <p>Loading...</p>;
  if (difficultyLevelsError) return <p>Error</p>;
  if (!difficultyLevels || difficultyLevels.length === 0) return <p>No difficulty levels found.</p>;

  return (
    <div>
      <h1>All Difficulty Levels</h1>
      <AddButton
        onClick={() => navigate('/difficulty-levels/new', { state: { from: location.pathname } })}
      />
      <div className="table-container">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {[...difficultyLevels].map((difficultyLevel) => (
              <tr key={difficultyLevel.difficultyLevelId}>
                <td>{difficultyLevel.difficultyLevelId}</td>
                <td>{difficultyLevel.difficultyLevelName}</td>
                <td>
                  <EditButton
                    onClick={() =>
                      navigate(`/difficulty-levels/${difficultyLevel.difficultyLevelId}/edit`, {
                        state: { from: location.pathname },
                      })
                    }
                  />
                  <DeleteButton
                    onClick={() =>
                      navigate(`/difficulty-levels/${difficultyLevel.difficultyLevelId}/delete`, {
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

export default DifficultyLevelsView;
