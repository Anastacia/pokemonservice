package com.service.pokemon.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.service.pokemon.utils.JwtUtils;

import io.jsonwebtoken.Claims;

public class AuthTokenFilter extends OncePerRequestFilter{

	@Value("${pokemon.app.jwtScret}")
	private String jwtSecret;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	private static final String HEADER_TOKEN = "Authorization";
	private static final String PREFIX_TOKEN = "Bearer ";
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if(checkJwtToken(request, response)) {
				Claims claims = jwtUtils.validateJwtToken(request);
				
				if(claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				}else {
					SecurityContextHolder.clearContext();
				}
			}else {
				SecurityContextHolder.clearContext();
			}
			
			filterChain.doFilter(request, response);
		}catch(Exception e) {
			logger.error("Cannot set user authentication ", e);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
		}
	}
	
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())) ;
		
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	private boolean checkJwtToken(HttpServletRequest request, HttpServletResponse response) {
		String authenticationHeader = request.getHeader(HEADER_TOKEN);
		if(authenticationHeader == null || !authenticationHeader.startsWith(PREFIX_TOKEN)) {
			return false;
		}
		
		return true;
	}
	
}
