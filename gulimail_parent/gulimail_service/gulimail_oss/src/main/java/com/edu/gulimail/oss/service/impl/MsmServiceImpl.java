package com.edu.gulimail.oss.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.edu.gulimail.oss.service.MsmService;
import com.edu.gulimail.oss.utils.ConstantPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    private final ConstantPropertiesUtils constantPropertiesUtils;
    @Autowired
    public MsmServiceImpl(ConstantPropertiesUtils constantPropertiesUtils) {
        this.constantPropertiesUtils = constantPropertiesUtils;
    }

    // 使用阿里云短信服务进行发送的方法
    @Override
    public boolean sendCodeByAly(Map<String, String> param, String phoneNum) {
        String id = constantPropertiesUtils.getKeyId();
        String accessKey = constantPropertiesUtils.getKeySecret();

        // 进行参数的判断：
        if (StringUtils.isEmpty(phoneNum)){
            return false;
        }
        // 使用 id 和 秘钥进行发送
        DefaultProfile profile = DefaultProfile.getProfile("default", id, accessKey);
        // 设置相关参数：
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        // 阿里云中的 签名
        request.putQueryParameter("SignName", "DDG在线教育网");
        // 模板code
        request.putQueryParameter("TemplateCode", "SMS_187756511");
        // 需要的是一种json格式 不能直接传code {"code": 123455}
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
        // 最终的发送：
        boolean success = false;
        try {
            CommonResponse commonResponse = client.getCommonResponse(request);
            success = commonResponse.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return success;
    }
}
