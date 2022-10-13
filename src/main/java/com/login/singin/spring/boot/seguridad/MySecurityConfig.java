
package com.login.singin.spring.boot.seguridad;

import com.login.singin.spring.boot.impl.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity //Nos permite especificar la configuracion de acceso a los recursos publicados
@Configuration
//prePostEnabled: Habilita las anotaciones previas y posteriores de Spring Security.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    
   

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); 
    }
    
    
     @Bean //Encriptamos las contraseñas de los usuarios
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    

    //PasswordEncriptado
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/usuario/registro","/usuario/eliminar/**").hasAuthority("ADMIN")
                .antMatchers("/usuario/lista-usuarios").hasAuthority("ADMIN")
                .antMatchers("/usuario/username/**").hasAnyAuthority("USUARIO","ADMIN")
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
        .and()
        .formLogin()
        .permitAll()
        .and()
        .logout().permitAll();
               
                
    }
    
   
    
    
    
    
    
    
}
