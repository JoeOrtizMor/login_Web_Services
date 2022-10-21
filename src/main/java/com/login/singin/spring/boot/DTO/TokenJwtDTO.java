package com.login.singin.spring.boot.DTO;


public class TokenJwtDTO {
    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

    public String getTokenDeAcceso() {
        return tokenDeAcceso;
    }

    public void setTokenDeAcceso(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }

    public String getTipoDeToken() {
        return tipoDeToken;
    }

    public void setTipoDeToken(String tipoDeToken) {
        this.tipoDeToken = tipoDeToken;
    }
    
    //constructor para almacenar el token de acceso
    public TokenJwtDTO(String tokenDeAcceso) {
        this.tokenDeAcceso = tokenDeAcceso;
    }
    
    
}
