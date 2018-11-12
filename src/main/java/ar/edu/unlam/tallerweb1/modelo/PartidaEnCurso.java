package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PartidaEnCurso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Usuario jugador1;
	@ManyToOne
	private Usuario jugador2;
	private String mensaje;
	private Integer estado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Usuario getJugador1() {
		return jugador1;
	}
	public void setJugador1(Usuario jugador1) {
		this.jugador1 = jugador1;
	}
	public Usuario getJugador2() {
		return jugador2;
	}
	public void setJugador2(Usuario jugador2) {
		this.jugador2 = jugador2;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	
}
