
package com.login.singin.spring.boot.impl;


import com.login.singin.spring.boot.entidades.Usuario;
import com.login.singin.spring.boot.entidades.UsuarioRol;
import com.login.singin.spring.boot.repositorios.UsuarioRepository;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
    

	public UserDetails loadUserByUsername2(String usernameOrEmail) throws UsernameNotFoundException {
            //metodo para buscar de acuerdo al dato de usuario o email
		Usuario usuario = usuarioRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese username o email : " + usernameOrEmail));
	
		return new User(usuario.getEmail(), usuario.getPassword(), mapearRoles(usuario.getUsuarioRoles()));
	}

	private Collection<? extends GrantedAuthority> mapearRoles(Set<UsuarioRol> roles){
		return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getUsuario().getNombre())).collect(Collectors.toList());
	}
    
}
