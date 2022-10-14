
package com.login.singin.spring.boot.excepciones;

import lombok.Data;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super();
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
  
}
