package com.idata3d.scheduler.dadui.service.base;

import com.idata3d.core.util.DbTool;
import com.idata3d.scheduler.dadui.bean.*;
import com.idata3d.scheduler.dadui.bean.ConversionLater.OptionsLater;
import com.idata3d.scheduler.dadui.bean.ConversionLater.PublishLater;
import com.idata3d.scheduler.dadui.bean.ConversionLater.QuestionLater;
import com.idata3d.scheduler.dadui.bean.ConversionLater.QuestionnaireLater;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

/**
 * YZG on 2017/7/30
 */
@Component
@Log4j
public class QuestionnaireBase {

    @Autowired
    private CommonMethod commonMethod;

    /**
     * 保存答对数据
     */
    public void saveDaduiData(List<Group> groups, List<Group> groupList) {
        // 查询问卷
        List<Question> questionList = queryQuestions();
        // 查询选项
        List<QuestionOption> questionOptionList = queryQuestionOptions();
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

        // 数据处理(拼接)
        Integer code = daduiDataDispose(groups, groupList, questionList, questionOptionList,
                questionnaireSql, questionSql, optionsSql);
        saveDadui(questionnaireSql, questionSql, optionsSql);
        commonMethod.codeAnalyzeMapper.saveMaxCode(code);
    }

    /**
     * 答对数据处理
     *
     * @param groups 需同步的套题数据
     * @param groupList 套题下的所有问题与选项
     * @param questionList 需同步的问题数据
     * @param questionOptionList 需同步的选项数据
     * @param questionnaireSql 问卷Insert前缀
     * @param questionSql 问题Insert前缀
     * @param optionsSql 选项Insert前缀
     * @return 自定义Code
     */
    public Integer daduiDataDispose(List<Group> groups, List<Group> groupList, List<Question> questionList,
                                    List<QuestionOption> questionOptionList, StringBuilder questionnaireSql,
                                    StringBuilder questionSql, StringBuilder optionsSql) {
        // 查询套题的发布时间
        List<String> groupIds = groups.stream().map(Group::getGroupId).collect(Collectors.toList());
        List<Publish> publishes = commonMethod.daDuiMapper.queryPublishIdByGroupId(groupIds);
        List<String> questionnaireDaduiIds = commonMethod.questionnaireAnalyzeMapper.queryQuestionnaireDaduiIds
                (groupIds);
        // Code最大值
        Integer questionnaireCode = getMaxCode();
        // 问卷
        for (Group group : groups) {
            final String exists = questionnaireDaduiIds.stream().filter(date -> date.equals(group.getGroupId()))
                    .findFirst().orElse(null);
            if (exists == null) {
                questionnaireCode++;
                String code = DbTool.valiCode(questionnaireCode);
                // 拼接套题信息
                boolean publishIdExist = jointQuestionnaire(group, code, questionnaireSql, publishes);
                if (publishIdExist) {
                    // 筛选该问卷下的问题信息
                    List<Question> questions = questionList.stream().filter(question -> question.getGroupId()
                            .equals(group.getGroupId())).collect(Collectors.toList());
                    // 问题数据处理
                    questionDataOption(questionOptionList, questionSql, optionsSql, group, questions);
                    groupList.add(group);
                } else {
                    log.info(group.getGroupId() + "套题没有发布ID!");
                }
            }
        }
        return questionnaireCode;
    }

    /**
     * 问题数据拼接
     */
    private void questionDataOption(List<QuestionOption> questionOptionList, StringBuilder questionSql, StringBuilder
            optionsSql, Group group, List<Question> questions) {//String questionnaireId,
        if (questions.size() > 0) {
            int num = 0;
            for (Question question : questions) {       // 问题
                num++;
                final String code = DbTool.genCode(num);
                // 拼接问题信息
                final String questionId = jointQuestion(code, question, group, questionSql);
                // 根据问题读取第三方选项信息
                List<QuestionOption> questionOptions = questionOptionList.stream().filter(questionOption ->
                        questionOption.getQuestionId().equals(question.getQuestionId()))
                        .collect(Collectors.toList());
                optionsDataDispose(optionsSql, question, questionId, questionOptions);
            }
        }
    }

