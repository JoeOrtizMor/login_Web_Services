
package com.login.singin.spring.boot.controladores;
import com.login.singin.spring.boot.repositorios.TokenRedisService;
import com.login.singin.spring.boot.DTO.JwtRedisDTO;
import com.login.singin.spring.boot.DTO.LogueoDTO;
import com.login.singin.spring.boot.DTO.TokenJwtDTO;
import com.login.singin.spring.boot.entidades.Roles;
import com.login.singin.spring.boot.entidades.Usuario;
import com.login.singin.spring.boot.entidades.UsuarioRol;
import com.login.singin.spring.boot.entidades.servicios.UsuarioService;
import com.login.singin.spring.boot.excepciones.JwtExceptionSeguridad;
import com.login.singin.spring.boot.repositorios.UsuarioRepository;
import com.login.singin.spring.boot.seguridad.JwtTokenProvider;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.swagger.v3.oas.annotations.Operation;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuario")
public class UsuarioController {
    
    private TokenRedisService tokenService; 
    
    public UsuarioController(TokenRedisService tokenService) {
        this.tokenService = tokenService;
    }
    
    @Autowired
    private UsuarioService usuarioService;
    
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    //inyeccion de repositorio para llamar funciones Jpa 
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    //Inyeccion para autenticacion con jwt
    @Autowired
    private AuthenticationManager authenticationManager;
    
    //Inyeccion de clase para generar los tokens
    @Autowired
    private JwtTokenProvider jwtTokenProvider; 
    
    //Inyeccion para agregar excepciones de Jwt
    @Autowired
    private JwtExceptionSeguridad jwtExceptionSeguridad; 
    
    //@Autowired
    //private GrantedAuthority grantedAuthority;
    
    
    
  
    @PostMapping("/registro")
    public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception{
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
    public Usuario obtnerUsuario(@PathVariable("username") String username, @RequestParam(required = false) String token){
        //validacion del token
        jwtExceptionSeguridad.validarToken(token);
        //String datos = grantedAuthority.getAuthority();
        
        return usuarioService.obtenerUsuario(username);

    }
    
    
    @Operation(summary = "Inicio de sesion", description = "Inicio de sesión a través del correo/usuario y contraseña", tags = "Sesiones y registros")
    @PostMapping("/iniciarSesion")
    
     public ResponseEntity<TokenJwtDTO> authenticateUser(@RequestBody  LogueoDTO logueoDTO){
        System.out.println("a");
         
    //obteniendo y almacenando usuario o contraseña solicitado en el requestBody
    String usernameOrEmail = logueoDTO.getUsernameOrEmail();

    //almacenando la tabla del usuario encontrado por el nombre o correo
    Optional<Usuario> usuario = usuarioRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    
    //almacenando nombre de usuario para usarlo como autenticacion
    String username = usuario.get().getUsername();
    
    //obteniendo id del usuario de la tabla guardada
    String Iduser = String.valueOf(usuario.get().getId());
    
        System.out.println("NOMBRE DE USUARIO O CORRREO: "+usernameOrEmail);
        System.out.println("NOMBRE DE USUARIO : "+username);
        System.out.println("ID DE USUARIO: "+Iduser);
        System.out.println("CONTRASEÑA DE USUARIO "+logueoDTO.getPassword());
      
    
    //autenticacion del usuario con los datos de LoginDTO con uso de spring    
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, logueoDTO.getPassword()));
    
    //constructor para enviar datos a redis (intentar inyectarlo)
    JwtRedisDTO jwtRedisDTO = new JwtRedisDTO();
    
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    //obtenemos el token del jwtTokenProvider
    String token = jwtTokenProvider.generarToken(authentication);
    
    
    //se guardar el token y un id para almacenarlo en redis
    jwtRedisDTO.setTokenDeAccesoGuardado(token);
    jwtRedisDTO.setId(Iduser);
    
    //se envia los datos para almacenarlos
    tokenService.save(jwtRedisDTO);
    
    //retorna un token con ResponseEntity
    return ResponseEntity.ok(new TokenJwtDTO(token));
    
     }
     
     //Comentando backend con Swagger
    @Operation(
            summary = "Refresh(actualización) tokens", 
            description = "Refresh(actualizar) un token a partir de un token válido para renovar las credenciales de expiración", 
            tags = "Sesiones y registros")
    
    //peticion para realizar un refresh token
    @PostMapping("/Refresh")
    //pidiendo todos los datos que se tiene declarado en JwtRedisDTO
    public ResponseEntity<JwtRedisDTO> refresh(@RequestBody JwtRedisDTO jwtRedisDTO) throws ParseException{
     
    
        
    //validacion del token  
    jwtExceptionSeguridad
        //Se llama la variable tipo jwtRedisDTO donde se almaceno el token y el id 
        .validarToken(jwtRedisDTO
            //se llama al token para enviarselo al metodo de validacion del token
            .getTokenDeAccesoGuardado());
        
        //se obtiene el token viejo ingresado en el RequestBody
        String oldToken = jwtRedisDTO.getTokenDeAccesoGuardado();
        
        //Se almacena el token como metodo para descronstruirlo y tomar los claims
        JWT jwtt = JWTParser.parse(oldToken);
        JWTClaimsSet jWTClaimsSet = jwtt.getJWTClaimsSet();
        
        //se toma el id del token viejo para guardarlo en una variable
        String id = jWTClaimsSet.getStringClaim("id");
        
        //se realiza el refresh token y se guarda en nuevo token en una variable
        String newToken = jwtTokenProvider.refreshToken(jwtRedisDTO);
        
        //se guardar el token y un id para almacenarlo en redis
        jwtRedisDTO.setTokenDeAccesoGuardado(newToken);
        jwtRedisDTO.setId(id);
        tokenService.update(jwtRedisDTO);

        //nuevo constructor para alcenar el nuevo token y poder mostrarlo
        TokenJwtDTO jwt = new TokenJwtDTO(newToken); //a revisar
        
        //se muestra el token
        return new ResponseEntity(jwt, HttpStatus.OK);

    }
    
        @DeleteMapping("/{usuarioId}")
    public void elminarUsuario(@PathVariable("usuarioId") Long usuarioId){
       usuarioService.eliminarusuario(usuarioId);
    }

    @GetMapping("/lista-usuarios")
    public List<Usuario> index(){
        return usuarioService.findAll();
    }
    
            
    
}
