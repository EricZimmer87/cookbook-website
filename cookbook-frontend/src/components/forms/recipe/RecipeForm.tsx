// components/recipe/RecipeForm.tsx
import React, { useEffect, useMemo, useState } from 'react';
import type { CategoryDTO } from '../../../types/category.ts';
import type { DifficultyLevelDTO } from '../../../types/difficulty-level.ts';
import type { IngredientDTO } from '../../../types/ingredient.ts';
import type { TagDTO } from '../../../types/tag.ts';
import { apiFetch } from '../../../api/apiFetch.ts';

export type IngredientRowForm = {
  key: string;
  ingredientId?: number;
  quantity?: string; // keep as string in UI
  unit?: string;
  isOptional?: boolean;
};

export type RecipeFormValues = {
  recipeName: string;
  recipeInstructions: string;
  categoryId: number | '';
  difficultyId: number | '';
  rows: IngredientRowForm[];
  tagIds: number[];
};

type Props = {
  initialValues?: Partial<RecipeFormValues> & { categoryName?: string; difficultyName?: string };
  submitLabel?: string;
  onSubmit: (values: RecipeFormValues) => Promise<void>;
};

export default function RecipeForm({ initialValues, submitLabel = 'Save', onSubmit }: Props) {
  // lookups
  const [categories, setCategories] = useState<CategoryDTO[]>([]);
  const [difficulties, setDifficulties] = useState<DifficultyLevelDTO[]>([]);
  const [ingredients, setIngredients] = useState<IngredientDTO[]>([]);
  const [tags, setTags] = useState<TagDTO[]>([]);
  const [loadingLists, setLoadingLists] = useState(true);

  // core fields
  const [recipeName, setRecipeName] = useState(initialValues?.recipeName ?? '');
  const [recipeInstructions, setRecipeInstructions] = useState(
    initialValues?.recipeInstructions ?? '',
  );
  const [categoryId, setCategoryId] = useState<number | ''>(initialValues?.categoryId ?? '');
  const [difficultyId, setDifficultyId] = useState<number | ''>(initialValues?.difficultyId ?? '');

  // selections
  const [rows, setRows] = useState<IngredientRowForm[]>(
    initialValues?.rows && initialValues.rows.length
      ? initialValues.rows
      : [{ key: crypto.randomUUID() }],
  );
  const [selectedTagIds, setSelectedTagIds] = useState<number[]>(initialValues?.tagIds ?? []);

  // inline add ingredient
  const [showAddIngredient, setShowAddIngredient] = useState(false);
  const [newIngredientName, setNewIngredientName] = useState('');

  const [saving, setSaving] = useState(false);

  useEffect(() => {
    (async () => {
      try {
        const [cats, diffs, ings, tgs] = await Promise.all([
          apiFetch<CategoryDTO[]>('/api/categories', 'GET'),
          apiFetch<DifficultyLevelDTO[]>('/api/difficulty-levels', 'GET'),
          apiFetch<IngredientDTO[]>('/api/ingredients', 'GET'),
          apiFetch<TagDTO[]>('/api/tags', 'GET'),
        ]);
        setCategories(cats ?? []);
        setDifficulties(diffs ?? []);
        setIngredients(ings ?? []);
        setTags(tgs ?? []);
      } catch (e: any) {
        alert(e.body || 'Failed to load form data');
      } finally {
        setLoadingLists(false);
      }
    })();
  }, []);

  useEffect(() => {
    if (!initialValues) return;
    setRecipeName(initialValues.recipeName ?? '');
    setRecipeInstructions(initialValues.recipeInstructions ?? '');
    setCategoryId(initialValues.categoryId ?? '');
    setDifficultyId(initialValues.difficultyId ?? '');
    setRows(
      initialValues.rows && initialValues.rows.length
        ? initialValues.rows
        : [{ key: crypto.randomUUID() }],
    );
    setSelectedTagIds(initialValues.tagIds ?? []);
  }, [initialValues]);

  // ingredient rows helpers
  const addRow = () => setRows((prev) => [...prev, { key: crypto.randomUUID() }]);
  const removeRow = (key: string) =>
    setRows((prev) => (prev.length === 1 ? prev : prev.filter((r) => r.key !== key)));
  const updateRow = (key: string, patch: Partial<IngredientRowForm>) =>
    setRows((prev) => prev.map((r) => (r.key === key ? { ...r, ...patch } : r)));

  const createIngredient = async () => {
    const name = newIngredientName.trim();
    if (!name) return;
    try {
      const created = await apiFetch<IngredientDTO>('/api/ingredients', 'POST', {
        ingredientName: name,
      });
      if (created?.ingredientId) {
        setIngredients((prev) => [...prev, created]);
        setRows((prev) => {
          const idx = prev.findIndex((r) => !r.ingredientId);
          if (idx >= 0) {
            const copy = [...prev];
            copy[idx] = { ...copy[idx], ingredientId: created.ingredientId };
            return copy;
          }
          return [...prev, { key: crypto.randomUUID(), ingredientId: created.ingredientId }];
        });
      }
      setNewIngredientName('');
      setShowAddIngredient(false);
    } catch (e: any) {
      alert(e.body || 'Failed to add ingredient');
    }
  };

  const canSubmit = useMemo(
    () =>
      !saving && !loadingLists && !!recipeName.trim() && categoryId !== '' && difficultyId !== '',
    [saving, loadingLists, recipeName, categoryId, difficultyId],
  );

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!canSubmit) return;

    const payload: RecipeFormValues = {
      recipeName: recipeName.trim(),
      recipeInstructions,
      categoryId,
      difficultyId,
      rows,
      tagIds: selectedTagIds,
    };

    setSaving(true);
    try {
      await onSubmit(payload);
    } finally {
      setSaving(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Title
        <input value={recipeName} onChange={(e) => setRecipeName(e.target.value)} required />
      </label>

      <label>
        Instructions
        <textarea
          value={recipeInstructions}
          onChange={(e) => setRecipeInstructions(e.target.value)}
          required
        />
      </label>

      <label>
        Category
        <select
          value={categoryId}
          onChange={(e) => setCategoryId(e.target.value ? Number(e.target.value) : '')}
          required
          disabled={loadingLists}
        >
          <option value="">Select a category…</option>
          {categories.map((c) => (
            <option key={c.categoryId} value={c.categoryId}>
              {c.categoryName}
            </option>
          ))}
        </select>
      </label>

      <label>
        Difficulty
        <select
          value={difficultyId}
          onChange={(e) => setDifficultyId(e.target.value ? Number(e.target.value) : '')}
          required
          disabled={loadingLists}
        >
          <option value="">Select difficulty…</option>
          {difficulties.map((d) => (
            <option key={d.difficultyLevelId} value={d.difficultyLevelId}>
              {d.difficultyLevelName}
            </option>
          ))}
        </select>
      </label>

      <fieldset style={{ marginTop: 16 }}>
        <legend>Ingredients</legend>
        {rows.map((row) => (
          <div
            key={row.key}
            style={{
              display: 'grid',
              gridTemplateColumns: '2fr 1fr 1fr auto auto',
              gap: 8,
              marginBottom: 8,
              alignItems: 'center',
            }}
          >
            <select
              value={row.ingredientId ?? ''}
              onChange={(e) =>
                updateRow(row.key, {
                  ingredientId: e.target.value ? Number(e.target.value) : undefined,
                })
              }
            >
              <option value="">Select ingredient…</option>
              {ingredients.map((i) => (
                <option key={i.ingredientId} value={i.ingredientId}>
                  {i.ingredientName}
                </option>
              ))}
            </select>

            <input
              placeholder="Qty"
              value={row.quantity ?? ''}
              onChange={(e) => updateRow(row.key, { quantity: e.target.value })}
              inputMode="decimal"
            />

            <input
              placeholder="Unit (e.g., g, cup)"
              value={row.unit ?? ''}
              onChange={(e) => updateRow(row.key, { unit: e.target.value })}
            />

            <label style={{ display: 'flex', alignItems: 'center', gap: 6 }}>
              <input
                type="checkbox"
                checked={!!row.isOptional}
                onChange={(e) => updateRow(row.key, { isOptional: e.target.checked })}
              />
              optional
            </label>

            <button type="button" onClick={() => removeRow(row.key)} disabled={rows.length === 1}>
              ✕
            </button>
          </div>
        ))}

        <div style={{ display: 'flex', gap: 8 }}>
          <button type="button" onClick={addRow}>
            + Add ingredient row
          </button>
          <button type="button" onClick={() => setShowAddIngredient(true)}>
            + New ingredient
          </button>
        </div>

        {showAddIngredient && (
          <div style={{ marginTop: 8, display: 'flex', gap: 8 }}>
            <input
              placeholder="New ingredient name"
              value={newIngredientName}
              onChange={(e) => setNewIngredientName(e.target.value)}
            />
            <button type="button" onClick={createIngredient} disabled={!newIngredientName.trim()}>
              Save
            </button>
            <button
              type="button"
              onClick={() => {
                setShowAddIngredient(false);
                setNewIngredientName('');
              }}
            >
              Cancel
            </button>
          </div>
        )}
      </fieldset>

      <fieldset style={{ marginTop: 16 }}>
        <legend>Tags</legend>
        <div style={{ display: 'flex', gap: 12, flexWrap: 'wrap' }}>
          {tags.map((t) => (
            <label key={t.tagId} style={{ display: 'inline-flex', alignItems: 'center', gap: 6 }}>
              <input
                type="checkbox"
                checked={selectedTagIds.includes(t.tagId)} // ← read from state
                onChange={() =>
                  setSelectedTagIds((prev) =>
                    prev.includes(t.tagId)
                      ? prev.filter((id) => id !== t.tagId)
                      : [...prev, t.tagId],
                  )
                }
              />
              {t.tagName}
            </label>
          ))}
        </div>
      </fieldset>

      <button type="submit" disabled={!canSubmit} style={{ marginTop: 16 }}>
        {saving ? 'Saving…' : submitLabel}
      </button>
    </form>
  );
}
