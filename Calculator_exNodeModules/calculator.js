const express = require("express");
const app = express();
const bodyParser = require("body-parser");

app.use(bodyParser.urlencoded({extended: true}));

app.get("/", function(req, res){

    res.sendFile(__dirname + "/index.html");
});

app.post("/", function(req, res){
    
    var result = Number(req.body.num1) + Number(req.body.num2);
    res.send("Addition result is " + result);
});

app.listen(3000, function(){

    console.log("Server is running on 3000");
});