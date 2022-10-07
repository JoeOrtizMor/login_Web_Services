
package com.login.singin.spring.boot.entidades;

import org.springframework.security.core.GrantedAuthority;


public class Authority implements GrantedAuthority{
    
    private String authority;
    
    public Authority(String authority) {
        this.authority = authority;
    }
    

    //Metodo de autoridad
    @Override
    public String getAuthority() {
        return this.authority;
    }
    
    
    
}
