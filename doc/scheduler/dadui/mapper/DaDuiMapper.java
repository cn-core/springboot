package com.idata3d.scheduler.dadui.mapper;

import com.idata3d.scheduler.dadui.bean.*;
import com.idata3d.scheduler.dadui.service.base.DatabaseConfig;
import com.idata3d.scheduler.dadui.service.base.MethodBase;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * yangzhiguo on 2017/8/23.
 */
@Component
public class DaDuiMapper {
    
    private final MethodBase methodBase;
    private final DatabaseConfig databaseConfig;

    @Autowired
    public DaDuiMapper(MethodBase methodBase, DatabaseConfig databaseConfig) {
        this.methodBase = methodBase;
        this.databaseConfig = databaseConfig;
    }

    // ===========================================套题======================================================

    /**
     * 查询套题信息中所有的groupId
     */
    public List<String> queryGroupIds(){
        return databaseConfig.db.findAll(String.class, "SELECT g.group_id FROM `group` g");
    }

    /**
     * 根据套题的groupId查询套题信息
     */
    public List<Group> queryGroups(List<String> groupIds) {
        StringBuilder sql = new StringBuilder("SELECT * FROM `group` g WHERE g.group_id IN(");
        methodBase.answerJoinSql(groupIds, sql);
        return databaseConfig.db.findAll(Group.class, sql.toString());
    }

    /**
     * 根据时间查询套题信息
     */
    public List<Group> queryGroupsByCreatedAt(final String datetime){
        final Long createdAt = methodBase.getTimestamp(datetime);
        String queryGroup = "SELECT * FROM `group` g ";
        if (createdAt != null && createdAt > 0) {
            // 添加时间戳的查询条件
            queryGroup = queryGroup + " WHERE g.created_at > " + createdAt;
        }
        return databaseConfig.db.findAll(Group.class, queryGroup);
    }

    /**
     * 查询套题中updated_at大于给定的时间数据
     */
    public List<String> queryGroupUpdatedAt(Long maxTime){
        return databaseConfig.db.findAll(String.class,"SELECT g.group_id FROM `group` g WHERE g.updated_at > ?",maxTime);
    }


    // ===========================================问题======================================================

    /**
     * 根据时间查询问题信息
     */
    public List<Question> queryQuestionsByCreatedAt(final String dateTime){
            final Long createdAt = methodBase.getTimestamp(dateTime);
            String queryGroup = "SELECT * FROM question q ";
            if (createdAt != null && createdAt > 0) {
                // 添加时间戳的查询条件
                queryGroup = queryGroup + " WHERE q.created_at > " + createdAt;
            }
            return databaseConfig.db.findAll(Question.class, queryGroup);
    }

    /**
     * 查询答对中问题总数
     */
    public List<Question> queryQuestionQueIds() {
        String sql = "SELECT q.question_id,q.group_id FROM question q";
        return databaseConfig.db.findAll(Question.class,sql);
    }

    /**
     *  根据套题groupId查询问题信息
     */
    public List<Question> queryQuestions(List<String> groupIds){
        final String findQuestionSql = "SELECT * FROM question q WHERE q.group_id in('";
        StringJoiner sql = new StringJoiner("','",findQuestionSql,"')");
        groupIds.forEach(groupId -> sql.add(groupId));
        return databaseConfig.db.findAll(Question.class,sql.toString());
    }

    /**
     * 根据问题ID查询问卷ID
     */
    public List<String> queryQuestionQueIds(List<String> questionIds) {
        StringBuilder sql = new StringBuilder("SELECT DISTINCT q.group_id FROM question q WHERE q.question_id IN(");
        methodBase.answerJoinSql(questionIds,sql);
        return databaseConfig.db.findAll(String.class,sql.toString());
    }

    /**
     * 根据questionId查询指定的问题
     */
    public List<Question> queryQuestionsByQueId(List<String> questionIds) {
        StringBuilder sql = new StringBuilder("SELECT * FROM question q WHERE q.question_id IN(");
        methodBase.answerJoinSql(questionIds,sql);
        return databaseConfig.db.findAll(Question.class,sql.toString());
    }

    /**
     * 删除问卷信息
     */
    public void deleteQuetionByGroupId(List<String> groupIdAll) {
        StringJoiner deleteSql = new StringJoiner("','",
                "DELETE FROM dadui.dc_dm_question where questionnaire_id IN('","')");
        groupIdAll.forEach(groupdId -> deleteSql.add(groupdId));
        databaseConfig.dbAnalyze.update(deleteSql.toString());
    }

