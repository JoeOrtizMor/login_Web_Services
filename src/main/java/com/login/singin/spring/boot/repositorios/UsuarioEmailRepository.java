
package com.login.singin.spring.boot.repositorios;

import com.login.singin.spring.boot.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioEmailRepository extends JpaRepository<Usuario, Integer>{
    
    //Encontrar datos en BD por email
    public Usuario findByEmail(String email); 
     
    //Cambios en base de datos en el dato de token
    public Usuario findByResetPasswordToken(String token);
}
