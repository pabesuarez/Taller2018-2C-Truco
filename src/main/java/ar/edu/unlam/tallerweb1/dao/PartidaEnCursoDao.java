package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.PartidaEnCurso;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface PartidaEnCursoDao {
	public PartidaEnCurso buscarPorId(Long partidaID);
	public void nuevaPartida(PartidaEnCurso partida);
	public void removerPartida(Long partidaID);
	public void cambiarEstado(Long partidaID, Integer estado);
	public void unirJugador(Long partidaID, Integer numero, Usuario jugador);
	public List<PartidaEnCurso> traerTodasLasPartidasEnProgreso();
}
