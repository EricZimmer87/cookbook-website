import { useNavigate } from 'react-router-dom';
import type { DifficultyLevelDTO } from '../../types/difficulty-level.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import toast from 'react-hot-toast';
import DifficultyLevelForm from '../../components/forms/difficulty-level/DifficultyLevelForm.tsx';

function DifficultyLevelCreateView() {
  const navigate = useNavigate();

  const handleSave = async (data: Partial<DifficultyLevelDTO>)=> {
    try{
      await apiFetch(`/api/difficulty-levels`, 'POST', data);
      toast.success('Difficulty level created successfully');
      navigate('/difficulty-levels');
    } catch {
      toast.error('Error creating difficulty level');
    }
  };

  return (
    <div>
      <h1>Create Difficulty Level</h1>
      <DifficultyLevelForm onSubmit={handleSave} />
    </div>
  )
}

export default DifficultyLevelCreateView;