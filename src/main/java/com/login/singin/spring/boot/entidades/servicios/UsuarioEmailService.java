
package com.login.singin.spring.boot.entidades.servicios;

import com.login.singin.spring.boot.entidades.Usuario;
import com.login.singin.spring.boot.repositorios.UsuarioEmailRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UsuarioEmailService {
    
    //inyeccion de peticiones JPA para email
    @Autowired
    private UsuarioEmailRepository usuarioEmailRepository;
    
        
        public void updateResetPasswordToken(String token, String email){
        //se obtiene los datos de usuario por el email   
        Usuario usuario = usuarioEmailRepository.findByEmail(email);
        //si el usuario tiene datos se entra en el if
        if (usuario != null) {
            //se manda el token en los datos del usuario
            usuario.setResetPasswordToken(token);
            //se guarda los datos
            usuarioEmailRepository.save(usuario);
        } else {
        }
    }
     
        //obtener datos de usuario por token
    public Usuario getByResetPasswordToken(String token) {
        return usuarioEmailRepository.findByResetPasswordToken(token);
    }
     
    //Actualizar contraseña
    public void updatePassword(Usuario usuario, String newPassword) {
        //constructor para codificar contraseña
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        //codificacion de la contraseña
        String encodedPassword = passwordEncoder.encode(newPassword);
        
        //actualiza la contraseña en los datos del usuario
        usuario.setPassword(encodedPassword);
         
        //se deja en blanco el la columna del token 
        usuario.setResetPasswordToken(null);
        
        //se guardan los datos
        usuarioEmailRepository.save(usuario);
    }
    
}
