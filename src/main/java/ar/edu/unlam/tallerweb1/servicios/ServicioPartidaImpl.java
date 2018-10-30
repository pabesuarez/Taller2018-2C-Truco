package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

import ar.edu.unlam.tallerweb1.modelo.Partida;

@Service("servicioPartida")
public class ServicioPartidaImpl implements ServicioPartida{

	@Override
	public void repartirCartas(Partida partida) {
		Collections.shuffle(mazo); //mezclar el mazo
		//repartir cartas
		partida.setManoJugador1(new int[]{mazo.get(0),mazo.get(2),mazo.get(4)});
		partida.setManoJugador2(new int[]{mazo.get(1),mazo.get(3),mazo.get(5)});
		partida.setCartasEnJuego1(new boolean[] {false,false,false});
		partida.setCartasEnJuego2(new boolean[] {false,false,false});
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
	public int[] tirarCarta(Partida partida, Integer jugador, Integer carta) {
		// PENDIENTE: aplicar sobre partida los cambios que provoque tirar la carta en la posicion del parametro carta
		return null;
	}

	@Override
	public Integer concluirMano(Partida partida) {
		// PENDIENTE: Aplicar el puntaje acumulado y resetear partida para la siguiente mano
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
		//opciones
		partida.setMano(1);
		
		
		partidasEnCurso.add(partida);
		partida.setPartidaID(partidasEnCurso.indexOf(partida));
		return partida;
	}
	
	// se une a la partida
	public Partida unirseAPartida(Integer partidaID) {
		Partida partida = getPartida(partidaID);
		Integer estado = partida.getEstado();
		if (estado == 0) {
			partida.setEstado(1);
		}else if(estado == 1) {
			partida.setEstado(2);
			repartirCartas(partida);
		}
		return partida;
	}
	
}
