package com.idata3d.scheduler.dadui.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 问卷
 * yangzhiguo on 2017/7/18.
 */
@Data
public class Group
{
    private Integer id;
    private String  groupId;
    private String  theDesc;
    private Integer adminId;
    private Integer status;
    private Long createdAt;
    private Long updatedAt;
    private List<Question> questions = new ArrayList<>();

    private String newId;
    private String newCode;
}
