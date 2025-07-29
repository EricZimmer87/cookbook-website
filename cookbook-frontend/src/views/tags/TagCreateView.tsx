import { useNavigate } from 'react-router-dom';
import { apiFetch } from '../../api/apiFetch.ts';
import type { TagDTO } from '../../types/tag.ts';
import TagForm from '../../components/forms/tag/TagForm.tsx';
import toast from 'react-hot-toast';

function TagCreateView() {
  const navigate = useNavigate();

  const handleSave = async (data: Partial<TagDTO>) => {
    try {
      await apiFetch('/api/tags', 'POST', data);
      toast.success('Tag created successfully.');
      navigate('/tags');
    } catch {
      toast.error('Error creating tag.');
    }
  };

  return (
    <div>
      <h1>Create Tag</h1>
      <TagForm onSubmit={handleSave} />
    </div>
  );
}

export default TagCreateView;
