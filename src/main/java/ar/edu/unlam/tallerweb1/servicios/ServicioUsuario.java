package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioUsuario {
	public Usuario buscarPorId(Long usuarioId);
	public Usuario login(String nombre,String pass);
	public void nuevoUsuario(Usuario usuario);
	Usuario consultarUsuario(Usuario usuario);
	void guardarUsuario(Usuario usuario);
}
