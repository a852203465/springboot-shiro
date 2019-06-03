package com.unionman.shiro.common.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.unionman.shiro.common.constants.CommonConstant;
import com.unionman.shiro.common.constants.EncryptConstant;
import com.unionman.shiro.common.constants.NumberConstant;
import com.unionman.shiro.common.enums.ResponseEnum;
import com.unionman.shiro.common.exception.CustomUnauthorizedException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * @descritpion: 加密/解密工具类
 * @author Rong.Jia
 * @date 2019/04/17 08:35:22
 */
public class EncryptUtils {

    public static EncryptUtils me;

    private EncryptUtils() {
        //单例
    }

    /**
     * @description: 双重锁
     * @date 2019/04/22 14:22:22
     * @return EncryptUtils
     */
    public static EncryptUtils getInstance() {
        if (me == null) {
            synchronized (EncryptUtils.class) {
                if (me == null) {
                    me = new EncryptUtils();
                }
            }
        }
        return me;
    }

    /**
     * 使用MessageDigest进行单向加密（无密码）
     * @param res       被加密的文本
     * @param algorithm 加密算法名称
     * @return String 加密后的字符串
     */
    private static String messageDigest(String res, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] resBytes = res.getBytes(CommonConstant.PROJECT_ENCODING_UTF8);
            return base64(md.digest(resBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用KeyGenerator进行单向/双向加密（可设密码）
     * @param res       被加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密使用的秘钥
     * @return
     */
    private static String keyGeneratorMac(String res, String algorithm, String key) {
        try {
            SecretKey sk = null;
            if (key == null) {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                sk = kg.generateKey();
            } else {
                byte[] keyBytes = key.getBytes(CommonConstant.PROJECT_ENCODING_UTF8);
                sk = new SecretKeySpec(keyBytes, algorithm);
            }
            Mac mac = Mac.getInstance(algorithm);
            mac.init(sk);
            byte[] result = mac.doFinal(res.getBytes());
            return base64(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用KeyGenerator双向加密，DES/AES，注意这里转化为字符串的时候是将2进制转为16进制格式的字符串，不是直接转，因为会出错
     *
     * @param res       加密的原文
     * @param algorithm 加密使用的算法名称
     * @param key       加密的秘钥
     * @param keysize
     * @param isEncode
     * @return
     */
    private static String keyGeneratorES(String res, String algorithm, String key, int keysize, boolean isEncode) {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            if (keysize == 0) {
                byte[] keyBytes = key.getBytes(CommonConstant.PROJECT_ENCODING_UTF8);
                kg.init(new SecureRandom(keyBytes));
            } else if (key == null) {
                kg.init(keysize);
            } else {
                byte[] keyBytes = key.getBytes(CommonConstant.PROJECT_ENCODING_UTF8);
                kg.init(keysize, new SecureRandom(keyBytes));
            }
            SecretKey sk = kg.generateKey();
            SecretKeySpec sks = new SecretKeySpec(sk.getEncoded(), algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            if (isEncode) {
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                byte[] resBytes = res.getBytes(CommonConstant.PROJECT_ENCODING_UTF8);
                return parseByte2HexStr(cipher.doFinal(resBytes));
            } else {
                cipher.init(Cipher.DECRYPT_MODE, sks);
                return new String(cipher.doFinal(parseHexStr2Byte(res)));
            }
        } catch (Exception e) {
            throw new CustomUnauthorizedException(ResponseEnum.ENCRYPTION_OR_DECRYPTION_FAILED);
        }
    }

    private static String base64(byte[] res) {
        return Base64.encode(res);
    }

    /**
     * 将二进制转换成16进制
     */
    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     */
    public static byte[] parseHexStr2Byte(String hexStr) {

        if (hexStr.length() < 1) {
            return null;
        }

        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / NumberConstant.TWO; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), NumberConstant.SIXTEEN);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), NumberConstant.SIXTEEN);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @return
     */
    public static String md5(String res) {
        return messageDigest(res, EncryptConstant.MD5);
    }

    /**
     * md5加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public static String md5(String res, String key) {
        return keyGeneratorMac(res, EncryptConstant.HMAC_MD5, key);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @return
     */
    public static String sha1(String res) {
        return messageDigest(res, EncryptConstant.SHA1);
    }

    /**
     * 使用SHA1加密算法进行加密（不可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public static String sha1(String res, String key) {
        return keyGeneratorMac(res, EncryptConstant.HMAC_SHA1, key);
    }

    /**
     * 使用DES加密算法进行加密（可逆）
     *
     * @param res 需要加密的原文
     * @param key 秘钥
     * @return
     */
    public static String encodeDES(String res, String key) {
        return keyGeneratorES(res, EncryptConstant.DES, key, NumberConstant.ZERO, true);
    }

    /**
     * 对使用DES加密算法的密文进行解密（可逆）
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public static String decodeDES(String res, String key) {
        return keyGeneratorES(res, EncryptConstant.DES, key, NumberConstant.ZERO, false);
    }

    /**
     * 使用AES加密算法经行加密（可逆）
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return
     */
    public static String encodeAES(String res, String key) {
        return keyGeneratorES(res, EncryptConstant.AES, key, EncryptConstant.KEYSIZE_AES, true);
    }

    /**
     * 对使用AES加密算法的密文进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public static String decodeAES(String res, String key) {
        return keyGeneratorES(res, EncryptConstant.AES, key, EncryptConstant.KEYSIZE_AES, false);
    }

    /**
     * 使用异或进行加密
     *
     * @param res 需要加密的密文
     * @param key 秘钥
     * @return
     */
    public static String encodeXOR(String res, String key) {
        byte[] bs = res.getBytes();
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return parseByte2HexStr(bs);
    }

    /**
     * 使用异或进行解密
     *
     * @param res 需要解密的密文
     * @param key 秘钥
     * @return
     */
    public static String decodeXOR(String res, String key) {
        byte[] bs = parseHexStr2Byte(res);
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) ((bs[i]) ^ key.hashCode());
        }
        return new String(bs);
    }

    /**
     * 直接使用异或（第一调用加密，第二次调用解密）
     *
     * @param res 密文
     * @param key 秘钥
     * @return
     */
    public static int xor(int res, String key) {
        return res ^ key.hashCode();
    }

    /**
     * 使用Base64进行加密
     *
     * @param res 密文
     * @return
     */
    public static String encodeBase64(String res) {
        return Base64.encode(res.getBytes());
    }

    /**
     * 使用Base64进行解密
     *
     * @param res
     * @return
     */
    public static String decodeBase64(String res) {
        return new String(Base64.decode(res));
    }




}
