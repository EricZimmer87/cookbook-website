export async function apiFetch<T>(
  url: string,
  method: 'POST' | 'PUT' | 'DELETE',
  body?: unknown,
): Promise<T | null> {
  const token = JSON.parse(localStorage.getItem('auth') || '{}').token;

  const response = await fetch(url, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
    body: body ? JSON.stringify(body) : undefined,
  });

  if (!response.ok) {
    const errorText = await response.text();
    console.error(`API ${method} ${url} failed:`, errorText);
    throw new Error(`Error ${response.status}: ${errorText}`);
  }

  // Guard against empty body
  const contentLength = response.headers.get('Content-Length');
  if (response.status === 204 || contentLength === '0') {
    return null; // no content
  }

  // Return parsed JSON if body exists
  return response.json();
}
