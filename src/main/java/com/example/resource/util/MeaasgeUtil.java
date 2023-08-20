package com.example.resource.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.resource.pojo.MessagePerson;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

//导入可选配置类
import com.tencentcloudapi.common.profile.ClientProfile;

// 导入对应SMS模块的client
import com.tencentcloudapi.sms.v20210111.SmsClient;

// 导入要请求接口对应的request response类
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Tencent Cloud Sms Sendsms
 *
 */
@Component
public class MeaasgeUtil {
    public static Credential cred;
    @Value("${secretId}")
    String secretId;
    @Value("${secretKey}")
    String secretKey;
    @Value("${sdkAppId}")
    String sdkAppId;
    @Value("${signName}")
    String signName ;
    @Value("${faceTemplateId}")
    String faceTemplateId;
    @Value("${passTemplateId}")
      String passTemplateId;
/**
 * 单次请求
 */
     public String sendFaceMessage(MessagePerson person) {
         String code=null;
        try {
            /* 必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
             * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
             * 你也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
             * 以免泄露密钥对危及你的财产安全。
             * SecretId、SecretKey 查询: https://console.cloud.tencent.com/cam/capi */
            Credential cred = new Credential(secretId, secretKey);

//             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
//            /* SDK默认用TC3-HMAC-SHA256进行签名
//             * 非必要请不要修改这个字段 */
            clientProfile.setSignMethod("HmacSHA256");
//        clientProfile.setHttpProfile(httpProfile);
            /* 实例化要请求产品(以sms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，支持的地域列表参考 https://cloud.tencent.com/document/api/382/52071#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8 */
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);

            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 你可以直接查询SDK源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
            SendSmsRequest req = new SendSmsRequest();
            req.setSmsSdkAppId(sdkAppId);
            req.setSignName(signName);

            req.setTemplateId(faceTemplateId);
            String[] templateParamSet = {person.getDepartmentName(),person.getUsername()};
            req.setTemplateParamSet(templateParamSet);
            String[] phoneNumberSet = {"+86"+person.getPhone()};
            req.setPhoneNumberSet(phoneNumberSet);
            String sessionContext = "";
            req.setSessionContext(sessionContext);
            String extendCode = "";
            req.setExtendCode(extendCode);

            /* 国内短信无需填写该项；国际/港澳台短信已申请独立 SenderId 需要填写该字段，默认使用公共 SenderId，无需填写该字段。注：月度使用量达到指定量级可申请独立 SenderId 使用，详情请联系 [腾讯云短信小助手](https://cloud.tencent.com/document/product/382/3773#.E6.8A.80.E6.9C.AF.E4.BA.A4.E6.B5.81)。*/

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);

            JSONObject json = (JSONObject) JSON.toJSON(res);
            JSONArray sendStatusSet = (JSONArray) json.get("SendStatusSet");
            JSONObject object = (JSONObject) sendStatusSet.get(0);
           code = object.getString("Code");


        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }


        return  code;
     }


    public String sendPassMessage(MessagePerson person)  {
         String code=null;
        try {
            /* 必要步骤：
             * 实例化一个认证对象，入参需要传入腾讯云账户密钥对secretId，secretKey。
             * 这里采用的是从环境变量读取的方式，需要在环境变量中先设置这两个值。
             * 你也可以直接在代码中写死密钥对，但是小心不要将代码复制、上传或者分享给他人，
             * 以免泄露密钥对危及你的财产安全。
             * SecretId、SecretKey 查询: https://console.cloud.tencent.com/cam/capi */
            Credential cred = new Credential(secretId, secretKey);

//             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
//            /* SDK默认用TC3-HMAC-SHA256进行签名
//             * 非必要请不要修改这个字段 */
            clientProfile.setSignMethod("HmacSHA256");
//        clientProfile.setHttpProfile(httpProfile);
            /* 实例化要请求产品(以sms为例)的client对象
             * 第二个参数是地域信息，可以直接填写字符串ap-guangzhou，支持的地域列表参考 https://cloud.tencent.com/document/api/382/52071#.E5.9C.B0.E5.9F.9F.E5.88.97.E8.A1.A8 */
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 你可以直接查询SDK源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
            SendSmsRequest req = new SendSmsRequest();
            req.setSmsSdkAppId(sdkAppId);
            req.setSignName(signName);
            //需要更改
            req.setTemplateId(faceTemplateId);
            String[] templateParamSet = {person.getDepartmentName(), person.getUsername()};
            req.setTemplateParamSet(templateParamSet);
            String[] phoneNumberSet = {"+86"+person.getPhone()};
            req.setPhoneNumberSet(phoneNumberSet);
            String sessionContext = "";
            req.setSessionContext(sessionContext);
            String extendCode = "";
            req.setExtendCode(extendCode);

            /* 国内短信无需填写该项；国际/港澳台短信已申请独立 SenderId 需要填写该字段，默认使用公共 SenderId，无需填写该字段。注：月度使用量达到指定量级可申请独立 SenderId 使用，详情请联系 [腾讯云短信小助手](https://cloud.tencent.com/document/product/382/3773#.E6.8A.80.E6.9C.AF.E4.BA.A4.E6.B5.81)。*/

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);

            JSONObject json = (JSONObject) JSON.toJSON(res);
            JSONArray sendStatusSet = (JSONArray) json.get("SendStatusSet");
            JSONObject object = (JSONObject) sendStatusSet.get(0);
            code = object.getString("Code");

        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
        return  code;
    }


}