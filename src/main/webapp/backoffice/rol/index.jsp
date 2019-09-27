<%@page import="com.ipartek.formacion.controller.backoffice.RolController"%>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>



<style>


.azul{

color:#17a2b8!important;

}


</style>

<h1 class="azul">Listado Roles <i class="fas fa-universal-access"></i></h1>
	
	
	<%@include file="../../includes/mensaje.jsp"%>
	
	<a href="backoffice/rol?op=<%=RolController.OP_IR_FORMULARIO%>" class="mb-3 btn btn-outline-primary">Nuevo Rol <i class="fas fa-tag mr-1"></i></a>
	
			
	<ul class="list-group">
	  <c:forEach items="${roles}" var="r">	
	  	<li class="list-group-item">
	  			<a href="backoffice/rol?op=<%=RolController.OP_IR_FORMULARIO%>&id=${r.id}">
	  			<p class="d-inline">${r.id}</p>
	  			<p class="d-inline">${r.nombre}</p>
	  		
	  	</li>
	  </c:forEach>	  	  
	</ul>

	
	
<%@include file="../../includes/footer.jsp"%>