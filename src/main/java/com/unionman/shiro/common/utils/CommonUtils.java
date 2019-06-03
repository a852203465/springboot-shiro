package com.unionman.shiro.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unionman.shiro.common.constants.FileConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: 通用工具类
 * @date 2019/01/08 14:29
 * @author Rong.Jia
 */
@Slf4j
public class CommonUtils {

    /**
     * @description: 降序排序
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;

    }

    /**
     * @description:  判断文件夹是否存在
     * @param file file
     * @author Rong.Jia
     * @date 2018/12/05 12:15
     */
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
//				System.out.println("dir exists");
            } else {
//				System.out.println("the same name file exists, can not create dir");
            }
        } else {
//			System.out.println("dir not exists, create it ...");
            file.mkdirs();
        }

    }

    /**
     * @description: 判断运行环境是linux还是windows
     * @author Rong.Jia
     * @date 2019/01/23 9:19
     * @return false/true windows/linux
     */
    public static Boolean judgeSystem(){

        Properties prop = System.getProperties();
        String os = prop.getProperty(FileConstant.SYSTEM_ENVIRONMENT);
        if (AssertUtils.isNotNull(os) && FileConstant.LINUX_SYSTEM.equalsIgnoreCase(os)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @description: 判断字符串是中文还是英文
     * @param str 字符串
     * @return true/false
     */
    public static boolean isChinese(String str){

        String regEx = "[\\u4e00-\\u9fa5]+";

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(str);

        if(m.find()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @description: 生成UUID
     * @date 2019/02/14 08:40:22
     * @author Rong.Jia
     * @return String UUID
     */
    public static String generateUUID(){

        String uuid = UUID.randomUUID().toString();

        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }

    /**
     * listToTree
     * 将List数组转为树状结构
     * @param list 需要转化的数据
     * @param id 数据唯一的标识键值
     * @param pid 父id唯一标识键值
     * @param child 子节点键值
     * @return JSONArray
     */
    public static JSONArray listToTree(List list, String id, String pid, String child){

        JSONArray arr= JSONArray.parseArray(JSON.toJSONString(list));

        JSONArray r = new JSONArray();
        JSONObject hash = new JSONObject();
        //将数组转为Object的形式，key为数组中的id
        for(int i=0;i<arr.size();i++){
            JSONObject json = (JSONObject) arr.get(i);
            hash.put(json.getString(id), json);
        }
        //遍历结果集
        for(int j=0;j<arr.size();j++){
            //单条记录
            JSONObject aVal = (JSONObject) arr.get(j);
            //在hash中取出key为单条记录中pid的值
            JSONObject hashVP = (JSONObject) hash.get(aVal.get(pid).toString());
            //如果记录的pid存在，则说明它有父节点，将她添加到孩子节点的集合中
            if(hashVP!=null){
                //检查是否有child属性
                if(hashVP.get(child)!=null){
                    JSONArray ch = (JSONArray) hashVP.get(child);
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }else{
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }
            }else{
                r.add(aVal);
            }
        }
        return r;
    }

    /**
     * url 替换
     * @param url 需替换的url
     * @param newHost 新的host
     * @return String 替换后的url
     */
    public static String urlReplace(String url, String newHost){

        URI uri = CommonUtils.getAddress(url);

        return url.replace(uri.toString(), newHost);

    }

    /**
     * 获取url中的ip和port
     * @param uriStr 完整url
     * @return
     */
    public static URI getAddress(String uriStr) {

        URI uri = URI.create(uriStr);

        URI effectiveURI = null;

        try {
            // URI(String scheme, String userInfo, String host, int port, String
            // path, String query,String fragment)
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), null, null, null);
        } catch (Throwable var4) {
            effectiveURI = null;
        }

        return effectiveURI;
    }

}
