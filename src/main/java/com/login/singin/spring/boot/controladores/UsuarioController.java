
package com.login.singin.spring.boot.controladores;

import com.login.singin.spring.boot.entidades.Roles;
import com.login.singin.spring.boot.entidades.Usuario;
import com.login.singin.spring.boot.entidades.UsuarioRol;
import com.login.singin.spring.boot.entidades.serivicios.UsuarioService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    
    
    @PostMapping("/")
    public Usuario guardarUsuario(@RequestBody Usuario usuario ) throws Exception{
        usuario.setPerfil("default.png");
        
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        
        //Asignamos Roles
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        
        Roles rol = new Roles();
        rol.setRolId(2L);
        rol.setRolNombre("USER");
        rol.setNombre("USUARIO");//Asignamos el rol Usuario por defecto a todos los que se registren
        
        
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        
        usuarioRoles.add(usuarioRol);
        return usuarioService.guardarUsuario(usuario, usuarioRoles);
    }
    
    
    @GetMapping("/{username}")
    public Usuario obtnerUsuario(@PathVariable("username") String username){
        return usuarioService.obtenerUsuario(username);
        
    }
    
    
            
    
}
