package com.ipartek.formacion.controller.backoffice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.controller.ControladorCrud;
import com.ipartek.formacion.controller.pojo.Alert;
import com.ipartek.formacion.model.dao.CategoriaDAO;
import com.ipartek.formacion.model.pojo.Categoria;

/**
 * Servlet implementation class CategoriaController
 */
@WebServlet("/backoffice/categoria")
public class CategoriaController extends ControladorCrud {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_INDEX = "categoria/index.jsp";
	public static final String VIEW_FORM = "categoria/formulario.jsp";

	private static final CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();

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
		request.setAttribute("categorias", categoriaDAO.getAll());
		view = VIEW_INDEX;

	}

	@Override
	protected void IrAFormulario(HttpServletRequest request, HttpServletResponse response) {

		String sid = request.getParameter("id");

		Categoria c = new Categoria();
		if (sid != null) {
			int id = Integer.parseInt(sid);

			c = categoriaDAO.getById(id);

		}
		request.setAttribute("categoria", c);
		view = VIEW_FORM;

	}

	@Override
	protected void eliminar(HttpServletRequest request, HttpServletResponse response) {

		String sId = request.getParameter("id");
		try {
			categoriaDAO.delete(Integer.parseInt(sId));
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

		Categoria c = new Categoria();
		c.setId(id);
		c.setNombre(nombre);

		try {

			if (id == -1) {
				c = categoriaDAO.create(c);
				request.setAttribute("mensaje", new Alert("success", "Categoria creada con éxito"));

			} else {

				categoriaDAO.modificar(c);
				request.setAttribute("mensaje", new Alert("success", "Categoria modificada con éxito"));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		request.setAttribute("categoria", c);
		view = VIEW_FORM;
	}

	@Override
	protected void buscar(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
