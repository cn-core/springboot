package com.idata3d.scheduler.dadui.service.impl;

import com.idata3d.scheduler.dadui.bean.ConversionLater.QuestionnaireLater;
import com.idata3d.scheduler.dadui.bean.*;
import com.idata3d.scheduler.dadui.service.DaduiDataSync;
import com.idata3d.scheduler.dadui.service.base.CommonMethod;
import com.idata3d.scheduler.dadui.service.base.QuestionnaireBase;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 答对数据全量检测
 * YZG on 2017/8/25
 */
@Service
@Log4j
public class FullAmountDetectionSync implements DaduiDataSync{

    private final QuestionnaireBase questionnaireBase;
    private final CommonMethod commonMethod;

    @Autowired
    public FullAmountDetectionSync(QuestionnaireBase questionnaireBase, CommonMethod commonMethod) {
        this.questionnaireBase = questionnaireBase;
        this.commonMethod = commonMethod;
    }

    /**
     * 全量检测答对数据
     *      检测答对与同步后的问卷&问题&选项是否一致,如不一致将问题(Error)数据同步:
     *          1:套题不一致
     *          2:套题一致,但是问题&选项不一致,
     */
    @Override
    public void inspectionDaduiData() {
        // 多数据源事物控制
        commonMethod.db.withVoidTransaction(tx -> {
            // 有问题的套题
            List<String> groupIds = checkDaduiData();
            if (groupIds != null && groupIds.size() > 0) {
                List<QuestionnaireLater> questionnaireLaters = commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireLater(groupIds);


                List<String> daduiIds = questionnaireLaters.stream().map(QuestionnaireLater::getDaduiId).collect
                        (Collectors.toList());
                groupIds.removeAll(daduiIds);
                // 1:套题不一致(同步套题数据)
                if (groupIds.size() > 0) {
                    dataProcessing(groupIds);
                }
                // 2:套题一致,但是问题&选项不一致
                if (questionnaireLaters.size() > 0) {
                    // 删除不完整的套题数据
                    deleteData(questionnaireLaters);
                    List<String> groupIdList = questionnaireLaters.stream().map(QuestionnaireLater::getDaduiId)
                            .collect(Collectors.toList());
                    dataProcessing(groupIdList);
                }
            }
            // 发布数据检测
            publishDataDetect();
            // 答题数据检测
            answerDataDetect();
        });
    }

    /**
     * 1:同步套题&问题&选项数据
     * 2:创建动答题动态表
     * 3:同步答题数据
     */
    private void dataProcessing(List<String> groupIds) {
        // 同步套题&问题&选项数据
        List<Group> groups = dataSync(groupIds);
        // 创建动答题动态表
        questionnaireBase.dynamicCreateAnswerTable(groups);
        // 同步答题数据
        answerSheetSync(groupIds);
    }

    /**
     * 答题数据检测
     */
    private void answerDataDetect() {
        List<String> groupIds = commonMethod.daDuiMapper.queryGroupIds();
        if (groupIds != null && groupIds.size() > 0){
            List<String> daduiIds = commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireDaduiIds();
            groupIds.retainAll(daduiIds);
            if (groupIds.size() > 0){
                List<QuestionnaireLater> questionnaireLaters =
                        commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireLaters(groupIds);
                // 检测交集套题对应的答题数据是否一致
                for (String groupId : groupIds) {
                    final Integer daduiConciseNum = commonMethod.daDuiMapper.queryAnswerNum(groupId);
                    QuestionnaireLater questionnaire = questionnaireLaters.stream().filter(questionnaireLater
                            -> questionnaireLater.getDaduiId().equals(groupId)).findFirst().orElse(null);
                    if (questionnaire != null){
                        final String answerSheetCode = questionnaire.getAnswerSheetCode();
                        final Integer idatawayConciseNum =
                                commonMethod.questionnaireAnalyzeMapper.queryAnswerNum(answerSheetCode);
                        if (!Objects.equals(daduiConciseNum, idatawayConciseNum)){
                            // 该问卷答题数据不一致,删除该问卷答题数据后重新同步
                            commonMethod.dynamicTableMapper.deleteAnswerSheetData(answerSheetCode);
                            // 同步该问卷的答题数据
                            answerDataSync(groupId,questionnaireLaters);
                        }
                    }
                }
            }
        }
    }

