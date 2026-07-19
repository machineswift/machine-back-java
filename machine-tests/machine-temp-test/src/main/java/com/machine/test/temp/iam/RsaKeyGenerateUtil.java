package com.machine.test.temp.iam;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import lombok.SneakyThrows;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.MessageFormat;
import java.util.Base64;

/**
 * RSA密钥对生成工具
 * 运行 main 方法即可生成 PEM 格式的公钥和私钥，直接复制粘贴到 application.yml 配置中
 */
public class RsaKeyGenerateUtil {

    @SneakyThrows
    static void main(String[] args) {
        // 1. 生成2048位RSA密钥对
        RSAKey jwk = new RSAKeyGenerator(2048).generate();
        RSAPublicKey publicKey = jwk.toRSAPublicKey();
        RSAPrivateKey privateKey = jwk.toRSAPrivateKey();

        // 2. 转换为 PEM 格式
        String publicKeyPem = toPem("PUBLIC KEY", publicKey.getEncoded());
        String privateKeyPem = toPem("PRIVATE KEY", privateKey.getEncoded());

        // 3. 输出到控制台
        System.out.println("===== 复制以下内容到 application.yml =====");
        System.out.println();
        System.out.println("machine:");
        System.out.println("  jwt:");
        System.out.println("    rsa:");
        System.out.println("      public-key: |");
        for (String line : publicKeyPem.split("\n")) {
            System.out.println("        " + line);
        }
        System.out.println("      private-key: |");
        for (String line : privateKeyPem.split("\n")) {
            System.out.println("        " + line);
        }
        System.out.println();
        System.out.println("===== 公钥 =====");
        System.out.println(publicKeyPem);
        System.out.println("===== 私钥(请勿泄露) =====");
        System.out.println(privateKeyPem);
    }

    private static String toPem(String type, byte[] encoded) {
        String base64 = Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(encoded);
        return MessageFormat.format("-----BEGIN {0}-----\n{1}\n-----END {0}-----", type, base64);
    }
}
