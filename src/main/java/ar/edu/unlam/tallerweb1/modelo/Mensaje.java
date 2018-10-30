package ar.edu.unlam.tallerweb1.modelo;

//Base para mensajes entre el cliente y el servidor
public class Mensaje {
	private Integer comando;
	private Integer parametro;
	private Integer partidaID;
	private Integer jugador;
	
	
	public Integer getJugador() {
		return jugador;
	}
	public void setJugador(Integer jugador) {
		this.jugador = jugador;
	}
	public Integer getPartidaID() {
		return partidaID;
	}
	public void setPartidaID(Integer partidaID) {
		this.partidaID = partidaID;
	}
	public Integer getComando() {
		return comando;
	}
	public void setComando(Integer comando) {
		this.comando = comando;
	}
	public Integer getParametro() {
		return parametro;
	}
	public void setParametro(Integer parametro) {
		this.parametro = parametro;
	}
	
	
}
