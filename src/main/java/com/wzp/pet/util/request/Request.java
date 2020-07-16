package com.wzp.pet.util.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    public static void main(String[] args) throws IOException {

        URL url=new URL("http://www.baidu.com");
        BufferedReader in=new BufferedReader(
                new InputStreamReader(url.openStream(),"UTF-8"));//UTF-8处理乱码
        String str;
        while((str=in.readLine())!=null){
            Pattern pattern = Pattern.compile("<font color=''>(.+?)</font>"); //正则表达式
            // 定义一个matcher用来做匹配
            Matcher matcher = pattern.matcher(str);
            // 如果找到了
            if (matcher.find()) {
                // 打印出结果
                System.out.println(matcher.group(1));
            }
        }
        in.close();//关闭处理流
    }
}
