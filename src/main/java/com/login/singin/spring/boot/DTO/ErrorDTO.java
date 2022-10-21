
package com.login.singin.spring.boot.DTO;

import lombok.Builder;

@Builder
public class ErrorDTO {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDTO(String message) {
        this.message = message;
    }
    
    
    
}
