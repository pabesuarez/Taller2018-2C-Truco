package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Mazo;

public interface ServicioCartas {
	public Mazo getMazo();
	public Integer obtenerValor(Integer carta);
	public Integer compararValor(Integer carta1,Integer carta2);
}
