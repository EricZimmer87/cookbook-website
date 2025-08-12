import { useFetch } from '../../api/useFetch.ts';
import RecipeCard from '../../components/recipe-card';
import type { RecipeDTO } from '../../types/recipe.ts';
import { useEffect, useState } from 'react';
import Button from '../../components/buttons/Button.tsx';
import { useErrorRedirect } from '../../hooks/useErrorRedirect.ts';
import AddButton from '../../components/buttons/AddButton.tsx';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/useAuth.ts';
import { apiFetch } from '../../api/apiFetch.ts';

// Normalize for de-duplication but preserve original casing
function getUniqueValuesPreserveCase(values: (string | null | undefined)[]): string[] {
  const seen = new Set<string>();
  const result: string[] = [];

  for (const val of values) {
    const trimmed = val?.trim();
    const normalized = trimmed?.toLowerCase();

    if (trimmed && normalized && !seen.has(normalized)) {
      seen.add(normalized);
      result.push(trimmed);
    }
  }

  return result.sort((a, b) => a.localeCompare(b));
}

function RecipesView() {
  const { data: recipes, loading: recipesLoading, error } = useFetch<RecipeDTO[]>('/api/recipes');
  useErrorRedirect(error);
  const { user: authUser } = useAuth();
  const navigate = useNavigate();

  const [list, setList] = useState<RecipeDTO[]>([]);
  useEffect(() => {
    if (recipes) setList(recipes);
  }, [recipes]);

  // helper: can current user delete this recipe?
  const canDelete = (r: RecipeDTO): boolean => {
    const role = authUser?.role?.roleName?.toLowerCase();
    const isAdmin = role === 'admin';
    const isOwner = !!authUser && r.ownerId === authUser.userId;
    return !!authUser && (isAdmin || isOwner);
  };

  const handleDelete = async (recipeId: number) => {
    if (!window.confirm('Delete this recipe? This cannot be undone.')) return;
    // optimistic remove
    const prev = list;
    setList(prev.filter((r) => r.recipeId !== recipeId));
    try {
      await apiFetch<void>(`/api/recipes/${recipeId}`, 'DELETE');
      // success: nothing else to do
    } catch (e: any) {
      // revert on failure
      setList(prev);
      alert(e.body || 'Failed to delete recipe');
    }
  };

  const [searchTerm, setSearchTerm] = useState('');
  const [sortOption, setSortOption] = useState<'name-asc' | 'name-desc'>('name-asc');
  const [selectedTag, setSelectedTag] = useState('all');
  const [selectedIngredient, setSelectedIngredient] = useState('all');
  const [selectedCategory, setSelectedCategory] = useState('all');
  const [selectedDifficulty, setSelectedDifficulty] = useState('all');

  if (recipesLoading) return <p>Loading...</p>;
  if (error) return <p style={{ color: 'red' }}>Error: {error}</p>;

  // Extract all filter options (preserve case)
  const allTags = getUniqueValuesPreserveCase(
    (recipes ?? []).flatMap((recipe) => recipe.recipeTags?.map((tag) => tag.tagName) ?? []),
  );

  const allIngredients = getUniqueValuesPreserveCase(
    (recipes ?? []).flatMap((recipe) => recipe.recipeIngredients?.map((ing) => ing.ingredientName)),
  );

  const allCategories = getUniqueValuesPreserveCase(
    (recipes ?? []).map((recipe) => recipe.categoryName),
  );

  const allDifficulties = getUniqueValuesPreserveCase(
    (recipes ?? []).map((recipe) => recipe.difficultyLevel),
  );

  // Apply filters
  let displayedRecipes = [...list];

  if (searchTerm.trim() !== '') {
    displayedRecipes = displayedRecipes.filter((recipe) =>
      recipe.recipeName.toLowerCase().includes(searchTerm.toLowerCase()),
    );
  }

  if (selectedTag !== 'all') {
    displayedRecipes = displayedRecipes.filter((recipe) =>
      recipe.recipeTags?.some((tag) => tag.tagName === selectedTag),
    );
  }

  if (selectedIngredient !== 'all') {
    displayedRecipes = displayedRecipes.filter((recipe) =>
      recipe.recipeIngredients?.some((ing) => ing.ingredientName === selectedIngredient),
    );
  }

  if (selectedCategory !== 'all') {
    displayedRecipes = displayedRecipes.filter(
      (recipe) => recipe.categoryName === selectedCategory,
    );
  }

  if (selectedDifficulty !== 'all') {
    displayedRecipes = displayedRecipes.filter(
      (recipe) => recipe.difficultyLevel === selectedDifficulty,
    );
  }

  // Sort
  displayedRecipes.sort((a, b) => {
    if (sortOption === 'name-asc') return a.recipeName.localeCompare(b.recipeName);
    if (sortOption === 'name-desc') return b.recipeName.localeCompare(a.recipeName);
    return 0;
  });

  return (
    <div>
      <h1>All Recipes</h1>
      <AddButton onClick={() => navigate('/recipes/new')}>Add Recipe</AddButton>

      <div style={{ marginBottom: '1rem', display: 'flex', flexWrap: 'wrap', gap: '1rem' }}>
        <input
          type="text"
          placeholder="Search recipes..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />

        <select
          value={sortOption}
          onChange={(e) => setSortOption(e.target.value as 'name-asc' | 'name-desc')}
        >
          <option value="name-asc">Sort A–Z</option>
          <option value="name-desc">Sort Z–A</option>
        </select>

        <select value={selectedTag} onChange={(e) => setSelectedTag(e.target.value)}>
          <option value="all">All Tags</option>
          {allTags.map((tag) => (
            <option key={tag} value={tag}>
              {tag}
            </option>
          ))}
        </select>

        <select value={selectedIngredient} onChange={(e) => setSelectedIngredient(e.target.value)}>
          <option value="all">All Ingredients</option>
          {allIngredients.map((ing) => (
            <option key={ing} value={ing}>
              {ing}
            </option>
          ))}
        </select>

        <select value={selectedCategory} onChange={(e) => setSelectedCategory(e.target.value)}>
          <option value="all">All Categories</option>
          {allCategories.map((cat) => (
            <option key={cat} value={cat}>
              {cat}
            </option>
          ))}
        </select>

        <select value={selectedDifficulty} onChange={(e) => setSelectedDifficulty(e.target.value)}>
          <option value="all">All Difficulties</option>
          {allDifficulties.map((diff) => (
            <option key={diff} value={diff}>
              {diff}
            </option>
          ))}
        </select>

        <Button
          onClick={() => {
            setSearchTerm('');
            setSortOption('name-asc');
            setSelectedTag('all');
            setSelectedIngredient('all');
            setSelectedCategory('all');
            setSelectedDifficulty('all');
          }}
        >
          Reset Filters
        </Button>
      </div>

      {displayedRecipes.length > 0 ? (
        displayedRecipes.map((recipe) => (
          <div key={recipe.recipeId} style={{ marginBottom: 16 }}>
            <RecipeCard
              recipe={recipe}
              clickable={true}
              showReviews={false}
              canDelete={canDelete(recipe)}
              onDelete={handleDelete}
            />
          </div>
        ))
      ) : (
        <p>No recipes match your filters.</p>
      )}
    </div>
  );
}

export default RecipesView;
