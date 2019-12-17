package com.tlongx.sorder.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Http 工具类
 * <p>
 * 2018/08/09
 */
public class HttpClient {
    private static HttpClient instance = null;

    private HttpClient() {
    }

    public static HttpClient getInstance() {
        if (instance == null) {
            synchronized (HttpClient.class) {
                if (instance == null) {
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url        发送请求的 URL
     * @param jsonData   请求Json报文
     * @param checkValue 签名后字符串
     * @param traceNo    请求跟踪号
     * @return 所代表远程资源的响应结果
     */
    public String send(String url, String jsonData, String checkValue, String traceNo) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type" , "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);


            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), Constant.CHARSET_UTF8), Boolean.FALSE);
            String httpParameter = buildHttpParameter(jsonData, checkValue, traceNo);
            System.out.println("httpParameter:" + httpParameter);
            out.write(httpParameter);
            out.flush();

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), Constant.CHARSET_UTF8));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    private String buildHttpParameter(String jsonData, String checkValue, String traceNo) {
        StringBuilder requestParameter = new StringBuilder();
        try {
            requestParameter.append("jsonData=").append(URLEncoder.encode(jsonData, Constant.CHARSET_UTF8)).append("&")
                    .append("checkValue=").append(URLEncoder.encode(checkValue, Constant.CHARSET_UTF8));
            if (traceNo != null && traceNo.length() > 0) {
                requestParameter.append("&").append("traceNo=").append(URLEncoder.encode(traceNo, Constant.CHARSET_UTF8));
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
        return requestParameter.toString();
    }
}
