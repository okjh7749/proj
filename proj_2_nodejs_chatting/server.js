var express = require("express");

var app = express();

var bodyParser = require("body-parser");

app.use(express.static("chatting"));

app.use(bodyParser.urlencoded({ extended: false }));





var roomnum = 0;

var Messages = new Array(Array(), Array());

var Names = new Array(Array(), Array());

var Times = new Array(Array(), Array());


app.get("/inputmessage/:myroom", function (request, response) {

    var msg = request.query.msg;
    console.log(msg);
    var myroom = request.params.myroom;
    console.log(myroom);
    Messages.push = function (id, value) {
        if (!this[id]) {
            this[id] = [];
        }
        this[id].push(value)
    }
    Messages.push(myroom, msg);
    console.log("recived " + myroom + " room : " + msg);

    response.send("OK");

});//메세지 전송



app.all("/getmessage/:myroom", function (request, response) {



    var count = request.query.count;


    var myroom = request.params.myroom;

    var sendMsg = new Array();



    if (Messages[myroom].length > count)

        sendMsg = Messages[myroom].slice(count);



    response.send(JSON.stringify(sendMsg));

    console.log("send mesaages: " + JSON.stringify(sendMsg));



});//메세지 받기





app.get("/inputclient/:myroom", function (request, response) {



    var Name = request.query.name;
    var myroom = request.params.myroom;

    Names.push = function (id, value) {
        if (!this[id]) {
            this[id] = [];
        }
        this[id].push(value)
    }
    Names.push(myroom, Name);
    console.log(myroom + " room name input: " + Name);

    response.send("OK");

});//아이디 전송



app.all("/getclient/:myroom", function (request, response) {



    var count = request.query.count;
    var myroom = request.params.myroom;
    var sendname = new Array();

    if (Names.length > count)
        sendname = Names[myroom].slice(count);



    response.send(JSON.stringify(sendname));

    console.log(myroom + " room name setting: " + JSON.stringify(sendname));



});//아이디 받기



app.get("/inputRoom", function (request, response) {



    var myroom = request.query.room;
    roomnum = myroom;
    console.log("myroomNum is : " + roomnum);
    response.send("OK");

});//방번호 전송



app.all("/getRoom", function (request, response) {

    var sendroom = roomnum;

    response.send(JSON.stringify(sendroom));

    console.log("roomnumber setting : " + JSON.stringify(sendroom));


});//방번호 받기

app.get("/inputtime/:myroom", function (request, response) {

    var myroom = Number(request.params.myroom);
    var time = Number(request.query.time);
    var name_count = Number(request.query.name_count);
    var times = new Array(Array(), Array());
    Times.push = function (id, value) {
        if (!this[id]) {
            this[id] = [];
        }
        this[id].push(value)
    }
    Times.push(myroom, 0);
    Times[myroom][name_count] = time;
    Times[myroom].slice(0, 20);
   
    response.send("OK");
});

app.all("/gettime/:myroom", function (request, response) {
    var d = new Date();
    var nowtime = d.getTime();
    var myroom = Number(request.params.myroom); //
    var countNames = new Array();
    
    for (var i = 0; i < Times[myroom].length; i++) {
        
        if (Times[myroom][i] + 10000 < nowtime)
            
        Times[myroom][i] = 0;
        
        if (Times[myroom][i] != 0) {
            countNames.push(Names[myroom][i-1]);
        }

    }
    response.send(JSON.stringify(countNames));//
    console.log("man_count setting : " + JSON.stringify(countNames));


});

app.use(function (request, response) {

    response.send("hello. Node.js");

});//기본



app.listen(1997, function () {

    console.log('Server Running at http://127.0.0.1:1997');

});//서버받아오기