package br.com.rbarbioni.rbstore.security;

import br.com.rbarbioni.rbstore.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;

/**
 * Created by renan on 12/02/2017.
 */

@Service
public class JWTService {

    private final String secret;


    @Autowired
    public JWTService(@Value("${security.jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String encode(Customer account) throws JsonProcessingException {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder();
        Claims claims = Jwts.claims();
        claims.setId(account.getEmail());
        builder.setClaims(claims).signWith(signatureAlgorithm, signingKey);

        return Base64.getEncoder().encodeToString(builder.compact().getBytes());
    }

    public String decode(String token) throws IOException {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(new String(Base64.getDecoder().decode(token))).getBody();
        return claims.getId();
    }
}
