package com.idata3d.scheduler.dadui.bean;

import lombok.Data;

/**
 * 用户答题记录表
 * yangzhiguo on 2017/7/18.
 */
@Data
public class UserAnswerConcise
{
    private Integer     id;
    private String      questionId;
    private Integer     optionId;
    private String      fillText;
    private Integer     infoId;
    private Integer     createdAt;
}
