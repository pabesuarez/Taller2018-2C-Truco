package ar.edu.unlam.tallerweb1.persistencia;

import org.junit.Ignore;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.servicios.ServicioPartida;
import ar.edu.unlam.tallerweb1.servicios.ServicioPartidaImpl;
import ar.edu.unlam.tallerweb1.servicios.ServicioTanto;
import ar.edu.unlam.tallerweb1.servicios.ServicioTantoImpl;

public class JuegoTest {

	@Test
	public void anchoDeEspadaLeGanaAAnchoDeBastoTest() {
		ServicioPartida servicioPartida = new ServicioPartidaImpl();
		
		Integer resultado = servicioPartida.compararValor(0, 10);
		
		assertThat(resultado).isEqualTo(1);
		
	}
	
	@Test
	public void calcularEnvidoTest() {
		ServicioTanto servicioTanto = new ServicioTantoImpl();
		
		Integer tanto1 = servicioTanto.obtenerTantoDeLaMano(new int[] {2,6,14});
		Integer tanto2 = servicioTanto.obtenerTantoDeLaMano(new int[] {5,18,19});
		Integer tanto3 = servicioTanto.obtenerTantoDeLaMano(new int[] {29,24,6});
		Integer tanto4 = servicioTanto.obtenerTantoDeLaMano(new int[] {0,10,20});
		Integer tanto5 = servicioTanto.obtenerTantoDeLaMano(new int[] {6,29,2});
		
		
		assertThat(tanto1).isEqualTo(30);
		assertThat(tanto2).isEqualTo(20);
		assertThat(tanto3).isEqualTo(25);
		assertThat(tanto4).isEqualTo(1);
		assertThat(tanto5).isEqualTo(30);
		
	}
	
	@Test
	public void comparacionEnvidoTest() {
		ServicioTanto servicioTanto = new ServicioTantoImpl();
		Partida partida = new Partida();
		partida.setManoJugador1(new int[] {2,6,14});
		partida.setManoJugador2(new int[] {5,18,19});

		Integer ganador = servicioTanto.compararTanto(partida);
		
		assertThat(ganador).isEqualTo(new Integer(1));		
	}
	
	
}
