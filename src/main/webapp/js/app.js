var width = 512;
var height = 550;

var timeout;

var cartasPropias = [];
var cartasOponente = [];
var cartasMesaPropias = [];
var cartasMesaOponente = [];
var rondaGanadaPropia = [];
var rondaGanadaOponente = [];
var puntajeJ1 = []
var puntajeJ2 = []
var nombre1;
var nombre2;
var envido = 0;
var envidoOponente = 0;
var puntajeParaGanar = 0;

var layer = new Konva.Layer();
$(function(){
	actualizar();		
});


function calcularEnvido(cartas){
	var caso = 0;
	var mano = [].slice.call(cartas)
	if( Math.floor(mano[0]/10) == Math.floor(mano[1]/10) && Math.floor(mano[0]/10) == Math.floor(mano[2]/10)) {
		caso = 1;
	}else if(Math.floor(mano[0]/10) == Math.floor(mano[1]/10)) {
		caso = 3;
	}else if(Math.floor(mano[1]/10) == Math.floor(mano[2]/10)) {
		caso = 2;
	}else if(Math.floor(mano[0]/10) == Math.floor(mano[2]/10)){
		caso = 4
	}
	
	for(i=0;i<=2;i++){
		if ((mano[i]%10)<=6){
			mano[i]=(mano[i]%10)+1;
		}else{
			
			mano[i]=0
		}
	}
	
	switch (caso) {
		case 1:
			mano.sort(function(a, b){return a-b});
			return 20 + mano[1] + mano[2];
			break;
		case 2:
			return 20 + mano[1] + mano[2];
			break;
		case 3:
			return 20 + mano[0] + mano[1];
			break;
		case 4:
			return 20 + mano[0] + mano[2];
			break;
		default:
			mano.sort(function(a, b){return a-b});
			return mano[2];
			break;
	}
}


function getOponente(){
	if (jugador == 1) {
		return 2;
	}else{
		return 1;
	}
}

function comando(tipo){
	var send={}
	send["partidaID"]=idPartida;
	send["comando"]=tipo;
	send["jugador"]=jugador;
    $.ajax({
        type: 'POST',
        contentType : "application/json",
        url: proyecto+'/app/comando',
        datatype: 'json',
        data: JSON.stringify(send)
    });
}

function abandonarPartida(){
	var send={}
	send["partidaID"]=idPartida;
	send["jugador"]=jugador;
    $.ajax({
        type: 'POST',
        contentType : "application/json",
        url: proyecto+'/abandonoDePartida',
        datatype: 'json',
        data: JSON.stringify(send)
    });
}
 
$(window).bind('beforeunload', function(){
	abandonarPartida();
});



function mazo(){
	var send={}
	send["partidaID"]=idPartida;
	send["comando"]=6;
	send["jugador"]=jugador;
    $.ajax({
        type: 'POST',
        contentType : "application/json",
        url: proyecto+'/app/comando',
        datatype: 'json',
        data: JSON.stringify(send)
    });
}

function refresh(){
	layer.draw();
	
	switch(estado){
	case 2:
		if(turno==jugador){
			$("#estado").text("tu turno");
		}else{
			$("#estado").text("turno del oponente");
		}
		break;
	case 9:
		$("#estado").text("la ronda termino");
	}
}

function dibujar(carta,valorCarta){
	carta.setFillPatternOffset({ x : (valorCarta%10)*80, y : Math.floor(valorCarta/10)*123});
}
function dibujarGanador(ronda,ganador){
	if (ganador==1){
		if (jugador==1){
			rondaGanadaPropia[ronda].setFillPatternOffset({ x : 0, y : 0});
			rondaGanadaOponente[ronda].setFillPatternOffset({ x : 80, y : 0});
		}else{
			rondaGanadaPropia[ronda].setFillPatternOffset({ x : 80, y : 0});
			rondaGanadaOponente[ronda].setFillPatternOffset({ x : 0, y : 0});
		}
	}else if (ganador==2){
		if (jugador==1){
			rondaGanadaPropia[ronda].setFillPatternOffset({ x : 80, y : 0});
			rondaGanadaOponente[ronda].setFillPatternOffset({ x : 0, y : 0});
		}else{
			rondaGanadaPropia[ronda].setFillPatternOffset({ x : 0, y : 0});
			rondaGanadaOponente[ronda].setFillPatternOffset({ x : 80, y : 0});
		}
	}else if (ganador==3){
		rondaGanadaPropia[ronda].setFillPatternOffset({ x : 0, y : 0});
		rondaGanadaOponente[ronda].setFillPatternOffset({ x : 0, y : 0});
	}else{
		rondaGanadaPropia[ronda].setFillPatternOffset({ x : 80, y : 0});
		rondaGanadaOponente[ronda].setFillPatternOffset({ x : 80, y : 0});
	}
}


