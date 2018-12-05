package ar.edu.unlam.tallerweb1.dao;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.modelo.PartidaEnCurso;
import ar.edu.unlam.tallerweb1.modelo.PartidasTerminadas;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("partidaTerminadaDao")
public class PartidaTerminadaDaoImpl implements PartidaTerminadaDao{

	@Inject
    private SessionFactory sessionFactory;

	@Inject
	private PartidaEnCursoDao partidaEnCursoDao;
	
	public void guardarPartidaTerminada(Partida partida, Integer ganador){
		final Session session = sessionFactory.getCurrentSession();
		Long idPartidaEnCurso = partida.getPartidaEnCursoID();
		PartidasTerminadas partidaTerminada = new PartidasTerminadas();
		// Busco la partida en curso con el ID
		PartidaEnCurso partidaEnCurso = partidaEnCursoDao.buscarPorId(idPartidaEnCurso);
		//Obtengo datos y los guardo en partidaTerminada
		Usuario jugador1 = partidaEnCurso.getJugador1();
		Usuario jugador2 = partidaEnCurso.getJugador2();
		partidaTerminada.setJugador1(jugador1);
		partidaTerminada.setJugador2(jugador2);
		Integer puntajeJugador1 = partida.getPuntajeJugador1();
		Integer puntajeJugador2 = partida.getPuntajeJugador2();
		partidaTerminada.setPuntajeJugador1(puntajeJugador1);
		partidaTerminada.setPuntajeJugador2(puntajeJugador2);
		Usuario ganadorUsuario;
		if(ganador == 1) {
			 ganadorUsuario = jugador1;
		}else if(ganador==2){
			 ganadorUsuario = jugador2;
		}else {
			//Cambiar después xdxdxd Caso empate
			 ganadorUsuario = jugador1;
		}
		partidaTerminada.setGanador(ganadorUsuario);
		session.save(partidaTerminada);
		
	};
}
