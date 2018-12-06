package ar.edu.unlam.tallerweb1.controladores;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.modelo.PartidaEnCurso;
import ar.edu.unlam.tallerweb1.servicios.ServicioPartida;


public class ControladorAppTest {

	@Test
	public void LobbyQueDeberiaTraerUnaListaDePartidas() {
		ServicioPartida servicioPartida = mock(ServicioPartida.class);
		List<PartidaEnCurso> partidas = new ArrayList<PartidaEnCurso>();
		when(servicioPartida.obtenerPartidasEnCurso()).thenReturn(partidas);
		ControladorApp sut = new ControladorApp();
		sut.setServicioPartida(servicioPartida);
		ModelAndView modelo = sut.lobby();
		assertThat(modelo.getModel().get("partidas")).isEqualTo(partidas);
	}
	
	@Test
	public void MetodoQueDeberiaUnirAPartidaExistente() {
		ServicioPartida servicioPartida = mock(ServicioPartida.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		Partida partida = new Partida();
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("usuarioId")).thenReturn(1L);
		when(servicioPartida.unirseAPartida(1, 1L)).thenReturn(partida);
		ControladorApp sut = new ControladorApp();
		sut.setServicioPartida(servicioPartida);
		ModelAndView modelo = sut.jugar(1, request);
		assertThat(modelo.getModel().get("partida")).isEqualTo(partida);
	}

}
