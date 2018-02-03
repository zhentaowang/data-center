package com.adatafun.base.data.center.util;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @date: 2017/12/19 下午4:15
 * @author: ironc
 * @version: 1.0
 */
public class StringUtils {

    public static boolean isNoneBlank(final CharSequence... css) {
        return !isAnyBlank(css);
    }

    public static boolean isAnyBlank(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static String getNumCode(int length) {
        char[] numbersAndLetters = "0123456789".toCharArray();
        Random randGen = new Random();
        if (length < 1) {
            return null;
        } else {
            char[] randBuffer = new char[length];

            for (int i = 0; i < randBuffer.length; ++i) {
                randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
            }

            return new String(randBuffer);
        }
    }

    public static String getNumAndLetterCode2(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                int realTemp = random.nextInt(26) + temp;
                val.append((char) realTemp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return val.toString();
    }

    /**
     * 获取数字字母的固定长度字符串
     *
     * @param length
     * @return
     */
    public static String getNumAndLetterCode(int length) {
        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        if (length < 1) {
            return null;
        }
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val.append(chr[random.nextInt(62)]);
        }
        return val.toString();
    }

    /**
     * 中文unicode转字符串
     *
     * @param str
     * @return
     */
    public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }

        return str;
    }


    public static void main(String[] args) {
        long l1, l2, l3, l4;
        long sum1 = 0, sum2 = 0;
        String s1, s2;
        for (int j = 0; j < 100; j++) {
            l1 = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                s1 = getNumAndLetterCode(18);
//                System.out.println(s1);
            }
            l2 = System.currentTimeMillis();
//            System.out.println("getNumAndLetterCode 执行时间");
//            System.out.println(l2 - l1);
            sum1 += (l2 - l1);
            l3 = System.currentTimeMillis();
            for (int i = 0; i < 100; i++) {
                s2 = getNumAndLetterCode2(18);
//                System.out.println(s2);
            }
            l4 = System.currentTimeMillis();
            sum2 += (l4 - l3);
//            System.out.println("getNumAndLetterCode2 执行时间");
//            System.out.println(l4 - l3);
        }
        System.out.println("getNumAndLetterCode 平均执行时间");
        System.out.println(sum1);
        System.out.println("getNumAndLetterCode2 平均执行时间");
        System.out.println(sum2);

    }

}
