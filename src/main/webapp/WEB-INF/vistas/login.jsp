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

				<form:form action="validar-login" method="POST" modelAttribute="usuario">
			    	 <h6 class="form-signin-heading">Ingrese sus datos para loguearse</h6>
					<hr class="colorgraph"><br>

					<%--Elementos de entrada de datos, el elemento path debe indicar en que atributo del objeto usuario se guardan los datos ingresados--%>
					<label for="nombre">Nombre:</label>
					<form:input path="nombre" id="nombre" type="nombre" class="form-control" placeholder="Nombre"/>
					<br>
					<label for="pass">Password:</label>
					<form:input path="pass" type="pass" id="pass" class="form-control" placeholder="Password"/>     		  
					<br> 
					<button class="btn btn-lg btn-primary btn-block btn-success" Type="Submit">Ingresar</button>
				</form:form>
                   
				<%--Bloque que es visible si el elemento error no está vacío	--%>
				<c:if test="${not empty error}">
			        <h4><span>${error}</span></h4>
			        <br>
		        </c:if>	
			</div>
		</div>
		
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
	</jsp:attribute>
</t:default>