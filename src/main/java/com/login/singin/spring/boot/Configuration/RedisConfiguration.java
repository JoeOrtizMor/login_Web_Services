
package com.login.singin.spring.boot.Configuration;



import com.login.singin.spring.boot.DTO.JwtRedisDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

//configuracion para el uso de redis
@Configuration
public class RedisConfiguration {
    
    //Conexión con Redis
    @Bean
    JedisConnectionFactory jedisConecctionFactory(){
        //RedisStandaloneConfiguration redisStandaloneConfiguration = new
                //RedisStandaloneConfiguration("127.0.0.1", 6380);
        
        //return new JedisConnectionFactory(new RedisStandaloneConfiguration("127.0.0.1", 6380)); 
        return new JedisConnectionFactory();
   }
    
    @Bean
    RedisTemplate<String, JwtRedisDTO> redisTemplate(){
        final RedisTemplate<String, JwtRedisDTO> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConecctionFactory());
        return redisTemplate;
    }
    
}
