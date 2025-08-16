import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/useAuth.ts';
import './Navbar.css';
import { useEffect, useRef, useState } from 'react';
import { FaCaretDown } from 'react-icons/fa';
import Button from '../buttons/Button.tsx';
import { websiteTitle } from '../../config.ts';

function Navbar() {
  const { user, logout } = useAuth();
  const [loginOpen, setLoginOpen] = useState(false);
  const [adminOpen, setAdminOpen] = useState(false);
  const [mobileOpen, setMobileOpen] = useState(false); // ← NEW
  const navigate = useNavigate();

  const adminRef = useRef<HTMLDivElement>(null);
  const loginRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (adminRef.current && !adminRef.current.contains(event.target as Node)) {
        setAdminOpen(false);
      }
      if (loginRef.current && !loginRef.current.contains(event.target as Node)) {
        setLoginOpen(false);
      }
    };

    document.addEventListener('click', handleClickOutside);
    return () => document.removeEventListener('click', handleClickOutside);
  }, []);

  // close mobile menu on navigation helper
  const closeMobile = () => setMobileOpen(false);

  return (
    <nav className="navbar">
      <div className="navbar-left">
        <Link to="/" className="navbar-logo navbar-menu-link" onClick={closeMobile}>
          {websiteTitle}
        </Link>
      </div>

      {/* Hamburger (mobile only) */}
      <button
        className="navbar-hamburger"
        aria-label="Toggle menu"
        aria-controls="navbar-mobile-menu"
        aria-expanded={mobileOpen}
        onClick={() => setMobileOpen((v) => !v)}
      >
        <span className="hamburger-bar" />
        <span className="hamburger-bar" />
        <span className="hamburger-bar" />
      </button>

      {/* Desktop links */}
      <div className="navbar-right">
        <Link to="/" className="navbar-menu-link">
          Home
        </Link>
        <Link to="/recipes" className="navbar-menu-link">
          Recipes
        </Link>

        {user?.role?.roleName === 'admin' && (
          <div className="navbar-dropdown" ref={adminRef}>
            <Button className="navbar-dropdown-link" onClick={() => setAdminOpen((prev) => !prev)}>
              Admin <FaCaretDown />
            </Button>
            {adminOpen && (
              <div className="dropdown-menu">
                <Link to="/users" className="dropdown-item" onClick={() => setAdminOpen(false)}>
                  Users
                </Link>
                <Link to="/tags" className="dropdown-item" onClick={() => setAdminOpen(false)}>
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
        )}

        {user?.role?.roleName === 'contributor' && (
          <Link to="/ingredients" className="navbar-menu-link">
            Ingredients
          </Link>
        )}

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
                    navigate('/');
                  }}
                >
                  Logout
                </Button>
              </div>
            )}
          </div>
        ) : (
          <>
            <Link to="/login" className="navbar-menu-link">
              Login
            </Link>
            <Link to="/register" className="navbar-menu-link">
              Register
            </Link>
          </>
        )}
      </div>

      {/* Mobile slide-down menu */}
      <div
        id="navbar-mobile-menu"
        className={`navbar-menu ${mobileOpen ? 'open' : ''}`}
        onClick={(e) => {
          // close when clicking a link/button inside
          const target = e.target as HTMLElement;
          if (target.closest('a, button')) closeMobile();
        }}
      >
        <Link to="/" className="navbar-menu-link">
          Home
        </Link>
        <Link to="/recipes" className="navbar-menu-link">
          Recipes
        </Link>

        {user?.role?.roleName === 'admin' && (
          <>
            <div className="navbar-menu-section">Admin</div>
            <Link to="/users" className="navbar-menu-link">
              Users
            </Link>
            <Link to="/tags" className="navbar-menu-link">
              Tags
            </Link>
            <Link to="/categories" className="navbar-menu-link">
              Categories
            </Link>
            <Link to="/difficulty-levels" className="navbar-menu-link">
              Difficulty Levels
            </Link>
            <Link to="/ingredients" className="navbar-menu-link">
              Ingredients
            </Link>
          </>
        )}

        {user?.role?.roleName === 'contributor' && (
          <Link to="/ingredients" className="navbar-menu-link">
            Ingredients
          </Link>
        )}

        {user ? (
          <>
            <Link to={`/users/${user.userId}/profile`} className="navbar-menu-link">
              View Profile
            </Link>
            <button
              className="navbar-menu-link button-reset"
              onClick={() => {
                logout();
                navigate('/');
              }}
            >
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="navbar-menu-link">
              Login
            </Link>
            <Link to="/register" className="navbar-menu-link">
              Register
            </Link>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
