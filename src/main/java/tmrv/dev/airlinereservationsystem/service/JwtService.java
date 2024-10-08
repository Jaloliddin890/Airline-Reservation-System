package tmrv.dev.airlinereservationsystem.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tmrv.dev.airlinereservationsystem.domains.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtService {
    private final String secretKey = "d807f91afd4bd4cd9ae2ec263abe66e1a616779581dd572aa5bf4502e9eab4ad";

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name()) // Include user role or other claims if needed
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // Token valid for 24 hours
                .signWith(getSecretKey()) // Signing the token with the secret key
                .compact();
    }

    public boolean isValid(String token, UserDetails user) {
        String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String token, Function<Claims, T> resolver) {
        Claims claims = getAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims getAllClaims(String token) {
        // Use parserBuilder() to build the JWT parser and set the signing key.
        SecretKey key = getSecretKey(); // Retrieve the secret key

        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key) // Setting the signing key for verifying the JWT
                .build()
                .parseClaimsJws(token); // Parse the JWT claims

        return jws.getBody(); // Retrieve the claims from the JWT
    }

    private SecretKey getSecretKey() {
        // Decode the base64-encoded secret key into a SecretKey object
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes); // Generate the SecretKey using the decoded bytes
    }
}
