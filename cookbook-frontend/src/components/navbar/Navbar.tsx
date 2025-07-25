import { Link } from 'react-router-dom';
import { useAuth } from '../../context/useAuth.ts';
import './Navbar.css';
import { useState } from 'react';
import { FaCaretDown } from 'react-icons/fa';
import Button from '../buttons/Button.tsx';
import { websiteTitle } from '../../config.ts';

function Navbar() {
  const { user, logout } = useAuth();
  const [isOpen, setIsOpen] = useState(false);

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <Link to="/" className="navbar-logo">
          {websiteTitle}
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
          <div className="navbar-dropdown">
            <Button className="navbar-dropdown-link" onClick={() => setIsOpen((prev) => !prev)}>
              Hello, {user.userName} <FaCaretDown />
            </Button>
            {isOpen && (
              <div className="dropdown-menu">
                <Link
                  to={`/users/${user.userId}/profile`}
                  className="dropdown-item"
                  onClick={() => setIsOpen(false)}
                >
                  View Profile
                </Link>
                <Button
                  className="dropdown-item"
                  onClick={() => {
                    logout();
                    setIsOpen(false);
                  }}
                >
                  Logout
                </Button>
              </div>
            )}
          </div>
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
