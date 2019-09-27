package com.ipartek.formacion.model.pojo;

import java.util.Date;

public class Like {
	
	private int idUsuario;
	
	private int idVideo;
	
	private Date fecha;

	public Like() {
		super();
		this.idUsuario = -1;
		this.idVideo = -1;
		this.fecha = new Date();
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdVideo() {
		return idVideo;
	}

	public void setIdVideo(int idVideo) {
		this.idVideo = idVideo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Like [idUsuario=" + idUsuario + ", idVideo=" + idVideo + ", fecha=" + fecha + "]";
	}
	
	
	
	

}
