
package com.login.singin.spring.boot.controladores;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundoJwt {
    
    

    	@GetMapping("helloUser")
	public String helloUser() {
		return "Hello User";
	}
        
        @GetMapping("helloAdmin")
	public String helloAdmin() {
		return "Hello Admin";
	}
    
}
