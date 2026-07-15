import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function Navbar() {

    const navigate = useNavigate();
    const { logout } = useAuth();

    function handleLogout() {

        logout();

        navigate("/login");

    }

    return (

        <nav className="navbar navbar-light bg-white shadow-sm">

            <div className="container-fluid">

                <h4 className="fw-bold mb-0">

                    Welcome Back 👋

                </h4>

                <div className="d-flex align-items-center">

                    <i className="bi bi-bell fs-4 me-4"></i>

                    <i className="bi bi-person-circle fs-3 me-4"></i>

                    <button
                        className="btn btn-outline-danger btn-sm"
                        onClick={handleLogout}
                    >
                        Logout
                    </button>

                </div>

            </div>

        </nav>

    );

}

export default Navbar;