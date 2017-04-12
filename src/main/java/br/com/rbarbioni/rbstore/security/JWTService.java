package br.com.rbarbioni.rbstore.security;

import br.com.rbarbioni.rbstore.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Date;

/**
 * Created by renan on 12/02/2017.
 */

@Service
public class JWTService {

    private final String secret;

    private final Long expirationTime;

    private final ObjectMapper objectMapper;

    @Autowired
    public JWTService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration_time}") String expirationTime,
            ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.secret = secret;
        this.expirationTime = Long.valueOf(expirationTime) * 60 * 1000;
    }

    public String encode(Customer account) throws JsonProcessingException {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        JwtBuilder builder = Jwts.builder();
        Claims claims = Jwts.claims();
        claims.setId(account.getId().toString()).put("payload", this.objectMapper.writeValueAsString(account));
        builder.setClaims(claims).signWith(signatureAlgorithm, signingKey);

        if (expirationTime >= 0) {
            long expMillis = System.currentTimeMillis() + expirationTime;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return Base64.getEncoder().encodeToString(builder.compact().getBytes());
    }

    public Customer decode(String token) throws IOException {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(new String(Base64.getDecoder().decode(token))).getBody();
        return this.objectMapper.readValue(claims.get("payload").toString(), Customer.class);
    }
}
