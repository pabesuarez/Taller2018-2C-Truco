package ar.edu.unlam.tallerweb1.modelo;

public class Partida {
	private Integer partidaID;
	private boolean cambiosJugador1 = false;
	private boolean cambiosJugador2 = false;
	private int[] manoJugador1;
	private int[] manoJugador2;
	private boolean[] cartasEnJuego1;
	private boolean[] cartasEnJuego2;
	private Integer puntajeJugador1;
	private Integer puntajeJugador2;
	private Integer turno;
	private Integer mano;
	private Integer estado=0;
	
	
	
	public boolean isCambiosJugador1() {
		return cambiosJugador1;
	}
	public void setCambiosJugador1(boolean cambiosJugador1) {
		this.cambiosJugador1 = cambiosJugador1;
	}
	public boolean isCambiosJugador2() {
		return cambiosJugador2;
	}
	public void setCambiosJugador2(boolean cambiosJugador2) {
		this.cambiosJugador2 = cambiosJugador2;
	}
	public Integer getPartidaID() {
		return this.partidaID;
	}
	public void setPartidaID(Integer partidaID) {
		this.partidaID = partidaID;
	}
	public int[] getManoJugador1() {
		return manoJugador1;
	}
	public void setManoJugador1(int[] manoJugador1) {
		this.manoJugador1 = manoJugador1;
	}
	public int[] getManoJugador2() {
		return manoJugador2;
	}
	public void setManoJugador2(int[] manoJugador2) {
		this.manoJugador2 = manoJugador2;
	}
	public boolean[] getCartasEnJuego1() {
		return cartasEnJuego1;
	}
	public void setCartasEnJuego1(boolean[] cartasEnJuego1) {
		this.cartasEnJuego1 = cartasEnJuego1;
	}
	public boolean[] getCartasEnJuego2() {
		return cartasEnJuego2;
	}
	public void setCartasEnJuego2(boolean[] cartasEnJuego2) {
		this.cartasEnJuego2 = cartasEnJuego2;
	}
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
	public Integer getTurno() {
		return turno;
	}
	public void setTurno(Integer turno) {
		this.turno = turno;
	}
	public Integer getMano() {
		return mano;
	}
	public void setMano(Integer mano) {
		this.mano = mano;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	
}
