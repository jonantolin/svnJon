package com.ipartek.formacion.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ipartek.formacion.model.ConnectionManager;
import com.ipartek.formacion.model.pojo.Categoria;
import com.ipartek.formacion.model.pojo.Like;
import com.ipartek.formacion.model.pojo.Usuario;
import com.ipartek.formacion.model.pojo.Video;
import com.mysql.jdbc.Statement;

public class VideoDAO {

	private static VideoDAO INSTANCE = null;

	
	// SENTENCIAS PARTE PUBLICA
	private static final String SQL_GET_ALL = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo,"
			+ " u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion,"
			+ " c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta" + " FROM video as v"
			+ " INNER JOIN usuario as u ON v.usuario_id = u.id " + " INNER JOIN categoria as c ON v.categoria_id = c.id"
			+ " LEFT JOIN likes as l ON v.id = l.video_id" + " GROUP BY v.id LIMIT 500;";

	private static final String SQL_GET_ALL_VISIBLE = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo,"
			+ " u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion,"
			+ " c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta" + " FROM video as v"
			+ " INNER JOIN usuario as u ON v.usuario_id = u.id " + " INNER JOIN categoria as c ON v.categoria_id = c.id"
			+ " LEFT JOIN likes as l ON v.id = l.video_id" + " WHERE u.fecha_eliminacion IS NULL"
			+ " GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";

	private static final String SQL_GET_ALL_NO_VISIBLE = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo,"
			+ " u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion,"
			+ " c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta" + " FROM video as v"
			+ " INNER JOIN usuario as u ON v.usuario_id = u.id " + " INNER JOIN categoria as c ON v.categoria_id = c.id"
			+ " LEFT JOIN likes as l ON v.id = l.video_id" + " WHERE u.fecha_eliminacion IS NOT NULL"
			+ " GROUP BY v.id LIMIT 500;";

	private static final String SQL_GET_BY_ID = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo,"
			+ " u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion,"
			+ " c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta" + " FROM video as v"
			+ " INNER JOIN usuario as u ON v.usuario_id = u.id " + " INNER JOIN categoria as c ON v.categoria_id = c.id"
			+ " LEFT JOIN likes as l ON v.id = l.video_id" + " WHERE v.id = ?;";

	
	private static final String SQL_GET_ALL_BY_USER_ID = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo,"
			+ " u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion,"
			+ " c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta   FROM video as v"
			+ " INNER JOIN usuario as u ON v.usuario_id = u.id    INNER JOIN categoria as c ON v.categoria_id = c.id"
			+ " LEFT JOIN likes as l ON v.id = l.video_id   WHERE u.fecha_eliminacion IS NULL AND u.id = ?"
			+ " GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";
	
	private static final String SQL_GET_ALL_BY_CAT_ID = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo,"
			+ " u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion,"
			+ " c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta   FROM video as v"
			+ " INNER JOIN usuario as u ON v.usuario_id = u.id    INNER JOIN categoria as c ON v.categoria_id = c.id"
			+ " LEFT JOIN likes as l ON v.id = l.video_id   WHERE u.fecha_eliminacion IS NULL AND c.id = ?"
			+ " GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";

	private static final String SQL_GET_ALL_BY_USER_NAME = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo,"
			+ " u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion,"
			+ " c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta   FROM video as v"
			+ " INNER JOIN usuario as u ON v.usuario_id = u.id    INNER JOIN categoria as c ON v.categoria_id = c.id"
			+ " LEFT JOIN likes as l ON v.id = l.video_id WHERE u.fecha_eliminacion IS NULL AND u.nombre LIKE ?"
			+ " GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";

	private static final String SQL_GET_ALL_BY_NAME = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo,"
			+ " u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion,"
			+ " c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta   FROM video as v"
			+ " INNER JOIN usuario as u ON v.usuario_id = u.id    INNER JOIN categoria as c ON v.categoria_id = c.id"
			+ " LEFT JOIN likes as l ON v.id = l.video_id  WHERE u.fecha_eliminacion IS NULL AND v.nombre LIKE ?"
			+ " GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";
	
	// END SENTENCIAS PARTE PUBLICA --------------------------------------------------
	
	
	// SENTENCIAS USUARIO LOGUEADO ---------------------------------------------------
	/**
	 * Consulta para sacar todos los videos visibles y a su vez, si el usuario logueado ha dado like a cada video<br>
	 * Si la columna de alias "usuario_like" tiene el id del usuario, le dio like, si es nula no le ha dado like
	 */
	private static final String SQL_GET_ALL_WITH_LIKE_USUARIO = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo," + 
			" u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion," + 
			" c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta, IFNULL(li.usuario_id, -1) as usuario_like " + 
			" FROM video as v" + 
			" INNER JOIN usuario as u ON v.usuario_id = u.id  " + 
			" INNER JOIN categoria as c ON v.categoria_id = c.id" + 
			" LEFT JOIN likes as l ON v.id = l.video_id " + 
			" LEFT JOIN likes as li ON v.id = li.video_id AND li.usuario_id = ?" + 
			" WHERE u.fecha_eliminacion IS NULL" + 
			" GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";
	
