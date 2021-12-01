function submitHTTPRequest(method, url, username, password, callback){
    let promise = new Promise(function(resolve, reject){
        let xhttp = new XMLHttpRequest();
        xhttp.open(method, url, true);
        xhttp.setRequestHeader("Authorization", "Basic " + btoa(username + ":" + password));
        xhttp.onload = function() {
            if (xhttp.status === 200 && xhttp.readyState === 4) {
                resolve(JSON.parse(xhttp.responseText));
            } else {
                reject("Failed authentication");
            }
        };
        xhttp.send();
    });
    promise.then(
        function(value) {callback(value)},
        function(error) {console.log(error)}
    )
}

function getTableData(username, password){
    submitHTTPRequest("GET", "http://localhost:8080/players", username, password, buildTable)
}

function buildTable(responseText){
    let embedded = responseText["_embedded"];
    let players = embedded["players"];
    console.log(players[0]);

    let tableHTML = "<table class=\"table\"><thead><tr>";

    for(const i in players){
        let obj = players[i];
        delete obj['id'];
        delete obj['_links'];
    }

    for (let headers in players[0]) {
        tableHTML += "<th>" + capitalizeFirstLetter(headers) + "</th>";
    }
    tableHTML += "</tr></thead><tbody>";

    for (let eachItem in players) {
        tableHTML += "<tr>";
        let dataObj = players[eachItem];
        for (let eachValue in dataObj){
            tableHTML += "<td>" + dataObj[eachValue] + "</td>";
        }
        tableHTML += "</tr>";
    }
    tableHTML += "</tbody></table>"
    document.getElementById("table").innerHTML = tableHTML;
}

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}