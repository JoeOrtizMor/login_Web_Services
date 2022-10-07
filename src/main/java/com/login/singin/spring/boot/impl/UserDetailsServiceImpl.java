
package com.login.singin.spring.boot.impl;

import com.login.singin.spring.boot.entidades.Usuario;
import com.login.singin.spring.boot.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;




@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    //Carga el usuario por medio del Username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Usuario usuario = this.usuarioRepository.findByUsername(username);
        
        return usuario;
    
}
    
}
