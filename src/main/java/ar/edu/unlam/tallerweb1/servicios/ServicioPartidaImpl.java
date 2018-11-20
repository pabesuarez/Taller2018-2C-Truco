package ar.edu.unlam.tallerweb1.servicios;


import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.PartidaEnCursoDao;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.modelo.PartidaEnCurso;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioPartida")
@Transactional
public class ServicioPartidaImpl implements ServicioPartida{

	@Inject
	private ServicioTanto servicioTanto;
	
	@Inject
	private PartidaEnCursoDao partidaEnCursoDao;
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Override
	public void repartirCartas(Partida partida) {
		Collections.shuffle(mazo); //mezclar el mazo
		//repartir cartas
		partida.setManoJugador1(new int[]{mazo.get(0),mazo.get(2),mazo.get(4)});
		partida.setManoJugador2(new int[]{mazo.get(1),mazo.get(3),mazo.get(5)});
		//reiniciar la mesa
		partida.setCartasEnJuego1(new int[] {3,3,3});
		partida.setCartasEnJuego2(new int[] {3,3,3});
		//reiniciar los resultados
		partida.setResultado(new int[] {0,0,0});
		//reiniciar la ronda
		partida.setRonda(0);
		//reiniciar estado de envido
		partida.setGanadorTanto(0);
		partida.setJugadorTanto(0);
		partida.setPuntosPorTanto(0);
		partida.setTipoTanto(0);
		//reiniciar estado de truco
		partida.setPuntosPorTruco(0);
		partida.setJugadorTruco(0);
		//refrescar la pantalla de ambos jugadores
		partida.setCambiosJugador1(true);
		partida.setCambiosJugador2(true);
	}

	@Override
	public Integer obtenerValor(Integer carta) {
		/*
			Cada carta del mazo tiene un valor numerico
			Las decenas indican el palo mientras que la unidad el numero de la carta
			0-9:   espada
			10-19: basto
			20-29: oro
			30-39: copa
		*/
		switch(carta) {
			// 1 de espada
			case 0: 
				return 14;	
			// 1 de basto
			case 10:
				return 13;
			// 7 de espada
			case 6:
				return 12;
			// 7 de oro
			case 26: 
				return 11;
			// 3 (cualquier palo)
			case 2: case 12: case 22: case 32: 
				return 10;
			// 2 (cualquier palo)
			case 1: case 11: case 21: case 31:
				return 9;
			// 1 (oro o copa)
			case 20: case 30:
				return 8;
			// 12 (cualquier palo)
			case 9: case 19: case 29: case 39:
				return 7;
			// 11 (cualquier palo)
			case 8: case 18: case 28: case 38:
				return 6;
			// 10 (cualquier palo)
			case 7: case 17: case 27: case 37:
				return 5;
			// 7 (basto y copa)
			case 16: case 36:
				return 4;
			// 6 (cualquier palo)
			case 5: case 15: case 25: case 35:
				return 3;
			// 5 (cualquier palo)
			case 4: case 14: case 24: case 34:
				return 2;
			// 4 (cualquier palo)
			case 3: case 13: case 23: case 33:
				return 1;
			// en caso de recibir un parametro invalido
			default: break;
		}
		return null;
	}

	@Override
	public void tirarCarta(Partida partida, Integer jugador, Integer carta) {
		// si un jugador tira una carta en su turno se aplica el cambio y cambia de turno
		if(jugador==1 && partida.getTurno() == 1 && partida.getEstado() == 2 && !IntStream.of(partida.getCartasEnJuego1()).anyMatch(x -> x == carta)) {
			partida.setCartaJuego1(partida.getRonda(),carta);
			partida.setTurno(2);
		}else if (jugador==2 && partida.getTurno() == 2 && partida.getEstado() == 2 && !IntStream.of(partida.getCartasEnJuego2()).anyMatch(x -> x == carta)) {
			partida.setCartaJuego2(partida.getRonda(), carta);
			partida.setTurno(1);
		}else {
			return;
		}
		//revisar que cartas hay en mesa
		Integer carta1 = partida.getCartaJuego1(partida.getRonda());
		Integer carta2 = partida.getCartaJuego2(partida.getRonda());
		//si ambos jugadores han tirado carta se comprueban las cartas
		if (carta1 !=3  && carta2 !=3) {
			Integer resultado = compararValor(partida.getCartaMano1(carta1),partida.getCartaMano2(carta2));
			partida.setResultado(partida.getRonda(),resultado);
			//si era la ultima carta de la ronda se concluye la mano
			if(partida.getRonda() == 2) {
				concluirMano(partida,0);
				return;
			} else {
				//verificacion en la segunda ronda
				if(partida.getRonda() == 1) {
					//si la ronda anterior fue parda y la actual tuvo ganador se termina la ronda
					if(partida.getResultado(partida.getRonda()-1) == 3 && resultado != 3) {
						concluirMano(partida,0);
						return;
					}
					//si la ronda anterior no fue parda y la actual si lo fue se termina la ronda
					if(partida.getResultado(partida.getRonda()-1) != 3 && resultado == 3) {
						concluirMano(partida,0);
						return;
					}
				}
				//si era la segunda ronda y el ganador fue el mismo jugador se termina la ronda
				if (partida.getRonda() == 1 && partida.getResultado(0) == partida.getResultado(1)) {
					concluirMano(partida,0);
					return;
				}
				
				//en caso de no ser parda pasa a ser turno del ganador de la ronda
				if(resultado != 3) {
					partida.setTurno(resultado);
				}
				partida.setRonda(partida.getRonda()+1);
			}
		}
		//se refresca la pantalla en ambos jugadores
		partida.setCambiosJugador1(true);
		partida.setCambiosJugador2(true);
	}

