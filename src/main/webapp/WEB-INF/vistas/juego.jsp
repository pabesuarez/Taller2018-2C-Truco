<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="url" value="${pageContext.request.contextPath}"/>
    
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:default>
	<jsp:attribute name="body">
		<div style="width:800px;height:550px">
			<div class="row">
				<div class="col" style="min-width:512px;max-width:512px;">
					<div id="juego"></div>
				</div>
				<div id="botones" style="height:512px" class="col border">
					<div class="row">
						<span style="height:31px" id="mensajeOponente" class="border w-100"></span>
					</div>
					<div class="row" style="height:122px">
						<div class="col-6"><button id="btnTruco" class="btn btn-success" onclick="truco()">truco</button></div><br>
						<div class="col-6"><button id="btnMazo" class="btn btn-success" onclick="mazo()">mazo</button></div>
					</div>
					<div class="row" style="height:122px">
						<div class="col-4"><button id="btnEnvido" class="btn btn-success" onclick="envido()">envido</button></div><br>
						<div class="col-4"><button id="btnRealEnvido" class="btn btn-success" onclick="realEnvido()">real envido</button></div><br>
						<div class="col-4"><button id="btnFaltaEnvido" class="btn btn-success" onclick="faltaEnvido()">falta envido</button></div>
					</div>
					<div class="row" style="height:122px">
						<div class="col-6"><button id="btnQuiero" class="btn btn-success" onclick="quiero()">Quiero</button></div><br>
						<div class="col-6"><button id="btnNoQuiero" class="btn btn-success" onclick="noQuiero()">No Quiero</button></div>
					</div>
					<div class="row">
						<span style="height:31px" id="mensajePropio" class="border w-100"></span>
					</div>
					<div class="row">
						<span style="height:2rem" id="estado" class="border w-100"></span>
					</div>
				</div>
			</div>
		</div>
	</jsp:attribute>
	<jsp:attribute name="scripts">
		<script>
			var proyecto="${url}";
			var jugador = ${partida.estado};		
			var idPartida = ${partida.partidaID};
			var nombreJugador1 = "${partida.nombreJugador1}";
			var nombreJugador2 = "${partida.nombreJugador2}";
			var turno = 0;
			var estado = 0;
		</script>
		<script src="${url}/js/konva.min.js"></script>
		<script src="${url}/js/app.js"></script>
	</jsp:attribute>
</t:default>