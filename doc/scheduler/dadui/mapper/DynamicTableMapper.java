package com.idata3d.scheduler.dadui.mapper;

import com.idata3d.scheduler.dadui.service.base.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * 答题数数据
 * YZG on 2017/8/13
 */
@Component
public class DynamicTableMapper {

    private final DatabaseConfig databaseConfig;

    @Autowired
    public DynamicTableMapper(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    /**
     * 创建动态表
     */
    public void dynamicCreateAnswerSheet(String sql){
        System.out.println(sql);
        databaseConfig.dbAnalyze.update(sql);
    }

    /**
     * 保存答题数据
     */
    public void insertAnswerSheet(String sql){
        databaseConfig.dbAnalyze.update(sql);
    }

    /**
     * 查询答题数据同步的最新时间
     */
    public Long queryUserAnswerInfoMaxCreatedAt(){
        final String sql = "SELECT MAX(created_at) FROM dadui.dc_dm_answer_info_time";
        Optional<Long> optional = databaseConfig.dbAnalyze.findOptional(Long.class, sql);
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    /**
     * 查询答题表中答题数量
     */
    public List<String> queryAnswerNum(String tableName){
        final String sql = "SELECT INFO_ID FROM dadui." + tableName;
        return databaseConfig.dbAnalyze.findAll(String.class,sql);
    }

    /**
     * 删除指定答题表的数据
     */
    public void deleteAnswerSheetData(String answerSheetCode) {
        final String sql = "DELETE FROM dadui.dc_dm_answer_sheet" + answerSheetCode;
        databaseConfig.dbAnalyze.update(sql);
    }

    /**
     * 删除答题表
     */
    public void  deleteAnserTable(List<String> questionnaireCodes){
        if (questionnaireCodes != null && questionnaireCodes.size() > 0){
            final String tableName = "dc_dm_answer_sheet";
            questionnaireCodes.forEach(code ->{
                databaseConfig.dbAnalyze.update("DROP TABLE dadui." + tableName + code);
            });
        }
    }
}
