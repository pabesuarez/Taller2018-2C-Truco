<!DOCTYPE html>
<html>
	<head>
		<!-- Bootstrap core CSS -->
	    <link href="/proyecto-limpio-spring/css/bootstrap.min.css" rel="stylesheet" >

	</head>
	<body>
		<!-- Placed at the end of the document so the pages load faster -->
		<div class="container">
			<div style="width:800px;height:550px">
				<div class="row">
					<div class="col" style="min-width:512px;max-width:512px;">
						<div id="juego"></div>
					</div>
					<div style="height:550px" class="col border">
						<div class="row">
							<span style="height:4rem" id="mensajeOponente" class="border w-100"></span>
						</div>
						<div class="row">
							<div class="col-6"><button class="btn btn-success" onclick="truco()">truco</button></div>
							<div class="col-6"><button class="btn btn-success" onclick="envido()">envido</button></div>
						</div>
						<div class="row">
							<div class="col-6"><button class="btn btn-success" onclick="mazo()">mazo</button></div>
							<div class="col-6"><button class="btn btn-success" onclick="faltaEnvido()">falta envido</button></div>
						</div>
						<div class="row">
							<span style="height:4rem" id="mensajePropio" class="border w-100"></span>
						</div>
						<div class="row">
							<span style="height:2rem" id="estado" class="border w-100"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		
		
		
		<script>
			var jugador = ${partida.estado};
			var idPartida = ${partida.partidaID};
			var turno = 0;
			var estado = 0;
		</script>
		<script src="/proyecto-limpio-spring/js/konva.min.js"></script>
		<script src="/proyecto-limpio-spring/js/jquery-3.0.0.min.js"></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="/proyecto-limpio-spring/js/bootstrap.min.js" type="text/javascript"></script>
		<script src="/proyecto-limpio-spring/js/app.js"></script>
		
	</body>
</html>