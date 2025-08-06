
package com.openclassrooms.mddapi.util;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.sql.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret; // Clé secrète pour signer le token

    @Value("${jwt.expiration}")
    private Long expiration; // Durée de vie du token (ex: 24h)

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes()); // Convertit le secret en clé crypto
    }

    // Créer un token à la connexion
    public String generateToken(String email, Long userId) {
        return Jwts.builder()
                .setSubject(email) // Le "propriétaire" du token
                .claim("userId", userId) // Données custom (userId)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Date de création
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Date
                                                                                  // d'expiration
                .signWith(secretKey(), SignatureAlgorithm.HS256) // Signature sécurisée
                .compact(); // Génère le token string
    }

    // Lire les données du token
    public String readToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey()) // Donner la clé secrète pour vérifier
                .build()
                .parseClaimsJws(token) // Lire et vérifier le token
                .getBody()
                .getSubject(); // Récupérer le sujet (propriétaire du token = email de
                               // l'utilisateur)
    }

}
