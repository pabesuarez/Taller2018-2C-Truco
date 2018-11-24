package ar.edu.unlam.tallerweb1.persistencia;

import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import ar.edu.unlam.tallerweb1.controladores.ControladorApp;
import ar.edu.unlam.tallerweb1.dao.PartidaEnCursoDao;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Mazo;
import ar.edu.unlam.tallerweb1.modelo.Mensaje;
import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.servicios.ServicioCartasImpl;
import ar.edu.unlam.tallerweb1.servicios.ServicioPartidaImpl;
import ar.edu.unlam.tallerweb1.servicios.ServicioTanto;
import ar.edu.unlam.tallerweb1.servicios.ServicioTantoImpl;


public class AppTest {

	@Test
	public void EnvidoAlInicioTest() {
		//mock de servlet y sesion para llamar a metodos del controlador que lo requieren
		HttpServletRequest servlet = mock(HttpServletRequest.class);
		HttpSession sesion = mock(HttpSession.class);
		when(servlet.getSession()).thenReturn(sesion);
		//mock del mazo para decidir el valor de las cartas
		Mazo mazo = mock(Mazo.class);
		//cartas jugador 1
		when(mazo.getCarta(0)).thenReturn(0);
		when(mazo.getCarta(2)).thenReturn(4);
		when(mazo.getCarta(4)).thenReturn(20);
		//cartas jugador 2
		when(mazo.getCarta(1)).thenReturn(12);
		when(mazo.getCarta(3)).thenReturn(14);
		when(mazo.getCarta(5)).thenReturn(25);
		ServicioCartasImpl servicioCartas = new ServicioCartasImpl();
		//se reemplaza el mazo real con nuestro mock
		servicioCartas.setMazo(mazo);
		ServicioTanto servicioTanto = new ServicioTantoImpl();
		ServicioPartidaImpl servicioPartida = new ServicioPartidaImpl();
		servicioPartida.setServicioTanto(servicioTanto);
		servicioPartida.setServicioCartas(servicioCartas);
		//se mockean los daos debido a que no son parte del test
		servicioPartida.setPartidaEnCursoDao(mock(PartidaEnCursoDao.class));
		servicioPartida.setUsuarioDao(mock(UsuarioDao.class));
		ControladorApp controlador = new ControladorApp();
		//crea una partida y une dos jugadores
		controlador.setServicioPartida(servicioPartida);
		controlador.nuevaPartida();
		Partida partida = servicioPartida.getPartida(0);
		controlador.jugar(partida.getPartidaID(), servlet);
		controlador.jugar(partida.getPartidaID(), servlet);
		//los objetos mensaje simulan las interacciones de los jugadores con la aplicacion
		Mensaje jugador1 = new Mensaje();
		jugador1.setJugador(1);
		jugador1.setPartidaID(partida.getPartidaID());
		Mensaje jugador2 = new Mensaje();
		jugador2.setJugador(2);
		jugador2.setPartidaID(partida.getPartidaID());
		
		
		//ejecucion
		
		jugador1.setComando(2); // envido
		controlador.comando(jugador1);
		jugador2.setComando(2); // envido
		controlador.comando(jugador2);
		jugador1.setComando(10); // quiero, 25
		controlador.comando(jugador1);
		jugador2.setComando(10); // 27 son mejores
		controlador.comando(jugador2);
		
		//verificar que el ganador del envido sea el jugador 2 y que el valor del puntaje sea 4 (envido envido)
		assertThat(partida.getGanadorTanto()).isEqualTo(2);
		assertThat(partida.getPuntosPorTanto()).isEqualTo(4);
		
		assertThat(partida.getManoJugador1()).isEqualTo(new int[] {0,4,20});
		assertThat(partida.getManoJugador2()).isEqualTo(new int[] {12,14,25});
		
	}
	
}
