package com.example.resourceserver.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;


@Component
public class OssUtil {
    private String secretId;

    private   String secretKey;

    private String bucketName;
    COSClient cosClient;
    public static OssUtil instance=null;
    public static OssUtil getInstance() {
        if (instance != null) {
            return instance;
        }else{
            instance=new OssUtil();

        }
        return instance;
    }
    OssUtil(){}
    @Autowired
    public OssUtil(
            @Value("${secretId}") String secretId,
            @Value("${secretKey}") String secretKey,
            @Value("${bucketName}") String bucketName) {
        this.secretId = secretId;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        setCosClient();
    }

    public void setCosClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region=new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 3 生成 cos 客户端。
        cosClient = new COSClient(cred, clientConfig);
    }

    public URL put(MultipartFile image, String key)  {

// 获取文件名


// 创建临时文件
        File tempFile = null;
        try {
            tempFile = File.createTempFile("tem", "");
            // 将MultipartFile对象写入临时文件
            image.transferTo(tempFile);
        } catch (IOException e) {
            throw new RuntimeException(e);

        }



// 现在你可以使用tempFile作为一个普通的File对象进行操作了
        File file = tempFile;

//            String key="img/"+id+".png";
        boolean exist ;
        boolean right=true;
        for (int i=0;right;i++){
            String keys="enroll/"+key+"-"+i+".png";
            exist=cosClient.doesObjectExist(bucketName,keys);
            if((!exist)){
                PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,keys,file);
                PutObjectResult putObjectResult=cosClient.putObject(putObjectRequest);
                right=false;
                return  cosClient.getObjectUrl(bucketName,keys);
            }
        }
        //获取URL

        return null;
    }
    public URL put(File file, String key){
        PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,key,file);
        PutObjectResult putObjectResult=cosClient.putObject(putObjectRequest);
        //获取URL
        URL url=cosClient.getObjectUrl(bucketName,key);
        return  url;
    }
    public void delete(String key){
        cosClient.deleteObject(bucketName,key);
    }
}

