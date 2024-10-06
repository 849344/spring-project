package spring.security.service;

import java.io.Serializable;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTUtilService {

	private String secretKey = "";
	
	public JWTUtilService() {
		try {
			KeyGenerator keyGen= KeyGenerator.getInstance("hmacSHA256");
			SecretKey sk = keyGen.generateKey();
			//Encoding to convert it to string
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String generateToken(String username) {
		
		Map<String, Object> claims = new HashMap<>();
		
		return Jwts.builder()
				   .claims()
				   .add(claims)
				   .subject(username)
				   .issuedAt(new Date(System.currentTimeMillis()))
				   .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
				   .and()
				   .signWith(getKey())
				   .compact();
	}
	
	/*
	 * io.jsonwebtoken.security.WeakKeyException:
	 *  The specified key byte array is 48 bits which is not secure enough for any JWT HMAC-SHA algorithm.  
	 *  The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms 
	 *  MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size).  
	 *  Consider using the Jwts.SIG.HS256.key() builder (or HS384.key() or HS512.key()) to create a key 
	 *  guaranteed to be secure enough for your preferred HMAC-SHA algorithm.  See https://tools.ietf.org/html/rfc7518#section-3.2 
	 *  for more information.
	 */


    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

	
}
