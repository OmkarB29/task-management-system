import { Link } from "react-router-dom";

function Sidebar() {
    return (
        <div className="bg-dark text-white vh-100 p-3">

            <h3 className="mb-4">
                <i className="bi bi-check2-square me-2"></i>
                TaskFlow
            </h3>

            <ul className="nav flex-column">

                <li className="nav-item mb-3">
                    <Link to="/dashboard" className="nav-link text-white">
                        <i className="bi bi-speedometer2 me-2"></i>
                        Dashboard
                    </Link>
                </li>

                <li className="nav-item mb-3">
                    <Link to="/tasks" className="nav-link text-white">
                        <i className="bi bi-list-task me-2"></i>
                        Tasks
                    </Link>
                </li>

                <li className="nav-item mb-3">
                    <Link to="/completed" className="nav-link text-white">
                        <i className="bi bi-check-circle me-2"></i>
                        Completed
                    </Link>
                </li>

                <li className="nav-item mb-3">
                    <Link to="/settings" className="nav-link text-white">
                        <i className="bi bi-gear me-2"></i>
                        Settings
                    </Link>
                </li>

            </ul>

        </div>
    );
}

export default Sidebar;