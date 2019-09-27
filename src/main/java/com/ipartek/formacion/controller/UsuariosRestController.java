package com.ipartek.formacion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ipartek.formacion.model.dao.UsuarioDAO;
import com.ipartek.formacion.model.pojo.Usuario;

@WebServlet("/api/usuarios")
public class UsuariosRestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioDAO usuarioDAO;

	@Override
	public void init() throws ServletException {

		super.init();
		usuarioDAO = UsuarioDAO.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nombre = request.getParameter("filtro");
		String ordenacion = request.getParameter("order");

		ArrayList<Usuario> lista = null;

		if (nombre != null) {

			if (ordenacion == null) {

				lista = usuarioDAO.getAllByNombre(nombre);
			} else {

				lista = usuarioDAO.getAllByNombreOrder(nombre, ordenacion);
			}

			if (lista.isEmpty()) {
				response.setStatus(204);
			}

		} else {
			lista = usuarioDAO.getAll();
		}

		// estos campos no apareceran en el json enviado
		for (Usuario u : lista) {
			u.setContrasenya(null);
			u.setFechaCreacion(null);
			u.setFechaEliminacion(null);
			u.setRol(null);

		}

		Gson gson = new Gson();

		// Gson gson = new GsonBuilder().setPrettyPrinting().create();

		String jsonLista = gson.toJson(lista);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		// response.setStatus(SC_OK);

		PrintWriter out = response.getWriter();
		out.print(jsonLista);
		out.flush();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
