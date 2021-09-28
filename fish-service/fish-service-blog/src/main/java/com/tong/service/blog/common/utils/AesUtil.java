package com.tong.service.blog.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

/**
 * AES-128-CBC 加密方式 注： AES-128-CBC可以自己定义“密钥”和“偏移量“。 AES-128是jdk自动生成的“密钥”。
 */
@Slf4j
public class AesUtil {

    /**
     * AES解密
     *
     * @param data     密文，被加密的数据
     * @param key      秘钥
     * @param iv       偏移量
     * @param encoding 解密后的结果需要进行的编码，默认utf-8
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv, String encoding) throws Exception {

        // 被加密的数据
        byte[] dataByte = Base64.decodeBase64(data);
        // 加密秘钥
        byte[] keyByte = Base64.decodeBase64(key);
        // 偏移量
        byte[] ivByte = Base64.decodeBase64(iv);

        try {
            // 如果密钥不足16位，那么就补足
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");

            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));

            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化

            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, StringUtils.isBlank(encoding) ? "UTF-8" : encoding);
                return result;
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidParameterSpecException | InvalidKeyException |
                InvalidAlgorithmParameterException | IllegalBlockSizeException |
                BadPaddingException | UnsupportedEncodingException e) {
            log.error("AES解密失败，请检查！参数：data：{}, key:{}, iv:{},encoding:{}", data, key, iv, encoding, e);
        }

        return null;
    }

}
