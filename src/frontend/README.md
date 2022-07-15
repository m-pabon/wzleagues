# Frontend Template
This is the static html, css, js theme for the frontend that performs CRUD operations against the player service.<br>
The plan is to port this to a react application.

## Nginx
The docker image uses nginx to serve the web content and contains a reverse proxy so that when http requests are <br>
made they are redirected to the backend container.

## Build Docker Image for Static Frontend
``docker build -t mpabon/wzleaguesfrontend . ``

## Run Docker Container
``docker run -it -d -p 8081:80 mpabon/wzleaguesfrontend:latest``