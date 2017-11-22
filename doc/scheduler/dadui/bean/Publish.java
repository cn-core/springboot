package com.idata3d.scheduler.dadui.bean;

import lombok.Data;

/**
 * 用户发布信息
 * yangzhiguo on 2017/7/18.
 */
@Data
public class Publish
{
    private Integer      id;
    private String       publishId;
    private Integer      adminId;
    private String       groupId;
    private String       theDesc;
    private Integer      multiEntry;
    private Integer      multiUkey;
    private Integer      status;
    private Integer      createdAt;
    private Integer      updatedAt;


}
