package br.edu.atitus.projeto.denguealerta.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.projeto.denguealerta.DTOs.SigninDTO;
import br.edu.atitus.projeto.denguealerta.DTOs.SignupDTO;
import br.edu.atitus.projeto.denguealerta.components.JwtUtils;
import br.edu.atitus.projeto.denguealerta.components.TipoUsuario;
import br.edu.atitus.projeto.denguealerta.entities.UsuarioEntity;
import br.edu.atitus.projeto.denguealerta.services.UsuarioService;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    
    private final AuthenticationConfiguration authConfig;
    
    public AuthController(UsuarioService usuarioService, AuthenticationConfiguration authConfig) {
    	super();
        this.usuarioService = usuarioService;
		this.authConfig = authConfig;
    }

    @PostMapping("/signup")
    public ResponseEntity<UsuarioEntity> signup(@RequestBody SignupDTO signup) throws Exception {
        UsuarioEntity novoUsuario = new UsuarioEntity();
        BeanUtils.copyProperties(signup, novoUsuario);
        novoUsuario.setTipo(TipoUsuario.Cidadao);
        this.usuarioService.save(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
    
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody SigninDTO signin) throws Exception {
    	authConfig.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(signin.getEmail(), signin.getSenha()));
    	String jwtToken = JwtUtils.generateTokenFromUsername(signin.getEmail());
    return ResponseEntity.ok(jwtToken);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception e) {
        String cleanMessage = e.getMessage().replaceAll("[\\r\\n]", "");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cleanMessage);
    }
}
