<%@page import="com.ipartek.formacion.controller.listener.UsuariosLogeadosListener"%>

<%@include file="../includes/header.jsp"%>
<%@include file="../includes/navbar.jsp"%> 
<%@include file="../includes/mensaje.jsp"%>





<style>

.azul{

margin-top:3%;
color:#17a2b8!important;;



}


</style>
<h1 class="azul">Mi Panel</h1>

 
<div class="row">
    <div class="col-xl-3 col-sm-6 mb-3">
      <div class="card text-white bg-primary o-hidden h-100">
        <div class="card-body">
          <div class="card-body-icon">
            <i class="fab fa-youtube"></i>
          </div>
          <div class="mr-5">
          	<p>${numeroVideos} videos</p>
          	<p>${numeroLikes} likes</p>
          </div>
        </div>
        <a class="card-footer text-white clearfix small z-1" href="frontoffice/videos">
          <span class="float-left">Ver Videos</span>
          <span class="float-right">
            <i class="fas fa-angle-right"></i>
          </span>
        </a>
      </div>
    </div>
    
   
      <div class="col-xl-3 col-sm-6 mb-3">
      <div class="card text-white bg-secondary o-hidden h-100">
        <div class="card-body">
          <div class="card-body-icon">
          <i class="fab fa-user"></i>
          </div>
          <div class="mr-5">Mi perfil</div>
        </div>
        <a class="card-footer text-white clearfix small z-1" href="frontoffice/usuario?id=${usuario.id }">
          <span class="float-left">Ver Mi Perfil</span>
          <span class="float-right">
            <i class="fas fa-angle-right"></i>
          </span>
        </a>
      </div>
    </div>
   
   
   
   
   
 </div>





<%@include file="../includes/footer.jsp"%>