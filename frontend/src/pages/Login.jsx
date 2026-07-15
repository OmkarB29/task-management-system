import { useState } from "react";
import { useNavigate ,Link} from "react-router-dom";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";
//import { Link } from "react-router-dom";

function Login(){

    const [email,setEmail]=useState("");

    const [password,setPassword]=useState("");

    const navigate=useNavigate();

    const {login}=useAuth();

    async function handleSubmit(e){

        e.preventDefault();

        try{

            const response=await api.post("/auth/login",{

                email,

                password

            });

            login(response.data.token);

            navigate("/dashboard");

        }catch{

            alert("Invalid Credentials");

        }

    }

    return(

        <div className="container mt-5">

            <div className="card shadow p-4 mx-auto" style={{maxWidth:"400px"}}>

                <h3 className="mb-4 text-center">

                    Login

                </h3>

                <form onSubmit={handleSubmit}>

                    <input
                        className="form-control mb-3"
                        placeholder="Email"
                        value={email}
                        onChange={e=>setEmail(e.target.value)}
                    />

                    <input
                        type="password"
                        className="form-control mb-3"
                        placeholder="Password"
                        value={password}
                        onChange={e=>setPassword(e.target.value)}
                    />

                    <button className="btn btn-primary w-100">

                        Login

                    </button>

                </form>
                <div className="text-center mt-3">

    Don't have an account?

    <br />

    <Link to="/register">

        Register

    </Link>

</div>

            </div>

        </div>

    );

}

export default Login;