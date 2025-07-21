import { Routes, Route } from 'react-router-dom';

function App() {
    return (
        <Routes>
            <Route path="/" element={<h1>Home Page</h1>} />
            <Route path="/recipes" element={<h1>All Recipes</h1>} />
            <Route path="/recipes/:id" element={<h1>Recipe Detail</h1>} />
        </Routes>
    );
}

export default App;