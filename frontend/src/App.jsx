import { Routes, Route, Navigate } from "react-router-dom";

import Dashboard from "./pages/Dashboard";
import Login from "./pages/Login";
import Register from "./pages/Register";
import ProtectedRoute from "./routes/ProtectedRoute";

import MainLayout from "./layouts/MainLayout";

function App() {

    return (
        <Routes>

            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route
                path="/dashboard"
                element={
                    <ProtectedRoute>
                        <MainLayout>
                            <Dashboard />
                        </MainLayout>
                    </ProtectedRoute>
                }
            />

            <Route
    path="/"
    element={
        localStorage.getItem("token")
            ? <Navigate to="/dashboard" replace />
            : <Navigate to="/login" replace />
    }
/>

<Route
    path="*"
    element={<Navigate to="/" replace />}
/>

        </Routes>
    );

}

export default App;