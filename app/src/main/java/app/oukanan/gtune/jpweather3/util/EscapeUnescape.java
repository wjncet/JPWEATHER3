package app.oukanan.gtune.jpweather3.util;

import app.oukanan.gtune.jpweather3.model.DataMd;

/**
 * Created by 王佳楠 on 2016/07/25.
 */
public class EscapeUnescape {
//    public static String escape(String src) {
//        int i;
//        char j;
//        StringBuffer tmp = new StringBuffer();
//        tmp.ensureCapacity(src.length() * 6);
//        for (i = 0; i < src.length(); i++) {
//            j = src.charAt(i);
//            if (Character.isDigit(j) || Character.isLowerCase(j)
//                    || Character.isUpperCase(j))
//                tmp.append(j);
//            else if (j < 256) {
//                tmp.append("%");
//                if (j < 16)
//                    tmp.append("0");
//                tmp.append(Integer.toString(j, 16));
//            } else {
//                tmp.append("%u");
//                tmp.append(Integer.toString(j, 16));
//            }
//        }
//        return tmp.toString();
//    }

    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    public static void main(String[] args) {
//        String tmp = "中文";
//        System.out.println("testing escape : " + tmp);
//        tmp = escape(tmp);
//        System.out.println(tmp);
//        System.out.println("testing unescape :" + tmp);
//        System.out.println("\u6771\u4eac\u90fd \u6771\u4eac \u306e\u5929\u6c17");
//        // System.out.println(URLDecoder.decode(""name\u4e2d\u9813\u5225\u753a"","UTF-8"));
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN);
//        try {
//            System.out.println(sdf.format(sdf.parse("2016-07-25T17:00:00+0900")));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        System.out.println( DataMd.sub2ArrSub1Code.length);
        System.out.println( DataMd.sub2ArrCode.length);
        System.out.println( DataMd.sub2ArrName.length);
    }
}
