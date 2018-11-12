<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="url" value="${pageContext.request.contextPath}"/>
    
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:default>
	<jsp:attribute name="body">
	    <br>
		<div class = "container" >
		
	<div style="text-align: center" class="alert alert-success">
		<h1>${aviso}</h1>
		</div>
	<div style="text-align: center" class="alert alert-danger">	
		<h1>${aviso1}</h1>
	</div>
	     </div>
	</jsp:attribute>
</t:default>