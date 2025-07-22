import { Link } from 'react-router-dom';
import { useAuth } from '../../context/useAuth.ts';
import './Navbar.css';

function Navbar() {
  const { user, logout } = useAuth();

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <Link to="/" className="navbar-logo">
          Cookbook
        </Link>
      </div>
      <div className="navbar-right">
        <Link to="/" className="navbar-link">
          Home
        </Link>
        <Link to="/users" className="navbar-link">
          Users
        </Link>
        <Link to="/recipes" className="navbar-link">
          Recipes
        </Link>

        {user ? (
          <>
            <span className="navbar-user">Hello, {user}!</span>
            <button onClick={logout} className="navbar-link">
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="navbar-link">
              Login
            </Link>
            <Link to="/register" className="navbar-link">
              Register
            </Link>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
