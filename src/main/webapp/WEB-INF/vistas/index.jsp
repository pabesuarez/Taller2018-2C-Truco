<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="url" value="${pageContext.request.contextPath}"/>
    
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:default>
	<jsp:attribute name="body">
	    <br>
	      <br>
         <div align="center">
				<img src="img/logo.png" class="img-fluid" alt="Responsive image"  width="auto" height="100">
		</div>
		  <br>
		    <br>
		<a href="${url}/lobby" class="btn btn-primary btn-block btn-success">Jugar</a>
		<h1>Últimas 5 partidas jugadas</h1>
	<table id="partidasindex" class="display">
    <thead>
        <tr>
        <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${partidas}" var="partida">
            <tr>
            <td>${partida.id} </td>
			<c:choose>
    			<c:when test="${partida.puntajeJugador1 > partida.puntajeJugador2}">
    			            <c:choose>
           		<c:when test="${empty partida.jugador1}">
    				<td class="text-success">Invitado</td>
    			</c:when>
    			<c:otherwise>
    				<td class="text-success">${partida.jugador1.nombre}</td>
    			</c:otherwise>
    		</c:choose>
       				<td><p class="text-success">${partida.puntajeJugador1}</p></td>
       				<td> - </td>
       				<td><p class="text-danger">${partida.puntajeJugador2}</p></td>
       				       				 <c:choose>
           	<c:when test="${empty partida.jugador2}">
    				<td class="text-danger" >Invitado</td>
    				</c:when>
    				<c:otherwise>
    				<td class="text-danger">${partida.jugador2.nombre}</td>
    				</c:otherwise>
    		</c:choose>
    			</c:when>
    			<c:otherwise>
    			 <c:choose>
           		<c:when test="${empty partida.jugador1}">
    				<td class="text-danger">Invitado</td>
    			</c:when>
    			<c:otherwise>
    				<td class="text-danger">${partida.jugador1}</td>
    			</c:otherwise>
    		</c:choose>
       				<td><p class="text-danger">${partida.puntajeJugador1}</p></td>
       				<td> - </td>
       				<td><p class="text-success">${partida.puntajeJugador2}</p></td>
       				 <c:choose>
           	<c:when test="${empty partida.jugador2}">
    				<td class="text-success" >Invitado</td>
    				</c:when>
    				<c:otherwise>
    				<td class="text-success">${partida.jugador2.nombre}</td>
    				</c:otherwise>
    		</c:choose>
    			</c:otherwise>
			</c:choose>
    		</tr>
		</c:forEach>
    </tbody>
</table>
	</jsp:attribute>
</t:default>