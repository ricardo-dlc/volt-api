package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.user.User;
import med.voll.api.domain.user.UserLoginData;
import med.voll.api.infra.security.JWTTokenData;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<JWTTokenData> authenticateUser(@RequestBody @Valid UserLoginData userLoginData) {
		Authentication authtoken = new UsernamePasswordAuthenticationToken(userLoginData.username(),
				userLoginData.password());
		Authentication authenticatedUser = authenticationManager.authenticate(authtoken);
		String jwtToken = tokenService.generateToken((User) authenticatedUser.getPrincipal());
		return ResponseEntity.ok(new JWTTokenData(jwtToken));

	}
}
