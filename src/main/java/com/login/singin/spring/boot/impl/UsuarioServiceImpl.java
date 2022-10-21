
package com.login.singin.spring.boot.impl;


import com.login.singin.spring.boot.entidades.Usuario;
import com.login.singin.spring.boot.entidades.UsuarioRol;
import com.login.singin.spring.boot.entidades.servicios.UsuarioService;
import com.login.singin.spring.boot.repositorios.RolRepository;
import com.login.singin.spring.boot.repositorios.UsuarioRepository;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//Tagueamos que es un servicio
//Implementamos sus servicios junto con sus constructores
@Service
public class UsuarioServiceImpl implements UsuarioService{
    
    //Inyeccion de dependencias
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;

    @Override
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        Usuario usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());
        
        if(usuarioLocal != null){
            System.out.println("El usuario ya existe");
        }
        else{
            for(UsuarioRol usuarioRol:usuarioRoles){
                rolRepository.save(usuarioRol.getRol());
            }
        usuario.getUsuarioRoles().addAll(usuarioRoles);
        
        usuarioLocal = usuarioRepository.save(usuario);
            
        }
        return usuarioLocal;
    }

    
    //Metodos de UsuarioService
    @Override
    public Usuario obtenerUsuario(String username) {
        
        if(username != null){
            System.out.println("El usuario solicitado no existe");
        }else{
            System.out.println("Usuario encontrado");
        }
        
        return usuarioRepository.findByUsername(username);
    }
    
    @Override
    public void eliminarusuario(Long usuarioId) {
      usuarioRepository.deleteById(usuarioId);
    }

    @Override
    public List<Usuario> findAll() {
         return (List<Usuario>)usuarioRepository.findAll();
    }
    
    

    //Metodo para eliminar un usuario

    @Override
    public void eliminarusuario(Long usuarioId) {
      usuarioRepository.deleteById(usuarioId);
    }

    @Override
    public List<Usuario> findAll() {
         return (List<Usuario>)usuarioRepository.findAll();
    }

  

   

   

    

    

    
    

   

   
    
    
    
    
}
