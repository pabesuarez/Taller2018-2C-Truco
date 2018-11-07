package ar.edu.unlam.tallerweb1.modelo;

public class Partida {
	//identificador de la partida
	private Integer partidaID;
	//indican si es necesario refrescar la pantalla del jugador
	private boolean cambiosJugador1 = false;
	private boolean cambiosJugador2 = false;
	//cartas en mano de los jugadores (de 0 a 39)
	private int[] manoJugador1;
	private int[] manoJugador2;
	//que cartas fueron jugadas (0,1 o 2)
	private int[] cartasEnJuego1;
	private int[] cartasEnJuego2;
	//cuantos puntos lleva cada jugador
	private Integer puntajeJugador1=0;
	private Integer puntajeJugador2=0;
	//de quien es el turno
	private Integer turno=1;
	//quien es mano
	private Integer mano;
	//estado de la partida (0: recien creada, 1: esperando jugador, 2:empezada)
	private Integer estado=0;
	//indica la ronda de la mano actual
	private Integer ronda=0;
	private int[] resultado;
	
	
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
	
	public int[] getCartasEnJuego1() {
		return cartasEnJuego1;
	}
	public void setCartasEnJuego1(int[] cartasEnJuego1) {
		this.cartasEnJuego1 = cartasEnJuego1;
	}
	public int[] getCartasEnJuego2() {
		return cartasEnJuego2;
	}
	public void setCartasEnJuego2(int[] cartasEnJuego2) {
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
	public Integer getRonda() {
		return ronda;
	}
	public void setRonda(Integer ronda) {
		this.ronda = ronda;
	}
	public void setCartaJuego1(Integer i, Integer carta) {
		this.cartasEnJuego1[i]=carta;
	}
	public void setCartaJuego2(Integer i, Integer carta) {
		this.cartasEnJuego2[i]=carta;
	}
	public Integer getCartaJuego1(Integer i) {
		return this.cartasEnJuego1[i];
	}
	public Integer getCartaJuego2(Integer i) {
		return this.cartasEnJuego2[i];
	}
	public Integer getCartaMano1(Integer i) {
		return this.manoJugador1[i];
	}
	public Integer getCartaMano2(Integer i) {
		return this.manoJugador2[i];
	}
	public Integer getResultado(Integer i) {
		return this.resultado[i];
	}
	public void setResultado(Integer i,Integer valor) {
		this.resultado[i] = valor;
	}
	public int[] getResultado() {
		return resultado;
	}
	public void setResultado(int[] resultado) {
		this.resultado = resultado;
	}
	
}