    /**
     * 同步指定问卷的答题数据
     */
    private void answerDataSync(final String groupId, List<QuestionnaireLater> questionnaireLaters) {
        List<UserAnswerInfo> userAnswerInfos =
                commonMethod.daDuiMapper.queryUserAnswerInfoByGroupId(Collections.singletonList(groupId));
        List<String> userAnswerInfoIds = userAnswerInfos.stream().map(userAnswerInfo ->
                String.valueOf(userAnswerInfo.getId())).collect(Collectors.toList());
        List<UserAnswerConcise> userAnswerConcises = commonMethod.daDuiMapper.queryUAConciseByInfoIds(userAnswerInfoIds);
        if (userAnswerConcises != null && userAnswerConcises.size() > 0){
            // 数据处理拼接
            questionnaireBase.sqlJoint(questionnaireLaters, userAnswerInfos, userAnswerConcises);
        }
    }

    /**
     * 答题数据同步
     */
    private void answerSheetSync(List<String> groupIds) {
        List<UserAnswerInfo> userAnswerInfos = commonMethod.daDuiMapper.queryUserAnswerInfoByGroupId(groupIds);
        if (userAnswerInfos != null && userAnswerInfos.size() > 0){
            List<String> userAnswerInfoIds = userAnswerInfos.stream().map(userAnswerInfo -> String.valueOf(userAnswerInfo.getId()))
                    .collect(Collectors.toList());
            List<UserAnswerConcise> userAnswerConcises = commonMethod.daDuiMapper.queryUAConciseByInfoIds(userAnswerInfoIds);
            if (userAnswerConcises != null && userAnswerConcises.size() > 0){
                List<QuestionnaireLater> questionnaireLagers = commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireLater(groupIds);
                questionnaireBase.sqlJoint(questionnaireLagers, userAnswerInfos, userAnswerConcises);
            }
        }
    }

