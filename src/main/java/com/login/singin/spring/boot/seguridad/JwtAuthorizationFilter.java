
package com.login.singin.spring.boot.seguridad;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthorizationFilter extends OncePerRequestFilter{

    private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final String SECRET = "JWTSecretKey";

	@Override
        //metodo para 
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
			try {
                        //llamada para verificar si hay un dato nulo en el token
			if (checkJWTToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
                                    
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}	
        
        //!!!!!falta por revision!!!!!
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

            //validación del token
        private Claims validateToken(HttpServletRequest request) {
            //Obtiene el valor que esta en la cabecera y remplaza el valor de PREFIX a caracter en blanco para obtener solo el token
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
                //retorna los claims del token
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}
     


                 //Se obtiene el encabezado obtenido, el cual debe tener un token
	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
                //Se obtiene el encabezado guarda el dato del encabezado en un string
		String authenticationHeader = request.getHeader(HEADER);
                //si el encabezado viene nulo o solo viene con el prefijo returnar falso
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}
    
}
