package com.ipartek.formacion.controller.frontoffice;

import java.io.IOException;
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
import com.ipartek.formacion.model.dao.UsuarioDAO;
import com.ipartek.formacion.model.pojo.Usuario;

/**
 * Servlet implementation class UsuarioController
 */
@WebServlet("/frontoffice/usuario")
public class UsuarioController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_INDEX = "usuario/index.jsp";
	public static final String VIEW_FORM = "usuario/formulario.jsp";
	public static String view = VIEW_INDEX;

	public static final String OP_LISTAR = "0";
	public static final String OP_GUARDAR = "23";
	public static final String OP_BUSCAR = "8";
	public static final String OP_NUEVO = "4";
	public static final String OP_ELIMINAR = "hfd3";
	public static final String OP_DETALLE = "13";

	private static final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

	private Validator validator;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
		String op = request.getParameter("op");
		if (op == null) {
			op = OP_DETALLE;
		}

		switch (op) {
		case OP_DETALLE:
			detalle(request, response);
			break;

		case OP_BUSCAR:
			buscar(request, response);
			break;

		case OP_GUARDAR:
			guardar(request, response);
			break;

		case OP_ELIMINAR:
			eliminar(request, response);
			break;

		default:
			detalle(request, response);
			break;
		}

		request.getRequestDispatcher(view).forward(request, response);
	}

	private void buscar(HttpServletRequest request, HttpServletResponse response) {

		String nombreBuscar = request.getParameter("nombreBuscar");
		request.setAttribute("usuarios", usuarioDAO.getAllByNombre(nombreBuscar));
		view = VIEW_INDEX;
	}

	private void eliminar(HttpServletRequest request, HttpServletResponse response) {

		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);

		if (usuarioDAO.delete(id)) {
			request.setAttribute("mensaje", new Alert("success", "Registro Eliminado"));
			view = "/logout";
		} else {
			request.setAttribute("mensaje", new Alert("danger", "ERROR, no se pudo eliminar"));
		}

		// listar(request, response);

	}

	private void guardar(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");

		String contrasenya = request.getParameter("contrasenya");

		usuarioLogueado.setContrasenya(contrasenya);

		Set<ConstraintViolation<Usuario>> violations = validator.validate(usuarioLogueado);
		if (violations.isEmpty()) {

			try {

				usuarioDAO.modificar(usuarioLogueado);

				request.setAttribute("mensaje", new Alert("success", "Registro modificado con exito"));

			} catch (Exception e) {

				request.setAttribute("mensaje", new Alert("danger", "No se pudo modificar el registro"));
			}

		} else { // hay violaciones de las validaciones

			String mensaje = "";

			for (ConstraintViolation<Usuario> violation : violations) {
				mensaje += violation.getPropertyPath() + ": " + violation.getMessage() + "<br>";
			}
			request.setAttribute("mensaje", new Alert("warning", mensaje));
		}
		request.setAttribute("usuarioEditar", usuarioLogueado);
		view = VIEW_FORM;

	}

	private void detalle(HttpServletRequest request, HttpServletResponse response) {

		String sid = request.getParameter("id");
		int id = Integer.parseInt(sid);

		HttpSession session = request.getSession();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		Usuario u = usuarioDAO.getById(usuario.getId());
		request.setAttribute("usuarioEditar", u);
		view = VIEW_FORM;

	}

}
