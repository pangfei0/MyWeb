package juli.infrastructure;



import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Pang fei on 2015/11/23.
 */
public class AesUtils {
    /**
     * 获取密钥
     *
     * @param password 加密密码
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static SecretKeySpec getKey(String password)
            throws NoSuchAlgorithmException
    {
        // 密钥加密器生成器
//        KeyGenerator kgen = KeyGenerator.getInstance("AES");
//        kgen.init(128, new SecureRandom(password.getBytes()));
//
//        // 创建加密器
//        SecretKey secretKey = kgen.generateKey();
//        byte[] enCodeFormat = secretKey.getEncoded();
        byte[] enCodeFormat = password.getBytes();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");

        return key;
    }
    /**
     * AES加密后进行base64 编码
     * @param content 带加密信息
     * @param key 密钥
     * @return 加密后信息
     */
    public static String  aesEncrypt(String content,String key){
        String str="";
        try {
            str= base64Encode(aesEncryptToBytes(content,key));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
    /**
     * 解密后的数据AES解密
     * @param content
     * @param key
     * @return
     */
    public static String aesDecoder(String content,String key)
    {

        try {
            return decode(base64Decoder(content), key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String  base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }
    /**
     *
     * @param conetent 待解码的content
     * @return 解码后的AES字节码
     * @throws IOException
     */
    public static byte[]  base64Decoder(String conetent) throws IOException
    {
        return Base64.decodeBase64(conetent);
    }
    /**
     * AES加密
     * @param content 待加密的内容
     * @param key 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public  static byte[] aesEncryptToBytes(String content,String key) throws Exception {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
//            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, getKey(key));// 初始化
            byte[] result = cipher.doFinal(byteContent);

            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * AES解密
     * @param arr 密文数组
     * @param password 加密密码
     * @return
     */
    private static String decode(byte[] arr, String password)
    {
        try
        {
            // 创建密码器
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getKey(password));// 初始化

            byte[] result = cipher.doFinal(arr);
            return new String(result, "utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
