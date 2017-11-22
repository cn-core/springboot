package com.idata3d.scheduler.dadui.bean.ConversionLater;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * YZG on 2017/8/6
 */
@Data
@Builder
public class PublishLater {
    private String          id;
    private String          publishId;
    private Integer         adminId;
    private String          groupId;
    private String          theDesc;
    private Integer         multiEntry;
    private Integer         multiUkey;
    private Integer         status;
    private LocalDateTime   createdAt;
    private LocalDateTime   updatedAt;

    private String  createdAtAs;
    private String  updatedAtAs;

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAtAs = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAtAs = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
