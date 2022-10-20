
package com.login.singin.spring.boot.controladores;

import com.login.singin.spring.boot.entidades.Usuario;
import com.login.singin.spring.boot.entidades.servicios.UsuarioEmailService;
import com.login.singin.spring.boot.excepciones.CustomerNotFoundException;
import com.login.singin.spring.boot.excepciones.JwtExceptionSeguridad;
import com.login.singin.spring.boot.seguridad.JwtTokenProvider;
import com.login.singin.spring.boot.seguridad.utilerias.Utility;
import io.lettuce.core.dynamic.annotation.Param;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("*")
public class PasswordPerdidaController {
    
    @Autowired
    private JavaMailSender mailSender;
     
    @Autowired
    private UsuarioEmailService usuarioEmailService;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired 
    private JwtExceptionSeguridad jwtExceptionSeguridad;
    
    
        //entrada para pagina de recuperar contraseña
    @GetMapping("/forgot_password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Forgot Password");
            return "index";
 
    }
 
    //entrada para realizar recuperado de contraseña
    @PostMapping("/forgot_password")
    //creando llamada de metodos para solicitar datos y realizar el envio de correo
 public String processForgotPassword(HttpServletRequest request, Model model) {
     //obtieniendo dato (email) del parametro puesto en la pagina html
    String email = request.getParameter("email");
    //generando el token a partir del correo obtenido anteriormente
    String token = jwtTokenProvider.generarTokenRecuperarPassword(email);
     
    //try catch en caso de que se no se realice bien el correo
    try {
        usuarioEmailService.updateResetPasswordToken(token, email);
        String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
        sendEmail(email, resetPasswordLink);
        model.addAttribute("message", "Te hemos enviado un correo para cambiar tu contraseña, porfavor revisa tu buzón.");
         
    } catch (CustomerNotFoundException ex) {
        model.addAttribute("error", ex.getMessage());
    } catch (UnsupportedEncodingException | MessagingException e) {
        model.addAttribute("error", "Ha ocurrido un error mientras en enviaba el correo");
    }
         
    return "index";
}
     
    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
            MimeMessage message = mailSender.createMimeMessage();              
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("residente03.dattainc@gmail.com", "Residente");
            helper.setTo(recipientEmail);

            String subject = "Este es el link para recuperar tu contraseña";

            String content = "<p>Hola,</p>"
                    + "<p>Has solicitado el cambio de tu contraseña.</p>"
                    + "<p>Da clic al link de abajo para cambiar tu contraseña:</p>"
                    + "<p><a href=\"" + link + "\">Cambiar contraseña</a></p>"
                    + "<br>"
                    + "<p>Ignora este email si recuerdas tu contraseña, "
                    + "o si no solicitaste cambiar tu contraseña.</p>";

            helper.setSubject(subject);

            helper.setText(content, true);

            mailSender.send(message);
        }
 
    
     
     
    @GetMapping("/reset_password")

    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Usuario customer = usuarioEmailService.getByResetPasswordToken(token);
        model.addAttribute("token", token);
            //validacion del token  
    jwtExceptionSeguridad
        //Se llama la variable tipo jwtRedisDTO donde se almaceno el token y el id 
        .validarToken(token);


        if (customer == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
     
                return "reset_password_form";
            }
    
    
    @PostMapping("/reset_password")

    public String processResetPassword(HttpServletRequest request, Model model) {
    String token = request.getParameter("token");
    String password = request.getParameter("password");
     
    Usuario customer = usuarioEmailService.getByResetPasswordToken(token);
    model.addAttribute("title", "Cambiar tu contraseña");
     
    if (customer == null) {
        model.addAttribute("message", "Invalid Token");
        return "message";
    } else {           
        usuarioEmailService.updatePassword(customer, password);
         
        model.addAttribute("message", "Se ha cambiado tu contraseña correctamente.");
    }
     
            return "index";
        }
    
    
}
