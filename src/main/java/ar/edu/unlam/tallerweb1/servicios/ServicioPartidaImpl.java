package ar.edu.unlam.tallerweb1.servicios;


import java.util.List;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.dao.PartidaEnCursoDao;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Configuracion;
import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.modelo.PartidaEnCurso;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioPartida")
@Transactional
public class ServicioPartidaImpl implements ServicioPartida{

	@Inject
	private ServicioTanto servicioTanto;
	
	public void setServicioTanto(ServicioTanto servicioTanto) {
		this.servicioTanto=servicioTanto;
	}
	
	@Inject
	private ServicioCartas servicioCartas;
	
	@Inject
	private PartidaEnCursoDao partidaEnCursoDao;
	
	public void setPartidaEnCursoDao(PartidaEnCursoDao partidaEnCursoDao) {
		this.partidaEnCursoDao=partidaEnCursoDao;
	}
	
	@Inject
	private UsuarioDao usuarioDao;
	
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao=usuarioDao;
	}
	
	public ServicioCartas getServicioCartas() {
		return servicioCartas;
	}

	public void setServicioCartas(ServicioCartas servicioCartas) {
		this.servicioCartas = servicioCartas;
	}

	@Override
	public void repartirCartas(Partida partida) {
		servicioCartas.getMazo().mezclar();
		//repartir cartas
		partida.setManoJugador1(new int[]{servicioCartas.getMazo().getCarta(0),servicioCartas.getMazo().getCarta(2),servicioCartas.getMazo().getCarta(4)});
		partida.setManoJugador2(new int[]{servicioCartas.getMazo().getCarta(1),servicioCartas.getMazo().getCarta(3),servicioCartas.getMazo().getCarta(5)});
		//reiniciar la mesa
		partida.setCartasEnJuego1(new int[] {3,3,3});
		partida.setCartasEnJuego2(new int[] {3,3,3});
		//reiniciar los resultados
		partida.setResultado(new int[] {0,0,0});
		//reiniciar la ronda
		partida.setRonda(0);
		//reiniciar estado de envido
		partida.setGanadorTanto(0);
		partida.setJugadorTanto(0);
		partida.setPuntosPorTanto(0);
		partida.setTipoTanto(0);
		//reiniciar estado de truco
		partida.setPuntosPorTruco(0);
		partida.setJugadorTruco(0);
		//reiniciar mensajes
		partida.setMensajeJugador1("");
		partida.setMensajeJugador2("");
		//refrescar la pantalla de ambos jugadores
		partida.setCambiosJugador1(true);
		partida.setCambiosJugador2(true);
	}



	@Override
	public void tirarCarta(Partida partida, Integer jugador, Integer carta) {
		// si un jugador tira una carta en su turno se aplica el cambio y cambia de turno
		if(jugador==1 && partida.getTurno() == 1 && partida.getEstado() == 2 && !IntStream.of(partida.getCartasEnJuego1()).anyMatch(x -> x == carta)) {
			partida.setCartaJuego1(partida.getRonda(),carta);
			partida.setTurno(2);
		}else if (jugador==2 && partida.getTurno() == 2 && partida.getEstado() == 2 && !IntStream.of(partida.getCartasEnJuego2()).anyMatch(x -> x == carta)) {
			partida.setCartaJuego2(partida.getRonda(), carta);
			partida.setTurno(1);
		}else {
			return;
		}
		//revisar que cartas hay en mesa
		Integer carta1 = partida.getCartaJuego1(partida.getRonda());
		Integer carta2 = partida.getCartaJuego2(partida.getRonda());
		//si ambos jugadores han tirado carta se comprueban las cartas
		if (carta1 !=3  && carta2 !=3) {
			Integer resultado = servicioCartas.compararValor(partida.getCartaMano1(carta1),partida.getCartaMano2(carta2));
			partida.setResultado(partida.getRonda(),resultado);
			//si era la ultima carta de la ronda se concluye la mano
			if(partida.getRonda() == 2) {
				concluirMano(partida,0);
				return;
			} else {
				//verificacion en la segunda ronda
				if(partida.getRonda() == 1) {
					//si la ronda anterior fue parda y la actual tuvo ganador se termina la ronda
					if(partida.getResultado(partida.getRonda()-1) == 3 && resultado != 3) {
						concluirMano(partida,0);
						return;
					}
					//si la ronda anterior no fue parda y la actual si lo fue se termina la ronda
					if(partida.getResultado(partida.getRonda()-1) != 3 && resultado == 3) {
						concluirMano(partida,0);
						return;
					}
				}
				//si era la segunda ronda y el ganador fue el mismo jugador se termina la ronda
				if (partida.getRonda() == 1 && partida.getResultado(0) == partida.getResultado(1)) {
					concluirMano(partida,0);
					return;
				}
				
				//en caso de no ser parda pasa a ser turno del ganador de la ronda
				if(resultado != 3) {
					partida.setTurno(resultado);
				}
				if(partida.getGanadorTanto() == 0) {
					partida.setGanadorTanto(3);
				}
				partida.setRonda(partida.getRonda()+1);
			}
		}
		//se refresca la pantalla en ambos jugadores
		partida.setCambiosJugador1(true);
		partida.setCambiosJugador2(true);
	}

	@Override
	public void sumarPuntaje(Partida partida, Integer jugador) {
		//jugador al ganador de la mano, si no se especifica gana quien sea mano
		if (jugador == 0) {
			jugador = partida.getMano();
		}
		
		//se suman los puntos por truco +1 por ganar
		if (jugador == 1) {
			partida.setPuntajeJugador1(partida.getPuntajeJugador1()+1+partida.getPuntosPorTruco());
		}else{
			partida.setPuntajeJugador2(partida.getPuntajeJugador2()+1+partida.getPuntosPorTruco());
		}
		
		//se suma los puntos por envido
		if (partida.getGanadorTanto() == 1) {
			partida.setPuntajeJugador1(partida.getPuntajeJugador1()+partida.getPuntosPorTanto());
		}else if (partida.getGanadorTanto() == 2){
			partida.setPuntajeJugador2(partida.getPuntajeJugador2()+partida.getPuntosPorTanto());
		}
		
		//si se supera el puntaje requerido se iguala al maximo
		if (partida.getPuntajeJugador1() >partida.getPuntajeParaGanar()) {
			partida.setPuntajeJugador1(partida.getPuntajeParaGanar());
		}
		
		if (partida.getPuntajeJugador2() >partida.getPuntajeParaGanar()) {
			partida.setPuntajeJugador2(partida.getPuntajeParaGanar());
		}
	}
	
	@Override
	public Integer concluirMano(Partida partida, Integer ganador) {
		if (ganador==0) {
			Integer total1=0;
			Integer total2=0;
			
			//revisa quien cuantas rondas gano cada jugador
			for (Integer i=0;i<=2;i++) {
				if(partida.getResultado(i) == 1 ) {
					total1+=1;
				}else if (partida.getResultado(i) == 2 ) {
					total2+=1;
				}
			}
			// si gana el jugador 1
			if (total1>total2){
				ganador=1;
			//si gana el jugador 2
			}else if (total1<total2){
				ganador=2;
			// si se empata gana el jugador mano
			}else {
				ganador=partida.getMano();
			}
		}
		//aplica el puntaje
		sumarPuntaje(partida,ganador);
		
		//verifica si algun jugador alcanzo el puntaje para ganar
		Integer terminado = 0;
		if(partida.getPuntajeJugador1() == partida.getPuntajeParaGanar()) {
			terminado=1;
		}else if(partida.getPuntajeJugador2() == partida.getPuntajeParaGanar()) {
			terminado=2;
		}
		
		//deja al cliente sin poder hacer nada
		partida.setEstado(9);
		partida.setTurno(0);
		partida.setCambiosJugador1(true);
		partida.setCambiosJugador2(true);
		//esperar 3 segundos antes de continuar
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// si la partida termino eliminarla de memoria y base de datos
		if (terminado !=0) {
			//PENDIENTE: aplicar estadisticas
			partidasEnCurso.remove(partida);
			partidaEnCursoDao.removerPartida(partida.getPartidaEnCursoID());
		}else {
		// si no termino, reinicia la ronda
			repartirCartas(partida);
			partida.setEstado(2);
			partida.setMano(getOponente(partida.getMano())); //alterna quien es mano
			partida.setTurno(partida.getMano()); 
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		}
		return null;
	}

	@Override
	public Partida getPartida(Integer partidaID) {
		return partidasEnCurso.get(partidaID);
	}

	//Crea una nueva partida, lo agrega en memoria y le asigna un valor para identificarlo
	@Override
	public Partida nuevaPartida(Configuracion configuracion) {
		Partida partida = new Partida();
		partida.setEstado(0);
		partidasEnCurso.add(partida);
		partida.setPartidaID(partidasEnCurso.indexOf(partida));
		//agregar la partida a la base de datos
		PartidaEnCurso partidaEnCurso = new PartidaEnCurso();
		partidaEnCurso.setEstado(0);
		partidaEnCurso.setMensaje("prueba");
		partidaEnCurso.setIdPartida(partida.getPartidaID());
		partidaEnCursoDao.nuevaPartida(partidaEnCurso);
		//opciones
		partida.setFlor(configuracion.isFlor());
		partida.setMano(configuracion.getMano());
		partida.setPuntajeParaGanar(configuracion.getPuntos());
		partida.setPartidaEnCursoID(partidaEnCurso.getId());
		return partida;
	}
	
	// se une a la partida
	public Partida unirseAPartida(Integer partidaID,Long jugadorId) {
		Partida partida = getPartida(partidaID);
		Long partidaEnCursoID = partida.getPartidaEnCursoID();
		Usuario jugador = usuarioDao.buscarPorId(jugadorId);
		Integer estado = partida.getEstado();
		//si la partida esta recien creada se lo asigna como jugador 1 y cambia el estado a esperando jugador
		if (estado == 0) {
			partida.setEstado(1);
			//si el jugador esta logeado se cambia el nombre al nombre de usuario logeado, si no se lo ingresa como invitado
			if (jugador != null) {
				partida.setNombreJugador1(jugador.getNombre());
			}else {
				partida.setNombreJugador1("Invitado");
			}
			// aplica los cambios en la base de datos
			partidaEnCursoDao.unirJugador(partidaEnCursoID, 1, jugador);
			partidaEnCursoDao.cambiarEstado(partidaEnCursoID, 1);
		}else if(estado == 1) {
			// si a la partida le faltaba un jugador se lo asigna como jugador 2 y empieza la partida
			partida.setEstado(2);
			if (jugador != null) {
				partida.setNombreJugador2(jugador.getNombre());
			}else {
				partida.setNombreJugador2("Invitado");
			}
			partidaEnCursoDao.unirJugador(partidaEnCursoID, 2, jugador);
			partidaEnCursoDao.cambiarEstado(partidaEnCursoID, 2);
			repartirCartas(partida);
		}
		
		return partida;
	}


	
	// devuelve las partidas que esten en la base de datos
	@Override
	public List<PartidaEnCurso> obtenerPartidasEnCurso() {
		List<PartidaEnCurso> partidas = partidaEnCursoDao.traerTodasLasPartidasEnProgreso();
		return partidas;
	}
	
	@Override
	public void cantarTruco(Partida partida, Integer jugador) {
		//verifica si el jugador puede cantar truco
		if (partida.getJugadorTruco() != jugador && partida.getPuntosPorTruco() != 3 && (partida.getEstado() !=2 || partida.getEstado() !=3 )) {
			//si no es la primera vez que se canta truco y no se canto envido, se anula la posiblidad de cantar envido
			if (partida.getPuntosPorTruco() != 0 && partida.getGanadorTanto() == 0) {
				partida.setGanadorTanto(3);
			}
			//pone la partida en estado de espera de respuesta del otro jugador
			partida.setEstado(3);
			partida.setPuntosPorTruco(partida.getPuntosPorTruco()+1);
			partida.setJugadorTruco(jugador);
			
			//actualiza el mensaje del jugador
			if (jugador == 1) {
				partida.setMensajeJugador1(mensajeTruco(partida.getPuntosPorTruco()));
			}else {
				partida.setMensajeJugador2(mensajeTruco(partida.getPuntosPorTruco()));
			}
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		}
	}
	
	@Override
	public String mensajeTruco(Integer truco) {
		switch(truco) {
		case 1: return "Truco";
		case 2: return "Quiero ReTruco";
		case 3: return "Quiero Vale 4";
		}
		return null;
	}
	
	@Override
	public void cantarEnvido(Partida partida, Integer jugador, Integer comando) {
		boolean truco = (partida.getEstado() == 3 && jugador != partida.getJugadorTruco()); //si se canto truco
		Integer tipo=0;
		String mensaje="";
		//cambia el mensaje segun que se canto
		switch(comando) {
		case 2: tipo=1; mensaje="Envido"; break;
		case 3: tipo=2; mensaje="Real Envido"; break;
		case 5: tipo=3; mensaje="Falta Envido"; break;
		}
		// si aun no se canto tanto o no se anulo
		if (partida.getGanadorTanto() == 0) {
			if ((partida.getEstado() == 2 && jugador == partida.getTurno()) || (partida.getEstado() == 4 && jugador != partida.getJugadorTanto()) || truco ) {
				//si es una respuesta a un truco se reinicia el estado del truco
				if (truco) {
					partida.setJugadorTruco(0);
					partida.setPuntosPorTruco(0);
				}
				//guarda quien es el ultimo que canto tanto
				partida.setJugadorTanto(jugador);
				//actualiza el tipo del envido(ver servicioTanto)
				partida.setTipoTanto(servicioTanto.calcularTipoTanto(partida.getTipoTanto(),tipo));
				//pone la partida en estado de espera de respuesta de envido
				partida.setEstado(4);
				
				//actualiza el mensaje
				if (jugador == 1) {
					partida.setMensajeJugador1(mensaje);
				}else {
					partida.setMensajeJugador2(mensaje);
				}
				
				partida.setCambiosJugador1(true);
				partida.setCambiosJugador2(true);
			}
		}
	}

	//segun el estado aplica los cambios ante una respuesta positiva
	@Override
	public void quiero(Partida partida, Integer jugador) {
		// en caso de estar respondiendo a truco
		if (partida.getEstado() == 3 && jugador != partida.getJugadorTruco()) {
			//volver la partida al estado normal
			partida.setEstado(2);
			//actualizar el mensaje
			if(jugador==1) {
				partida.setMensajeJugador1("Quiero");
			}else {
				partida.setMensajeJugador2("Quiero");
			}
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		//en caso de estar respondiendo a envido
		}else if(partida.getEstado() == 4 && jugador != partida.getJugadorTanto()) {
			//poner la partida en estado de respuesta de envido (decir el puntaje o "son buenas")
			partida.setEstado(5);
			//actualizar el mensaje y decir cuanto tanto tiene en mano
			if(jugador==1) {
				partida.setMensajeJugador1("Quiero, " + servicioTanto.obtenerTantoDeLaMano(partida.getManoJugador1()));
			}else {
				partida.setMensajeJugador2("Quiero, " + servicioTanto.obtenerTantoDeLaMano(partida.getManoJugador2()));
			}
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		// en caso de responder la cantidad de tanto una vez aceptado el envido
		}else if(partida.getEstado() == 5 && jugador == partida.getJugadorTanto()) {
			//decidir el ganador del tanto y cuantos puntos vale
			partida.setGanadorTanto(servicioTanto.compararTanto(partida));
			partida.setPuntosPorTanto(servicioTanto.calcularValorTanto(partida.getTipoTanto(), true));
			partida.setEstado(2); // volver la partida al estado normal
			
			//en caso de ganar se le agrega " son mejores"
			if(partida.getGanadorTanto().equals(jugador)) {
				if(jugador==1) {
					partida.setMensajeJugador1(servicioTanto.obtenerTantoDeLaMano(partida.getManoJugador1())+" son mejores");
				}else {
					partida.setMensajeJugador2(servicioTanto.obtenerTantoDeLaMano(partida.getManoJugador2())+" son mejores");
				}
			}else {
				if(jugador==1) {
					partida.setMensajeJugador1(servicioTanto.obtenerTantoDeLaMano(partida.getManoJugador1())+" ");
				}else {
					partida.setMensajeJugador2(servicioTanto.obtenerTantoDeLaMano(partida.getManoJugador2())+" ");
				}
			}
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		}
	}
	
	//segun el estado aplica los cambios ante una respuesta negativa
	public void noQuiero(Partida partida, Integer jugador) {
		//en caso de estar respondiendo a truco
		if (partida.getEstado() == 3 && jugador != partida.getJugadorTruco()) {
			// se resta un punto al truco para coincidir con el valor
			partida.setPuntosPorTruco(partida.getPuntosPorTruco()-1);
			
			// se termina la ronda declarando como ganador al contrario
			if (jugador==1) {
				partida.setMensajeJugador1("No Quiero");
				concluirMano(partida, 2);
			}else {
				partida.setMensajeJugador2("No Quiero");
				concluirMano(partida ,1);
			}
			
		//en caso de estar respondiendo a envido
		}else if(partida.getEstado() == 4 && jugador != partida.getJugadorTanto()) {
			//el envido no se jugo por lo tanto no hay ganador
			partida.setGanadorTanto(3);
			//inmediatamente se suma el puntaje correspondiente al oponente
			if(jugador == 1) {
				partida.setMensajeJugador1("No Quiero");
				partida.setPuntajeJugador1(partida.getPuntajeJugador1()+servicioTanto.calcularValorTanto(partida.getTipoTanto(), false));
			}else {
				partida.setMensajeJugador2("No Quiero");
				partida.setPuntajeJugador2(partida.getPuntajeJugador2()+servicioTanto.calcularValorTanto(partida.getTipoTanto(), false));
			}
			
			//vuelve la partida al estado normal
			partida.setEstado(2);
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		// en caso de responder "Son buenas" una vez empezado el envido
		}else if(partida.getEstado() == 5 && jugador == partida.getJugadorTanto()) {
			//se declara ganador del envido al oponente y se aplica el calculo al puntaje
			partida.setGanadorTanto(getOponente(jugador));
			partida.setPuntosPorTanto(servicioTanto.calcularValorTanto(partida.getTipoTanto(), true));
			//vuelve la partida al estado normal
			partida.setEstado(2);
			//actualiza el mensaje
			if(jugador == 1) {
				partida.setMensajeJugador1("Son Buenas");
			}else {
				partida.setMensajeJugador2("Son Buenas");
			}
			partida.setCambiosJugador1(true);
			partida.setCambiosJugador2(true);
		}
	}
	
	//devuelve el jugador contrario al que entra por parametro
	@Override
	public Integer getOponente(Integer jugador) {
		if(jugador == 1) {
			return 2;
		}else {
			return 1;
		}
	}
	
	public void mazo(Partida partida, Integer jugador) {
		Integer ganador = getOponente(jugador);

		//actualizar el mensaje
		if(jugador==1) {
			partida.setMensajeJugador1("Mazo");
		}else {
			partida.setMensajeJugador2("Mazo");
		}
		
		partida.setCambiosJugador1(true);
		partida.setCambiosJugador2(true);
		
		concluirMano(partida, ganador);
	}
}
