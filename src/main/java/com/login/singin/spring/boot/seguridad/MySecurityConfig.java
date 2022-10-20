
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
import org.springframework.security.config.http.SessionCreationPolicy;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity //Nos permite especificar la configuracion de acceso a los recursos publicados
@Configuration
//prePostEnabled: Habilita las anotaciones previas y posteriores de Spring Security.
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    
   


    
    
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
               
                .cors()
                .disable()
                .csrf()
                .disable()
                .addFilterAfter(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()            
                .antMatchers("/api/auth/**", "/api/**", "/swagger-ui*/**", "/techgeeknext-openapi/**", "/v3/api-docs/**","/swagger-ui/**","/swagger-ui/index/**","/forgot_password","/rese_password","/usuario/registro","/usuario/eliminar/**").permitAll()
               //.antMatchers().hasAuthority("ADMIN")
                //.antMatchers().hasAuthority("ADMIN")
                //.antMatchers().hasAnyAuthority("USUARIO","ADMIN")
                .antMatchers(HttpMethod.POST, "/usuario/iniciarSesion").permitAll()
                //.antMatchers(HttpMethod.OPTIONS).permitAll()
                  .anyRequest().authenticated()
        /*.and()
        .formLogin()
        .permitAll()
        .and()
        .logout().permitAll()*/;
       
        
        
        /*http.cors().and().csrf().disable()
        .addFilterAfter(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/usuario/iniciarSesion").permitAll()*/
                                //.antMatchers("/api/auth/**", "/api/**", "/swagger-ui*/**", "/techgeeknext-openapi/**", "/v3/api-docs/**").permitAll()
				//.anyRequest().authenticated();;             
       
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); 
    }

    
}
