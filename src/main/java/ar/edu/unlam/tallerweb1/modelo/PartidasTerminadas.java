package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PartidasTerminadas {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Usuario ganador;
	@ManyToOne
	private Usuario jugador1;
	@ManyToOne
	private Usuario jugador2;
	
	private Integer puntajeJugador1;
	
	public Integer getPuntajeJugador1() {
		return puntajeJugador1;
	}
	public void setPuntajeJugador1(Integer puntajeJugador1) {
		this.puntajeJugador1 = puntajeJugador1;
	}
	public Integer getPuntajeJugador2() {
		return puntajeJugador2;
	}
	public void setPuntajeJugador2(Integer puntajeJugador2) {
		this.puntajeJugador2 = puntajeJugador2;
	}
	private Integer puntajeJugador2;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Usuario getGanador() {
		return ganador;
	}
	public void setGanador(Usuario ganador) {
		this.ganador = ganador;
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
	
}

