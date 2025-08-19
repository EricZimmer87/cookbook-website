import { useFetch } from '../../api/useFetch.ts';
import type { TagDTO } from '../../types/tag.ts';
import EditButton from '../../components/buttons/EditButton.tsx';
import DeleteButton from '../../components/buttons/DeleteButton.tsx';
import { useLocation, useNavigate } from 'react-router-dom';
import AddButton from '../../components/buttons/AddButton.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';

function TagsView() {
  const { data: tags, loading: tagsLoading, error } = useFetch<TagDTO[]>('/api/tags');

  useErrorRedirect(error);

  const navigate = useNavigate();
  const location = useLocation();

  if (tagsLoading) return <p>Loading tags...</p>;
  if (!tags || tags.length === 0) return <p>No tags found.</p>;

  return (
    <div>
      <h1>All Tags</h1>
      <AddButton onClick={() => navigate('/tags/new', { state: { from: location.pathname } })} />
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
            {[...tags]
              .sort((a, b) => a.tagName.localeCompare(b.tagName))
              .map((tag) => (
                <tr key={tag.tagId}>
                  <td>{tag.tagId}</td>
                  <td>{tag.tagName}</td>
                  <td>
                    <EditButton
                      onClick={() =>
                        navigate(`/tags/${tag.tagId}/edit`, { state: { from: location.pathname } })
                      }
                    />
                    <DeleteButton
                      onClick={() =>
                        navigate(`/tags/${tag.tagId}/delete`, {
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

export default TagsView;
