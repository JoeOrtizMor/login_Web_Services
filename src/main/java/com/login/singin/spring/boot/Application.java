package com.login.singin.spring.boot;

import com.login.singin.spring.boot.entidades.Roles;
import com.login.singin.spring.boot.entidades.Usuario;
import com.login.singin.spring.boot.entidades.UsuarioRol;
import com.login.singin.spring.boot.entidades.servicios.UsuarioService;
import com.login.singin.spring.boot.excepciones.UsuarioFoundException;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class Application implements CommandLineRunner{
    
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
   
    

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        //Registro del Usuario admin
       /* try{
        Usuario usuario = new Usuario();
        
        usuario.setNombre("Karen");
        usuario.setApellidos("datta");
        usuario.setUsername("Karen99");
        usuario.setPassword(bCryptPasswordEncoder.encode("password"));
        usuario.setEmail("karen@gmail.com");
        usuario.setNumero("2871516235");
        usuario.setPerfil("foto.png");
        
        Roles rol = new Roles();
        rol.setRolId(1L);
        rol.setNombre("ADMIN");
        
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setRol(rol);
        usuarioRol.setUsuario(usuario);
        usuarioRoles.add(usuarioRol);
        
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario, usuarioRoles);
        System.out.println(usuarioGuardado.getUsername());
            
        }catch(UsuarioFoundException exception){
            exception.printStackTrace();
            
        }*/
    }

}