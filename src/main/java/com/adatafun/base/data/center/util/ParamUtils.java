package com.adatafun.base.data.center.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 飞常准相关工具类
 *
 * @date: 2017/12/19 下午4:32
 * @author: ironc
 * @version: 1.0
 */
public class ParamUtils {

    /**
     * 获取签名内容
     * 1. 按首字母排序
     * 2. 加上&和=
     *
     * @param params
     * @return
     */
    public static String getSignContent(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        List list = new ArrayList<>(params.keySet());
        Collections.sort(list);

        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            String key = (String) list.get(i);
            key = key.trim();
            String value = (String) params.get(key);
            if (StringUtils.isNoneBlank(key) && StringUtils.isNoneBlank(value)) {
                sb.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * MD5加密
     *
     * @param sourceStr 原始数据
     * @return
     */
    public static String MD5(String sourceStr) {
        return MD5(sourceStr, 32);
    }

    /**
     * MD5加密
     *
     * @param sourceStr 原始数据
     * @param redix     加密位数
     * @return
     */
    public static String MD5(String sourceStr, int redix) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
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
            result = buf.toString();
            if (redix == 1 << 5) {
                // 32位 目前不需要操作
            }
            if (redix == 1 << 4) {
                result = result.substring(8, 24);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 首字母小写
     *
     * @param str
     * @return
     */
    public static String lowerCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }

    public static void main(String[] args) {

    }

}
