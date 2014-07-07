var queue = [];
var interval;
var stompClient = null;
$(connect);

function dropFile() {
	var zone = new FileDrop('instructions', {input: false});
	zone.event('send', function (files) {
	  files.each(function (file) {
	    file.readData(
	      function (str) { zone.el.value = str; },
	      function (e) { alert('Terrible error!'); },
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
        	draw($.parseJSON(data.body));
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
    $("#mowersound")[0].play();
    
    // Chaque 0.5s rafraichir la vue de la pelouse
    interval = self.setInterval(function(){update();},500);
}

/**
 * Mise à jour de la vue pelouse
 */
function update() {
	var parsedJSON = queue.shift();
	if (parsedJSON) {
    	draw(parsedJSON);
	} else {
	    clearInterval(interval);
	    $("#mowersound")[0].pause();
	    $("#demo-result").show();
	}
}

/**
 * Dessiner la pelouse et les tondeuses sur un canvas
 * @param monitor
 */
function draw(monitor) {
	var lawn = monitor.lawn;
	var mowers = monitor.mowers;

	var cellWith = 50;
	if ($("canvas")) {
		$("canvas").remove();
	}
	$('<canvas id="canvas" width="' + (20 + cellWith * lawn.width) + '" height="' + (20 + cellWith * lawn.height) + '"></canvas>').appendTo("#canvas-container");

	// Afficher les cellules de la pelouse
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

	// Afficher l'abscisse
	for ( var i = 0; i < lawn.width; i++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 10 + cellWith * i + (cellWith / 2) , y: 10 + cellWith * (lawn.height),
			  text: i + 1
		});
	}

	// Afficher l'ordonnée
	for ( var j = 0; j < lawn.height; j++) {
		$("canvas").drawText({
			  fillStyle: "#9cf",
			  strokeStyle: "#2a5",
			  strokeWidth: 1,
			  x: 10, y: cellWith * (lawn.height - j - 1) + (cellWith / 2),
			  text: j + 1
		});
	}

	// Afficher les tondeuses
	$("#demo-result").empty();
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
		$("#demo-result").append('<p>La tondeuse ' + (i*1 + 1) + ' => (' +  mower.cell.position.x + ',' + mower.cell.position.y + ',' + mower.orientation + ')</p>');
	}
}
