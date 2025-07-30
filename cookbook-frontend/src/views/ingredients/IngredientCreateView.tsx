import { useNavigate } from 'react-router-dom';
import type { IngredientDTO } from '../../types/ingredient.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import toast from 'react-hot-toast';
import IngredientForm from '../../components/forms/tag/IngredientForm.tsx';

function IngredientCreateView() {
  const navigate = useNavigate();

  const handleSave = async (data: Partial<IngredientDTO>) => {
    try {
      await apiFetch('/api/ingredients', 'POST', data);
      toast.success("Ingredient created successfully.");
      navigate("/ingredients");
    } catch {
      toast.error("Error creating ingredient");
    }
  };

  return (
    <div>
      <h1>Create Ingredient</h1>
      <IngredientForm onSubmit={handleSave}/>
    </div>
  )
}

export default IngredientCreateView;