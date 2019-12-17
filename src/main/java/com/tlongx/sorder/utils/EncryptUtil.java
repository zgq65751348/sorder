package com.tlongx.sorder.utils;

import com.tlongx.common.ErrorEnum;
import com.tlongx.common.exception.CodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.jasypt.util.password.StrongPasswordEncryptor;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

/**
 * 加密工具
 * 
 * @author dengjianjun
 *
 */
@Slf4j
public class EncryptUtil {

	/** RSA最大加密明文大小 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** RSA最大解密密文大小 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	private static final StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

	private static String encodeRules="xqq";


	/*
	 * 加密
	 * 1.构造密钥生成器
	 * 2.根据ecnodeRules规则初始化密钥生成器
	 * 3.产生密钥
	 * 4.创建和初始化密码器
	 * 5.内容加密
	 * 6.返回字符串
	 */
	public static String AESEncode(String content){
		try {
			//1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen=KeyGenerator.getInstance("AES");
			//2.根据ecnodeRules规则初始化密钥生成器
			//生成一个128位的随机源,根据传入的字节数组
			keygen.init(128, new SecureRandom(encodeRules.getBytes()));
			//3.产生原始对称密钥
			SecretKey original_key=keygen.generateKey();
			//4.获得原始对称密钥的字节数组
			byte [] raw=original_key.getEncoded();
			//5.根据字节数组生成AES密钥
			SecretKey key=new SecretKeySpec(raw, "AES");
			//6.根据指定算法AES自成密码器
			Cipher cipher=Cipher.getInstance("AES");
			//7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.ENCRYPT_MODE, key);
			//8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
			byte [] byte_encode=content.getBytes("utf-8");
			//9.根据密码器的初始化方式--加密：将数据加密
			byte [] byte_AES=cipher.doFinal(byte_encode);
			//10.将加密后的数据转换为字符串
			//这里用Base64Encoder中会找不到包
			//解决办法：
			//在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
			String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
			//11.将字符串返回
			return AES_encode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		//如果有错就返加nulll
		return null;
	}
	/*
	 * 解密
	 * 解密过程：
	 * 1.同加密1-4步
	 * 2.将加密后的字符串反纺成byte[]数组
	 * 3.将加密内容解密
	 */
	public static String AESDncode(String content){
		try {
			//1.构造密钥生成器，指定为AES算法,不区分大小写
			KeyGenerator keygen=KeyGenerator.getInstance("AES");
			//2.根据ecnodeRules规则初始化密钥生成器
			//生成一个128位的随机源,根据传入的字节数组
			keygen.init(128, new SecureRandom(encodeRules.getBytes()));
			//3.产生原始对称密钥
			SecretKey original_key=keygen.generateKey();
			//4.获得原始对称密钥的字节数组
			byte [] raw=original_key.getEncoded();
			//5.根据字节数组生成AES密钥
			SecretKey key=new SecretKeySpec(raw, "AES");
			//6.根据指定算法AES自成密码器
			Cipher cipher=Cipher.getInstance("AES");
			//7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
			cipher.init(Cipher.DECRYPT_MODE, key);
			//8.将加密并编码后的内容解码成字节数组
			byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
			/*
			 * 解密
			 */
			byte [] byte_decode=cipher.doFinal(byte_content);
			String AES_decode=new String(byte_decode,"utf-8");
			return AES_decode;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}

		//如果有错就返加nulll
		return null;
	}

	/**
	 * AES加密
	 * 
	 * @param content   待加密内容
	 * @param secretKey 密钥
	 * @param ivBytes   向量
	 * @return
	 */
	public static byte[] aesEncrypt(String content, byte[] secretKey, byte[] ivBytes) {
		if (ivBytes == null || ivBytes.length != 16) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivBytes);
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey, "AES"), iv);

			return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**
	 * AES解密
	 * 
	 * @param encryptBytes 加密内容
	 * @param secretKey    密钥
	 * @param ivBytes      向量
	 * @return
	 */
	public static String aesDecrypt(byte[] encryptBytes, byte[] secretKey, byte[] ivBytes) {
		if (ivBytes == null || ivBytes.length != 16) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}

		try {
			String encryptResultStr = parseByte2HexStr(encryptBytes);
			Cipher deCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(ivBytes);
			deCipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey, "AES"), iv);
			//解密
			byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);
			byte[] bytes = deCipher.doFinal(decryptFrom);
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**将二进制转换成16进制
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
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

	/**将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length()/2];
		for (int i = 0;i< hexStr.length()/2; i++) {
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * RSA签名
	 * @param content    待签名内容
	 * @param privateKey 私钥
	 * @return
	 */
	public static byte[] rsaSign(String content, byte[] privateKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initSign(priKey);
			signature.update(content.getBytes(StandardCharsets.UTF_8));

			return signature.sign();
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**
	 * RSA签名
	 * @param content    待签名内容
	 * @param privateKey 私钥
	 * @return
	 */
	public static byte[] rsa256Sign(String content, byte[] privateKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
			Signature signature = Signature.getInstance("SHA256WithRSA");
			signature.initSign(priKey);
			signature.update(content.getBytes(StandardCharsets.UTF_8));

			return signature.sign();
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**
	 * RSA验证签名
	 * 
	 * @param content   待验证内容
	 * @param signBytes 签名内容
	 * @param publicKey 公钥
	 * @return
	 */
	public static boolean rsaSignCheck(String content, byte[] signBytes, byte[] publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes(StandardCharsets.UTF_8));

			return signature.verify(signBytes);
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**
	 * RSA验证签名
	 * 
	 * @param content   待验证内容
	 * @param signBytes 签名内容
	 * @param publicKey 公钥
	 * @return
	 */
	public static boolean rsa256SignCheck(String content, byte[] signBytes, byte[] publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
			Signature signature = Signature.getInstance("SHA256WithRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes(StandardCharsets.UTF_8));

			return signature.verify(signBytes);
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**
	 * RSA加密
	 * @param content   待加密内容
	 * @param publicKey 公钥
	 * @return
	 */
	public static byte[] rsaEncrypt(String content, byte[] publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			byte[] data = content.getBytes(StandardCharsets.UTF_8);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}

			return out.toByteArray();
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**
	 * RSA解密
	 * 
	 * @param encryptedData 待解密内容
	 * @param privateKey    密钥
	 * @return
	 */
	public static String rsaDecrypt(byte[] encryptedData, byte[] privateKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, priKey);
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}

			byte[] decryptedData = out.toByteArray();
			return new String(decryptedData, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**
	 * HmacMD5
	 * 
	 * @param data
	 * @param secret
	 * @return
	 */
	public static byte[] hmac(String data, byte[] secret) {
		try {
			SecretKey secretKey = new SecretKeySpec(secret, "HmacMD5");
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**
	 * HmacMD5
	 * 
	 * @param data
	 * @param secret
	 * @param toLowerCase
	 * @return
	 */
	public static String hmacHex(String data, byte[] secret, boolean toLowerCase) {
		return new String(Hex.encodeHex(hmac(data, secret), toLowerCase));
	}

	/**
	 * HmacMD5
	 * 
	 * @param data
	 * @param secret
	 * @return
	 */
	public static String hmacHex(String data, byte[] secret) {
		return new String(Hex.encodeHexString(hmac(data, secret)));
	}

	/**
	 * MD5
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] md5(String data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			return digest.digest(data.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**
	 * MD5
	 * 
	 * @param data
	 * @param toLowerCase
	 * @return
	 */
	public static String md5Hex(String data, boolean toLowerCase) {
		return new String(Hex.encodeHex(md5(data), toLowerCase));
	}

	/**
	 * MD5
	 * 
	 * @param data
	 * @return
	 */
	public static String md5Hex(String data) {
		return new String(Hex.encodeHexString(md5(data)));
	}

	/**
	 * SHA256
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] sha256(String data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			return digest.digest(data.getBytes(StandardCharsets.UTF_8));
		} catch (Exception e) {
			throw new CodeException(ErrorEnum.ENCRYPT_ERROR);
		}
	}

	/**
	 * SHA256
	 * 
	 * @param data
	 * @param toLowerCase
	 * @return
	 */
	public static String sha256Hex(String data, boolean toLowerCase) {
		return new String(Hex.encodeHex(sha256(data), toLowerCase));
	}

	/**
	 * SHA256
	 * 
	 * @param data
	 * @return
	 */
	public static String sha256Hex(String data) {
		return new String(Hex.encodeHexString(sha256(data)));
	}

	/**
	 * UrlBase64处理
	 * 
	 * @param bytes
	 */
	public static String urlBase64AndTrim(byte[] bytes) {
		byte[] base64Bytes = Base64.getUrlEncoder().encode(bytes);
		int length = base64Bytes.length;
		if (base64Bytes[length - 1] == '=') {
			length--;
		}
		if (base64Bytes[length - 1] == '=') {
			length--;
		}
		return new String(base64Bytes, 0, length, StandardCharsets.UTF_8);
	}

	/**
	 * UrlBase64处理
	 * 
	 * @param content
	 * @return
	 */
	public static byte[] urlBase64(String content) {
		return Base64.getUrlDecoder().decode(content);
	}

	/**
	 * 登录态信息转换成Token字符串
	 * 
	 * @param dto       登录用户信息
	 * @param secretKey 密钥
	 * @param ivBytes   向量
	 * @return
	 */
//	public static String convertToken(LoginUsrInfo dto, byte[] secretKey, byte[] ivBytes) {
//		StringBuffer tokenContent = new StringBuffer(30);
//		tokenContent.append(dto.getTokenId()).append("|").append(dto.getUserId()).append("|").append(dto.getUserRole())
//				.append("|").append(dto.getDeviceId() != null ? dto.getDeviceId() : "").append("|")
//				.append(dto.getExpireDate().getTime());
//
//		byte[] encryptBytes = aesEncrypt(tokenContent.toString(), secretKey, ivBytes);
//		return urlBase64AndTrim(encryptBytes);
//	}

	/**
	 * Token字符串转换成登录信息
	 * 
	 * @param token     Token令牌
	 * @param secretKey 密钥
	 * @param ivBytes   向量
	 * @return
	 */
//	public static LoginUsrInfo convertToken(String token, byte[] secretKey, byte[] ivBytes) {
//		byte[] encryptBytes = urlBase64(token);
//		String tokenContent = aesDecrypt(encryptBytes, secretKey, ivBytes);
//
//		int startIndex = 0, endIndex = 0;
//		LoginUsrInfo info = new LoginUsrInfo();
//		// tokenId
//		endIndex = tokenContent.indexOf('|', startIndex);
//		info.setTokenId(Long.valueOf(tokenContent.substring(startIndex, endIndex)));
////
//		// usrid
//		startIndex = endIndex + 1;
//		endIndex = tokenContent.indexOf('|', startIndex);
//		info.setUserId(Long.valueOf(tokenContent.substring(startIndex, endIndex)));
////
////		// usrRole
//		startIndex = endIndex + 1;
//		endIndex = tokenContent.indexOf('|', startIndex);
//		int usrRoleOrd = Integer.parseInt(tokenContent.substring(startIndex, endIndex));
//		info.setUserRole(usrRoleOrd);
////
////		// deviceId
//		startIndex = endIndex + 1;
//		endIndex = tokenContent.indexOf('|', startIndex);
//		if (endIndex > startIndex) {
//			info.setDeviceId(tokenContent.substring(startIndex, endIndex));
//		}
//		// expireDate
//		startIndex = endIndex + 1;
//		log.info(tokenContent.substring(startIndex));
//		info.setExpireDate(new Date(Long.parseLong(tokenContent.substring(startIndex))));
//
//		return info;
//	}

	/**
	 * 生成32位随机UUID
	 * 
	 * @return
	 */
	public static String genId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 生成压缩22位随机UUID
	 * 
	 * @return
	 */
	public static String genCompId() {
		UUID uuid = UUID.randomUUID();
		byte[] byUuid = new byte[16];
		long least = uuid.getLeastSignificantBits();
		long most = uuid.getMostSignificantBits();
		long2bytes(most, byUuid, 0);
		long2bytes(least, byUuid, 8);
		byte[] compressUUID = Base64.getUrlEncoder().encode(byUuid);
		int end = compressUUID.length;
		if (compressUUID[compressUUID.length - 2] == '=') {
			end -= 2;
		} else if (compressUUID[compressUUID.length - 1] == '=') {
			end -= 1;
		}
		return new String(compressUUID, 0, end);
	}

	private static void long2bytes(long value, byte[] bytes, int offset) {
		for (int i = 7; i > -1; i--) {
			bytes[offset++] = (byte) ((value >> 8 * i) & 0xFF);
		}
	}

	/**
	 * 密码加密
	 * 
	 * @param password 明文密码
	 * @return 密文密码
	 */
	public static String encryptPassword(String password) {
		return passwordEncryptor.encryptPassword(password);
	}

	/**
	 * 验证密码
	 * 
	 * @param inputPassword     明文密码
	 * @param encryptedPassword 密文密码
	 * @return true：密码正确；false：密码错误
	 */
	public static boolean checkPassword(String inputPassword, String encryptedPassword) {
		return passwordEncryptor.checkPassword(inputPassword, encryptedPassword);
	}

}
