package com.idata3d.scheduler.dadui.bean.ConversionLater;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * yangzhiguo on 2017/7/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionLater
{
    private String        id;
    private String        queTitle;
    private ProblemTypes  queType;
    private String        queCode;
    private Integer       sortNum;
    private String        questionnaireId;
    private String        daduiId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String createdAtAs;
    private String updatedAtAs;

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAtAs = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

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
        NULL                    // 未知数据
    }
}
