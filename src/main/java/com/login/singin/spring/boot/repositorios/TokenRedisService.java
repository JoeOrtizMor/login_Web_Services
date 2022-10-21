package com.login.singin.spring.boot.repositorios;

import com.login.singin.spring.boot.DTO.JwtRedisDTO;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

//clase para peticiones de tokens en redis
@Repository
public class TokenRedisService implements TokenRedisRepositorio{
    private static final String KEY = "Token";

    private RedisTemplate<String, JwtRedisDTO> redisTemplate;
    private HashOperations hashOperations;

    public TokenRedisService(RedisTemplate<String, JwtRedisDTO> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    
    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<String, JwtRedisDTO> findAll() {
        return hashOperations.entries(KEY);
    }

    @Override
    public JwtRedisDTO findById(String id) {
        return (JwtRedisDTO) hashOperations.get(KEY, id);
    }

    @Override
    public void save(JwtRedisDTO jwtRedisDTO) {
        hashOperations.put(KEY, jwtRedisDTO.getId(), jwtRedisDTO);
        
    }
    
    @Override
    public void update(JwtRedisDTO jwtRedisDTO) {
        hashOperations.put(KEY, jwtRedisDTO.getId(), jwtRedisDTO);
        
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }
 
}
