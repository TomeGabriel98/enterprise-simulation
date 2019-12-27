package br.gov.sp.fatec.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.empresa.Empresa;
import br.gov.sp.fatec.projetofatecb.models.Usuario;
import br.gov.sp.fatec.view.View;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationManager auth;


    @JsonView(View.UsuarioResumo.class)
    @CrossOrigin
    @RequestMapping(path = "/logando", method = RequestMethod.POST)   
    public ResponseEntity<Empresa> login(@RequestBody Login login, HttpServletResponse response) throws JsonProcessingException {
        Authentication credentials = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        Empresa empresa = (Empresa) auth.authenticate(credentials).getPrincipal();
        response.setHeader("token", JwtUtils.generateToken(empresa));
        response.setHeader("Access-Control-Expose-Headers", "token");
        response.setHeader("Access-Control-Allow-Headers", 
        		"Authorization, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Expose-Headers");
        return new ResponseEntity<Empresa>(empresa,HttpStatus.OK);
    }


}
