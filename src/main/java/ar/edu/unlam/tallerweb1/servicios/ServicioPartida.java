package ar.edu.unlam.tallerweb1.servicios;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.modelo.PartidaEnCurso;

public interface ServicioPartida {
	static ArrayList<Integer> mazo = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39));
	static List<Partida> partidasEnCurso = new ArrayList<Partida>();
	public void repartirCartas(Partida partida);
	public Integer obtenerValor(Integer carta);
	public Integer compararValor(Integer carta1,Integer carta2);
	public void sumarPuntaje(Partida partida, Integer jugador);
	public void tirarCarta(Partida partida, Integer jugador,Integer carta);
	public void cantarTruco(Partida partida, Integer jugador);
	public void quiero(Partida partida, Integer jugador);
	public void noQuiero(Partida partida, Integer jugador);
	public Integer concluirMano(Partida partida, Integer ganador);
	public Partida getPartida(Integer partidaID);
	public Partida nuevaPartida();
	public Partida unirseAPartida(Integer partidaID,Long jugadorId);
	public List<PartidaEnCurso> obtenerPartidasEnCurso();
}
