package com.ipartek.formacion.model.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Video {

	private int id;

	@NotNull
	@Size(min = 3, max = 150)
	private String nombre;

	@NotNull
	@Size(min = 11, max = 11, message = "Exactamente debe ser 11")
	private String codigo;

	private Usuario usuario;

	private Categoria categoria;

	private int likes;
	
	// TODO ArrayList<Like> ??? quiza mas correcto
	// De momento recogera el like del usuario logueado si lo hubiera
	private Like like;

	public Video() {
		super();
		this.id = -1;
		this.nombre = "";
		this.codigo = "";
		this.usuario = new Usuario();
		this.categoria = new Categoria();
		this.likes = 0;
		this.like = new Like();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public Like getLike() {
		return like;
	}

	public void setLike(Like like) {
		this.like = like;
	}

	@Override
	public String toString() {
		return "Video [id=" + id + ", nombre=" + nombre + ", codigo=" + codigo + ", usuario=" + usuario + ", categoria="
				+ categoria + ", likes=" + likes + ", like=" + like + "]";
	}

	
}
