package ar.edu.unlam.tallerweb1.servicios;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.modelo.PartidaEnCurso;

public interface ServicioPartida {
	static List<Partida> partidasEnCurso = new ArrayList<Partida>();
	public void repartirCartas(Partida partida);
	public void sumarPuntaje(Partida partida, Integer jugador);
	public void tirarCarta(Partida partida, Integer jugador,Integer carta);
	public void cantarTruco(Partida partida, Integer jugador);
	public void cantarEnvido(Partida partida, Integer jugador, Integer tipo);
	public void quiero(Partida partida, Integer jugador);
	public void noQuiero(Partida partida, Integer jugador);
	public Integer concluirMano(Partida partida, Integer ganador);
	public Partida getPartida(Integer partidaID);
	public Partida nuevaPartida();
	public Partida unirseAPartida(Integer partidaID,Long jugadorId);
	public List<PartidaEnCurso> obtenerPartidasEnCurso();
	public Integer getOponente(Integer jugador);
	public String mensajeTruco(Integer truco);
}
