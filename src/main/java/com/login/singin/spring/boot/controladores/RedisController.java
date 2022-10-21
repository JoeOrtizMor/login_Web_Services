
package com.login.singin.spring.boot.controladores;

import com.login.singin.spring.boot.DTO.JwtRedisDTO;
import com.login.singin.spring.boot.repositorios.TokenRedisService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


//clase para redis otras peticiones de redis
@RestController("/redis")
@CrossOrigin("*")
public class RedisController {
    
       
    @Autowired
    private TokenRedisService tokenRedisService; 
    
        @Operation(
            summary = "Petición de los tokens Jwt", 
            description = "Petición de los tokens Jwt almacenados en redis", 
            tags = "Redis")
    //metodo para ver todos los tokens
    @GetMapping("/jwtRedis")
    public Map<String, JwtRedisDTO> findAll(){
       return tokenRedisService.findAll();
    }
    
    @Operation(
            summary = "Eliminar un token por Id", 
            description = "Elimina un token almacenado en redis a través del Id", 
            tags = "Redis")
    //metodo para eliminar un jwt con su id
    @DeleteMapping("/jwtRedis/{id}")
    public void delete(@PathVariable String id){
        tokenRedisService.delete(id);
    }
    
}
