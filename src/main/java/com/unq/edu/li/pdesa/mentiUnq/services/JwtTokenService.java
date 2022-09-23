package com.unq.edu.li.pdesa.mentiUnq.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenService {

	//@Value("${jwt.secret:373avlugbvu5bra0mon0t}")
	private String secret;
	private final Algorithm hmac256;
	private final JWTVerifier verifier;
	private final static Long JWT_TOKEN_VALIDITY = 60*8*60000l;
	private final Map<String, Object> header;

	public JwtTokenService() {
		header = new HashMap();
		header.put("alg", "HS256");
		header.put("typ", "JWT");
		this.hmac256 = Algorithm.HMAC256("G5*Nls/B/-yi82nB5Jtytbj2jajsjj2jDecCfTjURDFjIDESEu==(.");
		this.verifier = JWT.require(this.hmac256).withIssuer("OAUTH").build();
	}

	public String generateToken(final UserDetails userDetails) {
		return JWT.create()
				.withHeader(header)
				.withSubject(userDetails.getUsername())
				.withClaim("email", userDetails.getUsername())
				.withClaim("roles", Arrays.asList("USER"))
				.withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.withIssuer("OAUTH")
				.sign(this.hmac256);
	}

	public String validateTokenAndGetUsername(final String token) {
		try {
			return verifier.verify(token).getSubject();
		} catch (final JWTVerificationException verificationEx) {
			return null;
		}
	}


}
