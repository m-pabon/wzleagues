let playerIds = [];
let players = [];
let editablePlayerId;

document.addEventListener( "DOMContentLoaded", get_json_data, false ); 

var myModalEl = document.getElementById('exampleModal')
myModalEl.addEventListener('hidden.bs.modal', function (event) {
  document.querySelectorAll('input').value = '';
})


function get_json_data(){
  var json_url = 'http://localhost:8080/api/players';

  xmlhttp = new XMLHttpRequest();
  xmlhttp.onreadystatechange = function() { 
      if (this.readyState == 4 && this.status == 200) {
          var data = JSON.parse(this.responseText); 
          append_json(data);
      }
  }

  xmlhttp.open("GET", json_url, true);
  xmlhttp.setRequestHeader("Content-type", "application/json");
  //TODO: Research authentication system
  // xmlhttp.setRequestHeader("Authorization", "Basic " + btoa(""));
  xmlhttp.send();
}
function append_json(data){
  playerIds.length = 0;
  players.length = 0;
  const tableData = data._embedded.players;
  const table = document.querySelector('#table');
  tableData.forEach(function(object) {
      const tr = document.createElement('tr');
      tr.className = 'player-row';
      const deleteButton = `
        <button type="button" class="btn btn-danger btn-sm" onclick="deletePlayer(this.parentElement.parentElement.parentElement.rowIndex)">
          <span class="fa fa-trash-o fa-sm" aria-hidden="true"></span>
        </button>`
      const editButton = `
        <button type="button" class="btn btn-warning btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="editPlayerModalFill(this.parentElement.parentElement.parentElement.rowIndex)">
          <span class="fa fa-pencil-square-o fa-sm" aria-hidden="true"></span>
        </button>
      `
      const td = `
        <td>${object.name}</td>
        <td>${object.activisionId}</td>
        <td>${object.email}</td>
        <td>${getRankIcon(object.rank)}</td>
        <td><div>${editButton}${deleteButton}</div></td>
      `;
      tr.innerHTML = td;
      table.appendChild(tr);
      playerIds.push(object.id);
      players.push(object);
  });
}

function editPlayerModalFill(rowIndex){
  editablePlayerId = playerIds[rowIndex-1];
  console.log(editablePlayerId);
  console.log(players);
  console.log(rowIndex);
  let player = players[rowIndex-1];
  console.log(player);
  document.querySelector('#editName').value = player.name;
  document.querySelector('#editActivisionId').value = player.activisionId;
  document.querySelector('#editEmail').value = player.email;
  document.querySelector('#editRank').value = player.rank;
}



function editPlayer(){
  const name = document.querySelector('#editName').value;
  const activisionId = document.querySelector('#editActivisionId').value;
  const email = document.querySelector('#editEmail').value;
  const rank = document.querySelector('#editRank').value;
  const player = {
    name: name,
    email: email,
    activisionId: activisionId,
    rank: rank
  }
  const data = JSON.stringify(player);

  var url = `http://localhost:8080/api/players/${editablePlayerId}`;

  console.log(`Editing: ${editablePlayerId}`);

  xmlhttp = new XMLHttpRequest();
  xmlhttp.onreadystatechange = function() { 
      if (this.readyState == 4 && this.status == 201) {
          console.log(`Updated: ${editablePlayerId}`);
          refresh_table();
      }
  }

  xmlhttp.open("PUT", url, true);
  xmlhttp.setRequestHeader("Content-type", "application/json");
  //TODO: Research authentication system
  // xmlhttp.setRequestHeader("Authorization", "Basic " + btoa(""));
  xmlhttp.send(data);
}

function createPlayer(){
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

  var url = `http://localhost:8080/api/players`

  xmlhttp = new XMLHttpRequest();
  xmlhttp.onreadystatechange = function() { 
      if (this.readyState == 4 && this.status == 201) {
        console.log(JSON.parse(this.responseText));
        refresh_table();
      }
  }

  xmlhttp.open("POST", url, true);
  xmlhttp.setRequestHeader("Content-type", "application/json");
  //TODO: Research authentication system
  // xmlhttp.setRequestHeader("Authorization", "Basic " + btoa(""));
  xmlhttp.send(data);
}

function deletePlayer(rowIndex){
  if(confirm('Delete Player?')){
    let playerId = playerIds[rowIndex - 1];
    let player = players[rowIndex - 1];
    const rows = document.querySelectorAll('.player-row');
    var url = `http://localhost:8080/api/players/${playerId}`;
    xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() { 
        if (this.readyState == 4 && this.status == 200) {
          console.log(`Deleted: ${playerId}`);
          rows[rowIndex - 1].remove();
          playerIds.splice(rowIndex-1, rowIndex-1);
          players.splice(rowIndex-1, rowIndex-1);
        }
    }
    xmlhttp.open("DELETE", url, true);
    xmlhttp.setRequestHeader("Content-type", "application/json");
    xmlhttp.send();
  }
}

function refresh_table(){
  const myNode = document.getElementById("table");
  while (myNode.lastElementChild) {
    myNode.removeChild(myNode.lastElementChild);
  }
  get_json_data();
}

function getRankIcon(rank){
  if(rank === 'BRONZE'){
     return '<img src="img/bronze-medal.png" width="32" height="32"></img>'
  }
  else{
    return rank;
  }
}