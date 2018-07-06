package com.yinhu.tools;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName JSONTools
 * @auther 魏星
 * @DATE 2018/7/5
 */
public class JSONTools {
    public static List<Map<String,String>> stringToListMap(String s){
        List<Map<String,String>> listMap = new ArrayList<Map<String, String>>();
        String s1 = s.replace('[',' ');
        String s2 = s1.replace(']',' ');
        System.out.println(s2);
        String s3 = s2.replace('{',' ');
        System.out.println(s2);
        String s4 = s3.replace(':',' ');
        String s5 = s4.replaceAll(" ","");
        System.out.println(s5);

        String[] strings = s5.split("}");
        for(int i =0;i<strings.length;i++){
            Map<String,String> map = new HashMap<String, String>();
            System.out.println(strings[i]);
            String[] strings1 = strings[i].split(",");
            List<String> listStrings1 = deleteVoidString(strings1);
            for(int j = 0 ; j < listStrings1.size();j++){
                String[] strings2 = listStrings1.get(j).split("\"");
                List<String> list = deleteVoidString(strings2);
                map.put(list.get(0),list.get(1));
            }
            listMap.add(map);
        }
        return listMap;
    }

    public static List<String> deleteVoidString(String[] strings){
        List<String> list = new ArrayList<String>();
        for(int k = 0;k < strings.length;k++){
            if(!StringUtil.isEmpty(strings[k])){
                list.add(strings[k]);
            }
        }
        return list;
    }
}


