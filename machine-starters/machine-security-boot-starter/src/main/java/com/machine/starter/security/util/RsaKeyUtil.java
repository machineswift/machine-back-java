package com.machine.starter.security.util;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.StringReader;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA密钥工具类，用于解析PEM格式的RSA公钥/私钥
 */
public class RsaKeyUtil {

    /**
     * 从PEM格式字符串解析RSA公钥
     */
    public static RSAPublicKey parsePublicKey(String pem) {
        try (PEMParser parser = new PEMParser(new StringReader(pem))) {
            Object object = parser.readObject();
            SubjectPublicKeyInfo publicKeyInfo = null;
            if (object instanceof SubjectPublicKeyInfo) {
                publicKeyInfo = (SubjectPublicKeyInfo) object;
            } else if (object instanceof PEMKeyPair) {
                publicKeyInfo = ((PEMKeyPair) object).getPublicKeyInfo();
            } else {
                throw new IllegalArgumentException("无法解析PEM公钥，类型: " + object.getClass().getName());
            }
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PublicKey publicKey = converter.getPublicKey(publicKeyInfo);
            if (publicKey instanceof RSAPublicKey) {
                return (RSAPublicKey) publicKey;
            }
            throw new IllegalArgumentException("PEM公钥不是RSA类型");
        } catch (Exception e) {
            throw new RuntimeException("解析RSA公钥失败", e);
        }
    }

    /**
     * 从PEM格式字符串解析RSA私钥
     */
    public static RSAPrivateKey parsePrivateKey(String pem) {
        try (PEMParser parser = new PEMParser(new StringReader(pem))) {
            Object object = parser.readObject();
            PrivateKeyInfo privateKeyInfo;
            if (object instanceof PrivateKeyInfo) {
                privateKeyInfo = (PrivateKeyInfo) object;
            } else if (object instanceof PEMKeyPair) {
                privateKeyInfo = ((PEMKeyPair) object).getPrivateKeyInfo();
            } else {
                throw new IllegalArgumentException("无法解析PEM私钥，类型: " + object.getClass().getName());
            }
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKey privateKey = converter.getPrivateKey(privateKeyInfo);
            if (privateKey instanceof RSAPrivateKey) {
                return (RSAPrivateKey) privateKey;
            }
            throw new IllegalArgumentException("PEM私钥不是RSA类型");
        } catch (Exception e) {
            throw new RuntimeException("解析RSA私钥失败", e);
        }
    }
}
