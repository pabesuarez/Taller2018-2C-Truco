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
	
	
		/*
		Cod Tipo | Tanto Si | Tanto No
		1 E			2			1
		2 R			3			1
		3 F			*			1
		
		04 E/E		4			2
		05 E/R		5			2
		06 E/F		*			2
		07 R/F		*			3
		
		08 E/E/R	7			4
		09 E/E/F	*			4
		10 E/R/F	*			5
		11 E/E/R/F	*			7

		*/


	@Override
	public Integer calcularTipoTanto(Integer tipoActual, Integer tipo) {
		switch(tipoActual) {
		case 0: return tipo;
		case 1:
			switch(tipo) {
			case 1: return 4;
			case 2: return 5;
			case 3: return 6;
			}
		case 2:
			if (tipo == 3) return 7;
			break;
		case 4:
			if (tipo == 2) { 
				return 8;
			}else if (tipo == 3) {
				return 9;
			}
			break;
		case 5:
			if (tipo == 3) return 10;
			break;
		case 8:
			if (tipo == 3) return 11;
			break;
		}
		return tipoActual;
	}

	@Override
	public Integer calcularValorTanto(Integer tipo, boolean respuesta) {
		if (respuesta) {
			switch(tipo) {
			case 1: return 2;
			case 2: return 3;
			case 4: return 4;
			case 5: return 5;
			case 8: return 7;
			case 3: case 6: case 7: case 9: case 10: case 11: 
				return 30;
			}
			return 0;
		}else {
			if(tipo <= 3) {
				return 1;
			}else if (tipo <=7) {
				return 2;
			}else {
				return 3;
			}
		}
	}

}
