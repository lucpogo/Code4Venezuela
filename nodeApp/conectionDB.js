var  { Pool, Client } = require("pg");
var dotenv = require("dotenv");

dotenv.config();

const pool = new Pool({
  user: process.env.DB_USER,
  host: process.env.DB_HOST,
  database: process.env.DB_SCHEMA,
  password: process.env.DB_PASSWORD,
  port: process.env.DB_PORT
})

// pool.query('SELECT NOW()', (err, res) => {
//   console.log(err, res)
//   pool.end()
// })

const client = new Client({
  user: process.env.DB_USER,
  host: process.env.DB_HOST,
  database: process.env.DB_SCHEMA,
  password: process.env.DB_PASSWORD,
  port: process.env.DB_PORT,
})
client.connect()

// client.query('SELECT NOW()', (err, res) => {
//   console.log(err, res)
//   client.end()
// })

module.exports = pool;
