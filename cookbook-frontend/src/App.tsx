import { Routes, Route } from 'react-router-dom';
import RecipesView from "./views/recipes/RecipesView";

function App() {
    return (
        <Routes>
            <Route path="/" element={<h1>Home Page</h1>} />
            <Route path="/recipes" element={<RecipesView />} />
            <Route path="/recipes/:id" element={<h1>Recipe Detail</h1>} />
        </Routes>
    );
}

export default App;