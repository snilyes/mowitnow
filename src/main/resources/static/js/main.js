var queue = [];
var interval;
var stompClient = null;
$(connect);

function connect() {
	if (stompClient) {
	    stompClient.disconnect();
	}
	
    var socket = new SockJS('/mowitnow');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/mowers/init', function(data){
        	drawLawn($.parseJSON(data.body));
        });
        stompClient.subscribe('/mowers/update', function(data){
        	queue.push($.parseJSON(data.body));
        });

        stompClient.subscribe('/mowers/error', function(data){
    		$("#instructions-help").text(data.body);
    		$("#instructions-group").removeClass("error");
    	});

    	$("#start").removeAttr("disabled");
    	$("#start").text("Start!");

    	$("#start").click(function() {
    		console.log('Start!');
    		start();
    	    return false;
    	});
    });
}

function start() {
    var instructions = $("#instructions").val();
    stompClient.send("/app/execute", {}, JSON.stringify(instructions));
    $("#mowersound")[0].play();
    interval = self.setInterval(function(){clock();},500);
}

function clock() {
	var parsedJSON = queue.shift();
	if (parsedJSON) {
    	drawLawn(parsedJSON);
	} else {
		$("#instructions-help").text(parsedJSON);
		$("#instructions-group").addClass("error");
	    clearInterval(interval);
	    $("#mowersound")[0].pause();
	}
}

function drawLawn(monitor) {
	var pelouse = monitor.pelouse;
	var tondeuses = monitor.tondeuses;

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
			  text: i + 1
		});
	}

	for ( var j = 0; j < pelouse.longueur; j++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 10, y: cellWith * (pelouse.longueur - j - 1) + (cellWith / 2),
			  text: j + 1
		});
	}

	for ( var i in tondeuses ) {
		var tondeuse = tondeuses[i];
		$("canvas").drawImage({
			source : "img/mower-" + tondeuse.orientation + ".png",
			x : 20 + tondeuse.cellule.position.x * cellWith,
			y : (pelouse.longueur - tondeuse.cellule.position.y - 1) * cellWith,
			width: cellWith,
			height: cellWith,
			fromCenter : false,
			scale: 0.7
		});
	}
}

