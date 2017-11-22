package com.web.example;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author YZG 2017/11/13.
 */
@RestController
@Validated
@RequestMapping("/server")
public class ServerController {

    private String currentTime;
    private final ParameterConfig parameterConfig;
    @Autowired
    public ServerController(ParameterConfig parameterConfig) {
        this.parameterConfig = parameterConfig;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        currentTime = dateFormat.format(new Date());
    }

    @PostMapping(value = "service")
    public void service() throws Exception {
        // 设置URL后缀
        StringBuilder urlBuilder = new StringBuilder("/2013-12-26/Accounts/");
        urlBuilder.append(parameterConfig.getAccountSid()).append("/SMS/TemplateSMS?sig=");
        final String sigParameter = parameterConfig.getAccountSid() + parameterConfig.getAuthToken() + currentTime;
        final String sig = MD5Utils.encodeMD5(sigParameter);
        final String urlSuf = urlBuilder.append(sig).toString();
        parameterConfig.setReqeustUrl(urlSuf);

        // 设置Authorizationd
        authorization(currentTime);

        // 请求包体
        RequestData requestData = confRequestData(parameterConfig.getAppId());
        Object toJson = JSONObject.toJSON(requestData);
        JSONObject jsonObject = JSONObject.parseObject(toJson.toString());
        HttpClient.endPost(jsonObject,parameterConfig);



    }

    /**
     *  构造请求数据
     * @param appId     应用ID
     */
    @NotNull
    private RequestData confRequestData(@NotEmpty String appId){
        RequestData requestData = new RequestData();
        requestData.setTo(Arrays.asList("123","321"));
        requestData.setAppId(appId);
        requestData.setTemplateId("1");
        requestData.setDatas(Arrays.asList("替换内容","替换内容"));
        return requestData;
    }

    /**
     * 生成Authorization
     * @param currentTime       当前系统时间 yyyyMMddHHmmss
     */
    private void authorization(@NotEmpty String currentTime) throws UnsupportedEncodingException {
        final String accountSid = parameterConfig.getAccountSid();
        final String authorization = accountSid + ":" + currentTime;
        final String encodeAuthorization = Base64.encodeBase64String(authorization.getBytes("UTF-8"));
        parameterConfig.setAuthorization(encodeAuthorization);
    }
}
