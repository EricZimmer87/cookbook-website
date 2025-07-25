import { useFetch } from '../../api/useFetch.ts';
import type { TagDTO } from '../../types/tag.ts';
import EditButton from '../../components/buttons/EditButton.tsx';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import { useNavigate } from 'react-router-dom';
import AddButton from '../../components/buttons/AddButton.tsx';

function TagsView() {
  const { data: tags, loading: tagsLoading, error } = useFetch<TagDTO[]>('/api/tags');
  const navigate = useNavigate();

  if (tagsLoading) return <p>Loading tags...</p>;
  if (error) return <p>Error loading tags...</p>;
  if (!tags || tags.length === 0) return <p>No tags found.</p>;

  return (
    <div>
      <h1>All Tags</h1>
      <AddButton onClick={() => navigate('/tags/new')} />
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {[...tags]
            .sort((a, b) => a.tagName.localeCompare(b.tagName))
            .map((tag) => (
              <tr key={tag.tagId}>
                <td>{tag.tagId}</td>
                <td>{tag.tagName}</td>
                <td>
                  <EditButton onClick={() => navigate(`/tags/${tag.tagId}/edit`)} />
                  <DeleteButton onClick={() => navigate(`/tags/${tag.tagId}/delete`)} />
                </td>
              </tr>
            ))}
        </tbody>
      </table>
    </div>
  );
}

export default TagsView;
