package com.ipartek.formacion.controller.publica;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validation;
import javax.validation.Validator;

import com.ipartek.formacion.controller.pojo.Alert;
import com.ipartek.formacion.model.dao.VideoDAO;
import com.ipartek.formacion.model.pojo.Video;

/**
 * Servlet implementation class VideoController
 */
@WebServlet("/publica/videos")
public class VideoController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_INDEX = "index.jsp";
	public static final String VIEW_FORM = "detalle.jsp";
	public static String view = VIEW_INDEX;

	public static final String OP_LISTAR = "0";
	public static final String OP_LISTAR_POR_USUARIO = "75";
	public static final String OP_LISTAR_POR_CATEGORIA = "78";

	public static final String OP_DETALLE = "13";
	public static final String OP_BUSCAR_POR_NOMBRE = "8";

	public static final String OP_BUSCAR_POR_USUARIO = "41";

	private static VideoDAO videoDAO;

	private Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		videoDAO = VideoDAO.getInstance();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("op");
		if (op == null) {
			op = OP_LISTAR;
		}

		switch (op) {
		case OP_DETALLE:
			detalle(request, response);
			break;

		case OP_BUSCAR_POR_NOMBRE:
			listarPorNombre(request, response);
			break;

		case OP_BUSCAR_POR_USUARIO:
			listarPorNombreUsuario(request, response);
			break;
			
		case OP_LISTAR_POR_USUARIO:
			listarPorIdUsuario(request, response);
			break;	
			
		case OP_LISTAR_POR_CATEGORIA:
			listarPorIdCategoria(request, response);
			break;	

		default:
			listar(request, response);
			break;
		}

		request.getRequestDispatcher(view).forward(request, response);
	}

	private void listarPorIdUsuario(HttpServletRequest request, HttpServletResponse response) {

		String sIdUsuario = request.getParameter("idUsuario");
		int idUsuario = Integer.parseInt(sIdUsuario);
		
		request.setAttribute("nombreUsuario", request.getParameter("nombreUsuario"));
		
		request.setAttribute("videos", videoDAO.getAllByUserId(idUsuario));
		
		view = VIEW_INDEX;
		
	}
	
	private void listarPorIdCategoria(HttpServletRequest request, HttpServletResponse response) {

		String sIdCategoria = request.getParameter("idCategoria");
		int idCategoria = Integer.parseInt(sIdCategoria);
		
		request.setAttribute("nombreCategoria", request.getParameter("nombreCategoria"));
		
		request.setAttribute("videos", videoDAO.getAllByCatId(idCategoria));
		
		view = VIEW_INDEX;
		
	}


	private void listarPorNombre(HttpServletRequest request, HttpServletResponse response) {
		
		String nombre = request.getParameter("nombreBuscar");
		
		request.setAttribute("busquedaVideo", nombre);
		
		ArrayList<Video> lista = videoDAO.getAllByName(request.getParameter("nombreBuscar"));

		if(lista.size() == 0) {
			
			request.setAttribute("mensaje", new Alert("warning", "No hay resultados para tu búsqueda. <a class=\"btn btn-primary btn-sm\" href=\"publica/videos\">Volver a Inicio</a>"));
		}else {
			
			request.setAttribute("videos", lista);
		}

		view = VIEW_INDEX;

	}

	private void listarPorNombreUsuario(HttpServletRequest request, HttpServletResponse response) {
		
		String nombre = request.getParameter("usuarioBuscar");
		
		request.setAttribute("busquedaUsuario", nombre);
		
		ArrayList<Video> lista = videoDAO.getAllByUserName(request.getParameter("usuarioBuscar"));
		
		if(lista.size() == 0) {
			
			request.setAttribute("mensaje", new Alert("warning", "No hay resultados para tu búsqueda. <a class=\"btn btn-primary btn-sm\" href=\"publica/videos\">Volver a Inicio</a>"));
		}else {
			
			request.setAttribute("videos", lista);
		}
		

		view = VIEW_INDEX;

	}

	private void listar(HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("videos", videoDAO.getAllVisible(true));

		view = VIEW_INDEX;

	}

	private void detalle(HttpServletRequest request, HttpServletResponse response) {

		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);

		Video v = videoDAO.getById(id);
		request.setAttribute("video", v);

		view = VIEW_FORM;

	}

}
