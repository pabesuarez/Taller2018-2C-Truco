package ar.edu.unlam.tallerweb1.servicios;

import java.util.Arrays;
import org.springframework.stereotype.Service;
import ar.edu.unlam.tallerweb1.modelo.Partida;

@Service
public class ServicioTantoImpl implements ServicioTanto {

	private int obtenerPaloDeLaCarta(int carta) {
		return carta/10;
	}

	private int obtenerTantoDeLaCarta(int carta) {
		Integer valor = carta%10;
	    if (valor <= 6){
	        return valor+1;
	    }else{
	        return 0;
	    }
	}
	
	public int obtenerTantoDeLaMano(int[] arg) {
		int caso = 0;
		int mano[] = Arrays.copyOf(arg, arg.length);
		
		if( obtenerPaloDeLaCarta(mano[0]) == obtenerPaloDeLaCarta(mano[1]) && obtenerPaloDeLaCarta(mano[0]) == obtenerPaloDeLaCarta(mano[2])) {caso = 1;} else {
		if( obtenerPaloDeLaCarta(mano[0]) == obtenerPaloDeLaCarta(mano[1])) {caso = 3;} else {
		if( obtenerPaloDeLaCarta(mano[1]) == obtenerPaloDeLaCarta(mano[2])) {caso = 2;}}}
		
		mano[0] = obtenerTantoDeLaCarta(mano[0]);
		mano[1] = obtenerTantoDeLaCarta(mano[1]);
		mano[2] = obtenerTantoDeLaCarta(mano[2]);
		
		switch (caso) {
			case 1:
				Arrays.sort(mano);
			case 2:
				return 20 + mano[1] + mano[2];
			case 3:
				return 20 + mano[0] + mano[1];
			default:
				Arrays.sort(mano);
				return mano[2];
		}
	}
		
	@Override 
	public Integer compararTanto(Partida partida) {
		int[] mano1 = partida.getManoJugador1();
		int[] mano2 = partida.getManoJugador2();
		
		if(obtenerTantoDeLaMano(mano1) > obtenerTantoDeLaMano(mano2)) return new Integer(1);
		if(obtenerTantoDeLaMano(mano1) < obtenerTantoDeLaMano(mano2)) return new Integer(2);
		if(obtenerTantoDeLaMano(mano1) == obtenerTantoDeLaMano(mano2)) return partida.getMano();
		
		return 0;

	}
	
	
	@Override
	public void cantarTanto(Partida partida, Integer jugador, Integer comando) {
		/*
		Cod Tipo | Tanto Si | Tanto No
		2 E		2	1
		3 R		3	1
		5 F		*	1
		
		06 E/E		4	2
		07 E/R		5	2
		08 E/F		*	2
		09 R/F		*	3
		
		10 E/E/R	7	4
		11 E/E/F	*	4
		12 E/R/F	*	5
		13 E/E/R/F	*	7
		
		* 2 < 15 Ganador gana el partido. 
		* 1 >= 15 Ganador los puntos que le falte al que va primero para ganar.
		*/
		
		if(jugador >= partida.getJugadorTanto()) {
			if(partida.getTanto() == 0) {
				partida.setEstado(4);
				partida.setTanto(comando);
				return;
			} else {
				if(comando == 2 && partida.getTanto() ==  2) { partida.setTanto(6); partida.setJugadorTanto(jugador); return;}
				if(comando == 3 && partida.getTanto() ==  2) { partida.setTanto(7); partida.setJugadorTanto(jugador); return;}
				if(comando == 5 && partida.getTanto() ==  2) { partida.setTanto(8); partida.setJugadorTanto(jugador); return;}
				if(comando == 5 && partida.getTanto() ==  3) { partida.setTanto(9); partida.setJugadorTanto(jugador); return;}
				
				if(comando == 3 && partida.getTanto() ==  6) { partida.setTanto(10); partida.setJugadorTanto(jugador); return;}
				if(comando == 5 && partida.getTanto() ==  6) { partida.setTanto(11); partida.setJugadorTanto(jugador); return;}
				if(comando == 5 && partida.getTanto() ==  7) { partida.setTanto(12); partida.setJugadorTanto(jugador); return;}
				if(comando == 5 && partida.getTanto() == 10) { partida.setTanto(13); partida.setJugadorTanto(jugador); return;}
				
				if(partida.getTanto() == 2) {partida.setPuntosPorTanto(comando == 10 ? 2 : 1);} else {
				if(partida.getTanto() == 3) {partida.setPuntosPorTanto(comando == 10 ? 3 : 1);} else {
				if(partida.getTanto() == 5) {partida.setPuntosPorTanto(comando == 10 ? 30 : 1);} else {
				if(partida.getTanto() == 6) {partida.setPuntosPorTanto(comando == 10 ? 4 : 2);} else {
				if(partida.getTanto() == 7) {partida.setPuntosPorTanto(comando == 10 ? 5 : 2);} else {
				if(partida.getTanto() == 8) {partida.setPuntosPorTanto(comando == 10 ? 30 : 2);} else {
				if(partida.getTanto() == 9) {partida.setPuntosPorTanto(comando == 10 ? 30 : 3);} else {
				if(partida.getTanto() == 10) {partida.setPuntosPorTanto(comando == 10 ? 7 : 4);} else {
				if(partida.getTanto() == 11) {partida.setPuntosPorTanto(comando == 10 ? 30 : 4);} else {
				if(partida.getTanto() == 12) {partida.setPuntosPorTanto(comando == 10 ? 30 : 5);} else {
				if(partida.getTanto() == 6) {partida.setPuntosPorTanto(comando == 10 ? 30 : 7);}}}}}}}}}}}
				
				partida.setEstado(2);
				partida.setCambiosJugador1(true);
				partida.setCambiosJugador2(true);
			}
		}
		
		return;
	}

}
