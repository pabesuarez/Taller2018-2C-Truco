package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;


@Repository("usuarioDao")
public class UsuarioDaoImpl implements UsuarioDao {

	@Inject
    private SessionFactory sessionFactory;


	@Override
	public Usuario buscarPorId(Long usuarioId) {
		final Session session = sessionFactory.getCurrentSession();
		return (Usuario) session.createCriteria(Usuario.class).add(Restrictions.eq("id", usuarioId)).uniqueResult();
	}
	

	@Override
	public void nuevoUsuario(Usuario usuario) {
		final Session session = sessionFactory.getCurrentSession();
		session.save(usuario);
	}
	//Comprobar las credenciales y retorna el usuario logeado, si las credenciales son incorrectas
	//o el usuario no existe retorna null
	@Override
	public Usuario login(String nombre, String pass) {
		final Session session = sessionFactory.getCurrentSession();
		return (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("nombre", nombre))
				.add(Restrictions.eq("pass", pass))
				.uniqueResult();
	}

}
