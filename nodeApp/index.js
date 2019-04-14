const express = require("express");
const bodyParser = require("body-parser");
var cors = require("cors");
const path = require('path')

const dotenv = require("dotenv");
dotenv.config();
//CONEXION BD
var Pool = require("pg").Pool;

const pool = new Pool({
  user: process.env.DB_USER,
  host: process.env.DB_HOST,
  schema: process.env.DB_SCHEMA,
  database: process.env.DB_DATABASE,
  password: process.env.DB_PASSWORD,
  port: process.env.DB_PORT
});

var app = express();

app.set("port", process.env.PORT || 3000);
app.use("/image", express.static("server/uploads/"));
app.use(
  bodyParser.urlencoded({
    extended: true
  })
);

app.use(cors());
app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept"
  );
  res.header("Access-Control-Allow-Credentials", "true");
  next();
});

app.use(bodyParser.json());


app.get("/querys", (req, res) => {
  pool.query("SELECT DISTINCT query FROM tweets;", (error, results) => {
    if (error) {
      throw error;
    }
    res.status(200).json(results.rows);
  });
});
app.get("/ubicaciones", (req, res) => {
  pool.query("SELECT DISTINCT ubicacion FROM tweets;", (error, results) => {
    if (error) {
      throw error;
    }
    res.status(200).json(results.rows);
  });
});
app.get("/tweets/:query", (req, res) => {
  pool.query(
    "SELECT count(*), ubicacion, lat, lon FROM( SELECT tweets.ubicacion, tweets.tweet_date, ubicaciones.lat, ubicaciones.lon FROM tweets JOIN ubicaciones ON (tweets.ubicacion=ubicaciones.ubicacion) WHERE query='" +
      req.params.query + "' AND is_retweet=false) AS tab GROUP BY ubicacion, lat, lon;",
    (error, results) => {
      if (error) {
        throw error;
      } else if (isEmpty(results.rows)) {
        res.status(404).send("No existe la query en la BD");
      } else {
        res.status(200).json(results.rows);
      }
    }
  );
});
app.get("/tweets", (req, res) => {
  pool.query("SELECT * FROM tweets;", (error, results) => {
    if (error) {
      throw error;
    }
    res.status(200).json(results.rows);
  });
});
app.get('/map', function(request, response){
  response.status(200).sendFile(path.resolve('../c4v-front/empty-example/index.html'));
});
app.listen(process.env.PORT, function() {
  console.log("Running app on port " + process.env.PORT);
});

function isEmpty(obj) {
  for (var key in obj) {
    if (obj.hasOwnProperty(key)) return false;
  }
  return true;
}