function actualizarPuntaje(njugador,puntaje){
	if (puntaje>puntajeParaGanar){
		puntaje=puntajeParaGanar;
	}
	var i = 0;
	var p;
	if (njugador == 1){
		p=puntajeJ1;
	}else{
		p=puntajeJ2;
	}
	while(puntaje >5){
		p[i].setFillPatternOffset({ x : 160, y : 0});
		i+=1;
		puntaje-=5;
	}
	if (puntaje!=0){
		p[i].setFillPatternOffset({ x : (puntaje*32), y : 0});
	}
}


function mensajeTruco(truco){
	switch (truco){
	case 1: return "Truco"
	case 2: return "Quiero ReTruco"
	case 3: return "Quiero Vale 4"
	}
}

function mensajeEnvido(tipo){
	switch(tipo){
	case 1,4:
	return "envido";
	case 2,5,8:
	return "real envido";
	case 3,6,7,9,10,11:
	return "falta envido";
	}
	return "";
}

function actualizar(){
	
	var send = {}
	send["jugador"] = jugador;
	send["partidaID"] = idPartida;
    $.ajax({
        type: 'POST',
        contentType : "application/json",
        url: proyecto+'/app/actualizar',
        datatype: 'json',
        data: JSON.stringify(send),
        success: function (data) {
        	
        	
        	
        	if(data.partidaID == undefined){
        		timeout = setTimeout(function(){ actualizar() },1000);
        	}else{
        		if (data.puntosPorTruco <3){
        			$("#btnTruco").text(mensajeTruco(data.puntosPorTruco+1))
        		}
        		
        		if (data.estado != 5){
        			$("#btnQuiero").text("Quiero")
        			$("#btnNoQuiero").text("No Quiero")
        		}
        		
        		
        		switch(data.estado){
            	case 2:
            		$("#botones button").show();
            		if((data.jugadorTruco == jugador || data.puntosPorTruco == 3)){
            			$("#btnTruco").hide();
            		}
            		
            		
            		if((data.ganadorTanto != 0 || data.turno != jugador)){
            			$("#btnEnvido").hide();
            			$("#btnRealEnvido").hide();
            			$("#btnFaltaEnvido").hide();
            		}
            		
            		$("#btnQuiero").hide();
        			$("#btnNoQuiero").hide();
        			break;
            	case 3:
            		$("#botones button").hide();
            		
            		if (data.jugadorTruco != jugador){
            			$("#btnQuiero").show();
            			$("#btnNoQuiero").show();
            			if (data.puntosPorTruco <3){
            				$("#btnTruco").show();
            			}
            			if(data.ganadorTanto == 0){
	            			$("#btnEnvido").show();
	                		$("#btnRealEnvido").show();
	                		$("#btnFaltaEnvido").show();
            			}
            		}
            		break;
            	case 4:
            		$("#botones button").hide();
            		if (data.jugadorTanto != jugador){
            			switch(data.tipoTanto){
            			case 1:
            				$("#btnEnvido").show();
            				$("#btnRealEnvido").show();
	                		$("#btnFaltaEnvido").show();
	                		break;
            			case 4:
            				$("#btnRealEnvido").show();
	                		$("#btnFaltaEnvido").show();
            			case 2,5:
            				$("#btnFaltaEnvido").show(); break;
            			}
            			$("#btnQuiero").show();
            			$("#btnNoQuiero").show();
            		}
            		break;
            	case 5:
            		$("#botones button").hide();
            		if (data.jugadorTanto == jugador){
            			$("#btnQuiero").text(envido);
            			$("#btnNoQuiero").text("Son buenas");
            			$("#btnQuiero").show();
            			$("#btnNoQuiero").show();
            		}
            	}
        		
        		puntajeParaGanar = data.puntajeParaGanar;
        		console.log(data);
        		console.log(puntajeParaGanar);
        		
        		
        		if (jugador==1){
        			envido = calcularEnvido([data.manoJugador1[0],data.manoJugador1[1],data.manoJugador1[2]])
        			envidoOponente = calcularEnvido([data.manoJugador2[0],data.manoJugador2[1],data.manoJugador2[2]])
		        	$("#mensajePropio").text(data.mensajeJugador1);
        			$("#mensajeOponente").text(data.mensajeJugador2);
        			for(i=0;i<=2;i++){
			        	dibujar(cartasPropias[i],data.manoJugador1[i]);
			        	dibujar(cartasOponente[i],40);
			        	dibujarGanador(i,data.resultado[i]);
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
		        	
		        	actualizarPuntaje(1,data.puntajeJugador1);
    	        	actualizarPuntaje(2,data.puntajeJugador2);
            		nombre1.text(data.nombreJugador1);
	            	nombre2.text(data.nombreJugador2);
		        	
        		}else{
        			$("#mensajePropio").text(data.mensajeJugador2);
        			$("#mensajeOponente").text(data.mensajeJugador1);
        			envido = calcularEnvido([data.manoJugador2[0],data.manoJugador2[1],data.manoJugador2[2]])
        			envidoOponente = calcularEnvido([data.manoJugador1[0],data.manoJugador1[1],data.manoJugador1[2]])
		        	for(i=0;i<=2;i++){
			        	dibujar(cartasPropias[i],data.manoJugador2[i]);
			        	dibujar(cartasOponente[i],40);
			        	dibujarGanador(i,data.resultado[i]);
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
		        	
	            	actualizarPuntaje(1,data.puntajeJugador2);
	    	        actualizarPuntaje(2,data.puntajeJugador1);
		            nombre1.text(data.nombreJugador2);
		            nombre2.text(data.nombreJugador1);
        		}
        		estado=data.estado;
        		turno=data.turno;
        		
        		
            	refresh();
            	if(data.puntajeJugador1 == data.puntajeParaGanar || data.puntajeJugador2 == data.puntajeParaGanar){
            		$("#estado").text("partida terminada");
            		window.setTimeout(function(){
            	        window.location.href = proyecto;

            	    }, 3000);
            	}else{
            		timeout = setTimeout(function(){ actualizar() },1000);
            	}
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
    
	nombre1 = new Konva.Text({
        x: 382-(nombreJugador1.length*7),
        y: 2,
        text: nombreJugador1,
    });
	
	layer.add(nombre1);
	
	nombre2 = new Konva.Text({
        x: 390,
        y: 2,
        text: nombreJugador2,
    });
    
	layer.add(nombre2);
    
    for(i=0;i<=2;i++){
    	cartasPropias[i] = new Konva.Rect({
            x: 40+(90*i),
            y: 421,
            width:80,
            height:123,
            fillPatternImage: images.cartas,
            fillPatternOffset: { x : 0, y : 0},
        });
    	
    	
    	layer.add(cartasPropias[i]);
    	
    	rondaGanadaPropia[i] = new Konva.Rect({
            x: 40+(90*i),
            y: 396,
            width:80,
            height:20,
            fillPatternImage: images.ganador,
            fillPatternOffset: { x : 0, y : 0},
        });
    	layer.add(rondaGanadaPropia[i]);
    	
        cartasMesaPropias[i] = new Konva.Rect({
            x: 40+(90*i),
            y: 272,
            width:80,
            height:123,
            fillPatternImage: images.cartas,
            fillPatternOffset: { x : 0, y : 0},
        });
        
        layer.add(cartasMesaPropias[i]);
        
        cartasOponente[i] = new Konva.Rect({
            x: 40+(90*i),
            y: 2,
            width:80,
            height:123,
            fillPatternImage: images.cartas,
            fillPatternOffset: { x : 0, y : 0},
        });
        layer.add(cartasOponente[i])
        
        rondaGanadaOponente[i] = new Konva.Rect({
            x: 40+(90*i),
            y: 125,
            width:80,
            height:20,
            fillPatternImage: images.ganador,
            fillPatternOffset: { x : 0, y : 0},
        });
    	layer.add(rondaGanadaOponente[i]);
    	
        cartasMesaOponente[i] = new Konva.Rect({
            x: 40+(90*i),
            y: 146,
            width:80,
            height:123,
            fillPatternImage: images.cartas,
            fillPatternOffset: { x : 0, y : 0},
        });
        layer.add(cartasMesaOponente[i]);
    }
        
    for (i=0;i<6;i++){
    	puntajeJ1[i] = new Konva.Rect({
            x: 340,
            y: 40+(i*40),
            width:32,
            height:32,
            fillPatternImage: images.puntaje,
            fillPatternOffset: { x : 0, y : 0},
        });
    	
    	puntajeJ2[i] = new Konva.Rect({
            x: 390,
            y: 40+(i*40),
            width:32,
            height:32,
            fillPatternImage: images.puntaje,
            fillPatternOffset: { x : 0, y : 0},
        });
    	
    	layer.add(puntajeJ1[i]);
    	layer.add(puntajeJ2[i]);
    }
    
    cartasPropias[0].on('click', function(){ tirarCarta(0) });
    cartasPropias[1].on('click', function(){ tirarCarta(1) });
    cartasPropias[2].on('click', function(){ tirarCarta(2) });
 
    stage.add(layer);
}

function tirarCarta(carta){
	console.log(turno);
	if(jugador==turno){
		var send={}
		send["partidaID"]=idPartida;
		send["comando"]=1;
		send["jugador"]=jugador;
		send["parametro"]=carta;
	    $.ajax({
	        type: 'POST',
	        contentType : "application/json",
	        url: proyecto+'/app/comando',
	        datatype: 'json',
	        data: JSON.stringify(send)
	    });
	    actualizar();
	}
}

var sources = {
    cartas: proyecto+'/img/cartas.png',
    puntaje: proyecto+'/img/puntaje.png',
    ganador: proyecto+'/img/ganador.png'
};

loadImages(sources, function(images) {
    draw(images);
});