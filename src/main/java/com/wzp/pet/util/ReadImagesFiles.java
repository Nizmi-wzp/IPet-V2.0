package com.wzp.pet.util;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadImagesFiles {
    //批量获取数据文件
    public List getimgAddr(){
        List pathList=new ArrayList();
        List imgList=new ArrayList();
        String Addr="/imageupload/img/item/pets";
        File file=new File("D:/projectdev/imageupload/img/item/pets");
        File[] files=file.listFiles();
        for(File f: files){
            System.out.println(f.getName());
            pathList.add(f.getName());
        }
        for(int i=0;i<pathList.size();i++){
            imgList.add(Addr+'/'+pathList.get(i));
            System.out.println("相对地址"+imgList);
        }
        return imgList;
    }

    public static void main(String[] args) {
        /*try {
            java.awt.Desktop.getDesktop().open(new File("D:\\Java"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        ReadImagesFiles readImagesFiles=new ReadImagesFiles();
        List name=readImagesFiles.getimgAddr();
            System.out.println("相对地址"+name);



    }

}
