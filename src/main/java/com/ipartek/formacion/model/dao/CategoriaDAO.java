package com.ipartek.formacion.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.model.ConnectionManager;
import com.ipartek.formacion.model.pojo.Categoria;

public class CategoriaDAO {

	private static CategoriaDAO INSTANCE = null;

	private static final String SQL_GET_ALL = "{ CALL getAll_categoria()};";

	private static final String SQL_GET_BY_ID = "{ CALL getById_categoria(?)};";

	private static final String SQL_NEW = " { CALL create_categoria(?,?)};";

	private static final String SQL_UPDATE = "{ CALL update_categoria(?, ?) }";

	private static final String SQL_DELETE = "{ CALL delete_categoria(?) }";

	public static synchronized CategoriaDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CategoriaDAO();
		}
		return INSTANCE;
	}
	// cambio

	private CategoriaDAO() {
		super();
	}

	public ArrayList<Categoria> getAll() {

		ArrayList<Categoria> categorias = new ArrayList<Categoria>();

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall(SQL_GET_ALL);

				ResultSet rs = cs.executeQuery()) {

			while (rs.next()) {

				categorias.add(mapper(rs));

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return categorias;
	}

	public Categoria getById(int id) {

		Categoria categoria = new Categoria();

		try (Connection con = ConnectionManager.getConnection();
				CallableStatement cs = con.prepareCall(SQL_GET_BY_ID);) {

			// sustituyo la 1º ? por la variable id
			cs.setInt(1, id);

			try (ResultSet rs = cs.executeQuery()) {
				if (rs.next()) {

					categoria = mapper(rs);
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return categoria;
	}

	public Categoria create(Categoria pojo) throws Exception {

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

	private Categoria mapper(ResultSet rs) throws SQLException {

		Categoria c = new Categoria();

		c.setId(rs.getInt("id"));
		c.setNombre(rs.getString("nombre"));

		return c;
	}

	// REVISAR CATEGORIA
	public boolean modificar(Categoria pojo) throws Exception {
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

}
