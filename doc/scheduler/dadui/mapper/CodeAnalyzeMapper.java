package com.idata3d.scheduler.dadui.mapper;

import com.idata3d.scheduler.dadui.service.base.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * yangzhiguo on 2017/8/11.
 */
@Component
public class CodeAnalyzeMapper{

    private final DatabaseConfig databaseConfig;

    @Autowired
    public CodeAnalyzeMapper(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    /**
     * 查询最大Code值
     */
    public Integer queryMaxCode(){
        final String sql = "SELECT MAX(code) FROM dadui.dc_dm_answer_code";
        Optional<Integer> optional = databaseConfig.dbAnalyze.findOptional(Integer.class, sql);
        if (optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 保存Code
     */
    public void saveMaxCode(final Integer code){
        if (code != null  && code != 0)  {
            final String sql = "insert into dadui.dc_dm_answer_code(code) values(?)";
            databaseConfig.dbAnalyze.update(sql,code);
        } else {
            throw new RuntimeException("Code不能为空!");
        }
    }
}
