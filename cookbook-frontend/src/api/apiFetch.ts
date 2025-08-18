const baseUrl = import.meta.env.VITE_API_BASE_URL || '';

export class ApiError extends Error {
  status: number;
  body: string;

  constructor(status: number, body: string) {
    super(body);
    this.status = status;
    this.body = body;
  }
}

export async function apiFetch<T>(
  url: string,
  method: 'GET' | 'POST' | 'PUT' | 'DELETE' = 'GET',
  body?: unknown,
): Promise<T | null> {
  const token = localStorage.getItem('token');

  const resp = await fetch(`${baseUrl}${url}`, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
    },
    body: method !== 'GET' && body != null ? JSON.stringify(body) : undefined,
  });

  // Handle empty body success
  if (resp.ok) {
    if (resp.status === 204) return null;
    const text = await resp.text();
    if (!text) return null;
    try {
      return JSON.parse(text) as T;
    } catch {
      return null;
    }
  }

  // Error path: read body text for message
  const errorText = await resp.text();

  throw new ApiError(resp.status, errorText || `HTTP ${resp.status}`);
}
