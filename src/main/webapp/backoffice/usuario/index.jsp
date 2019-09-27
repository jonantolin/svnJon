<%@page import="com.ipartek.formacion.controller.backoffice.UsuarioController"%>
<%@page import="com.ipartek.formacion.controller.backoffice.VideoController"%>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>



<style>

.azul{

color:#17a2b8!important;



}



</style>



	<h1 class="azul">Listado Usuarios <i class="fas fa-users"></i></h1>
	
	
	<%@include file="../../includes/mensaje.jsp"%>
			
	
	<a href="backoffice/usuario?op=<%=UsuarioController.OP_NUEVO%>" class="mb-3 btn btn-outline-primary">Nuevo Usuario <i class="fas fa-user"></i></a>
	
	
	<form action="backoffice/usuario">
		<input type="search" name="nombreBuscar" placeholder="Buscar por Nombre" required>
		<input type="hidden" name="op" value="<%=UsuarioController.OP_BUSCAR%>">
		<button type="submit" class="btn btn-primary">Filtrar Nombre <i class="fas fa-search"></i></button>	
	</form>
	
	
	<ul class="list-group">
	  <c:forEach items="${usuarios}" var="u">	
	  	<li class="list-group-item">
	  		<p class="h3">
		  		<a href="backoffice/usuario?op=<%=UsuarioController.OP_DETALLE%>&id=${u.id}">${u.id} ${u.nombre}</a>
		  		<span class="mx-2 h5 text-danger">
		  			<c:if test="${u.numVideos != 0 }">
		  				<a class="text-danger" href="backoffice/videos?id=${u.id }&op=<%=VideoController.OP_LISTAR_POR_USUARIO %>"><i class="fab fa-youtube text-danger"></i> ${u.numVideos} vídeos</a>
		  			</c:if>
		  			
		  			<c:if test="${u.numVideos == 0 }">
		  				<i class="fab fa-youtube"></i> Sin vídeos
		  			</c:if>
		  		</span>

	  		</p>
	  	</li>
	  </c:forEach>	  	  
	</ul>

	
	
<%@include file="../../includes/footer.jsp"%>