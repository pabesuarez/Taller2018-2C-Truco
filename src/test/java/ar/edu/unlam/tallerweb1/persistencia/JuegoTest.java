package ar.edu.unlam.tallerweb1.persistencia;

import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import ar.edu.unlam.tallerweb1.servicios.ServicioPartida;
import ar.edu.unlam.tallerweb1.servicios.ServicioPartidaImpl;

public class JuegoTest {

	@Test
	public void anchoDeEspadaLeGanaAAnchoDeBastoTest() {
		ServicioPartida servicioPartida = new ServicioPartidaImpl();
		
		Integer resultado = servicioPartida.compararValor(0, 10);
		
		assertThat(resultado).isEqualTo(1);
		
	}
	
}
