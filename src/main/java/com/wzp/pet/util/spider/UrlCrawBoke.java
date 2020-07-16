package com.wzp.pet.util.spider;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlCrawBoke {
//个人爬虫自制
    public static void  main(String urlstr[]) throws IOException {
        InputStream is = doGet("http://www.cnblogs.com/qlqwjy/p/8721867.html");
        String pageStr = inputStreamToString(is, "UTF-8");
        is.close();
        System.out.println(pageStr);
    }



    public static InputStream doGet(String urlstr) throws IOException {
        URL url= new URL(urlstr);
        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        InputStream inputStream= conn.getInputStream();
        return inputStream;
    }


    public static String inputStreamToString(InputStream is, String charset) throws IOException {
        byte[] bytes = new byte[1024];
        int byteLength = 0;
        StringBuffer sb = new StringBuffer();
        while((byteLength = is.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, byteLength, charset));
        }
        return sb.toString();
    }
}
