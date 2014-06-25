var lawn;
var queue = [];
var interval;

var ws = new WebSocket("ws://"+document.location.host+document.location.pathname+"/websocket");
ws.onopen = function() {
	console.log("Websocket Ready!!");
}
ws.onmessage= function(data) {
	console.log(data.data);
	var parsedJSON = JSON.parse(data.data);
	queue.push(parsedJSON);
}

function clock() {
	var parsedJSON = queue.shift();
	if (parsedJSON) {
		if (parsedJSON == "start") {
		    $("#mowersound")[0].play();
		} else if (parsedJSON == "end") {
		    $("#mowersound")[0].pause();
		    clearInterval(interval);
		} else if (parsedJSON['height']) {
			lawn = {
					width: parsedJSON['width'],
					height: parsedJSON['height'],
					mowers : {}
				};
				drawLawn(lawn);
		} else if (parsedJSON['id']) {
			var id = parsedJSON['id'];
			var mower = { id: id, x:  parsedJSON['x'], y:  parsedJSON['y'], orientation:  parsedJSON['orientation']};
			if (lawn.mowers[id]) {
				deleteMower(lawn.mowers[id]);
			}
			drawMower(mower);
			lawn.mowers[id] = mower;
		} else {
			$("#instructions-help").text(parsedJSON);
			$("#instructions-group").addClass("error");
		    clearInterval(interval);
		}
	}
}

function drawLawn(lawn) {
	if ($("canvas")) {
		$("canvas").remove();
	}
	$('<canvas width="' + 100 * (lawn.width+2) + '" height="' + 100 * (lawn.height+2) + '"></canvas>').appendTo("#canvas-container");
	for ( var i = 0; i <= lawn.width; i++) {
		for ( var j = 0; j <= lawn.height; j++) {
			$("canvas").drawImage({
				source : "img/grass.jpg",
				x : i * 100,
				y : j * 100,
				fromCenter : false
			});
		}
	}
	for ( var i = 0; i <= lawn.width; i++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 50+100*i, y: 50+100*(lawn.height+1),
			  font: "32pt Verdana, sans-serif",
			  text: i
			});
	}
	for ( var j = 0; j <= lawn.height; j++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 50+100*(lawn.width+1), y: 50+100*j,
			  font: "32pt Verdana, sans-serif",
			  text: lawn.height-j
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
	interval = self.setInterval(function(){clock()},300);
    ws.send($("#instructions").val());
    return false;
});
