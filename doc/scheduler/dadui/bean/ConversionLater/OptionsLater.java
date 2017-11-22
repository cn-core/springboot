package com.idata3d.scheduler.dadui.bean.ConversionLater;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * yangzhiguo on 2017/7/24.
 */
@Data
public class OptionsLater
{
    private String          id;
    private String          optCode;
    private Integer         sortNum;
    private String          optValue;
    private String          questionId;
    private String          daduiId;
    private LocalDateTime   createdAt;
    private LocalDateTime   updatedAt;

    private String          createdAtAs;
    private String          updatedAtAs;

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAtAs = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAtAs = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
