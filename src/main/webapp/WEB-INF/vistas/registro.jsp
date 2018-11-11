<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="url" value="${pageContext.request.contextPath}"/>
    
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default>
	<jsp:attribute name="body">
	    <br>
		<div class = "container" >
		
	<div id="loginbox" style="margin-top:20px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

				<form:form action="registrarUsuario" method="POST" modelAttribute="usuario">
	<h3>Ingrese sus datos</h3>
	
	<label for="nombre">Nombre:</label>					
	<form:input path="nombre" id="nombre" type="text" class="form-control" placeholder="Nombre" /><br>
	<br>
	<label for="pass">Password:</label>
	<form:input path="pass" id="pass"  type="pass" class="form-control" placeholder="Password" /><br>
	
	<button class="btn btn-lg btn-block btn-success" Type="Submit"> Guardar </button>
	<a href="<c:url value="/login"/>" class="btn btn-lg btn-block btn-success" role="button">Volver</a>	 	  	
				</form:form>
			</div>
	</div>
	
	</jsp:attribute>
</t:default>