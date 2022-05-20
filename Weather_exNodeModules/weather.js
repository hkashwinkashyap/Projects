const express = require("express");
const app = express();
const bodyParser = require("body-parser");
const https = require("https");

app.use(bodyParser.urlencoded({extended: true}));


app.get("/", function(req, res){
    res.sendFile(__dirname + "/index.html");
});

app.post("/", function(req, res){
    const query = req.body.cityName;
    const apikey = "fc08d4bb35dd626b152e19f2de7c3463";
    const unit = "metric";
    const url = "https://api.openweathermap.org/data/2.5/weather?q=" + query + "&APPID=" + apikey + "&units=" + unit;

    https.get(url, function(response){
        console.log(response.statusCode);

        response.on("data", function(data){
            const weatherData = JSON.parse(data);
            const temp = weatherData.main.temp;
            const weatherDescription = weatherData.weather[0].description;
            const icon = weatherData.weather[0].icon;
            const imgURL = "http://openweathermap.org/img/wn/" + icon + "@2x.png";
            res.setHeader('Content-Type', 'text/html');
            res.write("<h1>The Temperature in " + query + " is " + temp + " degrees Celcius</h1>");
            res.write("<p>The Weather is currently " + weatherDescription + "</p>");
            res.write("<img src='" + imgURL + "'>");
            res.end();
        });
    });
});

app.listen(3000, function() {
    console.log("Server is running on port 3000");
});