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
		<a href="${url}/nuevaPartida" class="btn btn-primary btn-block btn-success">Nueva Partida</a>
	</jsp:attribute>
</t:default>