    /**
     * 检测为同步的套题
     */
    private List<String> checkDaduiData(){
        List<String> groupIdList = new ArrayList<>();
        // 选项数据检测
        List<String> groupIdsOption = optionsDataDetect();
        if (groupIdsOption != null){
            groupIdList.addAll(groupIdsOption);
        }
        // 问题数据检测
        List<String> groupIdsQuestion = queryDataDetect();
        if (groupIdsQuestion != null && groupIdsQuestion.size() > 0){
            groupIdList.addAll(groupIdsQuestion);
        }
        // 套题数据检测
        List<String> groupIds = groupDataDetect();
        if (groupIds != null && groupIds.size() > 0){
            groupIdList.addAll(groupIds);
        }
        // 未同步的答对中的套题数据
        return groupIdList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 同步答对数据
     */
    private List<Group> dataSync(List<String> groupIds) {
        // 套题信息SQL
        StringBuilder questionnaireSql = new StringBuilder();
        questionnaireSql.append("INSERT INTO dadui.dc_dm_questionnaire(id,project_name,answer_sheet_code," +
                "publish_id,dadui_id,created_at,updated_at) VALUES");
        // 问题信息SQL
        StringBuilder questionSql = new StringBuilder();
        questionSql.append("INSERT INTO dadui.dc_dm_question(id,que_title,que_type,que_code,sort_num," +
                "questionnaire_id,dadui_id,created_at,updated_at) VALUES ");
        // 选项信息SQL
        StringBuilder optionsSql = new StringBuilder();
        optionsSql.append("INSERT INTO dadui.dc_dm_options(id,opt_code,sort_num,opt_value,question_id,dadui_id," +
                "created_at,updated_at) VALUES");
        List<Group> groupList = new ArrayList<>();
        // 套题
        List<Group> groups = commonMethod.daDuiMapper.queryGroups(groupIds);
        // 问题
        List<Question> questions = commonMethod.daDuiMapper.queryQuestions(groupIds);
        // 选项
        List<String> questionIds = questions.stream().map(Question::getQuestionId).collect(Collectors.toList());
        List<QuestionOption> questionOptions = commonMethod.daDuiMapper.queryOptionsByQueId(questionIds);
        // 数据处理
        final Integer maxCode = questionnaireBase.daduiDataDispose(groups, groupList, questions, questionOptions, questionnaireSql, questionSql, optionsSql);
        System.out.println(questionnaireSql.toString());
        System.out.println(questionSql.toString());
        System.out.println(optionsSql.toString());
        questionnaireBase.saveDadui(questionnaireSql, questionSql, optionsSql);
        commonMethod.codeAnalyzeMapper.saveMaxCode(maxCode);
        return groupList;
    }

    /**
     * 删除未同步数据中在转换后数据中已存在的数据
     */
    private void deleteData(List<QuestionnaireLater> questionnaireLaters){
        List<String> questionnaireIds =
                questionnaireLaters.stream().map(QuestionnaireLater::getId).collect(Collectors.toList());

        // 删除不完整的数据
        commonMethod.daDuiMapper.deleteOptionsByGroupId(questionnaireIds);
        commonMethod.daDuiMapper.deleteQuetionByGroupId(questionnaireIds);
        // 查询问卷的Code
        List<String> codes
                = questionnaireLaters.stream().map(QuestionnaireLater::getAnswerSheetCode).collect(Collectors.toList());
        commonMethod.dynamicTableMapper.deleteAnserTable(codes);
        commonMethod.questionnaireAnalyzeMapper.deleteQuestionnaireById(questionnaireIds);
    }

    /**
     * 套题数据检测
     */
    private List<String> groupDataDetect() {
        // 套题数量
        List<String> groupIds = commonMethod.daDuiMapper.queryGroupIds();
        if (groupIds != null && groupIds.size() > 0){
            // 问卷数量
            List<String> daduiIds = commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireDaduiIds();
            groupIds.removeAll(daduiIds);
            // log.info("检测到答对的套题中有未同步的数据:" + groupIds.toString());
            return groupIds;
        }
        return null;
    }

     /**
     * 问题数据检测
     */
    private List<String> queryDataDetect() {
        // 答对问题数量
        List<Question> questions = commonMethod.daDuiMapper.queryQuestionQueIds();
        if (questions != null && questions.size() > 0){
            List<String> questionIds = questions.stream().map(Question::getQuestionId).collect(Collectors.toList());
            // 增量同步后问题数量
            List<String> daduiIds = commonMethod.questionAnalyzeMapper.queryQuestionDaduiIds();
            questionIds.removeAll(daduiIds);
            if (questionIds.size() > 0){
                // 未同步的问题数据
                return questions.stream().filter(question -> questionIds.contains(question.getQuestionId()))
                        .map(Question::getGroupId).distinct().collect(Collectors.toList());
            }
        }
        return null;
    }

    /**
     * 选项数据检测
     */
    private List<String> optionsDataDetect() {
        // 答对选项数量
        List<QuestionOption> questionOptions = commonMethod.daDuiMapper.queryOptions();
        if (questionOptions != null && questionOptions.size() > 0){
            List<String> optionIds = questionOptions.stream().map(questionOption -> String.valueOf(questionOption.getId()))
                    .collect(Collectors.toList());
            // 同步后的选项数量
            List<String> daduiIds = commonMethod.optionAnalyzeMapper.queryOptions();
            optionIds.removeAll(daduiIds);
            if (optionIds.size() > 0){
                List<String> questionIds = questionOptions.stream().filter(questionOption ->
                        optionIds.contains(String.valueOf(questionOption.getId()))).map(QuestionOption::getQuestionId).distinct()
                        .collect(Collectors.toList());
                // 通过问卷ID查询所对应的套题
                return commonMethod.daDuiMapper.queryQuestionQueIds(questionIds);
            }
        }
        return null;
    }

    /**
     * 发布数据检测
     */
    private void publishDataDetect() {
        List<Publish> publishes = commonMethod.daDuiMapper.queryPublish();
        if (publishes != null && publishes.size() > 0){
            List<String> daduiPublishIds = publishes.stream().map(Publish::getPublishId).collect(Collectors.toList());
            List<String> publishIds = commonMethod.questionnaireAnalyzeMapper.queryPublishId();
            daduiPublishIds.removeAll(publishIds);
            if (daduiPublishIds.size() > 0){
                List<String> groupIds = publishes.stream().filter(publish -> daduiPublishIds.contains(publish.getPublishId()))
                        .map(Publish::getGroupId).collect(Collectors.toList());
                List<Publish> publishList = commonMethod.daDuiMapper.queryPublishs(daduiPublishIds);
                List<String> daduiIds = commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireDaduiIds(groupIds);
                questionnaireBase.disposePublished(publishList, daduiIds);
            }
        }
    }
}
