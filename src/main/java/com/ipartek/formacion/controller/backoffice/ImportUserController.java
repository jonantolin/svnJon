package com.ipartek.formacion.controller.backoffice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipartek.formacion.model.dao.UsuarioDAO;
import com.ipartek.formacion.model.pojo.Usuario;

/**
 * Servlet implementation class ImportUserController
 */
@WebServlet("/backoffice/importar")
public class ImportUserController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDAO;

	public void init(ServletConfig config) throws ServletException {

		usuarioDAO = UsuarioDAO.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// request.setAttribute("listaPrueba", leerMensaje());

		int leidas = 0;
		int insertadas = 0;
		int erroneas = 0;

		long tiempoAntes = System.currentTimeMillis();

		try {
			FileReader lector = new FileReader("C:\\1713\\eclipse-workspace2\\svnJon\\personas.txt");
			BufferedReader buffer = new BufferedReader(lector);

			boolean eof = false;
			while (!eof) {
				String linea = buffer.readLine();
				if (linea == null) {
					eof = true;
				} else {

					leidas++;

					Usuario usuario = new Usuario();

					String[] lineaA = linea.split(","); // hacer pruebas

					String nombre = lineaA[0] + " " + lineaA[1] + " " + lineaA[2];

					usuario.setNombre(nombre);
					usuario.setContrasenya(lineaA[5]);

					try {

						usuarioDAO.crear(usuario);
						insertadas++;

					} catch (Exception e) {

						erroneas++;
					}

				}
			} // end while
			buffer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		long tardadoMiliSegundos = System.currentTimeMillis() - tiempoAntes;
		long tardadoSegundos = tardadoMiliSegundos / 1000;
		long tardadoMin = tardadoSegundos / 60;

		request.setAttribute("segundos", tardadoSegundos);
		request.setAttribute("minutos", tardadoMin);

		request.setAttribute("leidas", leidas);
		request.setAttribute("insertadas", insertadas);
		request.setAttribute("erroneas", erroneas);

		request.getRequestDispatcher("importacion.jsp").forward(request, response);

	}

	/*
	 * public ArrayList<String> leerMensaje() {
	 * 
	 * ArrayList<String> listaPrueba = new ArrayList<String>();
	 * 
	 * try { FileReader lector = new
	 * FileReader("C:\\1713\\eclipse-workspace2\\svnJon\\personas_prueba.txt");
	 * BufferedReader buffer = new BufferedReader(lector);
	 * 
	 * boolean eof = false; while (!eof) { String linea = buffer.readLine(); if
	 * (linea == null) { eof = true; } else {
	 * 
	 * String[] lineaA = linea.split(","); // hacer pruebas listaPrueba.add(linea);
	 * 
	 * } } buffer.close(); } catch (Exception ex) { ex.printStackTrace(); }
	 * 
	 * return listaPrueba;
	 * 
	 * }
	 */

}
