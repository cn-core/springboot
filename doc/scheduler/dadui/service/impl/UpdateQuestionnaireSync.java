package com.idata3d.scheduler.dadui.service.impl;

import com.idata3d.scheduler.dadui.bean.ConversionLater.OptionsLater;
import com.idata3d.scheduler.dadui.bean.ConversionLater.QuestionLater;
import com.idata3d.scheduler.dadui.bean.ConversionLater.QuestionnaireLater;
import com.idata3d.scheduler.dadui.bean.Group;
import com.idata3d.scheduler.dadui.bean.Question;
import com.idata3d.scheduler.dadui.bean.QuestionOption;
import com.idata3d.scheduler.dadui.service.DaduiDataSync;
import com.idata3d.scheduler.dadui.service.base.CommonMethod;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 检测套题更新同步
 * YZG on 2017/7/29
 */
@Service
@Log4j
public class UpdateQuestionnaireSync implements DaduiDataSync {

    private final CommonMethod commonMethod;

    @Autowired
    public UpdateQuestionnaireSync(CommonMethod commonMethod) {
        this.commonMethod = commonMethod;
    }

    /**
     * 套题更新(问卷)  注:不可在原套题中添加或删除问题&选项,只能修改
     */
    @Override
    public void updateQuestionnaire() {
        // 多数据源事物控制
        commonMethod.db.withVoidTransaction(tx -> {
            // 检测答对中问卷&问题&选项是否有更新,如果有更新数据就同步数据
            checkGroupData();
            checkQuestion();
            checkOptions();
        });
    }

    /**
     * 检测答对中套题是否有更新
     */
    private void checkGroupData(){
        final String questionnaireAbateAt = commonMethod.questionnaireAnalyzeMapper.questionnaireMaxUpdateAt();
        if (!StringUtils.isEmpty(questionnaireAbateAt)) {
            final Long maxTime = commonMethod.methodBase.getTimestamp(questionnaireAbateAt);
            // 查询答对套题是否有大于maxTime的数据
            List<String> groupIds = commonMethod.daDuiMapper.queryGroupUpdatedAt(maxTime);
            if (groupIds != null && groupIds.size() > 0){
                List<QuestionnaireLater> questionnaireLaters = commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireLaters(groupIds);
                if (questionnaireLaters != null && questionnaireLaters.size() > 0){
                    List<String> groupIdList = questionnaireLaters.stream().map(QuestionnaireLater::getDaduiId).collect(Collectors.toList());
                    // 需要更新的套题数据
                    List<Group> groups = commonMethod.daDuiMapper.queryGroups(groupIdList);
                    for (QuestionnaireLater questionnaireLater : questionnaireLaters) {
                        Group oneGroup = groups.stream().filter(group -> group.getGroupId().equals(questionnaireLater.getDaduiId()))
                                .findFirst().orElse(null);
                        final String updateTime = commonMethod.methodBase.dateTimeFormat(oneGroup.getUpdatedAt());
                        questionnaireLater.setProjectName(oneGroup.getTheDesc());
                        questionnaireLater.setUpdatedAtAs(updateTime);
                        commonMethod.questionnaireAnalyzeMapper.updateQuestionnaire(questionnaireLater);
                    }
                }
            }
        }
    }

    /**
     * 检测答对中问题是否有更新
     */
    private void checkQuestion() {
        // 查询套题问题中updated_at的最大值
        final String questionUpdateAt = commonMethod.questionnaireAnalyzeMapper.questionMaxUpdateAt();
        if (!StringUtils.isEmpty(questionUpdateAt)) {
            final Long maxTime = commonMethod.methodBase.getTimestamp(questionUpdateAt);
            List<String> questionIds = commonMethod.daDuiMapper.queryQuestionUpdatedAt(maxTime);
            if (questionIds != null && questionIds.size() > 0) {
                List<QuestionLater> questionLaters = commonMethod.questionAnalyzeMapper.queryQuestionLatersByDaduiId(questionIds);
                if (questionLaters != null && questionLaters.size() > 0){
                    List<String> daduiIds = questionLaters.stream().map(QuestionLater::getDaduiId).collect(Collectors.toList());
                    // 需要更新的问题数据
                    List<Question> questions = commonMethod.daDuiMapper.queryQuestionsByQueId(daduiIds);
                    for (QuestionLater questionLater : questionLaters) {
                        Question oneQuestion = questions.stream().filter(question -> question.getQuestionId().equals(questionLater.getDaduiId()))
                                .findFirst().orElse(null);
                        final String updateTime = commonMethod.methodBase.dateTimeFormat(oneQuestion.getUpdatedAt());
                        questionLater.setQueTitle(oneQuestion.getContent());
                        questionLater.setUpdatedAtAs(updateTime);
                        commonMethod.questionAnalyzeMapper.updateQuestion(questionLater);
                    }
                }
            }
        }
    }

    /**
     * 检测答对中选项是否有更新
     */
    private void checkOptions() {
        // 查询套题问题中updated_at的最大值
        final String optionsUpdateAt = commonMethod.optionAnalyzeMapper.OptionsMaxUpdateAt();
        if (!StringUtils.isEmpty(optionsUpdateAt)) {
            final Long maxTime = commonMethod.methodBase.getTimestamp(optionsUpdateAt);
            List<String> optionsIds = commonMethod.daDuiMapper.queryOptionsUpdatedAt(maxTime);
            if (optionsIds != null && optionsIds.size() > 0) {
                List<OptionsLater> optionsLaters = commonMethod.optionAnalyzeMapper.queryOptionsLatersByDaduiId(optionsIds);
                if (optionsLaters != null && optionsLaters.size() > 0) {
                    List<String> daduiIds = optionsLaters.stream().map(OptionsLater::getDaduiId).collect(Collectors.toList());
                    // 需要更新的选项数据
                    List<QuestionOption> questionOptions = commonMethod.daDuiMapper.queryOptionsById(daduiIds);
                    for (OptionsLater optionsLater : optionsLaters) {
                        QuestionOption oneQuestionOption = questionOptions.stream().filter(questionOption -> String.valueOf(questionOption.getId())
                                .equals(optionsLater.getDaduiId())).findFirst().orElse(null);
                        final String updateTime = commonMethod.methodBase.dateTimeFormat(oneQuestionOption.getUpdatedAt());
                        optionsLater.setOptValue(oneQuestionOption.getContent());
                        optionsLater.setUpdatedAtAs(updateTime);
                        commonMethod.optionAnalyzeMapper.updateOptions(optionsLater);
                    }
                }
            }
        }
    }
}
