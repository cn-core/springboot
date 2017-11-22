package com.web.example;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author YZG 2017/11/13.
 */
@Component
@Validated
public class ParameterConfig {
    @Value("${ACCOUNT_SID}")
    @NotEmpty
    private String accountSid;
    @Value("${AUTH_TOKEN}")
    @NotEmpty
    private String authToken;
    @Value("${BASE_URL}")
    @NotEmpty
    private String baseUrl;
    @Value("${APP_ID}")
    @NotEmpty
    private String appId;
    /**
     * 必须是Base64加密后的值
     */
    private String authorization;

    private String reqeustUrl;

    public String getReqeustUrl() {
        return reqeustUrl;
    }

    public void setReqeustUrl(String reqeustUrlSuffix) {
        this.reqeustUrl = baseUrl + reqeustUrlSuffix;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
