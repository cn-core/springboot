package com.idata3d.scheduler.dadui.bean.ConversionLater;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * yangzhiguo on 2017/7/24.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionnaireLater
{
    private String          id;
    private String          projectName;
    private String          answerSheetCode;
    private String          publishId;
    private String          daduiId;
    private LocalDateTime   createdAt;
    private LocalDateTime   updatedAt;
    private String          createdAtAs;
    private String          updatedAtAs;
    private String          publishTimeAs;

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAtAs = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAtAs = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
