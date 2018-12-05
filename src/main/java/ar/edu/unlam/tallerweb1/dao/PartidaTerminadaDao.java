package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Partida;

public interface PartidaTerminadaDao {
	public void guardarPartidaTerminada(Partida partida, Integer ganador);
}
