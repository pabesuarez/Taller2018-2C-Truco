<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="url" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html>
<%@attribute name="body" fragment="true"%>
<%@attribute name="scripts" fragment="true"%>
<html>
	<head>
	    <link href="${url}/css/bootstrap.min.css" rel="stylesheet" >
	    <link href="${url}/css/estilo.css" rel="stylesheet" >
	    <link href="${url}/css/bootstrap-theme.min.css" rel="stylesheet">
	</head>
	<body>
		<div class ="container">
			<div class="row">
				<div class="col-md-12 header">
					<img style="height:60px" src ="${url}/img/logo.png"/>
					<span class="ml-5">logeado como: ${sessionScope.usuarioNombre}</span>
					<a class="ml-5" href="${url}/registro">Registrarse</a>
					<a href="${url}/login">Login</a>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<jsp:invoke fragment="body"/>
				</div>
			</div>
			
		</div>
		<script src="${url}/js/jquery-3.0.0.min.js"></script>
		<script src="${url}/js/bootstrap.min.js" type="text/javascript"></script>
		    <jsp:invoke fragment="scripts"/>
	</body>
</html>