import { BrowserRouter, Route, Routes } from "react-router";
import MainLayout from "./layouts/MainLayout";
import LandingPage from "./pages/LandingPage";
import NotFoundPage from "./pages/NotFoundPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<MainLayout />}>
                    <Route index element={<LandingPage />} />
                </Route>
                <Route path="*" element={<NotFoundPage />}></Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
