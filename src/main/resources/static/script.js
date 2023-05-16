const todoInput = document.querySelector(".todo-input");
const todoButton = document.querySelector(".todo-button");
const todoList = document.querySelector(".todo-list");
const filterOption = document.querySelector(".filter-todo");

document.addEventListener("DOMContentLoaded", getRemoteTodos);
todoButton.addEventListener("click", createTodo);
todoList.addEventListener("click", handleTodoClick);
filterOption.addEventListener("change", filterTodo);

function createTodo(event) {
    event.preventDefault();
    const todoText = todoInput.value;

    if (todoText.trim() === "") {
        return;
    }

    const todo = {
        description: todoText,
        completed: false
    };

    fetch(`/task`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(todo)
    })
    .then(response => response.json())
    .then(createdTodo => {
        addTodoElement(createdTodo);
        // saveRemoteTodos();
        todoInput.value = "";
    })
    .then(response => {
        if (response.ok) {
            console.log("Todos saved successfully");
        } else {
            console.error("Error saving todos:", response.status);
        }
    })
    .catch(error => {
        console.error("Error saving todos:", error);
    });
}

function addTodoElement(todo) {
    const todoDiv = document.createElement("div");
    todoDiv.classList.add("todo");

    const newTodo = document.createElement("li");
    newTodo.innerText = todo.description;
    newTodo.classList.add("todo-item");
    todoDiv.appendChild(newTodo);

    const completedButton = document.createElement("button");
    completedButton.innerHTML = '<i class="fas fa-check-circle"></i>';
    completedButton.classList.add("complete-btn");
    if (todo.completed) {
        todoDiv.classList.add("completed");
    }
    todoDiv.appendChild(completedButton);

    const trashButton = document.createElement("button");
    trashButton.innerHTML = '<i class="fas fa-trash"></i>';
    trashButton.classList.add("trash-btn");
    todoDiv.appendChild(trashButton);

    todoList.appendChild(todoDiv);
}

function handleTodoClick(event) {
    const item = event.target;
    const todo = item.parentElement;
    const todoId = Number(todo.id);

    if (item.classList.contains("trash-btn")) {
        todo.classList.add("slide");

        fetch(`/delete/${todoId}`, {
            method: "DELETE"
        })
        .then(() => {
            removeTodoElement(todo);
        })
        .then(response => {
            if (response.ok) {
                console.log("Todos saved successfully");
            } else {
                console.error("Error saving todos:", response.status);
            }
        })
        .catch(error => {
            console.error("Error deleting todo:", error);
        });
    }

    if (item.classList.contains("complete-btn")) {
        todo.classList.toggle("completed");

        const completed = todo.classList.contains("completed");
        const updatedTodo = {
            completed
        };

        fetch(`/task/${todoId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedTodo)
        })
        .then(() => {
            saveRemoteTodos();
        })
        .catch(error => {
            console.error("Error updating todo:", error);
        });
    }
}

function filterTodo() {
    const todos = todoList.childNodes;
    todos.forEach(function (todo) {
        switch (filterOption.value) {
            case "all":
                todo.style.display = "flex";
                break;
            case "completed":
                todo.style.display = todo.classList.contains("completed") ? "flex" : "none";
                break;
            case "incomplete":
                todo.style.display = todo.classList.contains("completed") ? "none" : "flex";
                break;
        }
    });
}

function saveRemoteTodos()
{
    const todos = [];
    const todoElements = todoList.querySelectorAll(".todo");
    todoElements.forEach(todoElement => {
        const todo = {
            id: todoElement.id,
            description: todoElement.description,
            completed: todoElement.completed
        };
        todos.push(todo);
    });
    fetch(`/tasks`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(todos)
    })
    .then(response => {
        if (response.ok) {
            console.log("Todos saved successfully");
        } else {
            console.error("Error saving todos:", response.status);
        }
    })
    .catch(error => {
        console.error("Error saving todos:", error);
    });
}

function getRemoteTodos() {
    fetch('/tasks')
    .then(response => response.json())
    .then(todos => {
        todos.forEach(todo => {
            addTodoElement(todo);
        });
    })
    .catch(error => {
        console.error("Error getting todos:", error);
    });
}

function removeTodoElement(todo) {
    todo.remove();
}