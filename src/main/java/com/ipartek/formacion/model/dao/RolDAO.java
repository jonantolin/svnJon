package com.ipartek.formacion.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.model.ConnectionManager;
import com.ipartek.formacion.model.pojo.Rol;

public class RolDAO {

	private static RolDAO INSTANCE = null;

	// private final static String SQL_GET_ALL = "SELECT id, nombre FROM rol";

	private final static String SQL_GET_ALL = "{ CALL getAll_rol() }";
	private final static String SQL_GET_BY_ID = "{ CALL getById_rol(?) }";
	private final static String SQL_NEW = "{ CALL create_rol(?, ?) }";
	private final static String SQL_UPDATE = "{ CALL update_rol(?, ?) }";
	private final static String SQL_DELETE = "{ CALL delete_rol(?) }";

	private RolDAO() {
		super();
	}

	public static synchronized RolDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RolDAO();
		}
		return INSTANCE;
	}

	public ArrayList<Rol> getAll() {

		ArrayList<Rol> lista = new ArrayList<Rol>();

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall(SQL_GET_ALL);
				ResultSet rs = cs.executeQuery()) {

			while (rs.next()) {

				lista.add(mapper(rs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	public Rol getById(int id) {

		Rol rol = new Rol();

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall(SQL_GET_BY_ID);) {
			cs.setInt("pid", id);

			try (ResultSet rs = cs.executeQuery()) {
				if (rs.next()) {

					rol = mapper(rs);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return rol;
	}

	public Rol create(Rol pojo) throws Exception {

		try (Connection con = ConnectionManager.getConnection(); CallableStatement cst = con.prepareCall(SQL_NEW)) {

			cst.setString("pnombre", pojo.getNombre());
			cst.registerOutParameter(2, java.sql.Types.INTEGER);

			int affectedRows = cst.executeUpdate();

			if (affectedRows == 1) {

				pojo.setId(cst.getInt(2));

			}

		}

		return pojo;
	}

	public boolean modificar(Rol pojo) throws Exception {
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection(); CallableStatement cst = con.prepareCall(SQL_UPDATE)) {

			cst.setString(1, pojo.getNombre());

			cst.setInt(2, pojo.getId());

			int affectedRows = cst.executeUpdate();
			if (affectedRows == 1) {
				resultado = true;
			}

		}
		return resultado;
	}

	public boolean delete(int id) throws Exception {

		boolean borrado = false;

		try (Connection con = ConnectionManager.getConnection(); CallableStatement cst = con.prepareCall(SQL_DELETE)) {

			cst.setInt(1, id);

			int affectedRows = cst.executeUpdate();

			if (affectedRows == 1) {
				borrado = true;
			}

		}

		return borrado;
	}

	public Rol mapper(ResultSet rs) throws SQLException {
		Rol r = new Rol();
		r.setId(rs.getInt("id"));
		r.setNombre(rs.getString("nombre"));

		return r;
	}
}
