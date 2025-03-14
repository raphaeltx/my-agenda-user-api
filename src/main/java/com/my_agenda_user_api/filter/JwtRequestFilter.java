package com.my_agenda_user_api.filter;

import com.my_agenda_user_api.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

	private final JwtUtil jwtUtil;

	private final UserDetailsService userDetailsService;

	public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
		logger.info("JwtRequestFilter instantiated");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String jwtToken = extractJwtFromRequest(request);
		String username = null;

		if (jwtToken != null) {
			try {
				username = jwtUtil.extractUsername(jwtToken);
			}
			catch (IllegalArgumentException e) {
				logger.error("Unable to get JWT Token", e);
			}
			catch (ExpiredJwtException e) {
				logger.warn("JWT Token has expired", e);
			}
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if (jwtUtil.validateToken(jwtToken, userDetails.getUsername())) {
				setAuthenticationForUser(request, userDetails);
			}
		}
		chain.doFilter(request, response);
	}

	private String extractJwtFromRequest(HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader("Authorization");
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			return requestTokenHeader.substring(7);
		}
		else {
			logger.warn("JWT Token does not begin with Bearer String");
			return null;
		}
	}

	private void setAuthenticationForUser(HttpServletRequest request, UserDetails userDetails) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
				null, userDetails.getAuthorities());
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}

}