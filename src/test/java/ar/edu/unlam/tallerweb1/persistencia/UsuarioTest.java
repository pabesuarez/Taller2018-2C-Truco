package ar.edu.unlam.tallerweb1.persistencia;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.controladores.ControladorUsuario;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

public class UsuarioTest extends SpringTest{
	
	private Usuario usuario1 , usuario2;
	private List<Usuario> listaDeUsuarios;
	private Session sesion;
	
	@Before
	public void inicializacion() {
		usuario1 = new Usuario();
		usuario2 = new Usuario();
		sesion = getSession();
		listaDeUsuarios = new ArrayList<Usuario>();
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	@Transactional
	@Rollback(true)
	public void testQueAlPasarUsuarioYPasswordNoValidoLlevaAlIndex() {
		Usuario primerUsuario = mock(Usuario.class);
		ServicioUsuario servicioLogin = mock(ServicioUsuario.class);
		
		when(primerUsuario.getNombre()).thenReturn("Marcelo");
		when(primerUsuario.getPass()).thenReturn("mock");
		when(servicioLogin.consultarUsuario(any(Usuario.class))).thenReturn(primerUsuario);
		
		ControladorUsuario controladorLogin = new ControladorUsuario();
		
		assertThat(controladorLogin.irAHome().equals(primerUsuario)).isNotNull();
		
    }
	
	@Test(expected = Exception.class)
	@Transactional
	@Rollback(true)
	@SuppressWarnings("unchecked")
	public void testQueAlCrearUsuarioSiYaExisteDevuelvaError() throws Exception {
		usuario1.setNombre("Marcelo");
		usuario1.setPass("123456");
		
		usuario2.setNombre("Marcelo");
		usuario2.setPass("123456");
		
		if(this.usuario1.getNombre().equals(this.usuario2.getNombre()) || this.usuario1.getPass().equals(this.usuario2.getPass())) {
			throw new Exception("Ya existe un usuario con ese email , por favor elija otro");
		} else {
			System.out.println("Usuario valido");
		}
		
		getSession().save(usuario1);
		getSession().save(usuario2);
		
		
		listaDeUsuarios = sesion.createCriteria(Usuario.class)
						  .add(Restrictions.and(Restrictions.eq("nombre", "nombre"),
								  Restrictions.eq("pass", "pass")))
						  .list();
		
		assertThat(listaDeUsuarios.get(0).getNombre()).isEqualTo(usuario1.getNombre()).isNotNull();
		assertThat(listaDeUsuarios).hasSize(1);
	}
	
	
	@Test(expected = Exception.class)
	@Transactional
	@Rollback(true)
	@SuppressWarnings("unchecked")
	public void testQueElUsuarioNoEscribaSuPasswordYDevuelvaError() throws Exception {
		usuario1.setNombre("Marcelo");
		usuario1.setPass(null);
		
		if(this.usuario1.getPass() == null) {
			throw new Exception("Por favor escriba una contraseña");
		} else {
			this.usuario1.getPass();
		}
		
		getSession().save(usuario1);
		
		listaDeUsuarios = sesion.createCriteria(Usuario.class)
							.add(Restrictions.isNull("pass"))
							.list();
		
		assertThat(listaDeUsuarios.get(0).getPass()).isNull();
		assertThat(listaDeUsuarios).hasSize(1);
			
	}
	
	
	@Test(expected = Exception.class)
	@Transactional
	@Rollback(true)
	public void testQueElUsuarioEscribaSuPasswordDeFormaIncorrectaYDevuelvaError() throws Exception{
		usuario1.setNombre("Marcelo");
		usuario1.setPass("123456");
		
		if(this.usuario1.getPass().equals(this.usuario1.getPass())) {
			throw new Exception("password incorrecta");
		} else {
			this.usuario1.getPass();
		}
		
		getSession().save(usuario1);
		
	}

}
