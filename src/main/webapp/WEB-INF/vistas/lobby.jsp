<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="url" value="${pageContext.request.contextPath}"/>
    
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:default>
	<jsp:attribute name="body">
	<div>
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
       					<td>${partida.jugador1.id}</td>
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
 <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
 Crear nueva partida
	</button>
<div class="modal fade" id="config" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Configuración</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
 			<form:form action="nuevaPartida" method="POST" modelAttribute="configuracion">
				<h3>Nueva partida</h3>
				<p>Puntaje para ganar</p>
				<label for="puntos">15</label>					
				<form:input path="puntos" id="puntos1" type="radio" class="form-control"  value="15"/><br>
				<label for="puntos">30</label>
				<form:input path="puntos" id="puntos2" type="radio" class="form-control" value="30"/><br>
				<p>Puntaje para ganar</p>
				<label for="puntos">Si</label>					
				<form:input path="flor" id="florActivada" type="radio" class="form-control"  value="true"/><br>
				<label for="puntos">No</label>
				<form:input path="flor" id="florDesactivada" type="radio" class="form-control" value="false"/><br>
				<p>Jugador mano</p>
				<label for="puntos">Jugador 1</label>					
				<form:input path="mano" id="jugador1" type="radio" class="form-control"  value="1"/><br>
				<label for="puntos">Jugador 2</label>
				<form:input path="mano" id="jugador2" type="radio" class="form-control" value="2"/><br>
				<button type="submit" class="btn btn-primary">Aceptar</button>
			</form:form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
      </div>
    </div>
  </div>
</div>

	</div>
	</jsp:attribute>
</t:default>