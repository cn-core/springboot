package com.web.example;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangzhiguo  2017/11/14.
 */
@Validated
public class RequestData implements Serializable{
    @NotEmpty
    private String          to;
    @NotEmpty
    private String          appId;
    @NotEmpty
    private String          templateId;
    private List<String>    datas;
    private String          data;

    public String getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        if (to.isEmpty()) {
            throw new RuntimeException("to不能为空!");
        } else if (to.size() <= 200) {
            this.to = to.toString().replace("[","").replace("]","");
        }
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
