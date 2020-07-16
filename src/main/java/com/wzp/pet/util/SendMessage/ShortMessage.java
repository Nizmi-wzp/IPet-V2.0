package com.wzp.pet.util.SendMessage;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.0.3</version>
</dependency>
*/
//遇到的问题：JDK版本与阿里云版本过高出现不兼容，类未找到，jdk1.8配合4.1短信
public class ShortMessage {
    public String code=vcode();
    public String Sms(String phone){
        /**
         * 进行正则关系校验
         */
        System.out.println(phone);
        if (phone == null || phone == "") {
            System.out.println("手机号为空");
            return "";
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FiKJwkzdujeB6z9UgX8", "UzbJTWsCjoavZIfyIlKI2wHqVXbGi2");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "萌宠网");
        request.putQueryParameter("TemplateCode", "SMS_186599663");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + code + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return "true";

    }
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FiKJwkzdujeB6z9UgX8", "UzbJTWsCjoavZIfyIlKI2wHqVXbGi2");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "15070894798");
        request.putQueryParameter("SignName", "萌宠网");
        request.putQueryParameter("TemplateCode", "SMS_186599663");
        request.putQueryParameter("TemplateParam", "{\"code\":\"678900\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    public static String vcode() {
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int) (Math.random() * 9);
        }
        return vcode;
    }
}