    /**
     * 查询问题中updated_at大于给定的时间数据
     */
    public List<String> queryQuestionUpdatedAt(Long maxTime) {
        return databaseConfig.db.findAll(String.class, "SELECT q.question_id FROM question q WHERE q.updated_at > ?", maxTime);
    }


    // ===========================================选项信息===============================================

    /**
     * 根据时间查询选项信息
     */
    public List<QuestionOption> queryOptionsByCreatedAt(String dateTime){
        final Long createdAt = methodBase.getTimestamp(dateTime);
        String queryGroup = "SELECT * FROM question_option o";
        if (createdAt != null && createdAt > 0) {
            // 添加时间戳的查询条件
            queryGroup = queryGroup + " WHERE o.created_at > " + createdAt;
        }
        return databaseConfig.db.findAll(QuestionOption.class, queryGroup);
    }

    /**
     * 查询选项中updated_at大于给定的时间数据
     */
    public List<String> queryOptionsUpdatedAt(Long maxTime) {
        List<Integer> questionIds = databaseConfig.db.findAll(Integer.class, "SELECT q.id FROM question_option q WHERE q.updated_at > ?", maxTime);
        if (questionIds != null && questionIds.size() > 0){
            return questionIds.stream().map(questionId -> String.valueOf(questionId)).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 查询选项的总数量(选项ID,问题的ID)
     */
    public List<QuestionOption> queryOptions() {
        final String sql = "SELECT o.id,o.question_id FROM question_option o";
        return databaseConfig.db.findAll(QuestionOption.class,sql);
    }

    /**
     * 根据问题questionId查询选项信息
     */
    public List<QuestionOption> queryOptionsByQueId(List<String> questionIds) {
        final String optionsSql = "SELECT * FROM question_option o WHERE o.question_id IN('";
        StringJoiner sql = new StringJoiner("','",optionsSql,"')");
        questionIds.forEach(questionId -> sql.add(questionId));
        return databaseConfig.db.findAll(QuestionOption.class,sql.toString());
    }

    /**
     * 根据选项ID查询选项信息
     */
    public List<QuestionOption> queryOptionsById(List<String> ids) {
        StringJoiner sql = new StringJoiner("','","SELECT * FROM question_option q WHERE q.id IN('","')");
        ids.forEach(id -> sql.add(id));
        return databaseConfig.db.findAll(QuestionOption.class,sql.toString());
    }

    /**
     * 通过问卷ID删除选项
     */
    public void deleteOptionsByGroupId(List<String> questionnaireIds){
        String sql = "DELETE FROM dadui.dc_dm_options WHERE question_id " +
                "IN(SELECT q.id FROM dadui.dc_dm_question q where q.questionnaire_id IN('";
        StringJoiner deleteSql = new StringJoiner("','",sql,"'))");
        questionnaireIds.forEach(questionnaireId -> deleteSql.add(questionnaireId));
        databaseConfig.dbAnalyze.update(deleteSql.toString());
    }


    // =======================================问卷的发布信息==============================================

    /**
     * 查询发布信息的ID
     */
    public List<String> queryPublishId(){
        return databaseConfig.db.findAll(String.class, "SELECT DISTINCT p.publish_id FROM publish p");
    }

    /**
     * 根据publishId查询发布信息
     */
    public List<Publish> queryPublishs(List<String> publishIds){
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM publish p WHERE p.publish_id IN(");
        methodBase.answerJoinSql(publishIds, stringBuilder);
        return databaseConfig.db.findAll(Publish.class, stringBuilder.toString());
    }

    /**
     * 根据时间查询发布信息
     */
    public List<Publish> queryPublishByTime(final String time){
        StringBuilder publishSql = new StringBuilder("SELECT * FROM publish p ");
        if (!StringUtils.isEmpty(time)) {
            Long timestamp = methodBase.getTimestamp(time);
            publishSql.append("WHERE p.created_at > ").append(timestamp);
        }
        return databaseConfig.db.findAll(Publish.class, publishSql.toString());
    }

    /**
     * 根据套题ID查询发布ID
     */
    public List<Publish> queryPublishIdByGroupId(List<String> groupIds) {
        StringBuilder publishSql = new StringBuilder("SELECT p.publish_id,p.group_id \tFROM publish p  WHERE p" +
                ".group_id IN(");
        methodBase.answerJoinSql(groupIds, publishSql);
        return databaseConfig.db.findAll(Publish.class, publishSql.toString());
    }

    /**
     * 查询发布信息
     */
    public List<Publish> queryPublish(){
        final String sql = "SELECT p.id,p.publish_id,p.group_id FROM publish p";
        return databaseConfig.db.findAll(Publish.class,sql);
    }



    // =======================================答题信息=================================================

    /**
     * 查询答对总的答题数量
     */
    public Integer queryAnswerNum(String groupId){
        // 查询答对答题数量
        Optional<Integer> optional = databaseConfig.db.findOptional(Integer.class,
                "SELECT COUNT(DISTINCT c.info_id) FROM user_answer_concise c \n" +
                        "WHERE c.info_id IN(SELECT i.id FROM user_answer_info i WHERE i.group_id \n" +
                        "IN(SELECT g.group_id FROM `group` g WHERE g.group_id = ?))", groupId);
        return optional.orElse(null);
    }

    /**
     * 根据用户答题记录InfoId查询答题详细信息
     */
    public List<UserAnswerConcise> queryUAConciseByInfoIds(List<String> userAnswerInfoIds) {
        // 根据用户答题记录查询套题的用户答题记录
        StringBuilder userAnswerInfoSql =
                new StringBuilder("SELECT * FROM user_answer_concise c WHERE c.info_id IN(");
        methodBase.answerJoinSql(userAnswerInfoIds, userAnswerInfoSql);
        return databaseConfig.db.findAll(UserAnswerConcise.class, userAnswerInfoSql.toString());
    }

    /**
     * 根据用户答题记录InfoId与时间查询答题详细信息
     */
    public List<UserAnswerConcise> queryUAConciseByInfoIdAndCreatedAt(List<String> userAnswerInfoIds,Long timestamp){
        StringBuilder stringBuilderSql = new StringBuilder("SELECT * FROM user_answer_concise c WHERE c.info_id IN(");
        methodBase.answerJoinSql(userAnswerInfoIds, stringBuilderSql);
        if (timestamp != null && timestamp > 0) {
            stringBuilderSql.append(" AND c.created_at > ").append(timestamp);
        }
        return databaseConfig.db.findAll(UserAnswerConcise.class, stringBuilderSql.toString());
    }

    /**
     * 查询用户答题记录
     */
    @NotNull
    public List<UserAnswerInfo> queryAnswerInfoByIds(List<String> userAnswerInfoIds) {
        StringBuilder userAnswerInifSql = new StringBuilder("SELECT * FROM user_answer_info i WHERE i.id IN(");
        methodBase.answerJoinSql(userAnswerInfoIds, userAnswerInifSql);
        return databaseConfig.db.findAll(UserAnswerInfo.class, userAnswerInifSql.toString());
    }

    /**
     * 根据时间戳查询用户答题记录
     */
    public List<UserAnswerInfo> queryAnswerInfoByCreateAt(Long timestamp) {
        String queryUserAnswerInfo = "SELECT * FROM user_answer_info i ";
        if (timestamp != null && timestamp > 0) {
            queryUserAnswerInfo += "WHERE i.created_at > " + timestamp;
        }
        return databaseConfig.db.findAll(UserAnswerInfo.class, queryUserAnswerInfo);
    }

    /**
     * 根据套题groupId查询用户答题记录
     */
    public List<UserAnswerInfo> queryUserAnswerInfoByGroupId(List<String> groupIds){
        final String findUAISql = "SELECT * FROM user_answer_info i WHERE i.group_id IN('";
        StringJoiner sql = new StringJoiner("','",findUAISql,"')");
        groupIds.forEach(groupId -> sql.add(groupId));
        return databaseConfig.db.findAll(UserAnswerInfo.class ,sql.toString());
    }

    /**
     * 根据套题的group_id查询用户答题记录的ID
     */
    public List<String> findUserAnswerInfoIdByGroupId(List<String> groupIds){
        if (groupIds != null && groupIds.size() > 0){
            final String findUAIId = "SELECT i.id FROM user_answer_info i WHERE i.group_id IN('";
            StringJoiner sql = new StringJoiner("','",findUAIId,"')");
            return databaseConfig.db.findAll(String.class,sql.toString());
        }
        return null;
    }
}
