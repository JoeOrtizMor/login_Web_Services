
package com.login.singin.spring.boot.repositorios;

import com.login.singin.spring.boot.entidades.Roles;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolRepository extends JpaRepository<Roles, Long>{
    
}
