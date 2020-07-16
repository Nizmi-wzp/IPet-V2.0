package com.wzp.pet.util.spider;


import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/**
 * 一个简单的利用Jsoup获取网站数据的信息
 * @author liqiang
 *
 */
public class JsoupCraw {

    public static void main(String[] args) throws IOException {
        String url = "http://www.cnblogs.com/qlqwjy/p/7531579.html";
        //直接获取DOM树
        Document document = Jsoup.connect(url).get();
        System.out.println(document.toString());
    }
}
