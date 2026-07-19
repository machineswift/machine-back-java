package com.machine.starter.security.util;

import com.machine.sdk.base.exception.iam.authentication.AuthTokenInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Map;

@Slf4j
public class MachineJwtUtil {

    private final NimbusJwtDecoder jwtDecoder;
    private final NimbusJwtEncoder jwtEncoder;

    public MachineJwtUtil(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        this.jwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build();
        this.jwtEncoder = NimbusJwtEncoder.withKeyPair(publicKey, privateKey)
                .algorithm(SignatureAlgorithm.RS256)
                .build();
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
            log.error("token解析失败", e);
            throw new AuthTokenInvalidException("token解析失败");
        }
    }
}