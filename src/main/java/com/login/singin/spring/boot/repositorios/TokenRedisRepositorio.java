
package com.login.singin.spring.boot.repositorios;



import com.login.singin.spring.boot.DTO.JwtRedisDTO;
import java.util.Map;

    

public interface TokenRedisRepositorio{
    
    //Repositorio para peticioens de tokens para redis
    Map<String, JwtRedisDTO> findAll();
    JwtRedisDTO findById(String id);
    void save(JwtRedisDTO jwtRedisDTO);
    void delete(String id);
    void update(JwtRedisDTO jwtRedisDTO);
    
}
