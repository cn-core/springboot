package com.idata3d.scheduler.dadui.bean;

import lombok.Data;

/**
 * 问题选项
 * yangzhiguo on 2017/7/18.
 */
@Data
public class QuestionOption
{
    private Integer     id;
    private String      questionId;
    private String      content;
    private String      dictCode;
    private String      bName;
    private String      matrixCode;
    private Integer     matrixOptionId;
    private Integer     sort;
    private Integer     status;
    private Long        createdAt;
    private Long        updatedAt;

    private String queId;
    private String newId;
    private String newCode;
}
