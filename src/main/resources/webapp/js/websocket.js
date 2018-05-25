var ip;
var port;
function load() {
    connect();
}

function connect() {
    // var host = "ws://207.246.96.42:8125"
    var host = "ws://"+ ip +":"+port;
//    var host = "ws://192.168.1.105:8125"
//     alert(host);
    console.log("####websocket info " + host);
    socket = new WebSocket(host);
    try {
        socket.onopen = function (msg) {
            status("连接成功");
        };
        socket.onmessage = function (msg) {
            if (typeof msg.data == "string") {
                showLog(msg.data);
            } else {
                alert("非文本消息onmessage" + msg);
            }
        };

        socket.onerror = function (msg) {
            console.log('onerror received a message', msg);
            status("连接失败");
        };

        socket.onclose = function (msg) {
            console.log('onclose received a message', msg);
            status("连接关闭");
        };
    } catch (ex) {
        console.log('catch received a message', msg);
    }
}

function send() {
    var msg = getId("sendText").value
    socket.send(msg);
}

function disconnect() {
    try {
        socket.close();
        socket = null;
    } catch (ex) {
    }
}

function refresh() {
    try {
        socket.close();
        socket = null;
    } catch (ex) {
    }
    return "自定义内容";
}

window.onbeforeunload = function () {
    try {
        socket.close();
        socket = null;
    } catch (ex) {
    }
};

function getId(id) {
    return document.getElementById(id);
}


function getText() {
    return document.getElementById("txtContent").value
}

function status(status) {
    getId("status").innerHTML = status;
}

function showLog(msg) {
    var div = document.getElementById('txtContent');
            div.value += "\r\n" + msg;
            div.scrollTop = div.scrollHeight;
}

function clearLog() {
    document.getElementById("txtContent").value = "";
}


function onkey(event) {
    if (event.keyCode == 13) {
        send();
    }
}
