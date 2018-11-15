package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Partida;

public interface ServicioTanto {
	
	public Integer compararTanto(Partida partida);
	public int obtenerTantoDeLaMano(int[] mano);
	public void cantarTanto(Partida partida, Integer jugador, Integer comando);

}
