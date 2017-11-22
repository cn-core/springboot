package com.idata3d.scheduler.dadui.service.impl;

import com.idata3d.scheduler.dadui.bean.ConversionLater.QuestionnaireLater;
import com.idata3d.scheduler.dadui.bean.Group;
import com.idata3d.scheduler.dadui.bean.Publish;
import com.idata3d.scheduler.dadui.bean.UserAnswerConcise;
import com.idata3d.scheduler.dadui.bean.UserAnswerInfo;
import com.idata3d.scheduler.dadui.service.DaduiDataSync;
import com.idata3d.scheduler.dadui.service.base.CommonMethod;
import com.idata3d.scheduler.dadui.service.base.QuestionnaireBase;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 新创建的套题&答题数据的实时同步
 * YZG on 2017/7/29
 */
@Service
@Log4j
public class NewQuestionnaireSync implements DaduiDataSync {

    private final QuestionnaireBase questionnaireBase;
    private final CommonMethod commonMethod;

    @Autowired
    public NewQuestionnaireSync(QuestionnaireBase questionnaireBase, CommonMethod commonMethod) {
        this.questionnaireBase = questionnaireBase;
        this.commonMethod = commonMethod;
    }


    /*-- 查找每一套问卷的答题数量
        SELECT DISTINCT c.info_id FROM user_answer_concise c WHERE c.info_id
        IN(SELECT i.id FROM user_answer_info i WHERE i.group_id = 'OomNJ94')*/

    /**
     * 同步套题信息(新增数据)
     */
    @Override
    public void conversionQue() {
        // 多数据源事物控制
        commonMethod.db.withVoidTransaction(tx -> {
            // 查询套题信息
            final String datetime = commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireMaxUpdatetime();
            List<Group> groups = commonMethod.daDuiMapper.queryGroupsByCreatedAt(datetime);
            if (groups != null && groups.size() > 0) {
                // 套题下的所有问题与选项
                List<Group> groupList = new ArrayList<>();
                // 保存套题数据
                questionnaireBase.saveDaduiData(groups, groupList);
                // 创建动态表
                questionnaireBase.dynamicCreateAnswerTable(groupList);
                System.out.println("套题数据同步完成!");
            }
            // 同步答题数据
            answerSheetSync();
            System.out.println("用户答题数据同步完成!");
            // 发布套题数据同步
            syncPublishInfo();
            System.out.println("答题数据同步完成!");
        });
    }

    /**
     * 存储答对的答题信息
     */
    private void answerSheetSync() {
        // 查询用户答题记录详细信息中的最大时间
        final Long createdAt = commonMethod.dynamicTableMapper.queryUserAnswerInfoMaxCreatedAt();
        // 用户答题记录详情
        List<UserAnswerInfo> userAnswerIfs = commonMethod.daDuiMapper.queryAnswerInfoByCreateAt(createdAt);
        if (userAnswerIfs.size() > 0) {
            List<String> userAnswerInfoIds = userAnswerIfs.stream().map(userAnswerInfo ->
            String.valueOf(userAnswerInfo.getId())).collect(Collectors.toList());
            // 查询用户答题记录
            List<UserAnswerConcise> userAnswerConciser =
                    commonMethod.daDuiMapper.queryUAConciseByInfoIdAndCreatedAt(userAnswerInfoIds,createdAt);
            if (userAnswerConciser.size() > 0) {
                // 用户答题记录中所有的问卷
                final List<String> groupIds = userAnswerIfs.stream().map(UserAnswerInfo::getGroupId)
                        .distinct().collect(Collectors.toList());
                // 查询问卷
                List<QuestionnaireLater> questionnaireLagers = commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireLater(groupIds);
                // sql拼接
                if (questionnaireLagers != null && questionnaireLagers.size() > 0)
                    questionnaireBase.sqlJoint(questionnaireLagers, userAnswerIfs, userAnswerConciser);
            }
        }
    }

    /**
     * 同步发布信息
     */
    private void syncPublishInfo() {
        // 查询发布信息中的最大时间
        final String time = commonMethod.questionnaireAnalyzeMapper.queryPublishMaxTime();
        // 同步所有发布数据
        List<Publish> publishes = commonMethod.daDuiMapper.queryPublishByTime(time);
        if (publishes != null && publishes.size() > 0) {
            // 查询所有发布信息对应的问卷是否存在
            List<String> groupIds = publishes.stream().map(Publish::getGroupId)
                    .distinct().collect(Collectors.toList());
            List<String> daduiIds = commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireDaduiIds(groupIds);
            questionnaireBase.disposePublished(publishes, daduiIds);
        }
    }
}
