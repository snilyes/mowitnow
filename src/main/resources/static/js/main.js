var queue = [];
var interval;
var stompClient = null;
var cellWith = 50;
var monitor = null;
$(connect);

function dropFile() {
	var zone = new FileDrop('instructions', {input: false});
	zone.event('send', function (files) {
	  files.each(function (file) {
	    file.readData(
	      function (str) { zone.el.value = str; },
	      function (e) { $("#instructions-error").append("<strong>Erreur</strong>: " + e).show(); },
	      'text'
	    );
	  });
	});
}
/**
 * fonction de connection vers le serveur websocket
 */
function connect() {
	if (stompClient) {
	    stompClient.disconnect();
	}
	
    var socket = new SockJS('/mowitnow');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        
        // Intercepter initialisation de la pelouse
        stompClient.subscribe('/mowers/init', function(data){
    		$("#instructions-error").text('').hide();
    		monitor = $.parseJSON(data.body);
        	draw();
        });
        
        
        // Intercepter une mise à jour des tondeuses
        stompClient.subscribe('/mowers/update', function(data){
        	queue.push($.parseJSON(data.body));
        });

        // Intercepter une erreur
        stompClient.subscribe('/mowers/error', function(data){
    		$("#instructions-error").append("<strong>Erreur</strong>: " + data.body).show();
    	});

        // Ne commencer la démo que lorsque la connexion a été établie
    	$("#start").removeAttr("disabled").removeClass('btn-default').addClass('btn-primary');
    	$("#start").text("Start!");
    	$("#start").click(function() {
    		console.log('Start!');
    		start();
    	    return false;
    	});
    	
    	dropFile();
    });
}

/**
 * Démarre la démo
 */
function start() {
	$("#demo-result").hide();
    var instructions = $("#instructions").val();
    stompClient.send("/app/execute", {}, JSON.stringify(instructions));

    // Chaque 0.5s rafraichir la vue de la pelouse
    interval = self.setInterval(function(){update();},500);
}

/**
 * Mise à jour de la vue pelouse
 */
function update() {
	var parsedJSON = queue.shift();
	if (parsedJSON) {
		move(parsedJSON);
	} else {
	    clearInterval(interval);
	    result();	    
	}
}

/**
 * Dessiner la pelouse et les tondeuses sur un canvas
 * @param monitor
 */
function draw() {
	var lawn = monitor.lawn;
	var mowers = monitor.mowers;
	var width = lawn.width;
	var height = lawn.height;
	
	if ($("canvas")) {
		$("canvas").remove();
	}
	$('<canvas id="canvas" width="' + (20 + cellWith * width) + '" height="' + (20 + cellWith * height) + '"></canvas>').appendTo("#canvas-container");

	// Afficher les cellules de la pelouse
	for ( var i = 0; i < width; i++) {
		for ( var j = 0; j < height; j++) {
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

	// Afficher l'abscisse
	for ( var i = 0; i < width; i++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 10 + cellWith * i + (cellWith / 2) , y: 10 + cellWith * (height),
			  text: i + 1
		});
	}

	// Afficher l'ordonnée
	for ( var j = 0; j < height; j++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 10, y: cellWith * (height - j - 1) + (cellWith / 2),
			  text: j + 1
		});
	}

	// Afficher les tondeuses
	for ( var i in mowers ) {
		var mower = mowers[i];
		$("canvas").drawImage({
			source : "img/mower-" + mower.orientation + ".png",
			x : 20 + mower.cell.position.x * cellWith,
			y : (height - mower.cell.position.y - 1) * cellWith,
			width: cellWith,
			height: cellWith,
			fromCenter : false,
			scale: 0.7
		});
	}
}

function move(mower) {
	var width = monitor.lawn.width;
	var height = monitor.lawn.height;
	console.debug("deplacement de la tondeuse " + mower.id);
	var older = monitor.mowers[mower.id];
	if (older.cell != mower.cell) {
		$("canvas").drawImage({
			source : "img/mowed_grass.jpg",
			x : 20 + older.cell.position.x * cellWith,
			y : (height - older.cell.position.y - 1) * cellWith,
			width: cellWith,
			height: cellWith,
			fromCenter : false
		});
	}

	$("canvas").drawImage({
		source : "img/mower-" + mower.orientation + ".png",
		x : 20 + mower.cell.position.x * cellWith,
		y : (height - mower.cell.position.y - 1) * cellWith,
		width: cellWith,
		height: cellWith,
		fromCenter : false,
		scale: 0.7
	});
	
	monitor.mowers[mower.id] = mower;
}

function result () {
	$("#demo-result").empty();
	for ( var i in monitor.mowers ) {
		var mower = monitor.mowers[i];
		$("#demo-result").append('<p>La tondeuse ' + increment(i) + ' => (' +  increment(mower.cell.position.x) + ',' + increment(mower.cell.position.y) + ',' + mower.orientation + ')</p>');
	}
    $("#demo-result").show();
}

function increment(index) {
	return (1*index + 1);
} 
