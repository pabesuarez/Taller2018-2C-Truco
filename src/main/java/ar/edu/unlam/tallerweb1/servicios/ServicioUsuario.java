package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioUsuario {
	public Usuario buscarPorId(Long usuarioId);
	public Usuario login(String nombre,String password);
	public void nuevoUsuario(Usuario usuario);
}
