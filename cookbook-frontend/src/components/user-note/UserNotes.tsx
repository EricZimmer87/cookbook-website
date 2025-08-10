import type { UserNotesDTO } from '../../types/user-notes.ts';

type UserNotesProps = {
  userNote: UserNotesDTO | null;
  titleUserName?: string; // optional override from parent
};

function UserNotes({ userNote, titleUserName }: UserNotesProps) {
  if (!userNote) return null;

  const displayName = titleUserName ?? userNote.userName ?? 'User';

  return (
    <div className="user-notes">
      <h2>{displayName}'s Notes</h2>
      <p>{userNote.noteText}</p>
    </div>
  );
}

export default UserNotes;
