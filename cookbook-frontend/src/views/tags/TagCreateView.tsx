import { useNavigate } from 'react-router-dom';
import { apiFetch } from '../../api/apiFetch.ts';
import type { TagDTO } from '../../types/tag.ts';
import TagForm from '../../components/forms/tag/TagForm.tsx';

function TagCreateView() {
  const navigate = useNavigate();

  const handleSave = async (data: Partial<TagDTO>) => {
    await apiFetch('/api/tags', 'POST', data);
    navigate('/tags');
  };

  return (
    <div>
      <h1>Create Tag</h1>
      <TagForm onSubmit={handleSave} />
    </div>
  );
}

export default TagCreateView;
