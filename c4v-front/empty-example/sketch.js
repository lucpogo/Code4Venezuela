const mappa = new Mappa("Leaflet");
let tweetsMap;
let canvas;
let dataProcessed = false;
let gotTweets = false;
let querys;
let currentColor;
const options = {
  lat: 7,
  lng: -66,
  zoom: 6.4,
  style: "http://{s}.tile.osm.org/{z}/{x}/{y}.png"
};
const URL = "http://40.117.115.124:8080/";

function preload() {
  querys = $.get(URL + "querys", function() {})
    .done(function() {
      loadTweets();
      loadDataSources();
    })
    .fail(function() {})
    .always(function() {});
}
function loadTweets() {
  tweets = $.get(URL + "tweets/" + $("#dataSource").val(), function() {})
    .done(function() {
      gotTweets = true;
    })
    .fail(function() {})
    .always(function() {
    });
}
function setup() {
  canvas = createCanvas(1200, 600);
  tweetsMap = mappa.tileMap(options);
  tweetsMap.overlay(canvas);

  // dataSource = $("#dataSource"); // get the DOM element from the HTML
  $("#dataSource").change(function() {
   loadTweets();
  }); // assign callback

  currentColor = color(0, 255, 150, 100); // default color
}

function draw() {
  if (gotTweets) {
    clear();
    if (dataProcessed) {
      for (var key in tweets.responseJSON) {
        if (tweets.responseJSON.hasOwnProperty(key)) {
          if (
            tweetsMap.map.getBounds().contains({
              lat: tweets.responseJSON[key].lat,
              lng: tweets.responseJSON[key].lon
            })
          ) {
            fill(currentColor);
            let pixCoords = tweetsMap.latLngToPixel(
              tweets.responseJSON[key].lat,
              tweets.responseJSON[key].lon
            );

            const zoom = tweetsMap.zoom();
            const scl = pow(2, zoom) * 0.1; // * sin(frameCount * 0.1);
            ellipse(
              pixCoords.x,
              pixCoords.y,
              sqrt(tweets.responseJSON[key].count) * scl,
              sqrt(tweets.responseJSON[key].count) * scl
            );
          }
        }
      }
    } else {
      mapMaxMin();
    }
  }
}

function mapMaxMin() {
  let maxValue = 0;
  let minValue = Infinity;

  for (var key in tweets.responseJSON) {
    if (tweets.responseJSON.hasOwnProperty(key)) {
      if (tweets.responseJSON[key].count > maxValue) {
        maxValue = tweets.responseJSON[key].count;
      }
      if (tweets.responseJSON[key].count < minValue) {
        minValue = tweets.responseJSON[key].count;
      }
    }
  }
  dataProcessed = true;
  let minD = sqrt(minValue);
  let maxD = sqrt(maxValue);

  // for (var key in tweets.responseJSON) {
  //   tweets.responseJSON[key].diameter = map(sqrt(tweets.responseJSON[key].count), minD, maxD, 2,10);
  // }
}
