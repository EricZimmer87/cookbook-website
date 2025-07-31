import type { UserNotesDTO } from '../../types/user-notes.ts';
import { useAuth } from '../../context/useAuth.ts';

type UserNotesProps = {
  userNote: UserNotesDTO | null;
};

function UserNotes({ userNote }: UserNotesProps) {
  const { user } = useAuth();

  if (!user || !userNote) return null;

  return (
    <div className="user-notes">
      <h2>{user.userName}'s Notes</h2>
      <p>{userNote.noteText}</p>
    </div>
  );
}

export default UserNotes;
