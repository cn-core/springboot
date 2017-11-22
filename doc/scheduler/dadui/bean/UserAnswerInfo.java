package com.idata3d.scheduler.dadui.bean;

import lombok.Data;

/**
 * 答题信息
 * yangzhiguo on 2017/7/18.
 */
@Data
public class UserAnswerInfo
{
    private Integer      id;
    private String       userId;
    private String       openid;
    private String       publishId;
    private String       groupId;
    private String       userIp;
    private String       entryId;
    private String       ukey;
    private Integer      accType;
    private String       wasteTime;
    private Integer      questionNum;
    private Integer      answerType;
    private String       lbs;
    private Long         createdAt;
    private String       tagNum;
}
