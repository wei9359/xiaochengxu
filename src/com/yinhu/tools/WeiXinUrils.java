package com.yinhu.tools;

import com.alibaba.fastjson.JSONPObject;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import sun.plugin2.message.GetAppletMessage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ClassName WeiXinUrils
 * @auther 魏星
 * @DATE 2018/6/26
 */
public class WeiXinUrils {
    /**
    * @auther 魏星
    * @date   2018/6/26
    *   * @param code   前台传来的code
    * @return java.util.Map<java.lang.String,java.lang.String>
    */
    public static Map<Object,Object> getOpenId(String code) throws Exception{
        Map<Object,Object> weixinMap = new HashMap<Object, Object>();
        String wxxcxGetUserIfoUrl = ConfigUtils.wxxcxGetUserIfoUrl;
        String url = wxxcxGetUserIfoUrl + "?appid=" + ConfigUtils.wxxcxAppId + "&secret=" + ConfigUtils.wxxcxAppSecret+"&js_code="+code+"&grant_type=authorization_code";
        String result = GET(url);
        System.out.println(result);
        int index = result.indexOf("openid");
        String openID = result.substring(index+9,result.length()-2);

        weixinMap.put("openID",openID);
        System.out.println(openID);
        return weixinMap;
    }
    /**
    * @auther 魏星
    * @date   2018/6/26
    *   * @param url
    * @return java.lang.String
    */
    public static String GET(String url){
        StringBuffer result = new StringBuffer();
        BufferedReader br = null;
        InputStream is = null;
        InputStreamReader isr = null;
        try{
            URL realUrl = new URL(url);
            URLConnection conn =  realUrl.openConnection();
            conn.connect();
            Map<String,List<String>> map = conn.getHeaderFields();
            is = conn.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line;
            while ((line= br.readLine())!=null){
                result.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(br != null){
                    br.close();
                }
                if(is != null){
                    is.close();
                }
                if(isr != null){
                    isr.close();
                }
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
        return result.toString();
    }
    /**
    * @auther 魏星
    * @date   2018/6/26
    *   * @param xml
    * @return java.util.Map<java.lang.String,java.lang.String>
    */
    //将XML转为map
    public static Map<String, String> xmlToMap(String xml) throws Exception{
        if(!StringUtil.isEmpty(xml)){
            Map<String, String> map = new HashMap<>();
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
                Element e = (Element) iterator.next();
                map.put(e.getName(), e.getText());
                System.out.println(e.getName()+"="+e.getText());
            }
            return map;
        }else{
            return null;
        }
    }
}
