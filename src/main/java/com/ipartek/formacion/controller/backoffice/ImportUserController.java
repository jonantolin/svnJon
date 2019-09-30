package com.ipartek.formacion.controller.backoffice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ipartek.formacion.model.ConnectionManager;
import com.ipartek.formacion.model.dao.UsuarioDAO;
import com.ipartek.formacion.model.pojo.Usuario;

/**
 * Servlet implementation class ImportUserController
 */
@WebServlet("/backoffice/importar")
public class ImportUserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static Log LOG = LogFactory.getLog(ImportUserController.class);

	private UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int leidas = 0;
		int insertadas = 0;
		int erroneas = 0;

		FileReader lector = new FileReader("C:\\1713\\eclipse-workspace2\\svnJon\\personas.txt");

		long tiempoAntes = System.currentTimeMillis();

		try (Connection con = ConnectionManager.getConnection(); BufferedReader buffer = new BufferedReader(lector)) {

			con.setAutoCommit(false);

			boolean eof = false;

			Usuario usuario;

			while (!eof) {

				String linea = buffer.readLine();
				if (linea == null) {
					eof = true;

				} else {

					leidas++;

					usuario = new Usuario();

					String[] lineaA = linea.split(",");

					if (lineaA.length == 7) {

						usuario.setNombre(lineaA[0] + " " + lineaA[1] + " " + lineaA[2]);
						usuario.setContrasenya(lineaA[5]);

						try {

							usuarioDAO.importarMuchos(usuario, con);
							insertadas++;

						} catch (Exception e) { // error en la BD

							erroneas++;
							LOG.warn("***Linea error***" + linea);

						}

					} else { // error de lectura del fichero de texto

						erroneas++;
						LOG.warn("***Linea error***" + linea);

					}
				}
			} // end while

			con.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		long tardadoMiliSegundos = System.currentTimeMillis() - tiempoAntes;
		long tardadoSegundos = tardadoMiliSegundos / 1000;

		request.setAttribute("milisegundos", tardadoMiliSegundos);
		request.setAttribute("segundos", tardadoSegundos);

		request.setAttribute("leidas", leidas);
		request.setAttribute("insertadas", insertadas);
		request.setAttribute("erroneas", erroneas);

		LOG.info("------------ proceso migracion terminado ---------------");
		LOG.info("Leidas : " + leidas);
		LOG.info("Insertadas : " + insertadas);
		LOG.info("Erroneas : " + erroneas);
		LOG.info("Tiempo " + tardadoMiliSegundos + "ms");
		LOG.info("------------ proceso migracion terminado ---------------");

		request.getRequestDispatcher("importacion.jsp").forward(request, response);

	}

}
