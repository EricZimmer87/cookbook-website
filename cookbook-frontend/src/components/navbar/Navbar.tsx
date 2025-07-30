import { Link } from 'react-router-dom';
import { useAuth } from '../../context/useAuth.ts';
import './Navbar.css';
import { useState, useRef, useEffect } from 'react';
import { FaCaretDown } from 'react-icons/fa';
import Button from '../buttons/Button.tsx';
import { websiteTitle } from '../../config.ts';

function Navbar() {
  const { user, logout } = useAuth();
  const [loginOpen, setLoginOpen] = useState(false);
  const [adminOpen, setAdminOpen] = useState(false);

  const adminRef = useRef<HTMLDivElement>(null);
  const loginRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        adminRef.current &&
        !adminRef.current.contains(event.target as Node)
      ) {
        setAdminOpen(false);
      }

      if (
        loginRef.current &&
        !loginRef.current.contains(event.target as Node)
      ) {
        setLoginOpen(false);
      }
    };

    document.addEventListener('click', handleClickOutside);
    return () => document.removeEventListener('click', handleClickOutside);
  }, []);

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
        <Link to="/recipes" className="navbar-link">
          Recipes
        </Link>

        {/* Admin Dropdown */}
        <div className="navbar-dropdown" ref={adminRef}>
          <Button className="navbar-dropdown-link" onClick={() => setAdminOpen((prev) => !prev)}>
            Admin <FaCaretDown />
          </Button>
          {adminOpen && (
            <div className="dropdown-menu">
              <Link
                to="/users"
                className="dropdown-item"
                onClick={() => setAdminOpen(false)}
              >
                Users
              </Link>
              <Link
                to="/tags"
                className="dropdown-item"
                onClick={() => setAdminOpen(false)}
              >
                Tags
              </Link>
              <Link
                to="/categories"
                className="dropdown-item"
                onClick={() => setAdminOpen(false)}
              >
                Categories
              </Link>
              <Link
                to="/difficulty-levels"
                className="dropdown-item"
                onClick={() => setAdminOpen(false)}
              >
                Difficulty Levels
              </Link>
              <Link
                to="/ingredients"
                className="dropdown-item"
                onClick={() => setAdminOpen(false)}
              >
                Ingredients
              </Link>
            </div>
          )}
        </div>


        {/* User Dropdown */}
        {user ? (
          <div className="navbar-dropdown" ref={loginRef}>
            <Button className="navbar-dropdown-link" onClick={() => setLoginOpen((prev) => !prev)}>
              Hello, {user.userName} <FaCaretDown />
            </Button>
            {loginOpen && (
              <div className="dropdown-menu">
                <Link
                  to={`/users/${user.userId}/profile`}
                  className="dropdown-item"
                  onClick={() => setLoginOpen(false)}
                >
                  View Profile
                </Link>
                <Button
                  className="dropdown-item"
                  onClick={() => {
                    logout();
                    setLoginOpen(false);
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
