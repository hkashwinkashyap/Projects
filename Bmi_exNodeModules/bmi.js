const express = require("express");
const app = express();
const bodyParser = require("body-parser");

app.use(bodyParser.urlencoded({extended: true}));

app.get("/", function(req,res){
    res.sendFile(__dirname + "/index.html");
});

app.post("/", function(req,res){
    var w = Number(req.body.w);
    var h = Number(req.body.h);
    var bmi = w/(h^2);
    res.send("Your BMI is " + Math.round(bmi));
});

app.listen(3000, function(){
    console.log("Server is running on 3000");
});