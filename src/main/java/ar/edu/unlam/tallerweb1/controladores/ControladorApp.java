package ar.edu.unlam.tallerweb1.controladores;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Mensaje;
import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioPartida;

@Controller
public class ControladorApp {
	
	@Inject
	private ServicioPartida servicioPartida;
	
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
			3: falta envido
			4: cantar truco/retruco/vale4
			10: respuesta positiva (quiero / cantar envido)
			11: respuesta negativa (no quiero / son buenas)
		*/
		switch(mensaje.getComando()) {
		case 1:
			servicioPartida.tirarCarta(partida, jugador, mensaje.getParametro());
		}
		return respuesta;
	}
	
	// crea una nueva partida y se une
	@RequestMapping("/nuevaPartida")
	public String nuevaPartida() {
		Partida partida = servicioPartida.nuevaPartida();
		//opciones
		ModelMap modelo = new ModelMap();
		modelo.put("partida", partida);
		return "redirect:/jugar/"+partida.getPartidaID();
	}

	@RequestMapping("/jugar/{partidaID}")
	public ModelAndView jugar(@PathVariable Integer partidaID) {
		Partida partida = servicioPartida.unirseAPartida(partidaID);
		ModelMap modelo = new ModelMap();
		modelo.put("partida", partida);
		return new ModelAndView("juego",modelo);
	}
}
