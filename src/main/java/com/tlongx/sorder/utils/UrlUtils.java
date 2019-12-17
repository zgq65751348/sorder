package com.tlongx.sorder.utils;



/**
 * <p>
 * <title></title>
 * </p>
 * <p>
 * windows 7  旗舰版
 *
 * @author 雅诗兰黛
 * @ClassName: UrlUtils
 * @create <h>巴黎欧莱雅，你值得拥有</h>
 * @since 2019/12/13 0013 下午 3:56
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlUtils {
    /**
     * 获取url网址返回的数据内容
     * @param urlStr
     * @return
     */
    public static String loadURL(String urlStr){
        try{
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            return  ConvertToString(inputStream);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
    private static String ConvertToString(InputStream inputStream){
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder result = new StringBuilder();
        String line = null;
        try {
            while((line = bufferedReader.readLine()) != null){
                result.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                inputStreamReader.close();
                inputStream.close();
                bufferedReader.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return result.toString();
    }

}
