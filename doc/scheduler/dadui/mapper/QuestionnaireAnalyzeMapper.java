package com.idata3d.scheduler.dadui.mapper;

import com.idata3d.scheduler.dadui.bean.ConversionLater.PublishLater;
import com.idata3d.scheduler.dadui.bean.ConversionLater.QuestionnaireLater;
import com.idata3d.scheduler.dadui.service.base.DatabaseConfig;
import com.idata3d.scheduler.dadui.service.base.MethodBase;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 分析型数据库访问层Dao
 * yangzhiguo on 2017/8/11.
 */
@Component
public class QuestionnaireAnalyzeMapper {

    private final MethodBase methodBase;
    private final DatabaseConfig databaseConfig;

    @Autowired
    public QuestionnaireAnalyzeMapper(MethodBase methodBase, DatabaseConfig databaseConfig) {
        this.methodBase = methodBase;
        this.databaseConfig = databaseConfig;
    }

    /**
     * 查询问卷是否存在--单个
     */
    public String queryDaduiIdExist(final String daduiId) {  // questionnaireExists
        final String sql = "SELECT q.ID FROM dadui.dc_dm_questionnaire q WHERE q.DADUI_ID = ?";
        Optional<String> optional = databaseConfig.dbAnalyze.findOptional(String.class, sql, daduiId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    /**
     * 查询
     */
    public Integer queryAnswerNum(String groupId){
        if (!StringUtils.isEmpty(groupId)){
            final String tableName = "dadui.dc_dm_answer_sheet" + groupId;
            final String sql = "SELECT COUNT(*) FROM  " + tableName;
            Optional<Integer> optional = databaseConfig.dbAnalyze.findOptional(Integer.class, sql);
            return optional.orElse(null);
        }
        return null;
    }

    /**
     * 查询问卷是否存在--多个
     */
    public List<String> questionnairesExists(@Param("groupIds") List<String> groupIds) {
        StringBuilder sql = new StringBuilder("SELECT q.DADUI_ID FROM dadui.dc_dm_question q WHERE q.DADUI_ID IN(");
        methodBase.answerJoinSql(groupIds, sql);
        return databaseConfig.dbAnalyze.findAll(String.class, sql.toString());
    }

    /**
     * 删除问卷
     */
    public void deleteQuestionnaireById(List<String> questionnaireIds){
        if (questionnaireIds != null && questionnaireIds.size() > 0){
            StringJoiner deleteSql = new StringJoiner("','","DELETE FROM dadui.dc_dm_questionnaire WHERE id IN('","')");
            questionnaireIds.forEach(id -> deleteSql.add(id));
            databaseConfig.dbAnalyze.update(deleteSql.toString());
        }
    }


    /**
     * 查询表是否存在
     */
    public List<String> queryTableName(List<String> codes) {
        List<String> tableNames = new ArrayList<>();
        if (codes != null && !codes.isEmpty()) {
            for (String code : codes) {
                final String tableName = "dc_dm_answer_sheet" + code;
                tableNames.add(tableName);
            }
            final StringBuilder sql = new StringBuilder("SELECT DISTINCT table_name FROM information_schema.TABLES WHERE table_name In(");
            methodBase.answerJoinSql(tableNames,sql);
            return databaseConfig.dbAnalyze.findAll(String.class, sql.toString());
        }
        return null;
    }

    /**
     * 查询表中指定的列是否存在
     */
    public String queryColumn(String tableName, String columnName) {

        final String sql = "SELECT DISTINCT COLUMN_NAME FROM " +
                "information_schema.COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = ?";
        Optional<String> optional = databaseConfig.dbAnalyze.findOptional(String.class, sql, tableName, columnName);
        return optional.orElse(null);
    }

    /**
     * 添加问卷信息
     */
    public void insertQuestionnaire(final String questionnaireSql) {
        databaseConfig.dbAnalyze.update(questionnaireSql);
    }

    /**
     * 查询问卷中最大的时间
     */
    public String queryQuestionnaireMaxUpdatetime() {
        final String sql = "SELECT MAX(q.CREATED_AT) FROM dadui.dc_dm_questionnaire q";
        Optional<LocalDateTime> optional = databaseConfig.dbAnalyze.findOptional(LocalDateTime.class, sql);
        if (optional.isPresent()) {
            return optional.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            return null;
        }
    }

    /**
     * 保存每一个更新周期中用户答题记录中的最大时间
     */
    public void insertAnswerInfoTime(String id, Long maxCreatedAt, String questionnaireCode) {
        final String sql = "INSERT INTO dadui.dc_dm_answer_info_time" +
                "(id,created_at,Answer_sheet_code) VALUES(?,?,?)";
        databaseConfig.dbAnalyze.update(sql, id, maxCreatedAt, questionnaireCode);
    }

    /**
     * 查询问卷信息
     */
    public List<QuestionnaireLater> queryQuestionnaireLater(List<String> groupIds) {
        StringBuilder sql = new StringBuilder("SELECT q.ID,q.ANSWER_SHEET_CODE,q.DADUI_ID FROM  " +
                "dadui.dc_dm_questionnaire q WHERE q.DADUI_ID IN(");
        methodBase.answerJoinSql(groupIds, sql);
        return databaseConfig.dbAnalyze.findAll(QuestionnaireLater.class, sql.toString());
    }

    /**
     * 查询问卷中updated_at最大值
     */
    public String questionnaireMaxUpdateAt() {
        final String sql = "SELECT MAX(q.UPDATED_AT) FROM dadui.dc_dm_questionnaire q";
        Optional<LocalDateTime> optional = databaseConfig.dbAnalyze.findOptional(LocalDateTime.class, sql);
        if (optional.isPresent()) {
            return optional.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            return null;
        }
    }

    /**
     * 查询问卷信息
     */
    public List<QuestionnaireLater> queryQuestionnaireLaters(List<String> groupIds) {
        StringBuilder sql = new StringBuilder("SELECT * FROM dadui.dc_dm_questionnaire q  WHERE q.DADUI_ID IN(");
        methodBase.answerJoinSql(groupIds, sql);
        return databaseConfig.dbAnalyze.findAll(QuestionnaireLater.class, sql.toString());
    }

    /**
     * 更新问卷信息
     */
    public void updateQuestionnaire(QuestionnaireLater questionnaireLater) {
        final StringBuilder sql = new StringBuilder();
        sql.append("UPDATE dadui.dc_dm_questionnaire SET project_name = '");
        sql.append(questionnaireLater.getProjectName());
        sql.append("',updated_at = '");
        sql.append(questionnaireLater.getUpdatedAtAs());
        sql.append("'\tWHERE id = '");
        sql.append(questionnaireLater.getId());
        sql.append("'");
        databaseConfig.dbAnalyze.update(sql.toString());
    }

    /**
     * 查询问卷中问题updated_at的最大值
     */
    public String questionMaxUpdateAt() {
        final String sql = "SELECT MAX(q.UPDATED_AT) FROM dadui.dc_dm_question q";
        Optional<LocalDateTime> optional = databaseConfig.dbAnalyze.findOptional(LocalDateTime.class, sql);
        if (optional.isPresent()) {
            return optional.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            return null;
        }
    }

    /**
     * 查询问卷的数量
     */
    public List<String> queryQuestionnaireDaduiIds() {
        final String sql = "SELECT q.DADUI_ID FROM dadui.dc_dm_questionnaire q";
        return databaseConfig.dbAnalyze.findAll(String.class, sql);
    }

    /**
     * 查询问卷Code
     */
    public List<QuestionnaireLater> queryQuestionnaireCodes(List<String> questionnareIds) {
        StringBuilder sql = new StringBuilder("SELECT q.ANSWER_SHEET_CODE,q.DADUI_ID FROM " +
                "dadui.dc_dm_questionnaire q WHERE q.DADUI_ID IN(");
        methodBase.answerJoinSql(questionnareIds, sql);
        return databaseConfig.dbAnalyze.findAll(QuestionnaireLater.class, sql.toString());
    }

    /**
     * 查询发布信息中的最大时间
     */
    public String queryPublishMaxTime() {
        final String sql = "SELECT MAX(p.created_at) FROM dadui.dc_dm_publish p";
        Optional<LocalDateTime> optional = databaseConfig.dbAnalyze.findOptional(LocalDateTime.class, sql);
        if (optional.isPresent()) {
            return optional.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            return null;
        }
    }

    /**
     * 查询问卷daduiId
     */
    public List<String> queryQuestionnaireDaduiIds(List<String> groupIds) {
        StringBuilder sql = new StringBuilder("SELECT q.DADUI_ID FROM dadui.dc_dm_questionnaire q WHERE q.DADUI_ID IN" +
                "(");
        methodBase.answerJoinSql(groupIds, sql);
        return databaseConfig.dbAnalyze.findAll(String.class, sql.toString());
    }

    /**
     * 查询问卷的ID
     */
    public List<String> findQuesitonnaireIdByDaduiIds(List<String> groupIds){
        StringBuilder sql = new StringBuilder("SELECT q.id FROM dadui.dc_dm_questionnaire q WHERE q.DADUI_ID IN" +
                "(");
        methodBase.answerJoinSql(groupIds, sql);
        return databaseConfig.dbAnalyze.findAll(String.class, sql.toString());
    }

    /**
     * 保存发布信息
     */
    public void savePublish(PublishLater publishLater, Date createTime, Date updateTime) {
        final String sql = "INSERT INTO \"dadui\".\"dc_dm_publish\" (\"id\",\"publish_id\",\"admin_id\",\"group_id\"," +
                "\"the_desc\",\"multi_entry\",\n" +
                "\"multi_ukey\",\"status\",\"created_at\",\"updated_at\") VALUES(?,?,?,?,?,?,?,?,?,?)";
        databaseConfig.dbAnalyze.update(sql, publishLater.getId(), publishLater.getPublishId(), String.valueOf(publishLater
                        .getAdminId()),
                publishLater.getGroupId(), publishLater.getTheDesc(), publishLater.getMultiEntry(),
                publishLater.getMultiUkey(), publishLater.getStatus(), createTime, updateTime);
    }

    /**
     * 查询所有发布ID
     */
    public List<String> queryPublishId() {
        final String sql = "SELECT DISTINCT p.publish_id FROM dadui.dc_dm_publish p";
        return databaseConfig.dbAnalyze.findAll(String.class, sql);
    }

    /**
     * 发布信息数据检测
     */
    public List<String> queryPublishByGroupId(List<String> publishIds) {
        StringBuilder sql = new StringBuilder("SELECT p.group_id FROM dadui.dc_dm_publish p WHERE p.publish_id IN(");
        methodBase.answerJoinSql(publishIds, sql);
        return databaseConfig.dbAnalyze.findAll(String.class, sql.toString());
    }
}
