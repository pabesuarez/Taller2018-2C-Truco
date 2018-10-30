package ar.edu.unlam.tallerweb1.servicios;

import org.springframework.stereotype.Service;

@Service("servicioTP")
public class ServicioTPImpl implements ServicioTP {

	@Override
	public String invertirCadena(String cadena) {
		StringBuilder sb = new StringBuilder(cadena);
		return sb.reverse().toString();
	}
	
}