	@Override
	public void sumarPuntaje(Partida partida, Integer jugador) {
		if (jugador == 0) {
			jugador = partida.getMano();
		}
		if (jugador == 1) {
			partida.setPuntajeJugador1(partida.getPuntajeJugador1()+1+partida.getPuntosPorTruco());
		}else{
			partida.setPuntajeJugador2(partida.getPuntajeJugador2()+1+partida.getPuntosPorTruco());
		}
		
		if (partida.getGanadorTanto() == 1) {
			partida.setPuntajeJugador1(partida.getPuntajeJugador1()+partida.getPuntosPorTanto());
		}else if (partida.getGanadorTanto() == 2){
			partida.setPuntajeJugador2(partida.getPuntajeJugador2()+partida.getPuntosPorTanto());
		}
	}
	
	@Override
	public Integer concluirMano(Partida partida, Integer ganador) {
		if (ganador==0) {
			Integer total1=0;
			Integer total2=0;
			
			for (Integer i=0;i<=2;i++) {
				if(partida.getResultado(i) == 1 ) {
					total1+=1;
				}else if (partida.getResultado(i) == 2 ) {
					total2+=1;
				}
			}
			// si gana el jugador 1
			if (total1>total2){
				ganador=1;
			//si gana el jugador 2
			}else if (total1<total2){
				ganador=2;
			}
		}
		sumarPuntaje(partida,ganador);
		partida.setEstado(9);
		partida.setTurno(0);
		partida.setCambiosJugador1(true);
		partida.setCambiosJugador2(true);
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repartirCartas(partida);
		partida.setEstado(2);
		partida.setTurno(1);
		partida.setCambiosJugador1(true);
		partida.setCambiosJugador2(true);
		return null;
	}

	@Override
	public Partida getPartida(Integer partidaID) {
		return partidasEnCurso.get(partidaID);
	}

	//Crea una nueva partida, lo agrega en memoria y le asigna un valor para identificarlo
	@Override
	public Partida nuevaPartida() {
		Partida partida = new Partida();
		partida.setEstado(0);
		partidasEnCurso.add(partida);
		partida.setPartidaID(partidasEnCurso.indexOf(partida));
		//agregar la partida a la base de datos
		PartidaEnCurso partidaEnCurso = new PartidaEnCurso();
		partidaEnCurso.setEstado(0);
		partidaEnCurso.setMensaje("prueba");
		partidaEnCurso.setIdPartida(partida.getPartidaID());
		partidaEnCursoDao.nuevaPartida(partidaEnCurso);
		//opciones
		partida.setMano(1);
		partida.setPartidaEnCursoID(partidaEnCurso.getId());
		return partida;
	}
	
	// se une a la partida
	public Partida unirseAPartida(Integer partidaID,Long jugadorId) {
		Partida partida = getPartida(partidaID);
		Long partidaEnCursoID = partida.getPartidaEnCursoID();
		Usuario jugador = usuarioDao.buscarPorId(jugadorId);
		Integer estado = partida.getEstado();
		if (estado == 0) {
			partida.setEstado(1);
			if (jugador != null) {
				partida.setNombreJugador1(jugador.getNombre());
			}else {
				partida.setNombreJugador1("Invitado");
			}
			partidaEnCursoDao.unirJugador(partidaEnCursoID, 1, jugador);
			partidaEnCursoDao.cambiarEstado(partidaEnCursoID, 1);
		}else if(estado == 1) {
			partida.setEstado(2);
			if (jugador != null) {
				partida.setNombreJugador2(jugador.getNombre());
			}else {
				partida.setNombreJugador2("Invitado");
			}
			partidaEnCursoDao.unirJugador(partidaEnCursoID, 2, jugador);
			partidaEnCursoDao.cambiarEstado(partidaEnCursoID, 2);
			repartirCartas(partida);
		}
		
		return partida;
	}

