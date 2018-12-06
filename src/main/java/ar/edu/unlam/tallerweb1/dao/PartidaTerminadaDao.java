package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.modelo.PartidasTerminadas;

public interface PartidaTerminadaDao {
	public void guardarPartidaTerminada(Partida partida, Integer ganador);
	public List<PartidasTerminadas> obtenerUltimas5PartidasTerminadas();
}
