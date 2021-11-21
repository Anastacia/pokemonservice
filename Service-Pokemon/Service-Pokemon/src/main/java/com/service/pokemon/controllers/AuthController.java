package com.service.pokemon.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.pokemon.models.User;
import com.service.pokemon.repository.UserRepository;
import com.service.pokemon.utils.JwtUtils;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils utils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@Valid @RequestBody User user, BindingResult result){
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = utils.generateJwtToken(authentication);
		
		Map<String, Object> response = new HashMap<>();
		response.put("token", token);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User userRequest, BindingResult result){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		if(userRepository.existsByUsername(userRequest.getUsername())) {
			return ResponseEntity.badRequest().body("Error: Username already exist!");
		}
		
		User user = new User(userRequest.getUsername(), encoder.encode(userRequest.getPassword()));
		userRepository.save(user);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	private ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "Field " + err.getField() + " " + err.getDefaultMessage());
		});
		
		return ResponseEntity.badRequest().body(errores);
	}
}
