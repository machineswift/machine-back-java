package com.machine.starter.security.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import java.time.Instant;
import java.util.Map;

@Slf4j
public class MachineJwtUtil {

    private final JwtDecoder jwtDecoder;
    private final JwtEncoder jwtEncoder;

    public MachineJwtUtil(JwtDecoder jwtDecoder,
                          JwtEncoder jwtEncoder) {
       this.jwtDecoder=jwtDecoder;
       this.jwtEncoder=jwtEncoder;
    }

    /**
     * 生成JWT
     */
    public String generateToken(String username,
                                Map<String, Object> claims,
                                long expire) {
        String tokenId = claims.get("tokenId").toString();

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("machine")
                .subject(username)
                .issuedAt(Instant.now())
                .expiresAt(Instant.ofEpochMilli(expire))
                .id(tokenId);

        // 设置自定义claims（跳过已处理的tokenId）
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            if (!"tokenId".equals(entry.getKey())) {
                claimsBuilder.claim(entry.getKey(), entry.getValue());
            }
        }

        JwsHeader jwsHeader = JwsHeader.with(SignatureAlgorithm.RS256)
                .type("JWT")
                .build();

        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsBuilder.build()));
        return jwt.getTokenValue();
    }

    /**
     * 解析JWT，直接返回 Spring Security 原生 {@link Jwt} 对象
     */
    public Jwt getClaimsByToken(String token) {
        try {
            return jwtDecoder.decode(token);
        } catch (JwtException e) {
            log.error("登录凭证解析失败", e);
            throw new BadCredentialsException("登录凭证解析失败");
        }
    }
}