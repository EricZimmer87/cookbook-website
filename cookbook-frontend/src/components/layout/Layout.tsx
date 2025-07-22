import { Outlet } from 'react-router-dom';
import Navbar from '../navbar';

function Layout() {
  return (
    <>
      <Navbar />
      <main style={{ padding: '1rem' }}>
        <Outlet />
      </main>
    </>
  );
}

export default Layout;
