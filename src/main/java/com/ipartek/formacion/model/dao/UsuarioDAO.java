package com.ipartek.formacion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ipartek.formacion.model.ConnectionManager;
import com.ipartek.formacion.model.pojo.Rol;
import com.ipartek.formacion.model.pojo.Usuario;

public class UsuarioDAO {

	private static UsuarioDAO INSTANCE = null;

	private static final String SQL_GET_ALL = "SELECT u.id, u.nombre, r.id as 'id_rol', r.nombre as 'nombre_rol', contrasenya, fecha_creacion, fecha_eliminacion, "
			+ " ( select count(*) FROM video WHERE u.id = usuario_id )  as num_videos " + " FROM usuario as u "
			+ " INNER JOIN rol as r ON u.id_rol = r.id; ";

	// TODO get_by_id contar num videos del usuario como en get all
	private static final String SQL_GET_BY_ID = "SELECT u.id, u.nombre, r.id as 'id_rol', r.nombre as 'nombre_rol', contrasenya, fecha_creacion, fecha_eliminacion, COUNT(v.id) as num_videos"
			+ " FROM usuario as u INNER JOIN rol as r ON u.id_rol = r.id"
			+ " LEFT JOIN video as v ON u.id = v.usuario_id" + " WHERE u.id = ? GROUP BY u.id;";

	private static final String SQL_GET_ALL_BY_NOMBRE = "SELECT u.id, u.nombre, r.id as 'id_rol', r.nombre as 'nombre_rol', contrasenya, fecha_creacion, fecha_eliminacion, COUNT(v.id) as num_videos"
			+ " FROM usuario as u INNER JOIN rol as r ON u.id_rol = r.id"
			+ " LEFT JOIN video as v ON u.id = v.usuario_id"
			+ " WHERE u.nombre LIKE ? GROUP BY u.id ORDER BY u.nombre DESC LIMIT 500;";

	private static final String SQL_GET_ALL_BY_NOMBRE_ORDER = "SELECT u.id, u.nombre, r.id as 'id_rol', r.nombre as 'nombre_rol', contrasenya, fecha_creacion, fecha_eliminacion, COUNT(v.id) as num_videos"
			+ " FROM usuario as u INNER JOIN rol as r ON u.id_rol = r.id"
			+ " LEFT JOIN video as v ON u.id = v.usuario_id" + " WHERE u.nombre LIKE ? ORDER BY u.nombre ? LIMIT 500;";

	private static final String SQL_EXISTE = " SELECT u.id, u.nombre, r.id as 'id_rol', r.nombre as 'nombre_rol', contrasenya, fecha_creacion, fecha_eliminacion, 0 as num_videos "
			+ " FROM usuario as u, rol as r " + " WHERE u.id_rol = r.id AND u.nombre = ? AND contrasenya = ? ;";
	private static final String SQL_INSERT = "INSERT INTO usuario ( nombre, contrasenya) VALUES ( ? , ?);";
	private static final String SQL_UPDATE = "UPDATE usuario SET nombre= ?, contrasenya= ? WHERE id = ?;";
	// private static final String SQL_DELETE = "DELETE FROM usuario WHERE id = ?;";
	private static final String SQL_DELETE_LOGICO = "UPDATE usuario SET fecha_eliminacion = CURRENT_TIMESTAMP() WHERE id = ?;";

	/*
	 * IMPORTANTE:
	 * 
	 * Habia hecho consultas para la aplicacion con el numero de videos del usuario,
	 * likes, etc. A posterior se nos pide otra cosa, asi que no voy a ponerme a
	 * cambiar ahora las consultas antiguas y petar la app asi que creo unas
	 * consuktas nuevas poniendo _JSON al final
	 * 
	 */

	private UsuarioDAO() {
		super();
	}

	public static synchronized UsuarioDAO getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new UsuarioDAO();
		}

		return INSTANCE;

	}

	/**
	 * Compruab si existe el usuario en la base datos, lo busca por su nombre y
	 * conetrsenya
	 * 
	 * @param nombre
	 * @param contrasenya
	 * @return Usuario con datos si existe, null en caso de no existir
	 */
	public Usuario existe(String nombre, String contrasenya) {

		Usuario usuario = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_EXISTE);) {

			// sustituir ? por parametros
			pst.setString(1, nombre);
			pst.setString(2, contrasenya);

			// ejecutar sentencia SQL y obtener Resultado
			try (ResultSet rs = pst.executeQuery()) {

				if (rs.next()) {
					usuario = mapper(rs);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usuario;
	}

	public ArrayList<Usuario> getAll() {

		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				/*
				 * Usuario u = new Usuario(); u.setId(rs.getInt("id"));
				 * u.setNombre(rs.getString("nombre"));
				 * u.setContrasenya(rs.getString("contrasenya")); lista.add(u);
				 */
				lista.add(mapper(rs));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public ArrayList<Usuario> getAllByNombre(String nombre) {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_NOMBRE);) {

			pst.setString(1, "%" + nombre + "%");

			try (ResultSet rs = pst.executeQuery()) {

				while (rs.next()) {
					lista.add(mapper(rs));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public ArrayList<Usuario> getAllByNombreOrder(String nombre, String order) {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_NOMBRE_ORDER);) {

			pst.setString(1, "%" + nombre + "%");

			if (order.equals("asc")) {
				pst.setString(2, "ASC");
			} else {
				pst.setString(2, "DESC");
			}
			try (ResultSet rs = pst.executeQuery()) {

				while (rs.next()) {
					lista.add(mapper(rs));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lista;
	}

	public Usuario getById(int id) {
		Usuario resul = new Usuario();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID)) {

			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					resul = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resul;
	}

	public boolean delete(int id) {
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_DELETE_LOGICO);) {

			pst.setInt(1, id);

			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				resultado = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}

	public boolean modificar(Usuario pojo) throws Exception {
		boolean resultado = false;
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getContrasenya());
			pst.setInt(3, pojo.getId());

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				resultado = true;
			}

		}
		return resultado;
	}

	public Usuario crear(Usuario pojo) throws Exception {

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getContrasenya());

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				// conseguimos el ID que acabamos de crear
				ResultSet rs = pst.getGeneratedKeys();
				if (rs.next()) {
					pojo.setId(rs.getInt(1));
				}

			}

		}

		return pojo;
	}

	private Usuario mapper(ResultSet rs) throws SQLException {

		Usuario u = new Usuario();
		u.setId(rs.getInt("id"));
		u.setNombre(rs.getString("nombre"));
		u.setContrasenya(rs.getString("contrasenya"));
		u.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
		u.setFechaEliminacion(rs.getTimestamp("fecha_eliminacion"));
		u.setNumVideos(rs.getInt("num_videos"));

		Rol rol = new Rol();
		rol.setId(rs.getInt("id_rol"));
		rol.setNombre(rs.getString("nombre_rol"));
		u.setRol(rol);

		return u;
	}

}
