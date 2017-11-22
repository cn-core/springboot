package com.idata3d.scheduler.dadui.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题
 * yangzhiguo on 2017/7/18.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question
{
    private Integer     id;
    private String      groupId;
    private String      questionId;
    private String      content;
    private String      theDesc;
    private Integer     questionType;
    private Integer     matrixId;
    private String      dictCode;
    private Integer     adminId;
    private Integer     sort;
    private Integer     status;
    private Long        createdAt;
    private Long        updatedAt;
    private List<QuestionOption> questionOptions = new ArrayList<>();

    private String newId;
    private String newCode;

    /**
     * 问题类型
     */
    public enum ProblemTypes
    {
        // 1-10 选择题
        ONE_CHOICE,             // 1:单选
        MORE_CHOICE,            // 2:多选
        PICTURES_CHOOSE,        // 3:图片选择
        // 11-20 填空题
        GAP_FILLING,            // 11:填空题
        // 21-30 类型题
        IMAGE_UPLOAD,           // 21:图片上传
        SORT,                   // 22:排序
        PROPORTION,             // 24:比重
        GRADING_QUESTION,       // 27:打分题(单选)
        GRADE_ITEM,             // 28:打分题(多选)
        // 31-40 矩阵题
        MATRIX_DROP_DOWN,       // 31:矩阵下拉
        MATRIX_MULTI_SELECT,    // 32:矩阵多选
        MATRIX_FILL,            // 33:矩阵填空
        SCALE,                  // 34:量表
        // 41-50 特殊题
        BACKGROUND,             // 41:背景题
        SPECIAL,                // 42:特殊题
        NULL                    // 未知
    }
}
