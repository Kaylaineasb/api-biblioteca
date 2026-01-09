package com.biblioteca.api_biblioteca.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import com.biblioteca.api_biblioteca.model.Usuario;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {
    private static final String SECRET_KEY = "4Z^XR2$B8z&F#9q*L5v@p!m7W+td3G%yJ6kN1sA0rE(u)H4xC-oV?bM_jI";

    public String gerarToken(Usuario usuario){
        long expiracaoEmMillis = 1000 * 60 * 60 * 24;

        return Jwts.builder()
                .setSubject(usuario.getUsuTxEmail())
                .claim("id", usuario.getUsuNrId())
                .claim("perfil", usuario.getUsuTxPerfil())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiracaoEmMillis))
                .signWith(getChaveAssinatura(),SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getChaveAssinatura(){
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getSubject(String tokenJWT){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(getChaveAssinatura())
                    .build()
                    .parseClaimsJws(tokenJWT)
                    .getBody()
                    .getSubject();
        }catch (Exception e){
            return null;
        }
    }
}
