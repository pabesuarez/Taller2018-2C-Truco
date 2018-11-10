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
		// PENDIENTE: buscar un usuario por ID y retornarlo
		return null;
	}



	@Override
	public void nuevoUsuario(Usuario usuario) {
		// PENDIENTE: guardar un usuario en la base de datos
	}

	//Comprobar las credenciales y retorna el usuario logeado, si las credenciales son incorrectas
	//o el usuario no existe retorna null
	@Override
	public Usuario login(String nombre, String password) {
		final Session session = sessionFactory.getCurrentSession();
		return (Usuario) session.createCriteria(Usuario.class)
				.add(Restrictions.eq("nombre", nombre))
				.add(Restrictions.eq("password", password))
				.uniqueResult();
	}

}
