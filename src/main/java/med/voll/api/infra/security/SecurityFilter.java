package med.voll.api.infra.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

	private static final Map<String, String> PERMITTED_PATHS = new HashMap<>();

	static {
		PERMITTED_PATHS.put("/login", "POST");
		PERMITTED_PATHS.put("/swagger-ui.html", "GET");
		PERMITTED_PATHS.put("/v3/api-docs/**", "GET");
		PERMITTED_PATHS.put("/swagger-ui/**", "GET");
		PERMITTED_PATHS.put("/favicon.ico", "GET");
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain)
			throws ServletException, IOException {

		if (isPermittedRequest(request)) {
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

	private boolean isPermittedRequest(HttpServletRequest request) {
		String path = request.getRequestURI();
		String method = request.getMethod();

		// Check if the path is permitted and if the method matches
		return PERMITTED_PATHS.entrySet().stream()
				.anyMatch(entry -> entry.getKey().endsWith("/**")
						? path.startsWith(entry.getKey().substring(0, entry.getKey().length() - 3))
						: path.equals(entry.getKey()) && method.equalsIgnoreCase(entry.getValue()));
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
