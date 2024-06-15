package med.voll.api.infra.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import med.voll.api.domain.user.User;

@Service
public class TokenService {
	@Value("${api.security.secret}")
	private String apiSecret;

	public String generateToken(User user) {
		try {
			// Define the current time and the expiration time (2 hours from now)
			Date now = new Date();
			Date expireAt = new Date(now.getTime() + 2 * 60 * 60 * 1000); // 2 hours in milliseconds
			System.out.println(apiSecret);
			Algorithm algorithm = Algorithm.HMAC256(apiSecret);
			return JWT.create()
					.withIssuer("Voll Med")
					.withSubject(user.getUsername())
					.withClaim("id", user.getId())
					.withExpiresAt(expireAt)
					.withIssuedAt(now)
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException();
		}
	}
}
