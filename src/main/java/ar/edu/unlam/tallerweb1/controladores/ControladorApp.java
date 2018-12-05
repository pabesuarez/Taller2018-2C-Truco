package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Configuracion;
import ar.edu.unlam.tallerweb1.modelo.Mensaje;
import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.modelo.PartidaEnCurso;
import ar.edu.unlam.tallerweb1.servicios.ServicioPartida;

@Controller
public class ControladorApp {
	
	@Inject
	private ServicioPartida servicioPartida;
	
	
	
	public ServicioPartida getServicioPartida() {
		return servicioPartida;
	}

	public void setServicioPartida(ServicioPartida servicioPartida) {
		this.servicioPartida = servicioPartida;
	}

	// mientras no sea el turno del jugador, este constantemente estara llamando a esta funcion, en caso de haber un nuevo cambio, se le notificara al jugador
	@ResponseBody
	@RequestMapping(path = "/app/actualizar", method = RequestMethod.POST, produces = "application/json")
    public Partida actualizar (@RequestBody Mensaje mensaje) {
		Partida partida = servicioPartida.getPartida(mensaje.getPartidaID());
		Integer jugador = mensaje.getJugador();
		
        if (jugador==1 && partida.isCambiosJugador1()) {
        	partida.setCambiosJugador1(false);
        	return partida;
        }
        if (jugador==2 && partida.isCambiosJugador2()) {
        	partida.setCambiosJugador2(false);
        	return partida;
        }else {
        	return null;
        }
	}
	
	// esta funcion es llamada al realizar una accion mientras es el turno del cliente
	@ResponseBody
	@RequestMapping(path = "/app/comando", method = RequestMethod.POST, produces = "application/json")
    public Mensaje comando (@RequestBody Mensaje mensaje) {
		
		Partida partida = servicioPartida.getPartida(mensaje.getPartidaID());
		Integer jugador = mensaje.getJugador();
		Mensaje respuesta = new Mensaje();
		
		/*
			segun el numero enviado como comando por el cliente se realizaran distintas acciones
			1: tirar carta (parametro: que carta tiro)
			2: cantar envido
			3: cantar real envido
			4: cantar truco/retruco/vale4
			5: cantar falta envido
			6: mazo
			10: respuesta positiva (quiero / cantar envido)
			11: respuesta negativa (no quiero / son buenas)
		*/
		switch(mensaje.getComando()) {
		case 1:
			servicioPartida.tirarCarta(partida, jugador, mensaje.getParametro()); break;
		case 2: case 3:	case 5:
			servicioPartida.cantarEnvido(partida, jugador, mensaje.getComando()); break;
		case 4:
			servicioPartida.cantarTruco(partida, jugador); break;
		case 10:
			servicioPartida.quiero(partida,jugador); break;
		case 11:
			servicioPartida.noQuiero(partida,jugador); break;
		case 6:
			servicioPartida.mazo(partida, jugador); break;
		}
		return respuesta;
	}
	
	// crea una nueva partida y se une
	@RequestMapping(path="/nuevaPartida", method=RequestMethod.POST)
	public String nuevaPartida(@ModelAttribute("configuracion") Configuracion configuracion) {
		Partida partida = servicioPartida.nuevaPartida(configuracion);
		//opciones
		ModelMap modelo = new ModelMap();
		modelo.put("partida", partida);
		return "redirect:/jugar/"+partida.getPartidaID();
	}
	
	//unirse a una partida ya existente
	@RequestMapping("/jugar/{partidaID}")
	public ModelAndView jugar(@PathVariable Integer partidaID, HttpServletRequest request) {
		Partida partida = servicioPartida.unirseAPartida(partidaID,(Long)request.getSession().getAttribute("usuarioId"));
		ModelMap modelo = new ModelMap();
		modelo.put("partida", partida);
		return new ModelAndView("juego",modelo);
	}
	
	// lobby
	@RequestMapping("/lobby")
	public ModelAndView lobby() {
		List<PartidaEnCurso> partidas = servicioPartida.obtenerPartidasEnCurso();
		ModelMap modelo = new ModelMap();
		modelo.put("partidas", partidas);
		modelo.put("configuracion", new Configuracion());
		return new ModelAndView("lobby", modelo);
	}
	/*
	// en caso de que un jugador abandone la partida (cierre la ventana o esté ausente durante 1 minuto)	
	@ResponseBody
	@RequestMapping(path = "/abandonoDePartida", method = RequestMethod.POST, produces = "application/json")
    public void abandonoDePartida (@RequestBody Mensaje mensaje) {
		//obtengo la partida
		Partida partida = servicioPartida.getPartida(mensaje.getPartidaID());
		//Averiguo quién es el ganador por default
		Integer puntajeParaGanar = partida.getPuntajeParaGanar();
		Integer ganador = 0;
		if(mensaje.getJugador() == 1) {
			ganador = 2;
			partida.setPuntajeJugador2(puntajeParaGanar);
		}else {
			ganador = 1;
			partida.setPuntajeJugador1(puntajeParaGanar);
		}
		servicioPartida.concluirMano(partida, ganador);
	}*/
}