    /**
     * 选项数据拼接
     */
    private void optionsDataDispose(StringBuilder optionsSql, Question question, String questionId,
                                    List<QuestionOption> questionOptions) {
        if (questionOptions.size() > 0) {       // 选项
            int num = 0;
            for (QuestionOption questionOption : questionOptions) {
                num++;
                final String code = DbTool.genCode(num);
                // 拼接选项信息
                jointOption(code, question, questionId, questionOption, optionsSql);
            }
        }
    }

    /**
     * 获取最大值Code
     */
    private Integer getMaxCode() {
        Integer code;
        // 查询code最大值
        Integer maxCode = commonMethod.codeAnalyzeMapper.queryMaxCode();
        code = maxCode == null ? 0 : maxCode;
        return code;
    }

    /**
     * 保存问卷&问题&选项
     */
    public void saveDadui(StringBuilder questionnaireSql, StringBuilder questionSql, StringBuilder optionsSql) {
        if (',' == (questionnaireSql.charAt(questionnaireSql.toString().length() - 1))) {
            questionnaireSql.deleteCharAt(questionnaireSql.lastIndexOf(","));
            commonMethod.questionnaireAnalyzeMapper.insertQuestionnaire(questionnaireSql.toString());
        }
        if (',' == (questionSql.charAt(questionSql.toString().length() - 1))) {
            questionSql.deleteCharAt(questionSql.lastIndexOf(","));
            commonMethod.questionAnalyzeMapper.insertQuestion(questionSql.toString());
        }
        if (',' == (optionsSql.charAt(optionsSql.toString().length() - 1))) {
            optionsSql.deleteCharAt(optionsSql.lastIndexOf(","));
            commonMethod.optionAnalyzeMapper.insertOption(optionsSql.toString());
        }
    }

    /**
     * 拼接问卷信息
     *
     * @param group 问卷
     * @param genCode 问卷Code
     */
    private boolean jointQuestionnaire(Group group, String genCode, StringBuilder questionnaireSql,
                                       List<Publish> publishes) {
        Optional<Publish> optional = publishes.stream().filter(publish -> publish.getGroupId()
                .equals(group.getGroupId())).findFirst();
        if (!optional.isPresent()) {
            log.info(group.getGroupId() + "问卷没有指定的发布时间!");
            return false;
        }
        Publish publish = optional.get();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String createdAt = simpleDateFormat.format(group.getCreatedAt() * 1000L);
        final String updatedAt = simpleDateFormat.format(group.getUpdatedAt() * 1000L);

        final String id = DbTool.uuid();
        questionnaireSql.append("(").append("'").append(id).append("'").append(",").append("'").append(group
                .getTheDesc()).append("'").append(",").append("'").append(genCode).append("'").append(",").append("'")
                .append(publish.getPublishId()).append("'").append(",").append("'").append(group.getGroupId())
                .append("'").append(",").append("'").append(createdAt).append("'").append(",").append("'")
                .append(updatedAt).append("'").append("),");
        group.setNewId(id);
        group.setNewCode(genCode);
        return true;
    }

