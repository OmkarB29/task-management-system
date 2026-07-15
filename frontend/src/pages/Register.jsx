import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import api from "../services/api";

function Register() {

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const navigate = useNavigate();

    async function handleSubmit(e) {

        e.preventDefault();

        try {

            await api.post("/auth/register", {
                name,
                email,
                password
            });

            alert("Registration Successful!");

            navigate("/login");

        } catch (error) {

            if (error.response?.data?.message) {
                alert(error.response.data.message);
            } else {
                alert("Registration Failed");
            }

        }

    }

    return (

        <div className="container mt-5">

            <div className="card shadow p-4 mx-auto" style={{ maxWidth: "450px" }}>

                <h3 className="text-center mb-4">

                    Create Account

                </h3>

                <form onSubmit={handleSubmit}>

                    <input
                        className="form-control mb-3"
                        placeholder="Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />

                    <input
                        type="email"
                        className="form-control mb-3"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />

                    <input
                        type="password"
                        className="form-control mb-3"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />

                    <button
                        className="btn btn-success w-100"
                        type="submit"
                    >
                        Register
                    </button>

                </form>

                <div className="text-center mt-3">

                    Already have an account?

                    <br />

                    <Link to="/login">

                        Login

                    </Link>

                </div>

            </div>

        </div>

    );

}

export default Register;