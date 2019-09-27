<%@page import="com.ipartek.formacion.controller.frontoffice.UsuarioController"%>
<%@page import="com.ipartek.formacion.controller.frontoffice.VideoController"%>

<%@include file="../../includes/header.jsp"%>
<%@include file="../../includes/navbar.jsp"%>

		
	
			
	
	
	<style>

	
	
	.azul{
	
	
	color:#17a2b8!important;
	
	}
	
	.btn-outline-primary{
	
	
	margin-top:2%;
	
	}
	
	
	</style>
	
	
	
	<a href="frontoffice/videos?op=<%=com.ipartek.formacion.controller.frontoffice.VideoController.OP_NUEVO%>" class="mb-3 btn btn-outline-primary">Nuevo Video <i class="fab fa-youtube"></i></a>

			<c:if test="${mensaje == null }">
				<h2>Mis Videos</h2>
			</c:if>
			<%@include file="../../includes/mensaje.jsp"%>
			<ul class="list-group">
	  			<c:forEach items="${videos}" var="v" >	  
  				  	<li class="list-group-item">
  				  		<div class="row ml-1">
  				  			<div class="col-12 col-md-2">
	  				  			<img class="float-left mr-3" src="https://img.youtube.com/vi/${v.codigo}/default.jpg" alt="Imagen destacda del video ${v.nombre}"/>
	  				  		</div>
	  				  		<div class="col-12 col-md-9">
						  		<a href="frontoffice/videos?op=<%=com.ipartek.formacion.controller.frontoffice.VideoController.OP_DETALLE%>&id=${v.id}">
									<p class="h3">${v.nombre}</p>
						  		</a>
						  		
						  		<p><i class="fas fa-user mr-1"></i>${v.usuario.nombre}</p>
						  					  			
						  		<p><i class="fas fa-tag mr-1"></i>${v.categoria.nombre}</p>	
						  		<p class="text-danger"><i class="fas fa-heart"></i> ${v.likes }</p>	
					  		</div>
				  		</div>				  		
				  	</li>					
				</c:forEach>
			</ul>		
		</div>
		
		
	</div>
	
	
<%@include file="../../includes/footer.jsp"%>