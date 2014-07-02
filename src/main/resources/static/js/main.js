var lawn;
var queue = [];
var interval;
var stompClient = null;
var tondeuses = null;
var pelouse = null;
$(connect);

function connect() {
	if (stompClient) {
	    stompClient.disconnect();
	}
    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
       // setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function(data){
        	queue.push($.parseJSON(data.body));
        });

        stompClient.subscribe('/topic/load', function(data){
        	drawLawn($.parseJSON(data.body));
        });

    });
}

function start() {
    var instructions = $("#instructions").val();
    stompClient.send("/app/hello", {}, JSON.stringify(instructions));
    interval = self.setInterval(function(){clock()},500);
}

function clock() {
	var parsedJSON = queue.shift();
	if (parsedJSON) {
    	drawLawn(parsedJSON);
	} else {
		$("#instructions-help").text(parsedJSON);
		$("#instructions-group").addClass("error");
	    clearInterval(interval);
	}
}

function drawLawn(monitor) {
	pelouse = monitor.pelouse;
	tondeuses = monitor.tondeuses;

	var cellWith = 50;
	if ($("canvas")) {
		$("canvas").remove();
	}
	$('<canvas width="' + (20 + cellWith * pelouse.largeur) + '" height="' + (20 + cellWith * pelouse.longueur) + '"></canvas>').appendTo("#canvas-container");
	for ( var i = 0; i < pelouse.largeur; i++) {
		for ( var j = 0; j < pelouse.longueur; j++) {
			$("canvas").drawImage({
				source : "img/grass.jpg",
				x : 20 + i * cellWith,
				y : j * cellWith,
				width: cellWith,
				height: cellWith,
				fromCenter : false
			});
		}
	}

	for ( var i = 0; i < pelouse.largeur; i++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 10 + cellWith * i + (cellWith / 2) , y: 10 + cellWith * (pelouse.longueur),
			  text: i
		});
	}

	for ( var j = 0; j < pelouse.longueur; j++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 10, y: cellWith * j + (cellWith / 2),
			  text: pelouse.longueur - j - 1
		});
	}

	for ( var i in tondeuses ) {
		var tondeuse = tondeuses[i];
		$("canvas").drawImage({
			source : "img/mower-" + tondeuse.orientation + ".png",
			x : 20 + tondeuse.cellule.position.x * cellWith,
			y : tondeuse.cellule.position.y * cellWith,
			width: cellWith,
			height: cellWith,
			fromCenter : false,
			scale: 0.7
		});
	}
}

function drawMower(mower) {
	$("canvas").drawImage({
		source : "img/mowed_grass.jpg",
		x : mower.x * 100,
		y : ((lawn.height) - mower.y) * 100,
		fromCenter : false
	});
	$("canvas").drawImage({
		source : "img/mower-" + mower.orientation + ".png",
		x : mower.x * 100,
		y : ((lawn.height) - mower.y) * 100,
		fromCenter : false
	});
}

function deleteMower(mower) {
	$("canvas").drawImage({
		source : "img/mowed_grass.jpg",
		x : mower.x * 100,
		y : ((lawn.height) - mower.y) * 100,
		fromCenter : false
	});
}


$("#start").click(function() {
	$("#instructions-help").text("");
	$("#instructions-group").removeClass("error");
	console.log('Start!');
	//interval = self.setInterval(function(){clock()},300);
	start();
    return false;
});

