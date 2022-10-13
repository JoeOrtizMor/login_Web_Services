
package com.login.singin.spring.boot.excepciones;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtExceptionSeguridad {
    
    
        //clave secreta siendo llamada desde properties
    @Value("${app.jwt-secret}")
    private final String jwtSecret = "JWTSecretKey";
    
        //metodo para validar el token
    public boolean validarToken(String token){
        //try catch de acuerdo a que excepción  se cumpla 
        try {
            Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
            return true;
            
        } catch (SignatureException ex) {
            throw new SignatureException("Firma JWT no valido");
            
        }catch (MalformedJwtException ex) {
            System.out.println("2");
            throw new MalformedJwtException("Token JWT no valida");

        }catch (ExpiredJwtException ex) {
            System.out.println("3");
            throw new ExpiredJwtException(null, null, "token expirado");
        
        }catch (UnsupportedJwtException ex) {
            System.out.println("4");
            throw new UnsupportedJwtException("Token JWT no compatible");
        
        }catch (IllegalArgumentException ex) {
            System.out.println("5");
            throw new IllegalArgumentException("la cadena JWT esta vacia");
        }

    }
    
}
