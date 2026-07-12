import { useEffect, useState } from "react";
import api from "../services/api";
import TaskForm from "../components/TaskForm";
import{

FaCheckCircle,

FaClock

}

from

"react-icons/fa";

function Dashboard() {

    const [tasks, setTasks] = useState([]);
    const [selectedTask, setSelectedTask] = useState(null);
    const [search,setSearch]=useState("");

    useEffect(() => {

        loadTasks();

    }, []);

    async function loadTasks() {

        const response = await api.get("/tasks");

        setTasks(response.data.content);

    }

    async function addTask(task) {

    await api.post("/tasks", task);

    loadTasks();

}

async function updateTask(task) {

    await api.put(`/tasks/${task.id}`, task);

    loadTasks();

    setSelectedTask(null);

}

    function editTask(task){

    setSelectedTask(task);

}

async function deleteTask(id){

    const confirmDelete =
        window.confirm("Delete this task?");

    if(!confirmDelete) return;

    await api.delete(`/tasks/${id}`);

    loadTasks();

}

async function toggleComplete(task){

    const updatedTask={

        ...task,

        completed:!task.completed

    };

    await api.put(

        `/tasks/${task.id}`,

        updatedTask

    );

    loadTasks();

}

    return (

        <>

            
            <div className="d-flex justify-content-between align-items-center mb-4">

    <div>

        <h2 className="fw-bold mb-1">
            My Tasks
        </h2>

        <p className="text-muted mb-0">
            Manage all your daily tasks efficiently.
        </p>

    </div>

</div>

<hr/>
<div className="row mb-4">

    <div className="col-md-4">

        <div className="card text-bg-primary">

            <div className="card-body">

                <h5>Total Tasks</h5>

                <h2>{tasks.length}</h2>

            </div>

        </div>

    </div>

    <div className="col-md-4">

        <div className="card text-bg-success">

            <div className="card-body">

                <h5>Completed</h5>

                <h2>

                    {tasks.filter(t=>t.completed).length}

                </h2>

            </div>

        </div>

    </div>

    <div className="col-md-4">

        <div className="card text-bg-warning">

            <div className="card-body">

                <h5>Pending</h5>

                <h2>

                    {tasks.filter(t=>!t.completed).length}

                </h2>

            </div>

        </div>

    </div>

</div>
<input
className="form-control mb-3"

placeholder="Search task..."

value={search}

onChange={(e)=>setSearch(e.target.value)}
/>
            <TaskForm
    onAddTask={addTask}
    onUpdateTask={updateTask}
    selectedTask={selectedTask}
/>
            <hr/>

            {
    tasks.length === 0 ? (

        <div className="text-center mt-5">

            <h3>No Tasks Found</h3>

            <p>Create your first task.</p>

        </div>

    ) : (tasks
.filter(task=>

task.title

.toLowerCase()

.includes(search.toLowerCase())

).map(task => (

    <div key={task.id} className="card shadow-sm mb-3 border-0">

        <div className="card-body">

            <div className="d-flex justify-content-between align-items-center">

                <div>

                    <h5 className="mb-1">
                        {task.title}
                    </h5>

                    <p className="text-muted mb-2">
                        {task.description}
                    </p>

                    {
    task.completed ? (

        <span className="text-success">

            <FaCheckCircle className="me-1"/>

            Completed

        </span>

    ) : (

        <span className="text-warning">

            <FaClock className="me-1"/>

            Pending

        </span>

    )
}

                </div>

                <div>

                    <button
    className="btn btn-success btn-sm me-2"
    onClick={() => toggleComplete(task)}
>
    <FaCheckCircle />
</button>

                    <button
                        className="btn btn-warning btn-sm me-2"
                        onClick={() => editTask(task)}
                    >
                        ✏ Edit
                    </button>

                    <button
                        className="btn btn-danger btn-sm"
                        onClick={() => deleteTask(task.id)}
                    >
                        🗑 Delete
                    </button>

                </div>

            </div>

        </div>

    </div>

)))}

        </>

    );

}


export default Dashboard;