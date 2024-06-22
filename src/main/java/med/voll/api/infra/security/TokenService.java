package med.voll.api.infra.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import med.voll.api.domain.user.User;

@Service
public class TokenService {
	@Value("${api.security.secret}")
	private String apiSecret;

	private final String ISSUER = "Voll Med";

	public String generateToken(User user) {
		try {
			// Define the current time and the expiration time (2 hours from now)
			Date now = new Date();
			Date expireAt = new Date(now.getTime() + 2 * 60 * 60 * 1000); // 2 hours in milliseconds
			System.out.println(apiSecret);
			Algorithm algorithm = Algorithm.HMAC256(apiSecret);
			return JWT.create()
					.withIssuer(ISSUER)
					.withSubject(user.getUsername())
					.withClaim("id", user.getId())
					.withExpiresAt(expireAt)
					.withIssuedAt(now)
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			System.out.println(exception.toString());
			throw new RuntimeException();
		}
	}

	public String getSubject(String token) {
		try {
			DecodedJWT jwt;
			Algorithm algorithm = Algorithm.HMAC256(apiSecret);
			JWTVerifier verifier = JWT.require(algorithm)
					.withIssuer(ISSUER)
					.build();

			jwt = verifier.verify(token);
			return jwt.getSubject();
		} catch (JWTVerificationException exception) {
			System.out.println(exception.toString());
			return null;
		}
	}
}
