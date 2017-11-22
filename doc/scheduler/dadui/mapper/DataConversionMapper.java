package com.idata3d.scheduler.dadui.mapper;

import com.idata3d.scheduler.dadui.bean.ConversionLater.QuestionLater;
import com.idata3d.scheduler.dadui.bean.ConversionLater.QuestionnaireLater;
import com.idata3d.scheduler.dadui.bean.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * yangzhiguo on 2017/7/19.
 */
@Mapper
@Repository
public interface DataConversionMapper
{
    /**
     * 创建动态表
     */
    void dynamicCreateAnswerSheet(Map<String,String> map);

    /**
     * 查询问卷信息
     */
    List<QuestionnaireLater> queryQuestionnaireLater(@Param("groupIds") List<String> groupIds);

    /**
     * 根据当前套题中问题ID查询转换后的问卷信息
     */
     List<QuestionLater> queryQuestions(@Param("questionIds") List<String> questionIds);

    /**
     * 保存答题数据
     */
    void insertAnswerSheet(Map<String,String> map);

    /**
     * 查询问卷是否存在
     */
    List<String> questionnairesExists(@Param("groupIds")List<String> groupIds);

    /**
     * 查询问题是否存在
     */
    List<String> questionExists(@Param("questionIds")List<String> questionIds);

    /**
     * 查询套题未同步的数据
     */
    List<Group> queryGroups(@Param("groupdIds")List<String> groupIds);

    /**
     * 查询问卷Code
     */
    List<QuestionnaireLater> queryQuestionnaireCodes(@Param("questionnaireIds")List<String> questionnaireIds);

    /**
     * 查询问卷daduiId
     */
    List<String> queryQuestionnaireDaduiIds(@Param("groupIds")List<String> groupIds);

    /**
     * 发布信息数据检测
     */
    List<String> queryPublishByGroupId(List<Integer> publishIds);
}
