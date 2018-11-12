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
       					<td><a href="${url}/jugar/${partida.id}" type="button" class="btn btn-success">Entrar</a></td>
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
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</jsp:attribute>
</t:default>