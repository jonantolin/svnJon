
<%@page import="com.ipartek.formacion.controller.frontoffice.VideoController"%>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

		
	<%@include file="../../includes/mensaje.jsp"%>

		<h1 class="text-info mt-2">DETALLE VIDEO <i class="fab fa-youtube"></i></h1>	

			<div class="col">	
		
			<div class="embed-responsive embed-responsive-16by9">
		
				<iframe class="embed-responsive-item"
				        src="https://www.youtube.com/embed/${video.codigo}" 
				        frameborder="0" 
				        allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" 
				        allowfullscreen></iframe>
			</div> 
			
				<h4 class="text-info"><i class="fab fa-youtube"></i> ${video.nombre}</h4>
				<p class="text-info"><i class="fas fa-tag mr-1"></i> ${video.categoria.nombre} </p>
				
				<span class="mr-2 text-danger d-block float-right"><i class="fas fa-heart mr-1"></i>${video.likes} </span>
				
				
				<c:if test="${video.like.idUsuario != -1 }">
					<a href="frontoffice/videos?id=${video.id }&op=<%=VideoController.OP_RESTAR_LIKE %>" class="btn btn-success peque mr-2 float-right"><i class="far fa-thumbs-up"></i></a>
				</c:if>	
				
				<c:if test="${video.like.idUsuario == -1 }">
					<a href="frontoffice/videos?id=${video.id }&op=<%=VideoController.OP_SUMAR_LIKE %>" class="btn btn-outline-success peque mr-2 float-right "><i class="far fa-thumbs-up"></i></a>
				</c:if>		
				
				<p class="text-info"><i class="fas fa-user mr-1"></i> ${video.usuario.nombre} </p>
			       
		</div>
				
				
				
				
			
			
		</div>
		
		
	</div>
	
	
<%@include file="../../includes/footer.jsp"%>