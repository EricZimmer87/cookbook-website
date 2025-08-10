import { useEffect, useState } from 'react';
import { useAuth } from '../context/useAuth.ts';

const baseUrl = import.meta.env.VITE_API_BASE_URL || '';

export function useFetch<T>(url: string | null) {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(!!url); // Only loading if url exists
  const [error, setError] = useState<string | null>(null);
  const { token } = useAuth();

  useEffect(() => {
    if (!url) return;

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

        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(`HTTP ${response.status} ${errorText}`);
        }

        const contentLength = response.headers.get('Content-Length');
        if (response.status === 204 || contentLength === '0') {
          setData(null);
        } else {
          const json = await response.json();
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
