import type { FavoriteDTO } from '../../types/favorite.ts';
import { useAuth } from '../../context/useAuth.ts';

type UserFavoritesProps = {
  userFavorite: FavoriteDTO | null;
};

function UserFavorites({ userFavorite }: UserFavoritesProps) {
  const { user } = useAuth();

  if (!user || !userFavorite) return null;

  return (
    <div className="user-favorites">
      <h2>{user.userName}'s Favorites</h2>
    </div>
  );
}

export default UserFavorites;
