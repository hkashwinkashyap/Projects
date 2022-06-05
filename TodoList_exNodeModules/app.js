const express = require('express');
const bodyParser = require('body-parser');
const mongoose = require('mongoose');

const app = express();

app.set('view engine', 'ejs');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static("public"));

mongoose.connect("mongodb://localhost:27017/todolistDB");

const itemsSchema = {
    name: String
};

const Item = mongoose.model("Item", itemsSchema);


app.get("/", function (req, res) {
    Item.find({}, function (err, foundItems) {
        res.render("index", { listTitle: "Today", inputItems: foundItems });
    });
});

app.post("/", function (req, res) {
    const itemName = req.body.newItem;
    const item = new Item({
        name: itemName
    });
    item.save();
    res.redirect("/");
});

app.post("/delete", function (req, res) {
    const checkedItemId = req.body.checkbox;
   
    Item.findByIdAndRemove(checkedItemId, function (err) {
        if (!err) {
            console.log("Successfully deleted the checked item");
        }
    });
    res.redirect("/");
});

app.listen(3000, function () {
    console.log("Server started on port 3000");
});
