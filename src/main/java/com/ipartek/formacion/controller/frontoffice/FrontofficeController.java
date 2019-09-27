package com.ipartek.formacion.controller.frontoffice;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ipartek.formacion.model.dao.UsuarioDAO;
import com.ipartek.formacion.model.dao.VideoDAO;
import com.ipartek.formacion.model.pojo.Usuario;
import com.ipartek.formacion.model.pojo.Video;

/**
 * Servlet implementation class FrontofficeController
 */
@WebServlet("/frontoffice/inicio")
public class FrontofficeController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final VideoDAO videoDAO = VideoDAO.getInstance();
	private static final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		int numeroLikesTotales = videoDAO.getAllLikesByUserId(usuario.getId());

		ArrayList<Video> videos = videoDAO.getAllByUserId(usuario.getId());

		request.setAttribute("numeroLikes", numeroLikesTotales);

		request.setAttribute("numeroVideos", videos.size());

		request.getRequestDispatcher("mipanel.jsp").forward(request, response);

	}

}
