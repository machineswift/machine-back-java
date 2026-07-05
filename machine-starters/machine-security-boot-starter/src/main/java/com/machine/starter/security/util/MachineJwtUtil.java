package com.machine.starter.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import com.machine.sdk.base.exception.iam.authentication.AuthTokenExpireException;
import com.machine.sdk.base.exception.iam.authentication.AuthTokenParsingException;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class MachineJwtUtil {

    private final byte[] secretKey;

    public MachineJwtUtil(String secret) {
        this.secretKey = secret.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 生成JWT
     */
    public String generateToken(String username,
                                Map<String, Object> claims,
                                long expire) {
        String tokenId = claims.get("tokenId").toString();
        claims.remove("tokenId");

        JWT jwt = JWT.create()
                .setHeader("type", "JWT")
                .setHeader("alg", "HS512")
                .setPayload("sub", username)
                .setPayload("iss", "machine")
                .setPayload("iat", DateUtil.date())
                .setPayload("exp", DateUtil.date(expire))
                .setPayload("jti", tokenId)
                .setKey(secretKey);

        // 设置自定义claims
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            jwt.setPayload(entry.getKey(), entry.getValue());
        }

        return jwt.sign();
    }


    /**
     * 解析JWT
     */
    public JwtClaims getClaimsByToken(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token);
            jwt.setKey(secretKey);

            // 验证过期时间
            JWTValidator.of(jwt).validateDate(DateUtil.date());

            // 验证签名
            if (!jwt.verify()) {
                throw new AuthTokenParsingException("token认证失败");
            }
            return new JwtClaims(jwt);
        } catch (ValidateException e) {
            throw new AuthTokenExpireException("token过期");
        } catch (Exception e) {
            throw new AuthTokenParsingException("token解析失败");
        }
    }

    /**
     * JWT Claims 包装类，兼容原 jjwt Claims 的常用方法
     */
    public static class JwtClaims {

        private final JWT jwt;

        JwtClaims(JWT jwt) {
            this.jwt = jwt;
        }

        public String getId() {
            return jwt.getPayload().getClaimsJson().getStr("jti");
        }

        public Object get(String key) {
            return jwt.getPayload().getClaimsJson().getObj(key);
        }

        public String getStr(String key) {
            return jwt.getPayload().getClaimsJson().getStr(key);
        }

        public String getSubject() {
            return jwt.getPayload().getClaimsJson().getStr("sub");
        }

        public Date getExpiration() {
            return jwt.getPayload().getClaimsJson().getDate("exp");
        }
    }
}