package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.JwtUtils;
import com.example.demo.service.to.UsuarioTO;

@RestController
@RequestMapping("/tokens")
@CrossOrigin
public class TokenControllerResFul {

	private static final Logger LOG = LoggerFactory.getLogger(TokenControllerResFul.class);
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/obtener")
	private String getToken(@RequestBody UsuarioTO usuarioTO) {
		this.authenticated(usuarioTO.getUsername(), usuarioTO.getPassword());
		LOG.info("Semilla: " + usuarioTO.getSemilla() + " Tiempo: " + usuarioTO.getTiempo());
		return this.jwtUtils.generateJwtToken(usuarioTO.getUsername(), usuarioTO.getSemilla(), usuarioTO.getTiempo());
	}

	private void authenticated(String username, String password) {
		Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
