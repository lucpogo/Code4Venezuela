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

function preload() {
  $(document).ready(() => {
    querys = $.get("http://localhost:8080/querys", function() {})
      .done(function() {
      })
      .fail(function() {})
      .always(function() {});

    tweets = $.get(
      "http://localhost:8080/tweets/serviciopublico",
      function() {}
    )
      .done(function() {
        gotTweets = true;
        
        console.log(tweets.responseJSON);
      })
      .fail(function() {})
      .always(function() {});
 
  });
}
function setup() {

  canvas = createCanvas(800, 600);
  tweetsMap = mappa.tileMap(options);
  tweetsMap.overlay(canvas);

  // dataSource = select('#dataSource'); // get the DOM element from the HTML
  // dataSource.changed(processData); // assign callback

  currentColor = color(0, 255, 150, 100); // default color
}

function draw() {
  clear();
  if (gotTweets ) {
    if(dataProcessed){
      for (var key in tweets.responseJSON) {
        if (tweets.responseJSON.hasOwnProperty(key)) {
          fill(currentColor);
          let pixCoords = tweetsMap.latLngToPixel(
            tweets.responseJSON[key].lat,
            tweets.responseJSON[key].lon
            );
            // let diameter = Math.sqrt(tweets.responseJSON[key].count);
            let scaler = tweetsMap.zoom() /5;
            ellipse(pixCoords.x, pixCoords.y, tweets.responseJSON[key].count*scaler,tweets.responseJSON[key].count*scaler);
          }
        }
      } else {processData();}
  }



  function processData() {
    // let type = dataSource.value();
    // switch (type) {
    //   case 'query1':
    //     currentColor = color(64, 250, 200, 100);
    //     break;
    //   case 'query2':
    //     currentColor = color(255, 0, 200, 100);
    //     break;
    //   case 'query3':
    //     currentColor = color(200, 0, 100, 100);
    //     break;
    // }
    let maxValue = 0; // changed to something more generic, as we no longer only work with subs
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
    //   tweets.responseJSON[key].diameter = map(sqrt(tweets.responseJSON[key].count), minD, maxD, 35,100);
    // }
  }
  
}
