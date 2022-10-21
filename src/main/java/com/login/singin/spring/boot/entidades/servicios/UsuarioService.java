
package com.login.singin.spring.boot.entidades.servicios;

import com.login.singin.spring.boot.entidades.Usuario;
import com.login.singin.spring.boot.entidades.UsuarioRol;
import java.util.List;
import java.util.Set;


public interface UsuarioService {
    
    //Guarda usuarios y pasa por un conjunto de roles
    //Set: Pasa un conjunto de roles que asignamos
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles)throws Exception;
    
    public Usuario obtenerUsuario (String username);

     public void eliminarusuario(Long usuarioId);

    public List<Usuario> findAll();
    
  
     
}
