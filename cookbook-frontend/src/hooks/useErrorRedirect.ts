import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

/**
 * Automatically redirects based on HTTP error status in error string.
 * @param error - The error message returned from your fetch logic
 */
export function useErrorRedirect(error: string | null) {
  const navigate = useNavigate();

  useEffect(() => {
    if (!error) return;

    if (error.includes('403')) {
      navigate('/forbidden');
    } else if (error.includes('404')) {
      navigate('/not-found');
    }
  }, [error, navigate]);
}
