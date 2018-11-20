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
	
	private String ganador;
	private String perdedor;
	
	@ManyToOne
	private Usuario jugador1;
	@ManyToOne
	private Usuario jugador2;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGanador() {
		return ganador;
	}
	public void setGanador(String ganador) {
		this.ganador = ganador;
	}
	public String getPerdedor() {
		return perdedor;
	}
	public void setPerdedor(String perdedor) {
		this.perdedor = perdedor;
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

