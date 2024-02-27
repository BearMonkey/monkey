package org.monkey.oauth.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    public static final long DEFAULT_EXPIRE = 1000 * 60 * 20;
    public static final String DEFAULT_SECRET =  "3tgYDnO0RvuCp+nns+O/VEYHs4uK7TfqkTw4ttOJJYk=";// Encoders.BASE64.encode(SECRET.getEncoded());



    /**
     * 生成token
     *
     * @param claims claims
     * @param expire 过期时间
     * @return
     */
    public static String getJwt(Map<String, Object> claims, long expire, String  secret) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        JwtBuilder builder = Jwts.builder();
        return builder.claims(claims)
                .signWith(secretKey, Jwts.SIG.HS256)
                .expiration(new Date(System.currentTimeMillis() + expire))
                .compact();
    }

    public static String getJwt(Map<String, Object> cliams) {
        return getJwt(cliams, DEFAULT_EXPIRE, DEFAULT_SECRET);
    }

    public static Claims parseJwt(String token, String secret) {
        /*Jwt<?,?> jwt;
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        try {
            jwt = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parse(token);
            return jwt.getBody();
        } catch (JwtException e) {
            e.printStackTrace();
            return null;
        }*/
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);
        return claimsJws.getPayload();
    }

    public static Claims parseJwt(String token) {
        return parseJwt(token, DEFAULT_SECRET);
    }

    public static void main(String[] args) {
        /*SecretKey key = Jwts.SIG.HS256.key().build();
        String secretString = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(secretString);*/
        Map<String, Object> map = new HashMap<>();
        map.put("userName", "monkey");
        System.out.println(JwtUtil.getJwt(map));
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6Im1vbmtleSIsImV4cCI6MTcwMDM4MjUxN30.v-7v-R8QkX7uDICOUzFCfV_7OvqwQU0_W4VUY69PoV4";

        Claims claims = JwtUtil.parseJwt(token);
        System.out.println(claims.get("userName"));


    }
}
