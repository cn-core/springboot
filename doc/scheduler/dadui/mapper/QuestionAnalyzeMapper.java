package com.idata3d.scheduler.dadui.mapper;

import com.idata3d.scheduler.dadui.bean.ConversionLater.QuestionLater;
import com.idata3d.scheduler.dadui.service.base.DatabaseConfig;
import com.idata3d.scheduler.dadui.service.base.MethodBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * yangzhiguo on 2017/8/11.
 */
@Component
public class QuestionAnalyzeMapper {

    private final MethodBase methodBase;
    private final DatabaseConfig databaseConfig;

    @Autowired
    public QuestionAnalyzeMapper(MethodBase methodBase, DatabaseConfig databaseConfig) {
        this.methodBase = methodBase;
        this.databaseConfig = databaseConfig;
    }

    /**
     * 添加问题信息
     */
    public void insertQuestion(final String questionSql) {
        databaseConfig.dbAnalyze.update(questionSql);
    }

    /**
     * 更新问题数据
     */
    public void updateQuestion(QuestionLater questionLater) {
        final StringBuilder sql = new StringBuilder();
        sql.append("UPDATE dadui.dc_dm_question SET que_title = '");
        sql.append(questionLater.getQueTitle());
        sql.append("',updated_at = '");
        sql.append(questionLater.getUpdatedAtAs());
        sql.append("'\tWHERE id = '");
        sql.append(questionLater.getId());
        sql.append("'");
        databaseConfig.dbAnalyze.update(sql.toString());
    }

    /**
     * 查询问题信息--多个(条件:答对问题中的question_id)
     */
    public List<QuestionLater> queryQuestionLatersByDaduiId(List<String> questionIds) {
        StringBuilder sql = new StringBuilder("select * from dadui.dc_dm_question q where q.DADUI_ID IN(");
        methodBase.answerJoinSql(questionIds, sql);
        return databaseConfig.dbAnalyze.findAll(QuestionLater.class, sql.toString());
    }

    /**
     * 查询问题信息--多个(条件:问卷ID)
     */
    public List<QuestionLater> queryQuestionLaterByQutstId(List<String> questionnaireIds) {
        StringJoiner sql = new StringJoiner("','",
                "select * from dadui.dc_dm_question q where q.questionnaire_id in('","')");
        for (String questionnaireId : questionnaireIds) {
            sql.add(questionnaireId);
        }
        return databaseConfig.dbAnalyze.findAll(QuestionLater.class,sql.toString());
    }

    /**
     * 查询问题中最大的时间
     */
    public String queryQuestionMaxUpdatetime(){
        final String sql = "SELECT MAX(q.CREATED_AT) FROM dadui.dc_dm_question q";
        Optional<LocalDateTime> optional = databaseConfig.dbAnalyze.findOptional(LocalDateTime.class, sql);
        return optional.map(localDateTime -> localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        ).orElse(null);
    }

    /**
     * 问题的数量
     */
    public List<String> queryQuestionDaduiIds() {
        final String sql = "SELECT q.dadui_id FROM dadui.dc_dm_question q";
        return databaseConfig.dbAnalyze.findAll(String.class, sql);
    }
}
