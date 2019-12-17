package com.tlongx.sorder.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tlongx.common.ErrorEnum;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.order.pojo.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xin.rf
 * @date 2019/2/13 11:14
 * @Description 工具类
 **/
@Component
@Slf4j
public class MyUtil {

    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static void main(String[] args) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info(request.getRemoteHost()+":"+request.getRemotePort());
    }

    public static String getServerUrl(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        log.info(url);
        return url;
    }

    public static String getImageUrl(String imageUrl){
        return  getServerUrl()+"/file/oss"+imageUrl;
    }

    /**
     * 生成固定格式主键
     * @param prefix
     * @return
     */
    public static String createId(String prefix){
        return prefix+System.currentTimeMillis()+getcode();
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String createUUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    /**
     * 字符串转日期格式
     *
     * @param date
     * @param dateformat
     * @return
     * @throws ParseException
     */
    public static Date formDateFormat(String date, String dateformat)
            throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        Date d = dateFormat.parse(date);
        return d;
    }

    /**
     * 日期转字符串格式
     *
     * @param date
     * @param dateformat
     * @return
     */
    public static String formStrFormat(Date date, String dateformat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        String str = dateFormat.format(date);
        return str;
    }


    /**
     * 生成手机6位验证码
     *
     * @return
     */
    public static String getcode() {
        String validCode = "";
        Random random = new Random();
        for (int i = 0; i < 6; ++i) {
            validCode = validCode + random.nextInt(10);
        }
        return validCode;
    }

    /**
     * 发送手机验证码
     *
     * @param mobile
     * @param content
     * @return
     */
    public static String sendPhoneCode(String mobile, String content) {
        String result = "";
        String smsUrl = PropertiesUtil.getProperty("jc_sms_url");
        String smsAccount = PropertiesUtil.getProperty("jc_sms_account");
        String smsPwd = PropertiesUtil.getProperty("jc_sms_password");
        String password = null;

        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(smsUrl);
        client.getParams().setContentCharset("UTF-8");
        try {
            password = MD5Util.encryption(smsAccount + MD5Util.encryption(smsPwd));
            content = URLEncoder.encode(content, "UTF-8");
            Map<String, String> param = new HashMap<String,String>();
            param.put("content", content);
            param.put("username", smsAccount);
            param.put("password", password);
            param.put("mobile", mobile);
            for (Map.Entry<String,String> entry : param.entrySet()) {
                method.addParameter(entry.getKey(), entry.getValue());
            }
            method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
            method.setRequestHeader("Connection", "close");
            client.executeMethod(method);
            //result = method.getResponseBodyAsString();
            InputStream str = method.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(str));
            StringBuffer stringBuffer = new StringBuffer();
            while ((result = br.readLine())!=null){
                stringBuffer.append(result);
            }
            result = stringBuffer.toString();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
            if (client != null) {
               // ((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
                client.getHttpConnectionManager().closeIdleConnections(0);
                client = null;
            }
        }
        logger.info("短信验证码结果："+result);
        return result;
    }

    /**
     * 时间戳转为字符串 格式为yyyy-MM-dd HH:mm:ss
     *
     * @param longTime
     * @return
     */
    public static String longTimeToDate(String longTime) {
        Date date = new Date();
        date.setTime(Long.valueOf(longTime).longValue());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.format(date);
        return simpleDateFormat.format(date);
    }

    public static Map<String, Object> jsonToMap(String json)
            throws JSONException {
        Map retMap = new HashMap();

        if (json != null) {
            retMap = toMap(JSONObject.parseObject(json));
        }
        return retMap;
    }

    public static String strObject(Object object) {
        String result =null;
        if ((object != null) && (!("".equals(object)))) {
            result = object.toString();
        }
        return result;
    }


    public static List<Object> toList(JSONArray array)
            throws JSONException {
        List list = new ArrayList();
        for (int i = 0; i < array.size(); ++i) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map map = new HashMap();
        Set<String> keysItr = object.keySet();
        for (String str : keysItr) {
            Object value = object.get(str);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(str, value);
        }
        return map;
    }


    /**
     * 字符串转对应实体类list
     * @param str
     * @param classes
     * @return
     */
    public static List<?> verifyStrToList(String str, Class<?> classes) {
        List<?> list = null;
        try {
            list = JSONObject.parseArray(str, classes);
        } catch (Exception ex) {
            throw new CodeException(ErrorEnum.FORMAT_CONVERT_FAIL);
        }
        if (list.size()==0){
            throw new CodeException(ErrorEnum.FORMAT_CONVERT_FAIL);
        }
        return list;
    }

    /**
     * 返回分页数据
     *
     * @param list
     * @return
     */
    public static Map<String, Object> returnPageResultMap(List<?> list) {
        PageInfo<?> pageInfo = new PageInfo<>(list);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("list", list);
        resultMap.put("count", pageInfo.getTotal());
        resultMap.put("totalPage", pageInfo.getPages());
        return resultMap;
    }

    /**
     * 验证字符串转数组格式
     * @param arr
     * @return
     */
    public static String[] verifyArrayFormat(String arr) {
        if (arr == null) {
            throw new CodeException(ErrorEnum.CANTEEN_FOOD_NOT_SELECT);
        }
        String[] str = arr.split(",");
        if (str.length == 0) {
            throw new CodeException(ErrorEnum.CANTEEN_FOOD_NOT_SELECT);
        }
        return str;
    }

    /**
     * 根据某字段排序
     * @param result
     * @param serch
     * @return
     */
    public static List<Map<String, Object>> parseListBySort(List<Map<String, Object>> result, String serch) {
        List resultList = Lists.newArrayList();

        Set setTime = Sets.newHashSet();
        String yuding_time;
        for (Map map : result) {
            if ((strObject(map.get(serch)).isEmpty()) || (strObject(map.get(serch)).isEmpty()))
                continue;
            yuding_time = strObject(map.get(serch)).substring(0, 10);
            setTime.add(yuding_time);
        }

        List<String> stuList = new ArrayList();
        stuList.addAll(setTime);
        Collections.sort(stuList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        for (String string : stuList) {
            Map list = Maps.newHashMap();
            Map timeMap = Maps.newHashMap();

            List lists = Lists.newArrayList();
            for (Map map : result) {
                String time = strObject(map.get(serch));
                if (string.equals(time)) {
                    lists.add(map);
                }
            }

            timeMap.put("date", string);
            timeMap.put("info", lists);
            list.put("list", timeMap);
            resultList.add(list);
        }

        return resultList;
    }

    public static String parseXML(Map resMap) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = resMap.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if ((v != null) && (!("".equals(v)))) {
                sb.append("<" + k + ">" + resMap.get(k) + "</" + k + ">\n");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    public static Map getMap(int week, Date date) {
        Map map = Maps.newHashMap();
        String dateStr = formStrFormat(date, "yyyy/MM/dd");
        switch (week) {
            case 1:
                map.put("week", "周一");
                map.put("date", dateStr);
                break;
            case 2:
                map.put("week", "周二");
                map.put("date", dateStr);
                break;
            case 3:
                map.put("week", "周三");
                map.put("date", dateStr);
                break;
            case 4:
                map.put("week", "周四");
                map.put("date", dateStr);
                break;
            case 5:
                map.put("week", "周五");
                map.put("date", dateStr);
                break;
            case 6:
                map.put("week", "周六");
                map.put("date", dateStr);
                break;
            case 7:
                map.put("week", "周日");
                map.put("date", dateStr);
        }
        return map;
    }

    public static String random(int n) {
        if ((n < 1) || (n > 10)) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; ++i) {
            int k;
            do
                k = ran.nextInt(10);
            while ((bitField & 1 << k) != 0);
            bitField |= 1 << k;
            chs[i] = (char)(k + 48);
        }

        String returnStr = new String(chs);
        if ("0".equals(returnStr.substring(0, 1))) {
            returnStr = random(n);
        }
        return returnStr;
    }

    public static String getIp2(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

}
