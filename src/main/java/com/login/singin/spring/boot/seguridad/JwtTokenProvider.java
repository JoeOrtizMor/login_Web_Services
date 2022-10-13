package com.login.singin.spring.boot.seguridad;

import com.login.singin.spring.boot.DTO.JwtRedisDTO;
import com.login.singin.spring.boot.entidades.Usuario;
import com.login.singin.spring.boot.repositorios.UsuarioRepository;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.text.ParseException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


//clase para desarrollar el jwt
@Component
public class JwtTokenProvider {
    
    @Autowired
    private UsuarioRepository obtenerIdServicio;
    
    //clave secreta siendo llamada desde properties
    @Value("${app.jwt-secret}")
    private final String jwtSecret = "JWTSecretKey";
    
    //tiempo en milisegundos para el tiempo de vida de un token
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs = 6048;
    
    @Value("${app.jwt-password-forgot-expiration-milliseconds}")
    private int jwtExpirationInMsPassword;
    
    
    

    
    //metodo para generar el token
    public String generarToken(Authentication authentication){
        
        
        //obteniendo el nombre de usuario
        String username = authentication.getName();
        Usuario usuario = obtenerIdServicio.findByUsername(username);
        String correo = usuario.getEmail();
        String id = String.valueOf(usuario.getId());
        
        
        
        //variable para obtener la fecha actual
        Date fechaActual = new Date();
        
        //variable para sumar la fecha actual mas lo milisegundos para dar un tiempo limite de uso del token
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs );
        
        
        
        
        //variable para almacenar token
        String token = 
                //creacion del token tomando el nombre de usuario "setSubject(username)", fecha actual "setIssuedAt(new Date())",
                //fecha de expiracion "setExpiration(fechaExpiracion)" y firmando con la clave secreta "jwtSecret"
                Jwts.builder()
                        .setIssuer(username)
                        .setSubject(correo)
                        .claim("id", id)
                .setIssuedAt(new Date())
                        .setExpiration(fechaExpiracion)
                        .signWith(SignatureAlgorithm
                                .HS512, jwtSecret.getBytes())
                        .compact();
        
        //retorno de la variable
        return token;
    }
    
    public String generarTokenRecuperarPassword(String correo){
        
        
        //obteniendo el nombre de usuario
        
        
        //variable para obtener la fecha actual
        Date fechaActual = new Date();
        
        //variable para sumar la fecha actual mas lo milisegundos para dar un tiempo limite de uso del token
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMsPassword);
        
        
        
        
        //variable para almacenar token
        String token = 
                //creacion del token tomando el nombre de usuario "setSubject(username)", fecha actual "setIssuedAt(new Date())",
                //fecha de expiracion "setExpiration(fechaExpiracion)" y firmando con la clave secreta "jwtSecret"
                Jwts.builder()
                        .setIssuer(correo)
                .setIssuedAt(new Date())
                        .setExpiration(fechaExpiracion)
                        .signWith(SignatureAlgorithm
                                .HS512, jwtSecret.getBytes())
                        .compact();
        
        //retorno de la variable
        return token;
    }
    
    //metodo para desencriptar el token y obtener el nombre de usuario
    public String obtenerUsernameDelJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    
    

    
    //metodo para hacer refresh a un token 
     public String refreshToken (JwtRedisDTO jwtRedisDTO) throws ParseException{
         


        //obteniendo datos
        JWT jwt = JWTParser.parse(jwtRedisDTO.getTokenDeAccesoGuardado());
        
        JWTClaimsSet jWTClaimsSet = jwt.getJWTClaimsSet();
        
        //guardando datos en variables
        String id = jWTClaimsSet.getStringClaim("id");
        String username = jWTClaimsSet.getIssuer();
        String correo = jWTClaimsSet.getSubject();
        
        //variables para el token
        Date fechaActual = new Date();
        Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs )  ;
        //retorno y creacion del token
        return Jwts.builder()
                        .setIssuer(username)
                        .setSubject(correo)
                        .claim("id", id)
                .setIssuedAt(new Date())
                        .setExpiration(fechaExpiracion)
                        .signWith(SignatureAlgorithm
                                .HS512, jwtSecret.getBytes())
                        .compact();
        
    }


}
