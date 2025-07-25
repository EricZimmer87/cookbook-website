import { useNavigate, useParams } from 'react-router-dom';
import { useFetch } from '../../api/useFetch.ts';
import type { TagDTO } from '../../types/tag.ts';
import { apiFetch } from '../../api/apiFetch.ts';
import TagForm from '../../components/forms/tag/TagForm.tsx';

function TagEditView() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const { data: tag, loading: tagLoading } = useFetch<TagDTO>(`/api/tags/${id}`);

  if (tagLoading) return <p>Loading...</p>;
  if (!tag) return <p>Tag not found.</p>;

  const handleSave = async (data: Partial<TagDTO>) => {
    await apiFetch(`/api/tags/${id}`, 'PUT', data);
    navigate('/tags');
  };

  return (
    <div>
      <h1>Edit Tag</h1>
      <TagForm defaultValues={tag} onSubmit={handleSave} />
    </div>
  );
}

export default TagEditView;
