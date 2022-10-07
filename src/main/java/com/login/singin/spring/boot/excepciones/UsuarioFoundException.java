
package com.login.singin.spring.boot.excepciones;


public class UsuarioFoundException extends Exception{
    
     public UsuarioFoundException(){
        super("El usuario con ese username ya existe en la base de datos, vuelva a intentarlo");
    }
    
    public UsuarioFoundException(String mensaje){
        super(mensaje);
        
    }
    
}
