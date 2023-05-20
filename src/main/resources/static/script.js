const taskInput = document.querySelector(".task-input");
const taskButton = document.querySelector(".task-button");
const taskList = document.querySelector(".task-list");

document.querySelector(".filter-task");
document.addEventListener("DOMContentLoaded", getAllTasks);
taskButton.addEventListener("click", createTask);
taskList.addEventListener("click", deleteCheck);

function createTask() {
    const taskText = taskInput.value;
    if (taskText.trim() === "") {
        return;
    }

    const task = {
        description: taskText,
        completed: false
    };

    addTaskToList(task);

    fetch("/task", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(task)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Failed to create task");
        }
    })
    .then(() => {
        taskInput.value = "";
    })
    .catch(error => {
        console.error(error);
    });
}

function deleteCheck(event) {
    const item = event.target;
    const task = item.parentElement;
    const taskId = task.dataset.id;
    const taskDescription = task.querySelector(".task-item").innerText;

    if (item.classList.contains("trash-btn")) {
        fetch(`/delete/${taskId}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                removeTaskFromList(task);
            } else {
                throw new Error("Failed to delete task");
            }
        })
        .catch(error => {
            console.error(error);
        });
    }

    if (item.classList.contains("complete-btn")) {
        const taskItem = task.querySelector(".task-item");
        const completed = !taskItem.classList.contains("completed");

        taskItem.classList.toggle("completed");

        const updatedTask = {
            description: taskDescription,
            completed: completed,
        };

        fetch(`/update/${taskId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updatedTask),
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Failed to update task");
            }
        })
        .catch((error) => {
            console.error(error);
        });
    }
}

function addTaskToList(task) {
    const taskDiv = document.createElement("div");
    taskDiv.classList.add("task");
    taskDiv.dataset.id = task.id;

    const newTask = document.createElement("li");
    newTask.innerText = task.description;
    newTask.classList.add("task-item");
    if (task.completed) {
        newTask.classList.add("completed");
    }
    taskDiv.appendChild(newTask);

    const completedButton = document.createElement("button");
    completedButton.innerHTML = '<i class="fas fa-check-circle"></i>';
    completedButton.classList.add("complete-btn");
    taskDiv.appendChild(completedButton);

    const trashButton = document.createElement("button");
    trashButton.innerHTML = '<i class="fas fa-trash"></i>';
    trashButton.classList.add("trash-btn");
    taskDiv.appendChild(trashButton);

    taskList.appendChild(taskDiv);
}

function removeTaskFromList(task) {
    task.remove();
}

function getAllTasks() {
    fetch("/tasks")
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error("Failed to fetch tasks");
        }
    })
    .then(tasks => {
        tasks.forEach(task => {
            addTaskToList(task);
        });
    })
    .catch(error => {
        console.error(error);
    });
}
