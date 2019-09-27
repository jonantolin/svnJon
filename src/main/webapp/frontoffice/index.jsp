<%@include file="/includes/header.jsp"%>
<%@include file="/includes/navbar.jsp"%>


<%@page import="com.ipartek.formacion.controller.frontoffice.VideoController"%>

<style>


.peque{

	font-size: 75%;

}

.boton-buscar{
	margin-top: .7em;
	margin-bottom: 1em;

}


</style>
				<div class="border border-primary my-1 p-1 d-inline-block rounded shadow-sm">
					<h6>Buscar vídeos</h6>
					<div class="d-inline mr-2">
						<form action="frontoffice/videos" class="d-inline">
								<input class="form-control-inline" type="search" name="nombreBuscar" placeholder="Buscar por Nombre" required>
								<input type="hidden" name="op" value="<%=VideoController.OP_BUSCAR_POR_NOMBRE_CON_LIKE_USUARIO%>">
								<button type="submit" class="btn btn-primary btn-sm boton-buscar"><i class="fas fa-search"></i></button>
						</form>
					</div>	
						
					<div class="d-inline mr-2">
						<form action="frontoffice/videos" class="d-inline">
							<input class="form-control-inline" type="search" name="usuarioBuscar" placeholder="Buscar por Usuario" required>
							<input type="hidden" name="op" value="<%=VideoController.OP_BUSCAR_POR_USUARIO_CON_LIKE_USUARIO%>">
							<button type="submit" class="btn btn-primary btn-sm boton-buscar"><i class="fas fa-search"></i></button>
						</form>
					</div>
				</div>
				
				
				<c:if test="${busquedaVideo != null }">
					<h3 class="text-primary">Resultado de búsqueda de vídeo para : "<span class="text-success font-italic">${busquedaVideo}</span>"</h3>
					<a href="frontoffice/videos?op=<%=VideoController.OP_LISTAR_TODOS_CON_LIKE_USUARIO %>" class="btn btn-outline-primary btn-sm my-1">Volver a Inicio</a>

				</c:if>
				
				<c:if test="${busquedaUsuario != null }">
					<h3 class="text-primary">Resultado de búsqueda de vídeos de usuario : "<span class="text-success font-italic">${busquedaUsuario}</span>"</h3>
					<a href="frontoffice/videos?op=<%=VideoController.OP_LISTAR_TODOS_CON_LIKE_USUARIO %>" class="btn btn-outline-primary btn-sm my-1">Volver a Inicio</a>

				</c:if>
				
				<c:if test="${nombreUsuario != null }">
					<h3 class="text-primary">Vídeos de <span class="text-success"><i class="fas fa-user mr-1"></i>${nombreUsuario}</span></h3>
					<a href="frontoffice/videos?op=<%=VideoController.OP_LISTAR_TODOS_CON_LIKE_USUARIO %>" class="btn btn-outline-primary btn-sm my-1">Volver a Inicio</a>
				
				</c:if>
				
				<c:if test="${nombreCategoria != null }">
					<h3 class="text-primary">Vídeos de <span class="text-warning"><i class="fas fa-tag mr-1"></i>${nombreCategoria}</span></h3>
					<a href="frontoffice/videos?op=<%=VideoController.OP_LISTAR_TODOS_CON_LIKE_USUARIO %>" class="btn btn-outline-primary btn-sm my-1">Volver a Inicio</a>
				</c:if>
				
				<%@include file="../includes/mensaje.jsp"%>
				
				<ul class="list-group">
				
		  			<c:forEach items="${videos}" var="v" >	 
		  			 
	  				  	<li class="list-group-item">
	  				  	
		  				  	<div class="row ml-1">
		  				  			<div class="col-12 col-md-2">
						  				<div class="d-flex justify-content-center">
						  					
							  				<img src="https://img.youtube.com/vi/${v.codigo}/default.jpg" alt="Imagen destacda del video ${v.nombre}"/>
							  			</div>
							  			<div class="d-flex justify-content-center mt-1">
							  			
								  			<span class="mx-2 text-danger text-center d-block"><i class="fas fa-heart mr-1"></i>${v.likes} </span>
									  		
									  		<c:if test="${v.like.idUsuario != -1 }">
									  		
									  			<a href="frontoffice/videos?id=${v.id }&op=59" class="btn btn-success peque mx-1 "><i class="far fa-thumbs-up"></i></a>
									  		
									  		</c:if>	
									  		
									  		<c:if test="${v.like.idUsuario == -1 }">
									  		
									  			<a href="frontoffice/videos?id=${v.id }&op=55" class="btn btn-outline-success peque mx-1 "><i class="far fa-thumbs-up"></i></a>
									  		
									  		</c:if>
									  		
								  			
							  			</div>
						  			</div>
						  			<div class="col-12 col-md-9">
						  			
							  			<a href="frontoffice/videos?id=${v.id }&op=<%=VideoController.OP_DETALLE_CON_LIKE_USUARIO  %>"><p class="h3">${v.nombre}</p></a>
							  			<a href="frontoffice/videos?idUsuario=${v.usuario.id }&nombreUsuario=${v.usuario.nombre}&op=<%=VideoController.OP_LISTAR_POR_USUARIO_CON_LIKE_USUARIO %>" class="d-inline mx-2 text-success"><i class="fas fa-user mr-1"></i>${v.usuario.nombre}</a>  			
							  			<a href="frontoffice/videos?idCategoria=${v.categoria.id }&nombreCategoria=${v.categoria.nombre}&op=<%=VideoController.OP_LISTAR_POR_CATEGORIA_CON_LIKE_USUARIO %>" class="d-inline mx-2 text-warning"><i class="fas fa-tag mr-1"></i>${v.categoria.nombre}</a>

						  			</div>
						  		</div>
					  							  		
					  	</li>					
					</c:forEach>
				</ul>		


    
    	    	
<%@include file="/includes/footer.jsp"%>   