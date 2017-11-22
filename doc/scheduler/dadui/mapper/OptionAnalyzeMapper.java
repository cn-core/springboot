package com.idata3d.scheduler.dadui.mapper;

import com.idata3d.scheduler.dadui.bean.ConversionLater.OptionsLater;
import com.idata3d.scheduler.dadui.service.base.DatabaseConfig;
import com.idata3d.scheduler.dadui.service.base.MethodBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * yangzhiguo on 2017/8/11.
 */
@Component
public class OptionAnalyzeMapper {

    private final MethodBase methodBase;
    private final DatabaseConfig databaseConfig;

    @Autowired
    public OptionAnalyzeMapper(MethodBase methodBase, DatabaseConfig databaseConfig) {
        this.methodBase = methodBase;
        this.databaseConfig = databaseConfig;
    }

    /**
     * 保存选项信息
     */
    public void insertOption(final String optionsSql) {
        databaseConfig.dbAnalyze.update(optionsSql);
    }

    /**
     * 查询选项中updated_at最大值
     */
    public String OptionsMaxUpdateAt() {
        final String sql = "SELECT MAX(o.UPDATED_AT) FROM dadui.dc_dm_options o";
        Optional<LocalDateTime> optional = databaseConfig.dbAnalyze.findOptional(LocalDateTime.class, sql);
        if (optional.isPresent()) {
            return optional.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            return null;
        }
    }

    /**
     * 查询选项中最大的时间
     */
    public String queryOptionAnaMaxUpdateTime() {
        final String sql = "SELECT MAX(o.CREATED_AT) FROM dadui.dc_dm_options o";
        Optional<LocalDateTime> optional = databaseConfig.dbAnalyze.findOptional(LocalDateTime.class, sql);
        if (optional.isPresent()) {
            return optional.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            return null;
        }
    }

    /**
     * 查询选项信息
     */
    public List<OptionsLater> queryOptionsLaters(List<String> optionsIds) {
        StringBuilder sql = new StringBuilder("select * from dadui.dc_dm_options o  where o.id IN(");
        methodBase.answerJoinSql(optionsIds, sql);
        return databaseConfig.dbAnalyze.findAll(OptionsLater.class, sql.toString());
    }

    /**
     * 根据dadui_id查询选项信息
     */
    public List<OptionsLater> queryOptionsLatersByDaduiId(List<String> daduiIds) {
        StringBuilder sql = new StringBuilder("select * from dadui.dc_dm_options o  where o.dadui_id IN(");
        methodBase.answerJoinSql(daduiIds, sql);
        return databaseConfig.dbAnalyze.findAll(OptionsLater.class, sql.toString());
    }

    /**
     * 查询选项信息(条件问题ID)
     */
    public List<OptionsLater> queryOptionsLatersByQueId(final String questionId) {
        return databaseConfig.dbAnalyze.findAll(OptionsLater.class,
                "SELECT * FROM dadui.dc_dm_options s WHERE s.question_id = ?", questionId);
    }

    /**
     * 查询指定问题下所有选项
     */
    public List<String> queryOptionCount(final String questionId){
        return databaseConfig.dbAnalyze.findAll(String.class,
                "SELECT o.dadui_id FROM dadui.dc_dm_options o WHERE o.question_id = ?",questionId);
    }

    /**
     * 更新选项数据
     */
    public void updateOptions(OptionsLater optionsLater) {
        final StringBuilder sql = new StringBuilder();
        sql.append("UPDATE dadui.dc_dm_options SET opt_value = '");
        sql.append(optionsLater.getOptValue());
        sql.append("',updated_at = '");
        sql.append(optionsLater.getUpdatedAtAs());
        sql.append("'\tWHERE id = '");
        sql.append(optionsLater.getId());
        sql.append("'");
        databaseConfig.dbAnalyze.update(sql.toString());
    }

    /**
     * 查询问题选项是否存在
     */
    public List<String> questionExists(List<String> questionIds) {
        StringBuilder sql = new StringBuilder("SELECT o.DADUI_ID FROM dadui.dc_dm_options o WHERE o.DADUI_ID IN(");
        methodBase.answerJoinSql(questionIds, sql);
        return databaseConfig.dbAnalyze.findAll(String.class, sql.toString());
    }

    /**
     * 查询选项总量
     */
    public List<String> queryOptions() {
        final String sql = "SELECT o.dadui_id FROM dadui.dc_dm_options o";
        return databaseConfig.dbAnalyze.findAll(String.class,sql);
    }
}
