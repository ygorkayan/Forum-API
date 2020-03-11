package br.com.alura.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.LoginForm;

/**
 * AutenticacaoController
 */
@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody LoginForm loginForm) {
        UsernamePasswordAuthenticationToken dadosLogin = loginForm.converte();

        try {
            Authentication auth = authManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(auth);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }

}