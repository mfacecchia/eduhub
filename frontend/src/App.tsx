import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Route, Routes } from "react-router";
import BackButton from "./components/common/BackButton";
import AuthContext from "./context/AuthContext";
import MainLayout from "./layouts/MainLayout";
import ClassPage from "./pages/ClassPage";
import DashboardPage from "./pages/DashboardPage";
import LandingPage from "./pages/LandingPage";
import LoginPage from "./pages/LoginPage";
import NotFoundPage from "./pages/NotFoundPage";

const queryClient = new QueryClient();

function App() {
    return (
        <QueryClientProvider client={queryClient}>
            <BrowserRouter>
                <AuthContext>
                    <Routes>
                        <Route path="/" element={<MainLayout />}>
                            <Route index element={<LandingPage />} />
                            <Route
                                path="dashboard"
                                element={<DashboardPage />}
                            />
                            <Route element={<BackButton urlTo="dashboard" />}>
                                <Route
                                    path="classes/:classId"
                                    element={<ClassPage />}
                                />
                            </Route>
                        </Route>
                        <Route path="login">
                            <Route element={<BackButton />}>
                                <Route index element={<LoginPage />} />
                            </Route>
                        </Route>
                        <Route path="*" element={<NotFoundPage />}></Route>
                    </Routes>
                </AuthContext>
            </BrowserRouter>
        </QueryClientProvider>
    );
}

export default App;
