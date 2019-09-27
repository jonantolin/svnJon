<%@page import="com.ipartek.formacion.controller.backoffice.CategoriaController"%>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>



<style>


.azul{

color:#17a2b8!important;

}


</style>

<h1 class="azul">Listado Categorias <i class="fas fa-tag mr-1"></i></h1>
	
	
	<%@include file="../../includes/mensaje.jsp"%>
	
	<a href="backoffice/categoria?op=<%=CategoriaController.OP_IR_FORMULARIO%>" class="mb-3 btn btn-outline-primary">Nueva Categoria <i class="fas fa-tag mr-1"></i></a>
	
			
	<ul class="list-group">
	  <c:forEach items="${categorias}" var="c">	
	  	<li class="list-group-item">
	  			<a href="backoffice/categoria?op=<%=CategoriaController.OP_IR_FORMULARIO%>&id=${c.id}">
	  			<p class="d-inline">${c.id}</p>
	  			<p class="d-inline">${c.nombre}</p>
	  		
	  	</li>
	  </c:forEach>	  	  
	</ul>

	
	
<%@include file="../../includes/footer.jsp"%>