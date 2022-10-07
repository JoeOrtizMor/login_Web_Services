
package com.login.singin.spring.boot.repositorios;

import com.login.singin.spring.boot.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    //Busca por Username 
    public Usuario findByUsername(String username);
    
    //Busca ´por email del usuario
    public Usuario findByemail (String email);
    
}
