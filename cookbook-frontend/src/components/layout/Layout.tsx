import { Outlet } from 'react-router-dom';
import Navbar from '../navbar';
import Footer from '../footer/Footer.tsx';

function Layout() {
  return (
    <>
      <Navbar />
      <main style={{ padding: '1rem' }}>
        <Outlet />
      </main>
      <Footer />
    </>
  );
}

export default Layout;
