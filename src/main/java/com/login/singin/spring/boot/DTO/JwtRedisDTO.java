package com.login.singin.spring.boot.DTO;

import java.io.Serializable;

//Clase DTO para mover datos del token e id en redis
public class JwtRedisDTO implements Serializable{
    
    private String tokenDeAccesoGuardado;
    private String id;
    
    //getter y setter para token en redis
    public String getTokenDeAccesoGuardado() {
        return tokenDeAccesoGuardado;
    }

    public void setTokenDeAccesoGuardado(String tokenDeAccesoGuardado) {
        this.tokenDeAccesoGuardado = tokenDeAccesoGuardado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JwtRedisDTO() {
        super();
    }
    
}
