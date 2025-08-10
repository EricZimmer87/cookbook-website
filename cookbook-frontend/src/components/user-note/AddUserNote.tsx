// components/user-note/AddUserNote.tsx
import { useState } from 'react';
import { apiFetch } from '../../api/apiFetch';
import AddButton from '../buttons/AddButton';

type AddUserNoteProps = {
  recipeId: number;
  existingText?: string;
  onSaved?: (newText: string) => void;
  userIdOverride?: number; // if admin editing someone else
};

export default function AddUserNote({
  recipeId,
  existingText,
  onSaved,
  userIdOverride,
}: AddUserNoteProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [noteText, setNoteText] = useState(existingText ?? '');
  const [isSaving, setIsSaving] = useState(false);

  const label = existingText ? 'Edit Note' : 'Add Note';

  const openForm = () => {
    setNoteText(existingText ?? '');
    setIsOpen(true);
  };

  const handleSave = async () => {
    if (!noteText.trim()) return;
    try {
      setIsSaving(true);
      const path = userIdOverride
        ? `/api/user-notes/recipe/${recipeId}/user/${userIdOverride}`
        : `/api/user-notes/recipe/${recipeId}`;
      await apiFetch(path, 'POST', { noteText: noteText.trim() });
      setIsOpen(false);
      onSaved?.(noteText.trim());
    } catch (e) {
      console.error('Failed to save note:', e);
    } finally {
      setIsSaving(false);
    }
  };

  if (!isOpen) {
    return <AddButton onClick={openForm}>{label}</AddButton>;
  }

  return (
    <div className="add-note-form">
      <textarea
        value={noteText}
        onChange={(e) => setNoteText(e.target.value)}
        rows={4}
        placeholder="Your note..."
        className="note-textarea"
        disabled={isSaving}
        autoFocus
      />
      <div className="note-form-buttons">
        <button
          className="button button-green"
          onClick={handleSave}
          disabled={isSaving || !noteText.trim()}
        >
          {isSaving ? 'Saving...' : 'Save Note'}
        </button>
        <button className="button button-gray" onClick={() => setIsOpen(false)} disabled={isSaving}>
          Cancel
        </button>
      </div>
    </div>
  );
}
