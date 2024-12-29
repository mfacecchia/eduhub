import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Route, Routes } from "react-router";
import BackButton from "./components/common/BackButton";
import MainLayout from "./layouts/MainLayout";
import LandingPage from "./pages/LandingPage";
import LoginPage from "./pages/LoginPage";
import NotFoundPage from "./pages/NotFoundPage";

const queryClient = new QueryClient();

function App() {
    return (
        <BrowserRouter>
            <QueryClientProvider client={queryClient}>
                <Routes>
                    <Route path="/" element={<MainLayout />}>
                        <Route index element={<LandingPage />} />
                    </Route>
                    <Route path="login">
                        <Route element={<BackButton />}>
                            <Route index element={<LoginPage />} />
                        </Route>
                    </Route>
                    <Route path="*" element={<NotFoundPage />}></Route>
                </Routes>
            </QueryClientProvider>
        </BrowserRouter>
    );
}

export default App;
