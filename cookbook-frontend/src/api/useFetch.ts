import { useEffect, useState } from 'react';
import { useAuth } from '../context/useAuth.ts';

const baseUrl = import.meta.env.VITE_API_BASE_URL || '';

export function useFetch<T>(url: string) {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const { token } = useAuth();

  useEffect(() => {
    const controller = new AbortController();

    (async () => {
      setLoading(true);
      setError(null);

      const fullUrl = `${baseUrl}${url}`;

      try {
        const response = await fetch(fullUrl, {
          signal: controller.signal,
          headers: {
            'Content-Type': 'application/json',
            ...(token ? { Authorization: `Bearer ${token}` } : {}),
          },
        });

        const json = await response.json();

        if (!response.ok) {
          setError(`HTTP error! status: ${response.status}`);
        } else {
          setData(json);
        }
      } catch (err) {
        if ((err as Error).name !== 'AbortError') {
          console.error('Fetch error:', err);
          setError((err as Error).message);
        }
      } finally {
        setLoading(false);
      }
    })();

    return () => controller.abort();
  }, [url, token]);

  return { data, loading, error };
}
