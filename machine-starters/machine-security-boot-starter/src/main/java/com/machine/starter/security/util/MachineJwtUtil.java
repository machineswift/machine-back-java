package com.machine.starter.security.util;

import com.machine.sdk.common.exception.iam.authentication.AuthTokenExpireException;
import com.machine.sdk.common.exception.iam.authentication.AuthTokenParsingException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class MachineJwtUtil {

    private final String secret;

    public MachineJwtUtil(String secret) {
        this.secret = secret;
    }

    /**
     * 生成JWT
     */
    public String generateToken(String username,
                                Map<String, Object> claims,
                                long expire) {
        if (null == KEY) {
            KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }

        String tokenId = claims.get("tokenId").toString();
        claims.remove("tokenId");

        JwtBuilder builder = Jwts.builder();
        Date now = new Date();
        // 生成token
        builder.id(tokenId)
                .claims(claims) //数据
                .issuer("machine") //签发者
                .subject(username) //主题
                .issuedAt(now) //签发时间
                .expiration(new Date(expire))
                .signWith(KEY); //签名方式
        builder.header().add("type", "JWT").add("alg", "HS512");
        return builder.compact();
    }


    /**
     * 解析JWT
     */
    public Claims getClaimsByToken(String token) {
        if (null == KEY) {
            KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }

        try {
            return Jwts.parser()
                    .setSigningKey(KEY)
                    //.verifyWith((SecretKey) KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            if (e instanceof SecurityException) {
                throw new AuthTokenExpireException("token认证失败");
            } else if (e instanceof ExpiredJwtException) {
                throw new AuthTokenExpireException("token已过期");
            }
            throw new AuthTokenParsingException("token解析失败");
        }
    }

    private Key KEY;
}