package com.idata3d.scheduler.dadui.service.base;

import com.idata3d.scheduler.dadui.mapper.*;
import org.dalesbred.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * YZG on 2017/7/29
 */
@Component
public class CommonMethod {

    public final DaDuiMapper daDuiMapper;
    public final CodeAnalyzeMapper codeAnalyzeMapper;
    public final DynamicTableMapper dynamicTableMapper;
    public final OptionAnalyzeMapper optionAnalyzeMapper;
    public final QuestionAnalyzeMapper questionAnalyzeMapper;
    public final QuestionnaireAnalyzeMapper questionnaireAnalyzeMapper;
    public final MethodBase methodBase;
    public final Database db;
    public final Database dbAnalyze;

    @Autowired
    public CommonMethod(DaDuiMapper daDuiMapper, CodeAnalyzeMapper codeAnalyzeMapper, DynamicTableMapper dynamicTableMapper, OptionAnalyzeMapper
            optionAnalyzeMapper, QuestionAnalyzeMapper questionAnalyzeMapper, QuestionnaireAnalyzeMapper questionnaireAnalyzeMapper, DatabaseConfig
            databaseConfig, MethodBase methodBase) {
        this.daDuiMapper = daDuiMapper;
        this.codeAnalyzeMapper = codeAnalyzeMapper;
        this.dynamicTableMapper = dynamicTableMapper;
        this.optionAnalyzeMapper = optionAnalyzeMapper;
        this.questionAnalyzeMapper = questionAnalyzeMapper;
        this.questionnaireAnalyzeMapper = questionnaireAnalyzeMapper;
        this.methodBase = methodBase;
        this.db = databaseConfig.db;
        this.dbAnalyze = databaseConfig.dbAnalyze;
    }
}