	private static final String SQL_GET_BY_ID_WITH_LIKE_USUARIO = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo," + 
			" u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion," + 
			" c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta, IFNULL(li.usuario_id, -1) as usuario_like " + 
			" FROM video as v" + 
			" INNER JOIN usuario as u ON v.usuario_id = u.id  " + 
			" INNER JOIN categoria as c ON v.categoria_id = c.id" + 
			" LEFT JOIN likes as l ON v.id = l.video_id " + 
			" LEFT JOIN likes as li ON v.id = li.video_id AND li.usuario_id = ?" + 
			" WHERE u.fecha_eliminacion IS NULL AND v.id = ?" + 
			" GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";

	private static final String SQL_GET_ALL_LIKES_BY_USER = "SELECT COUNT(u.id) as num_likes"
			+ " FROM video as v, usuario as u, likes as l"
			+ " WHERE v.id = l.video_id AND v.usuario_id = u.id AND u.id = ?;";
	
	private static final String SQL_GET_ALL_BY_USER_ID_WITH_LIKE_USUARIO = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo," + 
			" u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion," + 
			" c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta, IFNULL(li.usuario_id, -1) as usuario_like " + 
			" FROM video as v" + 
			" INNER JOIN usuario as u ON v.usuario_id = u.id  " + 
			" INNER JOIN categoria as c ON v.categoria_id = c.id" + 
			" LEFT JOIN likes as l ON v.id = l.video_id " + 
			" LEFT JOIN likes as li ON v.id = li.video_id AND li.usuario_id = ?" + 
			" WHERE u.fecha_eliminacion IS NULL AND u.id = ?" + 
			" GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";
	
	private static final String SQL_GET_ALL_BY_CAT_ID_WITH_LIKE_USUARIO = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo," + 
			" u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion," + 
			" c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta, IFNULL(li.usuario_id, -1) as usuario_like " + 
			" FROM video as v" + 
			" INNER JOIN usuario as u ON v.usuario_id = u.id  " + 
			" INNER JOIN categoria as c ON v.categoria_id = c.id" + 
			" LEFT JOIN likes as l ON v.id = l.video_id " + 
			" LEFT JOIN likes as li ON v.id = li.video_id AND li.usuario_id = ?" + 
			" WHERE u.fecha_eliminacion IS NULL AND c.id = ?" + 
			" GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";
	
	private static final String SQL_GET_ALL_BY_NAME_WITH_LIKE_USUARIO = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo," + 
			" u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion," + 
			" c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta, IFNULL(li.usuario_id, -1) as usuario_like " + 
			" FROM video as v" + 
			" INNER JOIN usuario as u ON v.usuario_id = u.id  " + 
			" INNER JOIN categoria as c ON v.categoria_id = c.id" + 
			" LEFT JOIN likes as l ON v.id = l.video_id " + 
			" LEFT JOIN likes as li ON v.id = li.video_id AND li.usuario_id = ?" + 
			" WHERE u.fecha_eliminacion IS NULL AND v.nombre LIKE ?" + 
			" GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";
	
	private static final String SQL_GET_ALL_BY_USER_NAME_WITH_LIKE_USUARIO = "SELECT v.id as video_id, v.nombre as video_nombre, v.codigo as codigo," + 
			" u.id as usuario_id, u.nombre as usuario_nombre, u.fecha_creacion as usuario_creacion, u.fecha_eliminacion as usuario_eliminacion," + 
			" c.id as categoria_id, c.nombre as categoria_nombre, COUNT(l.video_id) as megusta, IFNULL(li.usuario_id, -1) as usuario_like " + 
			" FROM video as v" + 
			" INNER JOIN usuario as u ON v.usuario_id = u.id  " + 
			" INNER JOIN categoria as c ON v.categoria_id = c.id" + 
			" LEFT JOIN likes as l ON v.id = l.video_id " + 
			" LEFT JOIN likes as li ON v.id = li.video_id AND li.usuario_id = ?" + 
			" WHERE u.fecha_eliminacion IS NULL AND u.nombre LIKE ?" + 
			" GROUP BY v.id ORDER BY megusta DESC LIMIT 500;";

