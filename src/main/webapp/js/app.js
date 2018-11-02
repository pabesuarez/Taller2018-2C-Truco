var width = 512;
var height = 550;

var cartasPropias = [];
var cartasOponente = [];
var cartasMesaPropias = [];
var cartasMesaOponente = [];

var layer = new Konva.Layer();

$(function(){
	actualizar();		
});


function refresh(){
	console.log("draw");
	console.log(cartasPropias[1]);
	layer.draw();
}

function dibujar(carta,valorCarta){
	carta.setFillPatternOffset({ x : (valorCarta%10)*80, y : Math.floor(valorCarta/10)*123});
}

function actualizar(){
	
	var send = {}
	console.log("asd");
	send["jugador"] = jugador;
	send["partidaID"] = idPartida;
    $.ajax({
        type: 'POST',
        contentType : "application/json",
        url: '/proyecto-limpio-spring/app/actualizar',
        datatype: 'json',
        data: JSON.stringify(send),
        success: function (data) {
        	if(data.partidaID == undefined){
        		setTimeout(function(){ actualizar() },1000);
        	}else{
        		console.log(data);
        		if (jugador==1){
		        	for(i=0;i<=2;i++){
			        	dibujar(cartasPropias[i],data.manoJugador1[i]);
			        	dibujar(cartasOponente[i],40);
		        	}
		        	for(i=0;i<=2;i++){
			        	if (data.cartasEnJuego1[i] != 3){
			        		dibujar(cartasPropias[data.cartasEnJuego1[i]],41);
			        		dibujar(cartasMesaPropias[i],data.manoJugador1[data.cartasEnJuego1[i]]);
			        	}else{
			        		dibujar(cartasMesaPropias[i],41);
			        	}
			        	if (data.cartasEnJuego2[i] != 3){
			        		dibujar(cartasOponente[data.cartasEnJuego2[i]],41);
			        		dibujar(cartasMesaOponente[i],data.manoJugador2[data.cartasEnJuego2[i]]);
			        	}else{
			        		dibujar(cartasMesaOponente[i],41);
			        	}
		        	}
        		}else{
		        	for(i=0;i<=2;i++){
			        	dibujar(cartasPropias[i],data.manoJugador2[i]);
			        	dibujar(cartasOponente[i],40);
		        	}
		        	for(i=0;i<=2;i++){
			        	if (data.cartasEnJuego2[i] != 3){
			        		dibujar(cartasPropias[data.cartasEnJuego2[i]],41);
			        		dibujar(cartasMesaPropias[i],data.manoJugador2[data.cartasEnJuego2[i]]);
			        	}else{
			        		dibujar(cartasMesaPropias[i],41);
			        	}
			        	if (data.cartasEnJuego1[i] != 3){
			        		dibujar(cartasOponente[data.cartasEnJuego1[i]],41);
			        		dibujar(cartasMesaOponente[i],data.manoJugador1[data.cartasEnJuego1[i]]);
			        	}else{
			        		dibujar(cartasMesaOponente[i],41);
			        	}
		        	}
        		}
        		turno=data.turno;
            	estado=data.estado;
            	refresh();
            	setTimeout(function(){ actualizar() },1000);
        	}	
        },
    });
}

function loadImages(sources, callback) {
    var images = {};
    var loadedImages = 0;
    var numImages = 0;
    for(var src in sources) {
        numImages++;
    }
    for(var src in sources) {
        images[src] = new Image();
        images[src].onload = function() {
            if(++loadedImages >= numImages) {
                callback(images);
            }
        };
        images[src].src = sources[src];
    }
}
function draw(images) {
    var width = window.innerWidth;
    var height = window.innerHeight;

    var stage = new Konva.Stage({
        container: 'juego',
        width: width,
        height: height
    });
    

    cartasPropias[0] = new Konva.Rect({
        x: 112,
        y: 421,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
     
    cartasPropias[1] = new Konva.Rect({
        x: 200,
        y: 421,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
    
    cartasPropias[2] = new Konva.Rect({
        x: 283,
        y: 421,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
    
    cartasPropias[0].on('click', function(){ tirarCarta(0) });
    cartasPropias[1].on('click', function(){ tirarCarta(1) });
    cartasPropias[2].on('click', function(){ tirarCarta(2) });
    
    
    cartasMesaPropias[0] = new Konva.Rect({
        x: 112,
        y: 272,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
    
    cartasMesaPropias[1] = new Konva.Rect({
        x: 200,
        y: 272,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
    
    cartasMesaPropias[2] = new Konva.Rect({
        x: 283,
        y: 272,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
    
    cartasOponente[0] = new Konva.Rect({
        x: 112,
        y: 2,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
    
    cartasOponente[1] = new Konva.Rect({
        x: 200,
        y: 2,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
    
    cartasOponente[2] = new Konva.Rect({
        x: 283,
        y: 2,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
    
    cartasMesaOponente[0] = new Konva.Rect({
        x: 112,
        y: 146,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
    
    cartasMesaOponente[1] = new Konva.Rect({
        x: 200,
        y: 146,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });
    
    cartasMesaOponente[2] = new Konva.Rect({
        x: 283,
        y: 146,
        width:80,
        height:123,
        fillPatternImage: images.cartas,
        fillPatternOffset: { x : 0, y : 0},
    });

    layer.add(cartasPropias[0]);
    layer.add(cartasPropias[1]);
    layer.add(cartasPropias[2]);
    layer.add(cartasOponente[0]);
    layer.add(cartasOponente[1]);
    layer.add(cartasOponente[2]);
    layer.add(cartasMesaPropias[0]);
    layer.add(cartasMesaPropias[1]);
    layer.add(cartasMesaPropias[2]);
    layer.add(cartasMesaOponente[0]);
    layer.add(cartasMesaOponente[1]);
    layer.add(cartasMesaOponente[2]);
    
 
    stage.add(layer);
}

function tirarCarta(carta){
	var send={}
	send["partidaID"]=idPartida;
	send["comando"]=1;
	send["jugador"]=jugador;
	send["parametro"]=carta;
    $.ajax({
        type: 'POST',
        contentType : "application/json",
        url: '/proyecto-limpio-spring/app/comando',
        datatype: 'json',
        data: JSON.stringify(send)
    });
    actualizar();
}

var sources = {
    cartas: '/proyecto-limpio-spring/img/cartas.png'
};

loadImages(sources, function(images) {
    draw(images);
});

