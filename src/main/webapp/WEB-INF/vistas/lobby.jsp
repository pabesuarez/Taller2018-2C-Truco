<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="url" value="${pageContext.request.contextPath}"/>
    
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:default>
	<jsp:attribute name="body">
	<div>
	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#config">
 Crear nueva partida
	</button>
<div class="modal fade" id="config" tabindex="-1" role="dialog" aria-labelledby="configLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="configLabel">Configuración</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
 			<form:form action="nuevaPartida" method="POST" modelAttribute="configuracion">
				<h3>Nueva partida</h3>
				<p>Puntaje para ganar</p>
				<label for="puntos">15</label>					
				<form:radiobutton path="puntos" id="puntos1"  value="15"/><br>
				<label for="puntos">30</label>
				<form:radiobutton path="puntos" id="puntos2" value="30" checked="checked"/><br>
				<p>Flor</p>
				<label for="puntos">Si</label>					
				<form:radiobutton path="flor" id="florActivada" value="true"/><br>
				<label for="puntos">No</label>
				<form:radiobutton path="flor" id="florDesactivada" value="false" checked="checked"/><br>
				<p>Jugador mano</p>
				<label for="puntos">Jugador 1</label>					
				<form:radiobutton path="mano" id="jugador1"  value="1" checked="checked"/><br>
				<label for="puntos">Jugador 2</label>
				<form:radiobutton path="mano" id="jugador2"  value="2"/><br>
				<button type="submit" class="btn btn-primary">Aceptar</button>
			</form:form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
      </div>
    </div>
  </div>
</div>
	<table id="partidas" class="display">
    <thead>
        <tr>
            <th>ID</th>
            <th>Estado</th>
            <th>Mensaje</th>
            <th>Propietario</th>
            <th>Entrar</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${partidas}" var="partida">
            <tr>
    		<td>${partida.id}</td>
    		<c:choose>
    				<c:when test="${partida.estado == '1'}">
       					<td>Esperando jugador</td>
    				</c:when>
    				<c:when test="${partida.estado == '2'}">
       					<td>Full</td>
    				</c:when>
				</c:choose>
    		<td>${partida.mensaje}</td>
    		 	<c:choose>
    				<c:when test="${not empty partida.jugador1.id}">
       					<td>${partida.jugador1.nombre}</td>
    				</c:when>
    				<c:otherwise>
       					<td>Invitado</td>
    				</c:otherwise>
				</c:choose>
				<c:choose>
    				<c:when test="${partida.estado == '1'}">
       					<td><a href="${url}/jugar/${partida.idPartida}" type="button" class="btn btn-success">Entrar</a></td>
    				</c:when>
    				<c:when test="${partida.estado == '2'}">
       					<td>Full</td>
    				</c:when>
				</c:choose>
    		</tr>
		</c:forEach>
    </tbody>
</table>
 


	</div>
	</jsp:attribute>
</t:default>