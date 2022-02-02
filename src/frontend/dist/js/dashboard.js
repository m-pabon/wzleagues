document.addEventListener( "DOMContentLoaded", get_json_data, false ); 

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
  xmlhttp.setRequestHeader("Authorization", "Basic " + btoa(""));
  xmlhttp.send();
}
function append_json(data){
  console.log(data)
  let tableData = data._embedded.players;
  console.log(tableData)
  var table = document.getElementById('table');
  tableData.forEach(function(object) {
      var tr = document.createElement('tr');
      tr.innerHTML = '<td>' + object.name + '</td>' +
      '<td>' + object.activisionId + '</td>' +
      '<td>' + object.email + '</td>' +
      '<td>' + object.rank + '</td>';
      table.appendChild(tr);
  });
}

function refresh_table(){
  const myNode = document.getElementById("table");
  while (myNode.lastElementChild) {
    myNode.removeChild(myNode.lastElementChild);
  }
  get_json_data();
}