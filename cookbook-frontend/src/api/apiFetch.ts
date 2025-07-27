const baseUrl = import.meta.env.VITE_API_BASE_URL || '';

export async function apiFetch<T>(
  url: string,
  method: 'GET' | 'POST' | 'PUT' | 'DELETE',
  body?: unknown,
): Promise<T | null> {
  const auth = localStorage.getItem('auth');
  const token = auth ? JSON.parse(auth).token : null;

  const response = await fetch(`${baseUrl}${url}`, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
    body: method !== 'GET' && body ? JSON.stringify(body) : undefined,
  });

  if (!response.ok) {
    const errorText = await response.text();
    console.error(`API ${method} ${url} failed:`, errorText);
    throw new Error(`Error ${response.status}: ${errorText}`);
  }

  const contentLength = response.headers.get('Content-Length');
  if (response.status === 204 || contentLength === '0') {
    return null;
  }

  try {
    return await response.json();
  } catch {
    return null;
  }
}