    /**
     * 拼接问题信息
     *
     * @param code Code最大值
     * @param question 问题信息
     */
    private String jointQuestion(String code, Question question, Group group, StringBuilder questionSql) {
        final Integer questionType = question.getQuestionType();
        if (questionType == null || questionType == 0) {
            throw new RuntimeException("问题类型错误!");
        }
        Question.ProblemTypes quesType = Question.ProblemTypes.NULL;
        // 选择题
        if (1 <= questionType && questionType <= 10) {
            if (1 == questionType) {
                // 单选
                quesType = Question.ProblemTypes.ONE_CHOICE;
            } else if (2 == questionType) {
                // 多选
                quesType = Question.ProblemTypes.MORE_CHOICE;
            } else if (3 == questionType) {
                // 图片选择
                quesType = Question.ProblemTypes.PICTURES_CHOOSE;
            }
            // 填空题
        } else if (11 <= questionType && questionType <= 20) {
            if (11 == questionType) {
                // 填空题
                quesType = Question.ProblemTypes.GAP_FILLING;
            }
            // 类型题
        } else if (21 <= questionType && questionType <= 30) {
            if (21 == questionType) {
                // 图片上传
                quesType = Question.ProblemTypes.IMAGE_UPLOAD;
            } else if (22 == questionType) {
                // 排序题
                quesType = Question.ProblemTypes.SORT;
            } else if (24 == questionType) {
                // 比重题
                quesType = Question.ProblemTypes.PROPORTION;
            } else if (27 == questionType) {
                // 单选
                quesType = Question.ProblemTypes.GRADING_QUESTION;
            } else if (28 == questionType) {
                // 多选
                quesType = Question.ProblemTypes.GRADE_ITEM;
            }
            // 矩阵题
        } else if (31 <= questionType && questionType <= 40) {
            if (31 == questionType) {
                // 矩阵下拉
                quesType = Question.ProblemTypes.MATRIX_DROP_DOWN;
            } else if (32 == questionType) {
                // 矩阵多选
                quesType = Question.ProblemTypes.MATRIX_MULTI_SELECT;
            } else if (33 == questionType) {
                // 矩阵填空
                quesType = Question.ProblemTypes.MATRIX_FILL;
            } else if (34 == questionType) {
                // 量表
                quesType = Question.ProblemTypes.SCALE;
            }
            // 特殊题
        } else if (41 <= questionType && questionType <= 50) {
            if (41 == questionType) {
                // 41:背景题
                quesType = Question.ProblemTypes.BACKGROUND;
            } else if (42 == questionType) {
                // 42:特殊题
                quesType = Question.ProblemTypes.SPECIAL;
            }
        } else {
            throw new RuntimeException("没有符合问题的类型!");
        }
        final String id = DbTool.uuid();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String createdAt = simpleDateFormat.format(question.getCreatedAt() * 1000L);
        final String updatedAt = simpleDateFormat.format(question.getUpdatedAt() * 1000L);
        questionSql.append("(").append("'").append(id).append("'").append(",").append("'").append(question.getContent
                ()).append("'").append
                (",").append("'").append(quesType).append("'").append(",").append("'").append(code).append("'")
                .append(",")
                .append(question.getSort()).append(",").append("'").append(group.getNewId()).append("'").append(",")
                .append("'")
                .append(question.getQuestionId()).append("'").append(",").append("'").append(createdAt).append("'")
                .append(",").append("'")
                .append(updatedAt).append("'").append("),");
        question.setNewCode(code);
        question.setNewId(id);
        group.getQuestions().add(question);
        return id;
    }

