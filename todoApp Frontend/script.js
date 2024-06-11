const apiUrl = 'http://52.58.157.40:8080/v1/todo';

function fetchToDos() {
  fetch(`${apiUrl}/getAll`)
    .then(response => response.json())
    .then(data => {
      const list = document.getElementById('todo-list');
      list.innerHTML = '';
      data.forEach(todo => {
        const item = document.createElement('li');
        item.className = 'list-group-item d-flex justify-content-between align-items-center';
        if (todo.status === 'COMPLETED') {
          item.classList.add('completed');
        }
        item.innerHTML = `
          <span id="description-${todo.id}">${todo.description}</span>
          <span class="badge badge-${todo.status === 'PENDING' ? 'secondary' : 'success'}">${todo.status}</span>
          <div>
            <button onclick="completeTask(${todo.id})" class="btn btn-sm btn-outline-secondary">${todo.status === 'PENDING' ? 'Complete' : 'Completed'}</button>
            <button onclick="editTask(${todo.id})" class="btn btn-sm btn-info">Edit</button>
            <button onclick="deleteToDo(${todo.id})" class="btn btn-sm btn-danger">Delete</button>
          </div>
        `;
        list.appendChild(item);
      });
    });
}

function createToDo() {
  const description = document.getElementById('description').value;
  fetch(`${apiUrl}/create`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ description, status: 'PENDING' })
  })
  .then(response => response.json())
  .then(() => {
    fetchToDos();
    document.getElementById('description').value = '';
  });
}

function completeTask(id) {
  fetch(`${apiUrl}/updateStatus`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ id, status: 'COMPLETED' })
  })
  .then(() => fetchToDos());
}

function editTask(id) {
  const description = prompt('Görev açıklamasını girin:', document.getElementById(`description-${id}`).innerText);
  if (description) {
    fetch(`${apiUrl}/update`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ id, description })
    })
    .then(() => fetchToDos());
  }
}

function deleteToDo(id) {
  fetch(`${apiUrl}/delete/${id}`, { method: 'DELETE' })
    .then(() => fetchToDos());
}


fetchToDos();
