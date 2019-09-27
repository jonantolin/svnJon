<%@page import="com.ipartek.formacion.controller.backoffice.UsuarioController"%>
<%@page import="com.ipartek.formacion.controller.backoffice.VideoController"%>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

		
	<%@include file="../../includes/mensaje.jsp"%>
			
	
	
	<style>
	
	.azul{

margin-top:3%;
color:#17a2b8!important;



}
.btn-outline-primary{

margin-top:3%;

}


	
	</style>
	
		<h1 class="azul">Listado Videos <i class="fab fa-youtube"></i></h1>
	
	<a href="backoffice/videos?op=<%=VideoController.OP_NUEVO%>" class="mb-3 btn btn-outline-primary">Nuevo Video <i class="fab fa-youtube"></i></a>
	
	
	<div class="row">
		<div class="col">
			<h2 class="azul">Videos visibles <i class="fas fa-eye"></i></h2>
			<ul class="list-group">
			<c:forEach items="${videosVisibles }" var="v">	
			  	<li class="list-group-item">
			  		<div class="row">
			  			<div class="col-3">
			  				<img class="float-left mr-3" src="https://img.youtube.com/vi/${v.codigo}/default.jpg" alt="Imagen destacda del video ${v.nombre}"/>
			  			</div>
			  			<div class="col-9">
					  		<a href="backoffice/videos?op=<%=VideoController.OP_DETALLE%>&id=${v.id}">
					  			
					  			<p class="h3">${v.nombre}</p>
					  		</a>
					  		<a href="backoffice/usuario?op=<%=UsuarioController.OP_DETALLE%>&id=${v.usuario.id }">
					  			<p><i class="fas fa-user"></i> ${v.usuario.nombre }</p>
					  		</a>
					  		
					  			<p><i class="fas fa-tag"></i> ${v.categoria.nombre }</p>
					  		
					  		<p class="text-danger"><i class="fas fa-heart"></i> ${v.likes }</p>
				  		</div>
			  		</div>
			  	</li>
	  		</c:forEach>
	  		</ul>
		</div>
		
		<div class="col">
			<h2 class="azul">Videos no visibles <i class="fas fa-eye-slash"></i></h2>
			<ul class="list-group">
			<c:forEach items="${videosNoVisibles }" var="v">	
			  	<li class="list-group-item">
			  		<div class="row">
			  			<div class="col-3">
			  				<img class="float-left mr-3" src="https://img.youtube.com/vi/${v.codigo}/default.jpg" alt="Imagen destacda del video ${v.nombre}"/>
			  			</div>
			  			<div class="col-9">
					  		<a href="backoffice/videos?op=<%=VideoController.OP_DETALLE%>&id=${v.id}">
					  			
					  			<p class="h3">${v.nombre}</p>
					  		</a>
					  		<a href="backoffice/usuarios?op=<%=UsuarioController.OP_DETALLE%>&id=${v.usuario.id }">
					  			<p><i class="fas fa-user"></i> ${v.usuario.nombre }</p>
					  		</a>
					  		
					  			<p><i class="fas fa-tag"></i> ${v.categoria.nombre }</p>
					  		
					  		<p class="text-danger"><i class="fas fa-heart"></i> ${v.likes }</p>
				  		</div>
			  		</div>
			  	</li>
	  		</c:forEach>
	  		</ul>
		</div>
	</div>
	
	
<%@include file="../../includes/footer.jsp"%>