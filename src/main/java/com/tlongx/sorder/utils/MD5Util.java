package com.tlongx.sorder.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util
{
  private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", 
    "e", "f" };

  private static String byteArrayToHexString(byte[] b)
  {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; ++i) {
      resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString();
  }

  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0){
      n += 256;}
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2]; }

  public static String MD5Encode1(String origin, String charsetname) {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      if ((charsetname == null) || ("".equals(charsetname))) {
        resultString = byteArrayToHexString(md.digest(resultString
          .getBytes())); 
      }
      resultString = byteArrayToHexString(md.digest(resultString
        .getBytes(charsetname)));
    } catch (Exception localException) {
    }
    label62: return resultString;
  }

  // 可逆的加密算法  
  public static String KL(String inStr) {  
      // String s = new String(inStr);  
      char[] a = inStr.toCharArray();  
      for (int i = 0; i < a.length; i++) {  
          a[i] = (char) (a[i] ^ 't');  
      }  
      String s = new String(a);  
      return s;  
  }  

  public static String JM(String inStr)
  {
    char[] a = inStr.toCharArray();
    for (int i = 0; i < a.length; ++i) {
      a[i] = (char)(a[i] ^ 0x74);
    }
    String k = new String(a);
    return k;
  }

  //32位小写
  public static String encryption(String plain) { 
    String re_md5 = ""; 
    try { 
      if(plain!=null&&plain!=""){
        MessageDigest md = MessageDigest.getInstance("MD5"); 
            md.update(plain.getBytes()); 
          byte b[] = md.digest();  
          int i; 
          StringBuffer buf = new StringBuffer(""); 
          for (int offset = 0; offset < b.length; offset++) { 
              i = b[offset]; 
          if (i < 0) {
            i += 256;
          }
          if (i < 16) {
            buf.append("0");
          }
            buf.append(Integer.toHexString(i)); 
        } 
              re_md5 = buf.toString(); 
       
      }
    } catch (NoSuchAlgorithmException e) { 
        e.printStackTrace(); 
       } 
    return re_md5; 
    } 

  private static String MD5Encode(String origin, String charsetname)
  {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      if ((charsetname == null) || ("".equals(charsetname))) {
        resultString = byteArrayToHexString(md.digest(resultString.getBytes())); 
      }
      resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
    } catch (Exception localException) {
    }
    label62: return resultString.toUpperCase();
  }

  public static String MD5EncodeUtf8(String origin) {
    origin = origin + PropertiesUtil.getProperty("pwd.salt");
    return MD5Encode1(origin, "utf-8");
  }
}