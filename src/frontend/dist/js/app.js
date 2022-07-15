const url = 'http://localhost:8080/api/players';
const GET = 'GET';
const POST = 'POST';
const PUT = 'PUT';
const DELETE = 'DELETE';

// Grab UI Elements
const table = document.querySelector('table');
const tableBody = document.querySelector('tbody');
const refreshBtn = document.querySelector('#refresh');
const newBtn = document.querySelector('#new');
const createModal = document.querySelector('#createModal');
const editModal = document.querySelector('#editModal');
const createForm = document.querySelector('#createForm');
const editForm = document.querySelector('#editForm');
const confirmEditBtn = document.querySelector('#confirmEdit');

// Load all event listeners
loadEventListeners();

function loadEventListeners() {
  document.addEventListener('DOMContentLoaded', getRows);
  newBtn.addEventListener('click', addRow);
  refreshBtn.addEventListener('click', refreshRows);
  tableBody.addEventListener('click', removeOrEditRow);
  createModal.addEventListener('hidden.bs.modal', function (event) {
    createForm.reset();
  });
  editModal.addEventListener('hidden.bs.modal', function (event) {
    editForm.reset();
  });
  confirmEditBtn.addEventListener('click', editRow);
}

function getRows(){
  httpRequest(GET, url, function(data){
    const tableData = data._embedded.players;
    tableData.forEach(function(object) {
        const tr = document.createElement('tr');
        tr.className = 'player-row';
        const deleteButton = `
          <button type="button" class="btn btn-danger btn-sm delete">
            <span class="fa fa-trash-o fa-sm delete" aria-hidden="true"></span>
          </button>`
        const editButton = `
          <button type="button" class="btn btn-warning btn-sm edit" data-bs-toggle="modal" data-bs-target="#editModal">
            <span class="fa fa-pencil-square-o fa-sm edit" aria-hidden="true"></span>
          </button>
        `
        const td = `
          <td>${object.name}</td>
          <td>${object.activisionId}</td>
          <td>${object.email}</td>
          <td>${object.rank}</td>
          <td><div>${editButton}${deleteButton}</div></td>
        `;
        tr.innerHTML = td;
        tableBody.appendChild(tr);
    });
  });
}

function addRow(){
  const name = document.querySelector('#createName').value;
  const activisionId = document.querySelector('#createActivisionId').value;
  const email = document.querySelector('#createEmail').value;
  const rank = document.querySelector('input[name="rank"]:checked').value;
  const player = {
    name: name,
    email: email,
    activisionId: activisionId,
    rank: rank
  }
  const data = JSON.stringify(player);
  httpRequest(POST, url, function(res){
    refreshRows();
  }, data);
}

function refreshRows(){
  while(tableBody.firstChild) {
    tableBody.removeChild(tableBody.firstChild);
  }
  getRows();
}

function removeOrEditRow(e){
  const tds = e.target.closest('.player-row').children;
  //Delete Player
  if(e.target.classList.contains('delete')){
    //Get Activision Id
    const activisionId = tds[1].textContent;

    //Get Mongo Id
    let mongoId;
    const urlEncodedActivisionId = activisionId.replace('#', '%23');
    const endpoint = url + `/${urlEncodedActivisionId}`;
    httpRequest(GET, endpoint, function(data){
      mongoId = data.id;
      httpRequest(DELETE, (url + `/${mongoId}`), function(data){
        refreshRows();
      });
    });
  }
  //Edit player modal will spawn. Additional Confirmation needed to edit player
  else{
    //Populate Edit Modal
    document.querySelector('#editName').value = tds[0].textContent;
    document.querySelector('#editActivisionId').value = tds[1].textContent;
    document.querySelector('#editEmail').value = tds[2].textContent;
    radioCheck(tds[3].textContent);
  } 
}

//Runs after confirming an edited player
function editRow(){
  //Get the value of the edit modal prior to closing
  const name = document.querySelector('#editName').value;
  const activisionId = document.querySelector('#editActivisionId').value;
  const email = document.querySelector('#editEmail').value;
  const rank = document.querySelector('input[name="rank"]:checked').value;

  //Get the Mongo ID of the player
  let mongoId;
  const urlEncodedActivisionId = activisionId.replace('#', '%23');
  const endpoint = url + `/${urlEncodedActivisionId}`;
  httpRequest(GET, endpoint, function(data){
    mongoId = data.id;
    //HTTP PUT player to edit their data
    const player = {
      name: name,
      email: email,
      activisionId: activisionId,
      rank: rank
    }
    const playerInfo = JSON.stringify(player);
    httpRequest(PUT, (url + `/${mongoId}`), function(data){
      refreshRows();
    }, playerInfo);
  });
}

function radioCheck(rank){
  if(rank === 'BRONZE'){
    document.querySelector('#editBronzeRadio').checked = true;
  }
  else if(rank === 'SILVER'){
    document.querySelector('#editSilverRadio').checked = true;
  }
  else if(rank === 'GOLD'){
    document.querySelector('#editGoldRadio').checked = true;
  }
  else{
    document.querySelector('#editDiamondRadio').checked = true;
  }
}


function httpRequest(method, url, fn, body){
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function(){
    if(this.readyState == 4 && (this.status == 200 || this.status == 201)){
      if(this.responseText){
        fn(JSON.parse(this.responseText));
      }
      else{
        fn('');
      }
    }
  }
  xhttp.open(method, url);
  xhttp.setRequestHeader("Content-type", "application/json");
  if(body){
    xhttp.send(body);
  }
  else{
    xhttp.send();
  }
}
