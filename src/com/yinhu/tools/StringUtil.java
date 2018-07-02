package com.yinhu.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @ClassName StringUtil
 * @auther 魏星
 * @DATE 2018/6/23
 */
public class StringUtil {
    /**
     * 判断是否为null
     * @param str 字符串
     * @return 若为null则返回true，若不为null则返回false。
     */
    public static boolean isNull(String str) {
        return str == null;
    }

    /**
     * 判断是否为空(null或空字符串)
     * @param str 字符串
     * @return 若为null或空字符串则返回true，若既不为null，也不为空字符串则返回false。
     */
    public static boolean isEmpty(String str) {
        return (str == null) || ("".equals(str));
    }

    /**
     * 去除空格符，当字符串以某个分隔符进行分割时，去除分隔符中的空格
     * @param str 字符串
     * @param separator 分隔符
     * @return 去除空字符串后的字符串
     */
    public static String removeEmpty(String str,String separator) {
        String[] strs = str.split(separator);
        StringBuffer sb = new StringBuffer();
        for (String s : strs) {
            if (!"".equals(s.trim())) {
                sb.append(s.trim());
                sb.append(separator);
            }
        }

        return sb.length() > 0 ? sb.substring(0, sb.length() - 1).toString() : "";
    }

    /**
     * 去除分隔符后字符串是否为空
     * @param str 字符串
     * @param separator 分隔符
     * @return
     */
    public static boolean isEmpty(String str,String separator) {
        String[] strs = str.split(separator);
        StringBuffer sb = new StringBuffer();
        for (String s : strs) {
            if (!"".equals(s.trim())) {
                sb.append(s.trim());
            }
        }

        return sb.length() > 0 ? false : true;
    }

    /**
     * html标签过滤器
     * @param inputString 待过滤的内容
     * @return 过滤后的字符串
     */
    public static String htmlFilter(String inputString) {
        String htmlStr = inputString; //含html标签的字符串
        String textStr ="";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;
        java.util.regex.Pattern p_ba;
        java.util.regex.Matcher m_ba;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
            String patternStr = "\\s+";

            p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签

            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签

            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签

            p_ba = Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
            m_ba = p_ba.matcher(htmlStr);
            htmlStr = m_ba.replaceAll(""); //过滤空格

            textStr = htmlStr;

        }catch(Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        return textStr;//返回文本字符串
    }

    /**
     * 数字补齐
     * @param resource 原始数字
     * @param sign 所要补充的符号
     * @param length 长度
     * @return 补零后的字符创
     */
    public static String fillupDecimal(int resource,String sign,int length) {
        String str = String.valueOf(resource);
        while(str.length() < length) {
            str = sign + str;
        }
        return str;
    }

    /**
     * 从字符串中解析数字
     * @param str 字符串
     * @param isFirst 是否只解析第一个数字
     * @return 解析后的字符串，没有数字时返回null
     */
    public static String analyzeDigit(String str,boolean isFirst) {
        if(isEmpty(str))
            return null;

        boolean flag=false;
        String digitStr="";
        for(int i = 0;i < str.length();i++){
            Object _tmp = str.charAt(i);
            if(_tmp.toString().matches("[0-9]")){
                digitStr+=_tmp;
                flag=true;
            } else if(flag) {
                if(isFirst){
                    break;
                } else {
                    continue;
                }
            }
        }
        return digitStr.length() > 0 ? digitStr:null;
    }

    public static String StringToString(String[] str) {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<str.length; i++) {
            if(!list.contains(str[i].trim())) {
                list.add(str[i].trim());
            }
        }
        String[] newStr =  list.toArray(new String[1]); //返回一个包含所有对象的指定类型的数组
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < newStr.length; i++) {
            if(i==newStr.length-1){
                sb.append( newStr[i].trim());
            }else{
                sb.append( newStr[i].trim()+",");
            }

        }
        String a = new String(sb);
        return a;
    }

    public static String StringToTrim(String[] str) {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<str.length; i++) {
            if(!list.contains(str[i].trim())) {
                if(!StringUtil.isEmpty(str[i].trim())){
                    list.add(str[i].trim());
                }
            }
        }
        String[] newStr =  list.toArray(new String[1]); //返回一个包含所有对象的指定类型的数组
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < newStr.length; i++) {
            if(i==newStr.length-1){
                sb.append( newStr[i].trim());
            }else{
                sb.append( newStr[i].trim()+",");
            }

        }
        String a = new String(sb);
        return a;
    }
}
