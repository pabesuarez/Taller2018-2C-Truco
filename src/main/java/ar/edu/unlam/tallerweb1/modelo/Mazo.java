package ar.edu.unlam.tallerweb1.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Mazo {
	private ArrayList<Integer> mazo = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39));
	
	public void mezclar() {
		Collections.shuffle(this.mazo); //mezclar el mazo
	}

	public ArrayList<Integer> getMazo() {
		return mazo;
	}

	public void setMazo(ArrayList<Integer> mazo) {
		this.mazo = mazo;
	}
	
	public Integer getCarta(Integer posicion) {
		return mazo.get(posicion);
	}
	
	
}
