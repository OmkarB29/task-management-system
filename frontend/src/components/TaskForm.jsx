import { useState } from "react";
import { useEffect } from "react";
function TaskForm({
    onAddTask,
    onUpdateTask,
    selectedTask
}) {

    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");

    function handleSubmit(e) {
        e.preventDefault();

        if(selectedTask){

    onUpdateTask({

        id:selectedTask.id,

        title,

        description,

        completed:selectedTask.completed

    });

}else{

    onAddTask({

        title,

        description,

        completed:false

    });

}

        setTitle("");
        setDescription("");
    }

    useEffect(() => {

    if(selectedTask){

        setTitle(selectedTask.title);

        setDescription(selectedTask.description);

    }

}, [selectedTask]);

    return (
        <form onSubmit={handleSubmit} className="mb-4">

            <input
                className="form-control mb-2"
                placeholder="Task Title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />

            <textarea
                className="form-control mb-2"
                placeholder="Description"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />

            <button className="btn btn-primary">

    {

        selectedTask

        ?

        "Update Task"

        :

        "Add Task"

    }

</button>

        </form>
    );
}

export default TaskForm;