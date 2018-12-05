package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Partida;

public interface ServicioTanto {
	
	public Integer compararTanto(Partida partida);
	public int obtenerTantoDeLaMano(int[] mano);
	public Integer calcularTipoTanto(Integer tipoActual, Integer tipo);
	public Integer calcularValorTanto(Partida partida, boolean respuesta, Integer jugador);
	
}
