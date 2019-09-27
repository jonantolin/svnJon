package com.ipartek.formacion.controller.frontoffice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.ipartek.formacion.controller.pojo.Alert;
import com.ipartek.formacion.model.dao.CategoriaDAO;
import com.ipartek.formacion.model.dao.UsuarioDAO;
import com.ipartek.formacion.model.dao.VideoDAO;
import com.ipartek.formacion.model.pojo.Usuario;
import com.ipartek.formacion.model.pojo.Video;

/**
 * Servlet implementation class VideoController
 */
@WebServlet("/frontoffice/videos")
public class VideoController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_MIS_VIDEOS = "youtube/index.jsp";
	public static final String VIEW_MI_PANEL = "youtube/mipanel.jsp";
	public static final String VIEW_FORM = "youtube/formulario.jsp";
	public static final String VIEW_DETALLE_PUBLICO = "detalle.jsp";
	
	public static final String VIEW_INDEX = "index.jsp";
	
	public static String view = VIEW_INDEX;

	
	public static final String OP_LISTAR = "0";
	public static final String OP_GUARDAR = "23";
	public static final String OP_NUEVO = "4";
	public static final String OP_ELIMINAR = "hfd3";
	public static final String OP_DETALLE = "13";
	public static final String OP_SUMAR_LIKE = "55";
	public static final String OP_RESTAR_LIKE = "59";
	
	
	// Sacara todos los videos visibles incluyendo el like dado del usuario
	public static final String OP_LISTAR_TODOS_CON_LIKE_USUARIO = "98";
	public static final String OP_DETALLE_CON_LIKE_USUARIO = "91";
	public static final String OP_LISTAR_POR_USUARIO_CON_LIKE_USUARIO = "75";
	public static final String OP_LISTAR_POR_CATEGORIA_CON_LIKE_USUARIO = "66";
	
	public static final String OP_BUSCAR_POR_NOMBRE_CON_LIKE_USUARIO = "8";
	

	public static final String OP_BUSCAR_POR_USUARIO_CON_LIKE_USUARIO = "41";

	

	private static VideoDAO videoDAO;
	private static UsuarioDAO usuarioDAO;
	private static CategoriaDAO categoriaDAO;
	
	private static Usuario usuarioLogueado = null;

	private Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		videoDAO = VideoDAO.getInstance();
		usuarioDAO = UsuarioDAO.getInstance();
		categoriaDAO = CategoriaDAO.getInstance();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		usuarioLogueado = (Usuario) session.getAttribute("usuario");

		String op = request.getParameter("op");
		if (op == null) {
			op = OP_LISTAR;
		}

		switch (op) {
		case OP_DETALLE:
			detalle(request, response);
			break;
		
		case OP_DETALLE_CON_LIKE_USUARIO:
			detalleConLikesUsuario(request, response);
			break;

		case OP_GUARDAR:
			guardar(request, response);
			break;

		case OP_ELIMINAR:
			eliminar(request, response);
			break;

		case OP_NUEVO:
			nuevo(request, response);
			break;

		case OP_SUMAR_LIKE:
			sumarLike(request, response);
			break;
			
		case OP_RESTAR_LIKE:
			restarLike(request, response);
			break;	
			
		case OP_LISTAR_TODOS_CON_LIKE_USUARIO:
			listarTodosConLikeUsuario(request, response);
			break;
			
		case OP_LISTAR_POR_USUARIO_CON_LIKE_USUARIO:
			listarPorIdUsuarioConLikeUsuario(request, response);
			break;
			
		case OP_LISTAR_POR_CATEGORIA_CON_LIKE_USUARIO:
			listarPorIdCategoriaConLikeUsuario(request, response);
			break;
			
		case OP_BUSCAR_POR_NOMBRE_CON_LIKE_USUARIO:
			listarPorNombreConLikeUsuario(request, response);
			break;	
		
		case OP_BUSCAR_POR_USUARIO_CON_LIKE_USUARIO:
			listarPorUsuarioConLikeUsuario(request, response);
			break;

		default:
			listar(request, response);
			break;
		}

		request.getRequestDispatcher(view).forward(request, response);
	}
	

	private void listarPorUsuarioConLikeUsuario(HttpServletRequest request, HttpServletResponse response) {
		
		
		String nombre = request.getParameter("usuarioBuscar");
		
		request.setAttribute("busquedaUsuario", nombre);
		
		ArrayList<Video> lista = videoDAO.getAllByUserNameWithLikeUsuario(usuarioLogueado.getId(), nombre);

		if(lista.size() == 0) {
			
			request.setAttribute("mensaje", new Alert("warning", "No hay resultados para tu búsqueda. <a class=\"btn btn-primary btn-sm\" href=\"frontoffice/videos?op=98\">Volver a Inicio</a>"));
		}else {
			
			request.setAttribute("videos", lista);
		}
		
	}

	private void listarPorNombreConLikeUsuario(HttpServletRequest request, HttpServletResponse response) {
		
		String nombre = request.getParameter("nombreBuscar");
		
		request.setAttribute("busquedaVideo", nombre);
		
		ArrayList<Video> lista = videoDAO.getAllByNameWithLikeUsuario(usuarioLogueado.getId(), nombre);

		if(lista.size() == 0) {
			
			request.setAttribute("mensaje", new Alert("warning", "No hay resultados para tu búsqueda. <a class=\"btn btn-primary btn-sm\" href=\"frontoffice/videos?op=98\">Volver a Inicio</a>"));
		}else {
			
			request.setAttribute("videos", lista);
		}
		
		
	}

	private void listarTodosConLikeUsuario(HttpServletRequest request, HttpServletResponse response) {
		

		int idUsuario = usuarioLogueado.getId();
		
		request.setAttribute("videos", videoDAO.getAllWithLikeUsuario(idUsuario));
		
		view = VIEW_INDEX;
		
	}

	private void sumarLike(HttpServletRequest request, HttpServletResponse response) {


		int idUsuario = usuarioLogueado.getId();

		String sIdVideo = request.getParameter("id");

		int idVideo = Integer.parseInt(sIdVideo);

		videoDAO.sumarLike(idUsuario, idVideo);

		listarTodosConLikeUsuario(request, response);

	}
	
	private void restarLike(HttpServletRequest request, HttpServletResponse response) {


		int idUsuario = usuarioLogueado.getId();

		String sIdVideo = request.getParameter("id");

		int idVideo = Integer.parseInt(sIdVideo);

		videoDAO.restarLike(idUsuario, idVideo);

		listarTodosConLikeUsuario(request, response);

	}

	private void nuevo(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("video", new Video());
		// TODO que se mande el usuario, para que se pinte el id del usuario y se pueda
		// mandar
		request.setAttribute("categorias", categoriaDAO.getAll());

		view = VIEW_FORM;
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {

		String sId = request.getParameter("id");
		String sIdUsuario = request.getParameter("idUsuario");
		int id = Integer.parseInt(sId);
		int idUsuario = Integer.parseInt(sIdUsuario);

		Usuario usuario = new Usuario();
		usuario.setId(usuarioLogueado.getId());

		Video video = new Video();
		video.setId(id);
		video.setUsuario(usuario);

		if (videoDAO.deleteByUser(video)) {
			request.setAttribute("mensaje", new Alert("success", "Registro Eliminado"));
		} else {
			request.setAttribute("mensaje", new Alert("danger", "ERROR, no se pudo eliminar"));
		}

		listar(request, response);

	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) {


		String nombre = request.getParameter("nombre");
		String codigo = request.getParameter("codigo");

		int idVideo = Integer.parseInt(request.getParameter("id"));
		int idCategoria = Integer.parseInt(request.getParameter("categoria_id"));

		Usuario usuario = new Usuario();
		usuario.setId(usuarioLogueado.getId());

		Video v = new Video();
		v.setId(idVideo);
		v.setNombre(nombre);
		v.setCodigo(codigo);
		v.setUsuario(usuario);

		Set<ConstraintViolation<Video>> violations = validator.validate(v);
		if (violations.isEmpty()) {

			try {
				// TODO revisar update, que no pueda un hacker cambiar el id video y modificar
				// el video de otro
				if (v.getId() == -1) {
					videoDAO.crear(v, v.getUsuario().getId(), idCategoria);
					request.setAttribute("mensaje", new Alert("success", "Registro creado con exito"));
				} else {

					Video video = new Video();

					video = videoDAO.getById(idVideo);

					if (video.getUsuario().getId() == usuario.getId()) {

						videoDAO.modificar(v, v.getUsuario().getId(), idCategoria);
						request.setAttribute("mensaje", new Alert("success", "Registro modificado con exito"));

					} else {
						request.setAttribute("mensaje",
								new Alert("danger", "Esta muy feo intentar hackear, ahora iremos a por ti"));
					}

				}

			} catch (Exception e) {

				request.setAttribute("mensaje", new Alert("danger", "Tenemos un problema, el codigo ya existe"));
			}

		} else { // hay violaciones de las validaciones

			String mensaje = "";

			for (ConstraintViolation<Video> violation : violations) {
				mensaje += violation.getPropertyPath() + ": " + violation.getMessage() + "<br>";
			}
			request.setAttribute("mensaje", new Alert("warning", mensaje));
		}
		request.setAttribute("video", videoDAO.getById(idVideo));
		request.setAttribute("usuarios", usuarioDAO.getAll());
		request.setAttribute("categorias", categoriaDAO.getAll());

		view = VIEW_FORM;

	}

	private void listar(HttpServletRequest request, HttpServletResponse response) {



		if (usuarioLogueado != null) {

			ArrayList<Video> lista = videoDAO.getAllByUserId(usuarioLogueado.getId());

			if (lista.size() == 0) {

				request.setAttribute("mensaje", new Alert("warning", "Todavia no tienes videos creados."));
			} else {

				request.setAttribute("videos", videoDAO.getAllByUserId(usuarioLogueado.getId()));
			}
		}

		view = VIEW_MIS_VIDEOS;

	}
	
	private void listarPorIdUsuarioConLikeUsuario(HttpServletRequest request, HttpServletResponse response) {

		int idUsuarioLogueado = usuarioLogueado.getId();
		
		String sIdUsuario = request.getParameter("idUsuario");
		int idUsuario = Integer.parseInt(sIdUsuario);
		
		request.setAttribute("nombreUsuario", request.getParameter("nombreUsuario"));
		
		request.setAttribute("videos", videoDAO.getAllByUserIdWithLikeUsuario(idUsuarioLogueado, idUsuario));
		
		view = VIEW_INDEX;
		
	}
	
	private void listarPorIdCategoriaConLikeUsuario(HttpServletRequest request, HttpServletResponse response) {

		String sIdCategoria = request.getParameter("idCategoria");
		int idCategoria = Integer.parseInt(sIdCategoria);
		
		request.setAttribute("nombreCategoria", request.getParameter("nombreCategoria"));
		
		request.setAttribute("videos", videoDAO.getAllByCatIdWithLikeUsuario(usuarioLogueado.getId(), idCategoria));
		
		view = VIEW_INDEX;
		
	}

	private void detalle(HttpServletRequest request, HttpServletResponse response) {

		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);

		Video v = videoDAO.getById(id);
		request.setAttribute("video", v);

		request.setAttribute("categorias", categoriaDAO.getAll());

		view = VIEW_FORM;

	}
	
	private void detalleConLikesUsuario(HttpServletRequest request, HttpServletResponse response) {

		
		String sVideoId = request.getParameter("id");
		int videoId = Integer.parseInt(sVideoId);

		Video v = videoDAO.getByIdWithLikeUsuario(usuarioLogueado.getId(), videoId);
		request.setAttribute("video", v);

		view = VIEW_DETALLE_PUBLICO;

	}

}
