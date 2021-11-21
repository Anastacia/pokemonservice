package com.service.pokemon.utils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.service.pokemon.service.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	@Value("${pokemon.app.jwtScret}")
	private String jwtSecret;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	private static final String HEADER_TOKEN = "Authorization";
	private static final String PREFIX_TOKEN = "Bearer ";
	
	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		
		List<GrantedAuthority> grantedAuthority = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		return Jwts.builder().setSubject(user.getUsername())
				.claim("authorities", grantedAuthority.stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (60000 * 20)))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}
	
	public String getUsernameFromJwtToken(String token) {
		logger.info("Este es mi token " + token);
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public Claims validateJwtToken(HttpServletRequest request) {
		String token = request.getHeader(HEADER_TOKEN).replace(PREFIX_TOKEN, "");
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}
}
