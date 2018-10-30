<!DOCTYPE html>
<html>
	<head>
		<!-- Bootstrap core CSS -->
	    <link href="/proyecto-limpio-spring/css/bootstrap.min.css" rel="stylesheet" >

	</head>
	<body>
		<!-- Placed at the end of the document so the pages load faster -->
		<div class="container">
			<div style="width:800px">
				<div class="row">
					<div class="col" style="min-width:512px;max-width:512px;">
						<div id="juego"></div>
					</div>
					<div class="col">
						<div class="row">
							<div class="col-6"><button class="btn btn-success">truco</button></div>
							<div class="col-6"><button class="btn btn-success">envido</button></div>
						</div>
						<div class="row">
							<div class="col-6"><button class="btn btn-success">mazo</button></div>
							<div class="col-6"><button class="btn btn-success">falta envido</button></div>
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