    /**
     * 拼接问卷选项信息
     */
    private void jointOption(final String code, Question question, final String questionId,
                             QuestionOption questionOption, StringBuilder optionsSql) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String created = simpleDateFormat.format(questionOption.getCreatedAt() * 1000L);
        final String updated = simpleDateFormat.format(questionOption.getUpdatedAt() * 1000L);
        optionsSql.append("(").append("'").append(DbTool.uuid()).append("'").append(",").append("'").append(code)
                .append("'").append(",").append(questionOption.getSort()).append(",").append("'").append(questionOption
                .getContent()).append("'").append(",").append("'").append(questionId).append("'").append(",")
                .append("'").append(questionOption.getId()).append("'").append(",").append("'").append(created)
                .append("'").append(",").append("'").append(updated).append("'").append("),");
        question.getQuestionOptions().add(questionOption);
    }

    /**
     * 创建答题动态表
     */
    public void dynamicCreateAnswerTable(List<Group> groups) {
        if (groups != null && groups.size() > 0) {
            // 查询问卷所有对应的答题表
            List<String> codes = groups.stream().map(Group::getNewCode).collect(Collectors.toList());
            List<String> tableNames = commonMethod.questionnaireAnalyzeMapper.queryTableName(codes);
            for (Group group : groups) {
                final String groupCode = group.getNewCode();
                // 判断创建的动态表是否存在
                final String tableName = "dc_dm_answer_sheet" + groupCode;
                if (!tableNames.contains(tableName)) {
                    final String sqlPrefix = "CREATE TABLE \"dadui\".\"" + tableName + "\"(\n" +
                            "  \"id\" char(32) COLLATE \"pg_catalog\".\"default\" NOT NULL DEFAULT NULL,\n" +
                            "  \"questionnaire_id\" char(32) COLLATE \"pg_catalog\".\"default\" NOT NULL DEFAULT " +
                            "NULL,\n" +
                            "  \"user_id\" varchar(32) COLLATE \"pg_catalog\".\"default\" NOT NULL DEFAULT NULL,\n" +
                            "  \"info_id\" varchar(32) COLLATE \"pg_catalog\".\"default\" NOT NULL DEFAULT NULL,\n" +
                            "  \"publish_id\" varchar(32) COLLATE \"pg_catalog\".\"default\" DEFAULT NULL,\n" +
                            "  \"entry_id\" varchar(32) COLLATE \"pg_catalog\".\"default\" DEFAULT NULL,\n" +
                            "  \"answer_time\" timestamp(0) NOT NULL DEFAULT NULL,\n";
                    final String sqlSubfix = ");\n" +
                            "ALTER TABLE \"dadui\".\"" + tableName + "\" ADD CONSTRAINT \"" + tableName + "_pkey\" " +
                            "PRIMARY KEY (\"id\");";
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(sqlPrefix);
                    // 动态表中列的设置
                    dynamlTable(stringBuilder, group);
                    stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
                    stringBuilder.append(sqlSubfix);
                    // 创建动态表
                    commonMethod.dynamicTableMapper.dynamicCreateAnswerSheet(stringBuilder.toString());
                } else {
                    log.info(tableName + "答题表已存在!");
                }
            }
        }
    }

    /**
     * 动态表中列的设置
     */
    private void dynamlTable(StringBuilder sqlPrefix, Group group) {
        // 防止重复列
        List<String> columns = new ArrayList<>();
        // 问题拼接
        final List<Question> questions = group.getQuestions();
        if (questions != null && questions.size() > 0) {
            for (Question question : questions) {
                final String questionCode = question.getNewCode();
                if (!columns.contains(questionCode)) {
                    columns.add(questionCode);
                    columnSize(questionCode, question, sqlPrefix);
                }
            }
            columns.clear();
        }
    }

    /**
     * 指定动态列的大小
     */
    private void columnSize(String questionCode, Question question, StringBuilder sqlPrefix) {
        // 指定列的大小
        Integer questionType = question.getQuestionType();
        if (questionType != null && questionType > 0) {
            Boolean inexistence = true;
            if (1 <= questionType && questionType <= 10) {
                if (1 == questionType) {
                    // 单选
                    sqlPrefix.append("\t\"que").append(questionCode).append("_opt_codes\"").append("\tvarchar(50),\n");
                } else if (2 == questionType) {
                    // 多选
                    sqlPrefix.append("\t\"que").append(questionCode).append("_opt_codes\"").append("\tvarchar(53)[]," +
                            "\n");
                } else if (3 == questionType) {
                    // 图片选择
                    inexistence = false;
                }else {
                    inexistence = false;
                }
                // 填空题
            } else if (11 <= questionType && questionType <= 20) {
                if (11 == questionType) {
                    // 填空
                    sqlPrefix.append("\t\"que").append(questionCode).append("_opt_codes\"").append("\tvarchar(50),\n");
                } else {
                    inexistence = false;
                }
                // 类型题
            } else if (21 <= questionType && questionType <= 30) {
                if (21 == questionType) {
                    // 图片上传
                    inexistence = false;
                } else if (22 == questionType) {
                    // 排序
                    inexistence = false;
                } else if (24 == questionType) {
                    // 比重题
                    inexistence = false;
                } else if (27 == questionType) {
                    // 打分题(单选)
                    sqlPrefix.append("\t\"que").append(questionCode).append("_opt_codes\"").append("\tint8,\n");
                } else if (28 == questionType) {
                    // 打分题(多选)
                    sqlPrefix.append("\t\"que").append(questionCode).append("_opt_codes\"").append("\tint8[],\n");
                } else {
                    inexistence = false;
                }
            // 矩阵题
            } else if (31 <= questionType && questionType <= 40) {
                if (31 == questionType) {
                    // 矩阵下拉
                    inexistence = false;
                } else if (32 == questionType) {
                    // 矩阵多选
                    inexistence = false;
                } else if (33 == questionType) {
                    // 矩阵填空
                    inexistence = false;
                } else if (34 == questionType) {
                    // 量表
                    inexistence = false;
                } else {
                    inexistence = false;
                }
            // 特殊题
            } else if (41 <= questionType && questionType <= 50) {
                if (41 == questionType) {
                    // 背景题
                    inexistence = false;
                } else if (42 == questionType) {
                    // 特殊题
                    inexistence = false;
                } else {
                    inexistence = false;
                }
            } else {
                sqlPrefix.append("\t\"que").append(questionCode).append("_opt_codes\"").append("\tvarchar(50),\n");
                throw new RuntimeException(questionType + "没有指定的问题类型!");
            }
            if (!inexistence){
                sqlPrefix.append("\t\"que").append(questionCode).append("_opt_codes\"").append("\tvarchar(50),\n");
            }
        }
    }

    /**
     * 答题数据sql拼接
     */
    public void sqlJoint(List<QuestionnaireLater> questionnaireLagers, List<UserAnswerInfo> userAnswerIfs,
                         List<UserAnswerConcise> userAnswerConciser) {
        final Map<Long, String> map = new HashMap<>();
        // SQL固定前缀
        final String prefix = " (\n"
                + "\t\"id\",\n"
                + "\t\"questionnaire_id\",\n"
                + "\t\"user_id\",\n"
                + "\t\"info_id\",\n"
                + "\t\"publish_id\",\n"
                + "\t\"entry_id\",\n"
                + "\t\"answer_time\"";

        // questionnaireLaters问卷下所有的问题
        List<String> questionnaireIds = questionnaireLagers.stream().map(QuestionnaireLater::getId)
                .collect(Collectors.toList());
        List<QuestionLater> questionLaterList = commonMethod.questionAnalyzeMapper.queryQuestionLaterByQutstId
                (questionnaireIds);
        List<String> codes = questionnaireLagers.stream().map(QuestionnaireLater::getAnswerSheetCode).collect
                (Collectors.toList());
        List<String> tableNames = commonMethod.questionnaireAnalyzeMapper.queryTableName(codes);
        // 每套问卷
        for (QuestionnaireLater questionnaireLater : questionnaireLagers) {
            // 判断该问卷对应的动态答题表是否存在
            final String questionnaireCode = questionnaireLater.getAnswerSheetCode();
            final String tableName = "dc_dm_answer_sheet" + questionnaireCode;
            if (tableNames.contains(tableName)) {
                // 每套问卷的用户答题记录
                List<UserAnswerInfo> userAnswerInfoList = userAnswerIfs.stream().filter(userAnswerInfo ->
                        userAnswerInfo.getGroupId().equals(questionnaireLater.getDaduiId())).collect(Collectors
                        .toList());
                if (userAnswerInfoList != null && userAnswerInfoList.size() > 0) {
                    List<QuestionLater> questionLaters = questionLaterList.stream().filter(questionLater ->
                            questionLater.getQuestionnaireId()
                                    .equals(questionnaireLater.getId())).collect(Collectors.toList());
                    final StringBuilder preBuilder = new StringBuilder();
                    final StringBuilder subBuilder = new StringBuilder();
                    final String insert = "INSERT INTO\t" + "\"dadui\"." + "\"dc_dm_answer_sheet" +
                            questionnaireLater.getAnswerSheetCode() + "\"";
                    preBuilder.insert(0, insert).append(prefix);
                    subBuilder.append(")VALUES\n" + "\t(");
                    // 拼接列
                    boolean valid = dynamicColumn(userAnswerConciser, questionLaters, preBuilder, subBuilder,
                            questionnaireLater, userAnswerInfoList, map);
                    if (valid) {
                        // 保存答题数据
                        saveAngerSheet(preBuilder, subBuilder);
                    }
                }
            }
        }
        if (map.size() > 0) {
            final Set<Long> keySet = map.keySet();
            Optional<Long> max = keySet.stream().max(Long::compareTo);
            final Long maxCreatedAt = max.orElse(0L);
            final String questionnaireCode = map.get(maxCreatedAt);
            final String id = DbTool.uuid();
            // 保存本次同步用户答题记录信息中的最大时间
            commonMethod.questionnaireAnalyzeMapper.insertAnswerInfoTime(id, maxCreatedAt, questionnaireCode);
        }
    }

    /**
     * 拼接动态列
     */
    private boolean dynamicColumn(List<UserAnswerConcise> userAnswerConciser, List<QuestionLater> questionLaters,
                                  StringBuilder preBuilder, StringBuilder subBuilder, QuestionnaireLater
                                          questionnaireLater,
                                  List<UserAnswerInfo> userAnswerInfoList, Map<Long, String> map) {
        List<Integer> list = new ArrayList<>();
        Integer counter = 1;
        // 当前问卷下每一个用户的答题记录
        for (UserAnswerInfo userAnswerInfo : userAnswerInfoList) {
            // 当前问卷下某用户答题记录详情
            List<UserAnswerConcise> userAnswerConciseList = userAnswerConciser.stream()
                    .filter(userAnswerConcise -> userAnswerConcise.getInfoId()
                            .equals(userAnswerInfo.getId())).collect(Collectors.toList());
            // boolean exist = false;
            if (userAnswerConciseList != null && userAnswerConciseList.size() > 0) {
                if (userAnswerInfoList.size() > 1 && counter > 1) {
                    subBuilder.append(",(");
                }
                // 拼接固定的列
                fixedColumn(questionnaireLater, userAnswerInfo, subBuilder);
                // 循环当前问卷中下所有的问题
                for (QuestionLater question : questionLaters) {
                    // 判定问题类型,拼接SQL
                    questionType(userAnswerInfo.getId(), userAnswerConciseList, question, preBuilder, subBuilder);
                }
                subBuilder.append(")");
            } else {
                list.add(userAnswerInfo.getId());
            }
            counter++;
            map.put(userAnswerInfo.getCreatedAt(), questionnaireLater.getAnswerSheetCode());
        }
        if (list.size() > 0) {
            log.info("userAnswerInfo(" + list.toString() + "):" + "用户答题记录没有指定的答题信息!");
        }
        return list.size() != userAnswerInfoList.size();
    }

    /**
     * 解决user_answer_info中有用户答题记录,但是user_answer_concise中没有对应的答题信息
     */
    private void checkAnswerConcise(List<UserAnswerInfo> userAnswerInfoList, Integer counter,
                                    StringBuilder subBuilder, boolean exist) {

        if (counter > 1 && exist) {
            subBuilder.append("),(");
        } else if (userAnswerInfoList.size() == counter) {
            subBuilder.append(")");
        } else if (exist) {
            subBuilder.append(")");
        }
    }

    /**
     * 固定列SQL的拼接
     */
    private void fixedColumn(QuestionnaireLater questionnaireLater, UserAnswerInfo
            userAnswerInfo, StringBuilder subBuilder) {
        String id = DbTool.uuid();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(userAnswerInfo.getCreatedAt() * 1000L);
        subBuilder.append("'").append(id).append("',")
                .append("'").append(questionnaireLater.getId()).append("',")
                .append("'").append(userAnswerInfo.getUserId()).append("',")
                .append("'").append(userAnswerInfo.getId()).append("',")
                .append("'").append(userAnswerInfo.getPublishId()).append("',")
                .append("'").append(userAnswerInfo.getEntryId()).append("'").append(",")
                .append("'").append(time).append("'");
    }

    /**
     * 判定问题类型,拼接SQL
     */
    private void questionType(final Integer infoId, List<UserAnswerConcise> userAnswerConciseList,
                              QuestionLater question, StringBuilder preBuilder, StringBuilder subBuilder) {
        final String column = "\"que" + question.getQueCode() + "_opt_codes\"";
        Optional<UserAnswerConcise> answerco = userAnswerConciseList.stream().filter(userAnswerConcise -> String.valueOf
                (userAnswerConcise.getQuestionId()).equals(question.getDaduiId())).findFirst();
        QuestionLater.ProblemTypes queType = question.getQueType();
        if (answerco.isPresent()) {
            // 当前用户有答题信息
            efficacious(infoId, userAnswerConciseList, question, preBuilder, subBuilder, column, queType);
        } else {
            // 当前用户无答题信息
            Invalid(preBuilder, subBuilder, column, queType, question);
        }
    }

    /**
     * 无答题信息
     */
    private void Invalid(StringBuilder preBuilder, StringBuilder subBuilder, String column,
                         QuestionLater.ProblemTypes queType, QuestionLater question) {
        // 查询该问题下的所有选项
        List<String> optionCount = commonMethod.optionAnalyzeMapper.queryOptionCount(question.getId());
        if (!preBuilder.toString().contains(column)) {
            preBuilder.append(",").append(column);
        }
        // 判断没有选项的问题是什么类型的问题
        problemType(queType, subBuilder, optionCount);
    }

    /**
     * 有答题信息
     */
    private void efficacious(Integer infoId, List<UserAnswerConcise> userAnswerConciseList, QuestionLater question,
                             StringBuilder preBuilder, StringBuilder subBuilder, String column, QuestionLater
                                     .ProblemTypes queType) {
        Boolean status = false;
        if (QuestionLater.ProblemTypes.ONE_CHOICE.equals(queType)) {
            Optional<Integer> optionalId = userAnswerConciseList.stream().filter(data ->
                    data.getQuestionId().equals(question.getDaduiId()) && data.getInfoId().equals(infoId))
                    .findFirst().map(UserAnswerConcise::getOptionId);
            // 单选
            subBuilder.append(",'").append(optionalId.orElse(null)).append("'");
            status = true;
        } else if (QuestionLater.ProblemTypes.GRADING_QUESTION.equals(queType)) {
            Optional<Integer> optionalId = userAnswerConciseList.stream().filter(data ->
                    data.getQuestionId().equals(question.getDaduiId()) && data.getInfoId().equals(infoId))
                    .findFirst().map(userAnswerConcise -> Integer.parseInt(userAnswerConcise.getFillText()));
            // 打分题(单选)
            subBuilder.append(",'").append(optionalId.orElse(null)).append("'");
            status = true;
        } else if (QuestionLater.ProblemTypes.GRADE_ITEM.equals(queType)) {
            // 打分题(多选)
            scoringAndMultiSelect(userAnswerConciseList, question, subBuilder, infoId, "scoring");
            status = true;
        } else if (QuestionLater.ProblemTypes.MORE_CHOICE.equals(queType)) {
            // 多选
            scoringAndMultiSelect(userAnswerConciseList, question, subBuilder, infoId, "multiSelect");
            status = true;
        } else if (QuestionLater.ProblemTypes.GAP_FILLING.equals(queType)) {
            Optional<String> optional = userAnswerConciseList.stream().filter(data ->
                    data.getQuestionId().equals(question.getDaduiId()) && data.getInfoId().equals(infoId))
                    .findFirst().map(userAnswerConcise -> userAnswerConcise.getFillText());
            // 填空
            subBuilder.append(",'").append(optional.orElse(null)).append("'");
            status = true;
        } else {
            problemType(queType, subBuilder,null);
            log.info(question.getDaduiId() + "没有指定的问题类型!");
        }
        if (!preBuilder.toString().contains(column)) {
            preBuilder.append(",").append(column);
        }
    }

    /**
     * 判断没有选项的问题是什么类型的问题
     */
    private void problemType(QuestionLater.ProblemTypes queType, StringBuilder subBuilder,
                             List<String> optionCount) {
        StringJoiner stringJoiner = new StringJoiner(",","'{","}'");
        // 数组型
        if (QuestionLater.ProblemTypes.MORE_CHOICE.equals(queType) ||
                Question.ProblemTypes.GRADE_ITEM.equals(queType)) {
            if (optionCount != null && optionCount.size() > 0) {
                optionCount.forEach(data ->{
                    stringJoiner.add("NULL");
                });
                subBuilder.append(",").append(stringJoiner.toString());
            } else {
                subBuilder.append(",'{").append("}'");
            }
        } else {
            // 普通字符串
            subBuilder.append(",'").append("'");
        }
    }

    /**
     * 打分题&多选题处理
     */
    private void scoringAndMultiSelect(List<UserAnswerConcise> userAnswerConciseList, QuestionLater question,
                                       StringBuilder subBuilder, Integer infoId, final String type) {
        StringJoiner fillTextSql = new StringJoiner(",", "'{", "}'");
        // 查询该问题下的所有选项
        List<OptionsLater> optionsLaters = commonMethod.optionAnalyzeMapper.queryOptionsLatersByQueId(question.getId());
        optionsLaters.sort(Comparator.comparing(OptionsLater::getSortNum));
        for (OptionsLater optionsLater : optionsLaters) {
            Optional<UserAnswerConcise> userAnsConFirst = userAnswerConciseList.stream().filter(userAnswerConcise ->
                    userAnswerConcise.getQuestionId()
                            .equals(question.getDaduiId()) && String.valueOf(userAnswerConcise.getOptionId())
                            .equals(optionsLater.getDaduiId()) && String.valueOf(userAnswerConcise.getInfoId())
                            .equals(String.valueOf(infoId))).findFirst();
            if (userAnsConFirst.isPresent()) {
                if ("scoring".equals(type)) {            // 打分题
                    fillTextSql.add(userAnsConFirst.get().getFillText());
                } else if ("multiSelect".equals(type)) {  // 多选题
                    fillTextSql.add(String.valueOf(userAnsConFirst.get().getOptionId()));
                } else {
                    throw new RuntimeException(type + ":问题类型错误!");
                }
            } else {
                fillTextSql.add(null);
            }
        }
        subBuilder.append(",").append(fillTextSql.toString());
    }

    /**
     * 查询问题
     */
    private List<Question> queryQuestions() {
        final String dateTime = commonMethod.questionAnalyzeMapper.queryQuestionMaxUpdatetime();
        return commonMethod.daDuiMapper.queryQuestionsByCreatedAt(dateTime);
    }

    /**
     * 查询选项
     */
    private List<QuestionOption> queryQuestionOptions() {
        final String dateTime = commonMethod.optionAnalyzeMapper.queryOptionAnaMaxUpdateTime();
        return commonMethod.daDuiMapper.queryOptionsByCreatedAt(dateTime);
    }

    /**
     * 保存答题数据
     */
    private void saveAngerSheet(final StringBuilder preBuilder, final StringBuilder subBuilder) {
        final String sql = preBuilder.toString() + subBuilder.toString();
        System.out.println(sql);
        commonMethod.dynamicTableMapper.insertAnswerSheet(sql);
    }

    /**
     * 判断表中的列是否存在
     */
    private boolean queryColumn(final String questionnaireCode, final String queCode) {
        // 表名
        final String tableName = "dc_dm_answer_sheet" + questionnaireCode;
        // 判断指定的列是否存在
        final String columnName = "que" + queCode + "_opt_codes";
        if (!StringUtils.isEmpty(columnName) && !StringUtils.isEmpty(tableName)) {
            final String column = this.commonMethod.questionnaireAnalyzeMapper.queryColumn(tableName, columnName);
            return column != null && !StringUtils.isEmpty(column);
        }
        return false;
    }

    /**
     * 判断该问卷是否存在
     */
    private boolean questionnaireExists(final String groupId) {
        final String id = commonMethod.questionnaireAnalyzeMapper.queryDaduiIdExist(groupId);
        return !StringUtils.isEmpty(id);
    }

    /**
     * 判断问卷是否存在
     */
    private boolean judgment(String daduiId) {
        String id = commonMethod.questionnaireAnalyzeMapper.queryDaduiIdExist(daduiId);
        return !StringUtils.isEmpty(id);
    }

    /**
     * 答对发布数据转换
     */
    public void disposePublished(List<Publish> publishes, List<String> daduiIds) {
        for (Publish publish : publishes) {
            // 判断每条记录对应的问卷是否存在
            if (daduiIds.contains(publish.getGroupId())) {
                // 保存发布信息
                final Date createTime = new Date(publish.getCreatedAt() * 1000L);
                final Date updateTime = new Date(publish.getUpdatedAt() * 1000L);

                PublishLater publishLater = PublishLater.builder().id(DbTool.uuid())
                        .publishId(publish.getPublishId())
                        .adminId(publish.getAdminId()).groupId(publish.getGroupId())
                        .theDesc(publish.getTheDesc()).multiEntry(publish.getMultiEntry())
                        .multiUkey(publish.getMultiUkey()).status(publish.getStatus()).build();
                commonMethod.questionnaireAnalyzeMapper.savePublish(publishLater, createTime, updateTime);
            }
        }
    }
}
