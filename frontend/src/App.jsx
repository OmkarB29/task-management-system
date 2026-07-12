import { Routes, Route } from "react-router-dom";

import MainLayout from "./layouts/MainLayout";

import Dashboard from "./pages/Dashboard";
import Login from "./pages/Login";
import Register from "./pages/Register";
import NotFound from "./pages/NotFound";

function App(){

    return(

        <Routes>

            <Route path="/login" element={<Login/>}/>

            <Route path="/register" element={<Register/>}/>

            <Route
                path="/"
                element={
                    <MainLayout>

                        <Dashboard/>

                    </MainLayout>
                }
            />

            <Route path="*" element={<NotFound/>}/>

        </Routes>

    );

}

export default App;