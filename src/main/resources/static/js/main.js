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
    		$("#instructions-error").text('').hide();
        	drawLawn($.parseJSON(data.body));
        });
        stompClient.subscribe('/mowers/update', function(data){
        	queue.push($.parseJSON(data.body));
        });

        stompClient.subscribe('/mowers/error', function(data){
    		$("#instructions-error").append("<strong>Erreur</strong>: " + data.body).show();
    	});

    	$("#start").removeAttr("disabled").removeClass('btn-default').addClass('btn-primary');
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
	    clearInterval(interval);
	    $("#mowersound")[0].pause();
	}
}

function drawLawn(monitor) {
	var lawn = monitor.lawn;
	var mowers = monitor.mowers;

	var cellWith = 50;
	if ($("canvas")) {
		$("canvas").remove();
	}
	$('<canvas width="' + (20 + cellWith * lawn.width) + '" height="' + (20 + cellWith * lawn.height) + '"></canvas>').appendTo("#canvas-container");
	for ( var i = 0; i < lawn.width; i++) {
		for ( var j = 0; j < lawn.height; j++) {
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

	for ( var i = 0; i < lawn.width; i++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 10 + cellWith * i + (cellWith / 2) , y: 10 + cellWith * (lawn.height),
			  text: i + 1
		});
	}

	for ( var j = 0; j < lawn.height; j++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 10, y: cellWith * (lawn.height - j - 1) + (cellWith / 2),
			  text: j + 1
		});
	}

	for ( var i in mowers ) {
		var mower = mowers[i];
		$("canvas").drawImage({
			source : "img/mower-" + mower.orientation + ".png",
			x : 20 + mower.cell.position.x * cellWith,
			y : (lawn.height - mower.cell.position.y - 1) * cellWith,
			width: cellWith,
			height: cellWith,
			fromCenter : false,
			scale: 0.7
		});
	}
}

