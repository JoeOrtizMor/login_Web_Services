
package com.login.singin.spring.boot.repositorios;

import com.login.singin.spring.boot.entidades.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    //Busca por Username 
    public Usuario findByUsername(String username);
    
    //Busca ´por email del usuario
    public Usuario findByemail (String email);
    
    //Buscar por email o por nombre de usuario
    public Optional<Usuario> findByUsernameOrEmail(String username,String email);
    
    //Buscar si existe un valor igual que username
    public Boolean existsByUsername(String username);
    
    //Buscar si existe un valor igual que el email	
    public Boolean existsByEmail(String email);
}