	@Override
	public Integer compararValor(Integer carta1, Integer carta2) {
		Integer valor1=obtenerValor(carta1);
		Integer valor2=obtenerValor(carta2);
		if(valor1 > valor2) {
			return 1; //gana el jugador 1
		}else if (valor1 == valor2) {
			return 3; // parda
		}else {
			return 2; // gana el jugador 2
		}
	}
	
	@Override
	public List<PartidaEnCurso> obtenerPartidasEnCurso() {
		List<PartidaEnCurso> partidas = partidaEnCursoDao.traerTodasLasPartidasEnProgreso();
		return partidas;
	}

	@Override
	public void cantarTruco(Partida partida, Integer jugador) {
		if (partida.getJugadorTruco() != jugador && partida.getPuntosPorTruco() != 3 && (partida.getEstado() !=2 || partida.getEstado() !=3 )) {
			if (partida.getPuntosPorTruco() != 0 && partida.getGanadorTanto() == 0) {
				partida.setGanadorTanto(3);
			}
			partida.setEstado(3);
			partida.setPuntosPorTruco(partida.getPuntosPorTruco()+1);
			partida.setJugadorTruco(jugador);
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
			
		}
	}
	
	@Override
	public void cantarEnvido(Partida partida, Integer jugador, Integer comando) {
		boolean truco = (partida.getEstado() == 3 && jugador != partida.getJugadorTruco());
		Integer tipo=0;
		switch(comando) {
		case 2: tipo=1; break;
		case 3: tipo=2; break;
		case 5: tipo=3; break;
		}
		if (partida.getGanadorTanto() == 0) {
			if ((partida.getEstado() == 2 && jugador == partida.getTurno()) || (partida.getEstado() == 4 && jugador != partida.getJugadorTanto()) || truco ) {
				if (truco) {
					partida.setJugadorTruco(0);
					partida.setPuntosPorTruco(0);
				}
				partida.setJugadorTanto(jugador);
				partida.setTipoTanto(servicioTanto.calcularTipoTanto(partida.getTipoTanto(),tipo));
				partida.setEstado(4);
				partida.setCambiosJugador1(true);
				partida.setCambiosJugador2(true);
			}
		}
	}

	@Override
	public void quiero(Partida partida, Integer jugador) {
		if (partida.getEstado() == 3 && jugador != partida.getJugadorTruco()) {
			partida.setEstado(2);
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		}else if(partida.getEstado() == 4 && jugador != partida.getJugadorTanto()) {
			partida.setEstado(5);
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		}else if(partida.getEstado() == 5 && jugador == partida.getJugadorTanto()) {
			partida.setGanadorTanto(servicioTanto.compararTanto(partida));
			partida.setPuntosPorTanto(servicioTanto.calcularValorTanto(partida.getTipoTanto(), true));
			partida.setEstado(2);
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		}
	}
	
	public void noQuiero(Partida partida, Integer jugador) {
		if (partida.getEstado() == 3 && jugador != partida.getJugadorTruco()) {
			partida.setPuntosPorTruco(partida.getPuntosPorTruco()-1);
			if (jugador==1) {
				concluirMano(partida, 2);
			}else {
				concluirMano(partida ,1);
			}
		}else if(partida.getEstado() == 4 && jugador != partida.getJugadorTanto()) {
			partida.setGanadorTanto(3);
			if(jugador == 1) {
				partida.setPuntajeJugador1(partida.getPuntajeJugador1()+servicioTanto.calcularValorTanto(partida.getTipoTanto(), false));
			}else {
				partida.setPuntajeJugador2(partida.getPuntajeJugador2()+servicioTanto.calcularValorTanto(partida.getTipoTanto(), false));
			}
			partida.setEstado(2);
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		}else if(partida.getEstado() == 5 && jugador == partida.getJugadorTanto()) {
			partida.setGanadorTanto(getOponente(jugador));
			partida.setPuntosPorTanto(servicioTanto.calcularValorTanto(partida.getTipoTanto(), true));
			partida.setEstado(2);
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		}
	}

	@Override
	public Integer getOponente(Integer jugador) {
		if(jugador == 1) {
			return 2;
		}else {
			return 1;
		}
	}
	
	
}