	private static final String SQL_SUMAR_LIKE = "INSERT INTO likes (usuario_id, video_id)" + " VALUES (?, ?)";
	
	private static final String SQL_RESTAR_LIKE = "DELETE FROM likes WHERE usuario_id = ? AND video_id = ?";

	private static final String SQL_UPDATE = "UPDATE video SET `nombre`= ?, `codigo`= ? , `usuario_id`= ? , `categoria_id`= ? WHERE `id` = ?;";
	private static final String SQL_INSERT = "INSERT INTO video (nombre, codigo, usuario_id, categoria_id ) VALUES (?,?,?,?);";

	
	private static final String SQL_DELETE = "DELETE FROM video WHERE id = ?";
	private static final String SQL_DELETE_BY_USER = "DELETE FROM video WHERE id = ? AND usuario_id = ? ";

	private VideoDAO() {
		super();
	}

	public static synchronized VideoDAO getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VideoDAO();
		}
		return INSTANCE;
	}

	public boolean sumarLike(int idUsuario, int idVideo) {

		boolean sumado = false;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_SUMAR_LIKE)) {

			pst.setInt(1, idUsuario);
			pst.setInt(2, idVideo);

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				sumado = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sumado;

	}
	
	public boolean restarLike(int idUsuario, int idVideo) {
		
		boolean restado = false;
		
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_RESTAR_LIKE)) {

			pst.setInt(1, idUsuario);
			pst.setInt(2, idVideo);

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				restado = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return restado;
		
		
		
	}

	public ArrayList<Video> getAll() {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				lista.add(mapper(rs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<Video> getAllByUserId(int idUsuario) {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_USER_ID);) {

			pst.setInt(1, idUsuario);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				lista.add(mapper(rs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Video> getAllByUserIdWithLikeUsuario(int idUsuarioLogueado, int idUsuario) {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_USER_ID_WITH_LIKE_USUARIO);) {

			pst.setInt(1, idUsuarioLogueado);
			pst.setInt(2, idUsuario);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Video v = new Video();
				v = mapper(rs);
				
				Like like = new Like();
				int usuarioLike = rs.getInt("usuario_like");
				
				if(usuarioLike != -1) {
					like.setIdUsuario(usuarioLike);
					v.setLike(like);
					
				}
				lista.add(v);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Video> getAllByCatIdWithLikeUsuario(int idUsuarioLogueado, int idCategoria) {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_CAT_ID_WITH_LIKE_USUARIO);) {

			pst.setInt(1, idUsuarioLogueado);
			pst.setInt(2, idCategoria);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Video v = new Video();
				v = mapper(rs);
				
				Like like = new Like();
				int usuarioLike = rs.getInt("usuario_like");
				
				if(usuarioLike != -1) {
					like.setIdUsuario(usuarioLike);
					v.setLike(like);
					
				}
				lista.add(v);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Video> getAllByCatId(int idCategoria) {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_CAT_ID);) {

			pst.setInt(1, idCategoria);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				lista.add(mapper(rs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Video> getAllWithLikeUsuario(int idUsuario) {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_WITH_LIKE_USUARIO);) {

			pst.setInt(1, idUsuario);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Video v = new Video();
				v = mapper(rs);
				
				Like like = new Like();
				int usuarioLike = rs.getInt("usuario_like");
				
				if(usuarioLike != -1) {
					like.setIdUsuario(usuarioLike);
					v.setLike(like);
					
				}
				lista.add(v);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	public int getAllLikesByUserId(int idUsuario) {

		int numeroLikes = 0;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_LIKES_BY_USER);) {

			pst.setInt(1, idUsuario);

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {

				numeroLikes = rs.getInt("num_likes");
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return numeroLikes;
	}

	public ArrayList<Video> getAllByName(String nombre) {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_NAME);) {

			pst.setString(1, "%" + nombre + "%");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				lista.add(mapper(rs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Video> getAllByNameWithLikeUsuario(int idUsuario, String nombre) {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_NAME_WITH_LIKE_USUARIO);) {

			pst.setInt(1, idUsuario);
			pst.setString(2, "%" + nombre + "%");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Video v = new Video();
				v = mapper(rs);
				
				Like like = new Like();
				int usuarioLike = rs.getInt("usuario_like");
				
				if(usuarioLike != -1) {
					like.setIdUsuario(usuarioLike);
					v.setLike(like);
					
				}
				lista.add(v);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	public ArrayList<Video> getAllByUserName(String nombre) {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_USER_NAME);) {

			pst.setString(1, "%" + nombre + "%");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				lista.add(mapper(rs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<Video> getAllByUserNameWithLikeUsuario(int idUsuario, String nombre) {

		ArrayList<Video> lista = new ArrayList<Video>();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_ALL_BY_USER_NAME_WITH_LIKE_USUARIO);) {

			pst.setInt(1, idUsuario);
			pst.setString(2, "%" + nombre + "%");

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Video v = new Video();
				v = mapper(rs);
				
				Like like = new Like();
				int usuarioLike = rs.getInt("usuario_like");
				
				if(usuarioLike != -1) {
					like.setIdUsuario(usuarioLike);
					v.setLike(like);
					
				}
				lista.add(v);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Listado de videos a visibles o no visibles <br>
	 * <b>Visible:</b> Son los videos que el usuario tenga fecha_eliminacion == NULL
	 * <br>
	 * <b>No Visible:</b> Son los videos que el usuario tenga fecha_eliminacion <>
	 * NULL
	 * 
	 * @param isVisible true visbles, false los no visibles
	 * @return
	 */
	public ArrayList<Video> getAllVisible(boolean isVisible) {

		ArrayList<Video> lista = new ArrayList<Video>();

		String sql = SQL_GET_ALL_VISIBLE;
		if (!isVisible) {
			sql = SQL_GET_ALL_NO_VISIBLE;
		}

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(sql);
				ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				lista.add(mapper(rs));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return lista;
	}

	public Video getById(int id) {
		Video video = new Video();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID)) {

			// sustituyo la 1º ? por la variable id
			pst.setInt(1, id);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					/*
					 * Video v = new Video(); v.setId( rs.getInt("id") ); v.setNombre(
					 * rs.getString("nombre")); v.setCodigo( rs.getString("codigo"));
					 */
					video = mapper(rs);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return video;
	}
	
	public Video getByIdWithLikeUsuario(int idUsuario, int idVideo) {
		Video v = new Video();

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_GET_BY_ID_WITH_LIKE_USUARIO)) {

			// sustituyo la 1º ? por la variable id
			pst.setInt(1, idUsuario);
			pst.setInt(2, idVideo);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					
					v = mapper(rs);
					
					Like like = new Like();
					int usuarioLike = rs.getInt("usuario_like");
					
					if(usuarioLike != -1) {
						like.setIdUsuario(usuarioLike);
						v.setLike(like);
						
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}



	public boolean modificar(Video pojo, int usuarioId, int categoriaId) throws Exception {
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_UPDATE)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getCodigo());
			pst.setInt(3, usuarioId); // TODO antes de modificar, comprobar que el video a modificar pertenece al
										// usuario logueado
			pst.setInt(4, categoriaId);
			pst.setInt(5, pojo.getId());

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				resultado = true;
			}

		}
		return resultado;
	}

	public boolean crear(Video pojo, int usuarioId, int categoriaId) throws Exception {
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

			pst.setString(1, pojo.getNombre());
			pst.setString(2, pojo.getCodigo());
			pst.setInt(3, usuarioId);
			pst.setInt(4, categoriaId);

			int affectedRows = pst.executeUpdate();
			if (affectedRows == 1) {
				// conseguir id generado de forma automatica
				try (ResultSet rsKeys = pst.getGeneratedKeys()) {
					if (rsKeys.next()) {
						pojo.setId(rsKeys.getInt(1));
						resultado = true;
					}
				}
			}

		}

		return resultado;
	}


	public boolean delete(int id) { // Mandara el video para obtener el id, y el id de usuario
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_DELETE);) {

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

	public boolean deleteByUser(Video video) { // Mandara el video para obtener el id, y el id de usuario
		boolean resultado = false;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pst = con.prepareStatement(SQL_DELETE_BY_USER);) {

			pst.setInt(1, video.getId());
			pst.setInt(2, video.getUsuario().getId());

			int affetedRows = pst.executeUpdate();
			if (affetedRows == 1) {
				resultado = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultado;
	}


	/**
	 * Convierte un Resultado de la BD a un POJO
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private Video mapper(ResultSet rs) throws SQLException {
		Video v = new Video();
		v.setId(rs.getInt("video_id"));
		v.setNombre(rs.getString("video_nombre"));
		v.setCodigo(rs.getString("codigo"));
		v.setLikes(rs.getInt("megusta"));

		Usuario u = new Usuario();
		u.setId(rs.getInt("usuario_id"));
		u.setNombre(rs.getString("usuario_nombre"));
		u.setFechaCreacion(rs.getTimestamp("usuario_creacion"));
		u.setFechaEliminacion(rs.getTimestamp("usuario_eliminacion"));

		Categoria c = new Categoria();
		c.setId(rs.getInt("categoria_id"));
		c.setNombre(rs.getString("categoria_nombre"));

		v.setUsuario(u);
		v.setCategoria(c);
		return v;
	}



}
