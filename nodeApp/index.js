const http = require("http");
const bodyParser = require("body-parser");
const HttpDispatcher = require("httpdispatcher");

const PORT = 8080;

dispatcher = new HttpDispatcher();

function handleRequest(request, response) {
  try {
    // Dispatch
    dispatcher.dispatch(request, response);
  } catch (err) {
    console.log(err);
  }
}

dispatcher.onGet("/", function(req, res) {
  res.writeHead(200, { "Content-Type": "text/html" });
  res.end("<h1>Hey, this is the homepage of CODE 4 VENEZUELA</h1>");
});

dispatcher.onGet("/welcome", function(req, res) {
  res.writeHead(200, { "Content-Type": "text/plain" });
  res.end("Welcome homepage");
});

dispatcher.onError(function(req, res) {
  console.log("error");
  res.writeHead(404);
  res.end("Error, the URL doesn't exist");
});

const server = http.createServer(handleRequest);

const port =  PORT || 1337;
server.listen(port, function() {
  // Callback triggered when server is successfully listening. Hurray!
  console.log("Server listening on: http://localhost:%s", PORT);
});
