package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Interface que define los metodos del DAO de Usuarios.
public interface UsuarioDao {
	
	public Usuario buscarPorId (Long usuarioId);
	public void nuevoUsuario (Usuario usuario);
	public Usuario login(String nombre, String pass);
}
