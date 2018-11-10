<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="url" value="${pageContext.request.contextPath}"/>
    
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:default>
	<jsp:attribute name="body">
		<a href="${url}/nuevaPartida" class="btn btn-primary">Nueva Partida</a>
	</jsp:attribute>
</t:default>