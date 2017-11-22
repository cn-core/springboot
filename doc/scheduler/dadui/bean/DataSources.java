package com.idata3d.scheduler.dadui.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * YZG on 2017/9/21.
 */
@Data
@Component
public class DataSources {
    @Value("${dadui.datasource.username}")
    private String      daduiUsername;
    @Value("${dadui.datasource.password}")
    private String      dauiPassword;
    @Value("${dadui.datasource.url}")
    private String      daduiUrl;
    @Value("${ali.dadui.datasource.username}")
    private String      aliUsername;
    @Value("${ali.dadui.datasource.password}")
    private String      aliPassword;
    @Value("${ali.dadui.datasource.url}")
    private String      aliUrl;
}
