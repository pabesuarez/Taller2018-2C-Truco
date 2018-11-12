package ar.edu.unlam.tallerweb1.dao;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import ar.edu.unlam.tallerweb1.modelo.Partida;
import ar.edu.unlam.tallerweb1.modelo.PartidaEnCurso;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Repository("partidoEnCursoDao")
public class PartidaEnCursoDaoImpl implements PartidaEnCursoDao {

	@Inject
    private SessionFactory sessionFactory;
	
	@Override
	public PartidaEnCurso buscarPorId(Long partidaID) {
		final Session session = sessionFactory.getCurrentSession();
		return (PartidaEnCurso) session.createCriteria(PartidaEnCurso.class)
				.add(Restrictions.eq("id", partidaID))
				.uniqueResult();
	}
	
	@Override
	public Long nuevaPartida(PartidaEnCurso partida) {
		final Session session = sessionFactory.getCurrentSession();
		session.save(partida);
		return partida.getId();
	}

	@Override
	public void removerPartida(Long partidaID) {
		final Session session = sessionFactory.getCurrentSession();
		PartidaEnCurso partida = buscarPorId(partidaID);
		session.delete(partida);
	}

	@Override
	public void cambiarEstado(Long partidaID, Integer estado) {
		final Session session = sessionFactory.getCurrentSession();
		PartidaEnCurso partida = buscarPorId(partidaID);
		partida.setEstado(estado);
		session.update(partida);
	}

	@Override
	public void unirJugador(Long partidaID, Integer numero, Usuario jugador) {
		final Session session = sessionFactory.getCurrentSession();
		PartidaEnCurso partida = buscarPorId(partidaID);
		if (numero==1) {
			partida.setJugador1(jugador);
		}else if(numero==2) {
			partida.setJugador2(jugador);
		}
		session.update(partida);
	}

	@Override
	public List<PartidaEnCurso> traerTodasLasPartidasEnProgreso(){
		final Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<PartidaEnCurso> partidas = session.createCriteria(PartidaEnCurso.class)
								 		.list();
		return partidas;
	}

}
