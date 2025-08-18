import { useParams } from 'react-router-dom';
import { useEffect, useMemo, useState } from 'react';
import { useFetch } from '../../api/useFetch';
import { apiFetch } from '../../api/apiFetch';
import RecipeCard from '../../components/recipe-card';
import type { RecipeDTO } from '../../types/recipe';
import type { ReviewDTO } from '../../types/review';
import Reviews from '../../components/review/Reviews';
import type { UserNotesDTO } from '../../types/user-notes';
import UserNotes from '../../components/user-note/UserNotes';
import { useAuth } from '../../context/useAuth';
import { useErrorRedirect } from '../../hooks/useErrorRedirect';
import Button from '../../components/buttons/Button';
import { FaHeart } from 'react-icons/fa';
import AddButton from '../../components/buttons/AddButton';
import AddUserNote from '../../components/user-note/AddUserNote';
import type { UserDTO } from '../../types/user';

function RecipeDetailView() {
  const { recipeId } = useParams<{ recipeId: string }>();
  const recipeIdNum = useMemo(() => {
    const n = Number(recipeId);
    if (Number.isNaN(n)) throw new Error('Invalid recipe Id');
    return n;
  }, [recipeId]);

  const { user: authUser } = useAuth();
  const isPrivileged = ['admin', 'contributor'].includes(
    authUser?.role?.roleName?.toLowerCase() || '',
  );

  const [optimisticFavorited, setOptimisticFavorited] = useState<boolean | null>(null);
  const [note, setNote] = useState<UserNotesDTO | null>(null);
  const [selectedUserId, setSelectedUserId] = useState<number | null>(authUser?.userId ?? null);

  // recipe
  const {
    data: recipe,
    loading: recipeLoading,
    error: recipeError,
  } = useFetch<RecipeDTO>(`/api/recipes/${recipeIdNum}`);
  useErrorRedirect(recipeError);

  // reviews
  const {
    data: reviews,
    loading: reviewsLoading,
    error: reviewsError,
  } = useFetch<ReviewDTO[]>(`/api/reviews/recipe/${recipeIdNum}`);
  useErrorRedirect(reviewsError);

  // candidates (admins + contributors)
  const {
    data: candidates,
    loading: candidatesLoading,
    error: candidatesError,
  } = useFetch<UserDTO[]>(isPrivileged ? `/api/users/by-roles?roles=ADMIN,CONTRIBUTOR` : null);
  useErrorRedirect(candidatesError);

  // favorite flag
  const {
    data: isFavorited,
    loading: favoriteLoading,
    error: favoriteError,
  } = useFetch<boolean>(
    authUser ? `/api/user-favorites/user/${authUser.userId}/recipe/${recipeIdNum}` : null,
  );
  useErrorRedirect(favoriteError);

  // fetch the selected user's note whenever selection or recipe changes
  const noteUrl =
    selectedUserId != null ? `/api/user-notes/recipe/${recipeIdNum}/user/${selectedUserId}` : null;

  const {
    data: fetchedNote,
    loading: noteLoading,
    error: noteError,
  } = useFetch<UserNotesDTO>(noteUrl);
  useErrorRedirect(noteError);

  useEffect(() => {
    setNote(fetchedNote ?? null);
  }, [fetchedNote]);

  const actuallyFavorited = optimisticFavorited !== null ? optimisticFavorited : isFavorited;

  const handleAddToFavorites = async () => {
    try {
      setOptimisticFavorited(true);
      await apiFetch('/api/user-favorites', 'POST', {
        userId: authUser?.userId,
        recipeId: recipeIdNum,
      });
    } catch (e) {
      setOptimisticFavorited(false);
      console.error(e);
    }
  };

  const handleRemoveFromFavorites = async () => {
    try {
      setOptimisticFavorited(false);
      await apiFetch(
        `/api/user-favorites/user/${authUser?.userId}/recipe/${recipeIdNum}`,
        'DELETE',
      );
    } catch (e) {
      setOptimisticFavorited(true);
      console.error(e);
    }
  };

  // When the current user edits/saves their own note
  const handleMyNoteSaved = (newText: string) => {
    setNote((prev) => {
      if (prev) return { ...prev, noteText: newText };
      return {
        userId: authUser!.userId,
        userName: authUser!.userName,
        recipeId: recipeIdNum,
        recipeName: recipe?.recipeName ?? '',
        noteText: newText,
      };
    });
  };

  // UI guards
  if (recipeLoading) return <p>Loading...</p>;
  if (!recipe) return <p>Recipe not found.</p>;

  const viewingSelf = selectedUserId != null && selectedUserId === authUser?.userId;
  const canEditViewedNote =
    // owner can edit own note via self endpoint
    viewingSelf ||
    // admin can edit others via admin endpoint
    (authUser?.role?.roleName?.toLowerCase() === 'admin' && selectedUserId != null);

  return (
    <div>
      <h1>Recipe Details</h1>
      <RecipeCard recipe={recipe} clickable={false} />

      {/* Favorite button */}
      {authUser &&
        !favoriteLoading &&
        (actuallyFavorited ? (
          <Button type="button" onClick={handleRemoveFromFavorites} className="button-red">
            <FaHeart className="button-icon" />
            Remove from Favorites
          </Button>
        ) : (
          <AddButton onClick={handleAddToFavorites}>Add to Favorites</AddButton>
        ))}

      {/* Note viewer/selector */}
      {authUser && (
        <section style={{ marginTop: 16 }}>
          {isPrivileged && (
            <div className="form-group" style={{ maxWidth: 400 }}>
              <label>View notes by:</label>
              <select
                value={selectedUserId ?? ''}
                onChange={(e) => setSelectedUserId(e.target.value ? Number(e.target.value) : null)}
              >
                {/* “Me” option */}
                <option value={authUser.userId}>Me ({authUser.userName})</option>
                {/* Other admins/contributors */}
                {!candidatesLoading &&
                  candidates
                    ?.filter((u) => u.userId !== authUser.userId)
                    .map((u) => (
                      <option key={u.userId} value={u.userId}>
                        {u.userName} ({u.role.roleName})
                      </option>
                    ))}
              </select>
            </div>
          )}

          {/* Note display/editor */}
          {!noteLoading && (
            <>
              {note?.noteText ? (
                <>
                  <UserNotes userNote={note} />
                  {canEditViewedNote && viewingSelf && (
                    <AddUserNote
                      recipeId={recipeIdNum}
                      existingText={note.noteText}
                      onSaved={handleMyNoteSaved}
                    />
                  )}
                  {canEditViewedNote &&
                    !viewingSelf &&
                    authUser.role.roleName.toLowerCase() === 'admin' && (
                      // Admin editing someone else’s note
                      <AddUserNote
                        recipeId={recipeIdNum}
                        existingText={note.noteText}
                        onSaved={(txt) =>
                          setNote((prev) => (prev ? { ...prev, noteText: txt } : prev))
                        }
                        userIdOverride={selectedUserId!}
                      />
                    )}
                </>
              ) : (
                <>
                  <p>No note yet.</p>
                  {canEditViewedNote && viewingSelf && (
                    <AddUserNote recipeId={recipeIdNum} onSaved={handleMyNoteSaved} />
                  )}
                  {canEditViewedNote &&
                    !viewingSelf &&
                    authUser.role.roleName.toLowerCase() === 'admin' && (
                      <AddUserNote
                        recipeId={recipeIdNum}
                        onSaved={(txt) =>
                          setNote({
                            userId: selectedUserId!,
                            userName:
                              candidates?.find((u) => u.userId === selectedUserId!)?.userName || '',
                            recipeId: recipeIdNum,
                            recipeName: recipe.recipeName,
                            noteText: txt,
                          })
                        }
                        userIdOverride={selectedUserId!}
                      />
                    )}
                </>
              )}
            </>
          )}
        </section>
      )}

      {/* Reviews */}
      {!reviewsLoading && !reviewsError && (
        <Reviews reviews={reviews || []} recipeId={recipe.recipeId} />
      )}
    </div>
  );
}

export default RecipeDetailView;
