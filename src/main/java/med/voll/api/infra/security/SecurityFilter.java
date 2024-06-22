package med.voll.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.user.UserRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {

		if (isLoginRequest(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = getTokenFromHeaders(request);
		if (token == null) {
			throw new RuntimeException("Authorization token is missing.");
		}

		authenticateUser(token);
		filterChain.doFilter(request, response);
	}

	private boolean isLoginRequest(HttpServletRequest request) {
		String path = request.getRequestURI();
		return "/login".equals(path) && "POST".equalsIgnoreCase(request.getMethod());
	}

	private String getTokenFromHeaders(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || authHeader.isEmpty()) {
			return null;
		}
		return authHeader.replace("Bearer ", "");
	}

	private void authenticateUser(String token) {
		String username = tokenService.getSubject(token);
		if (username != null) {
			UserDetails user = userRepository.findByUsername(username);
			var authentication = new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	}
}
