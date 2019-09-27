package com.ipartek.formacion.controller.backoffice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.controller.ControladorCrud;
import com.ipartek.formacion.controller.pojo.Alert;
import com.ipartek.formacion.model.dao.RolDAO;
import com.ipartek.formacion.model.pojo.Rol;

/**
 * Servlet implementation class RolController
 */
@WebServlet("/backoffice/rol")
public class RolController extends ControladorCrud {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_INDEX = "rol/index.jsp";
	public static final String VIEW_FORM = "rol/formulario.jsp";

	private static final RolDAO rolDAO = RolDAO.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doProcess(request, response);
	}

	@Override
	protected void listar(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("roles", rolDAO.getAll());
		view = VIEW_INDEX;

	}

	@Override
	protected void IrAFormulario(HttpServletRequest request, HttpServletResponse response) {

		String sid = request.getParameter("id");

		Rol r = new Rol();
		if (sid != null) {
			int id = Integer.parseInt(sid);

			r = rolDAO.getById(id);

		}
		request.setAttribute("rol", r);
		view = VIEW_FORM;

	}
	/*
	 * @Override protected void eliminar(HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * String sId = request.getParameter("id"); try {
	 * rolDAO.delete(Integer.parseInt(sId)); request.setAttribute("mensaje", new
	 * Alert("success", "Registro eliminado.")); } catch (NumberFormatException e) {
	 * 
	 * e.printStackTrace(); } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } listar(request, response);
	 * 
	 * }
	 * 
	 * @Override protected void guardar(HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * String nombre = request.getParameter("nombre"); String sId =
	 * request.getParameter("id"); int id = Integer.parseInt(sId);
	 * 
	 * rol c = new rol(); c.setId(id); c.setNombre(nombre);
	 * 
	 * try {
	 * 
	 * if (id == -1) { c = rolDAO.create(c); request.setAttribute("mensaje", new
	 * Alert("success", "rol creada con éxito"));
	 * 
	 * } else {
	 * 
	 * rolDAO.modificar(c); request.setAttribute("mensaje", new Alert("success",
	 * "rol modificada con éxito")); } } catch (Exception e) {
	 * 
	 * e.printStackTrace(); } request.setAttribute("rol", c); view = VIEW_FORM; }
	 * 
	 * @Override protected void buscar(HttpServletRequest request,
	 * HttpServletResponse response) { // TODO Auto-generated method stub
	 * 
	 * }
	 */

	@Override
	protected void eliminar(HttpServletRequest request, HttpServletResponse response) {

		String sId = request.getParameter("id");
		try {
			rolDAO.delete(Integer.parseInt(sId));
			request.setAttribute("mensaje", new Alert("success", "Registro eliminado."));
		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
		listar(request, response);

	}

	@Override
	protected void guardar(HttpServletRequest request, HttpServletResponse response) {

		String nombre = request.getParameter("nombre");
		String sId = request.getParameter("id");
		int id = Integer.parseInt(sId);

		Rol r = new Rol();
		r.setId(id);
		r.setNombre(nombre);

		try {

			if (id == -1) {
				r = rolDAO.create(r);
				request.setAttribute("mensaje", new Alert("success", "Nuevo Rol creado con éxito"));

			} else {

				rolDAO.modificar(r);
				request.setAttribute("mensaje", new Alert("success", "Rol modificado con éxito"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		request.setAttribute("rol", r);
		view = VIEW_FORM;

	}

	@Override
	protected void buscar(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}
}
