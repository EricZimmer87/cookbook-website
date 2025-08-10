import { useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import type { DifficultyLevelDTO } from '../../types/difficulty-level.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import DifficultyLevelForm from '../../components/forms/difficulty-level/DifficultyLevelForm.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';

function DifficultyLevelEditView() {
  const { recipeId } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const {
    data: difficultyLevel,
    loading: difficultyLevelLoading,
    error: difficultyLevelError,
  } = useFetch<DifficultyLevelDTO>(`/api/difficulty-levels/${recipeId}`);
  useErrorRedirect(difficultyLevelError);

  if (difficultyLevelLoading) return <p>Loading...</p>;
  if (!difficultyLevel) return <p>Difficulty level not found.</p>;

  const handleSave = async (data: Partial<DifficultyLevelDTO>) => {
    await apiFetch(`/api/difficulty-levels/${recipeId}`, 'PUT', data);
    navigate('/difficulty-levels');
  };

  return (
    <div>
      <h1>Edit Difficulty Level</h1>
      <DifficultyLevelForm defaultValues={difficultyLevel} onSubmit={handleSave} />
    </div>
  );
}

export default DifficultyLevelEditView;
