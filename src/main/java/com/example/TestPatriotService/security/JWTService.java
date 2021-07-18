package com.example.TestPatriotService.security;

import javax.crypto.spec.SecretKeySpec;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import io.jsonwebtoken.*;


import java.security.KeyPair;
import java.util.Date;
import java.util.HashMap;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

/*
    Our simple static class that demonstrates how to create and decode JWTs.
 */

public class JWTService {
    private static Key KEY_TO_PARSE;

    // The secret key. This should be in a property file NOT under source
    // control and not hard coded in real life. We're putting it here for
    // simplicity.
    private static int HOUR_IN_MS = 3600000;

    //Sample method to construct a JWT
    public static String createJWT(String issuer, String subject) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
//You use the private key (keyPair.getPrivate()) to create a JWS and the public key (keyPair.getPublic()) to parse/verify a JWS.
        KeyPair keyPair = Keys.keyPairFor(signatureAlgorithm);
        KEY_TO_PARSE = keyPair.getPublic();

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        //new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        Key signingKey = keyPair.getPrivate();
        HashMap<String, Object> header = new HashMap<>();
        header.put("alg", "RS256");
        header.put("typ", "JWT");
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setHeader(header)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        long expMillis = nowMillis + HOUR_IN_MS;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) throws NullPointerException {

        //This line will throw an exception if it is not a signed JWS (as expected)
        if(jwt == null) throw new NullPointerException();
        Claims claims = Jwts.parser()
                .setSigningKey(KEY_TO_PARSE)
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

}
