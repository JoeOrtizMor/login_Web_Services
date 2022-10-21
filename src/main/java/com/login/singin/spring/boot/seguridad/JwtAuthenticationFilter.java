
package com.login.singin.spring.boot.seguridad;

import com.login.singin.spring.boot.excepciones.JwtExceptionSeguridad;
import com.login.singin.spring.boot.impl.UserDetailsServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthenticationFilter extends OncePerRequestFilter{

    //inyeccion de dependecias
    @Autowired
    private JwtExceptionSeguridad jwtExceptionSeguridad;
    
    @Autowired
    private JwtTokenProvider jwtTokenPrivader;
    
    @Autowired
    private UserDetailsServiceImpl customUserDetailsService;
    
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        
        //obtener el token de la solicitud http
        String token = obtenerJWTdelaSolicitud(request);
        
        //validamos el token, verificando si hay un texto y ejecutando el metodo para validar un token
        if (StringUtils.hasText(token) && jwtExceptionSeguridad.validarToken(token)) {
            //obtener el username del token
            String username= jwtTokenPrivader.obtenerUsernameDelJWT(token);
            
            
            //cargar el usuario asociado al token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken autheticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            autheticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            
            //establecer la seguridad
            SecurityContextHolder.getContext().setAuthentication(autheticationToken);
            
        }
        filterChain.doFilter(request, response);
        
    }
    
    //Bearer token de acceso
    private String obtenerJWTdelaSolicitud(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7,bearerToken.length());

            
        }
        return null;
    }
    
    
    
}
