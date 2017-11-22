package com.idata3d.scheduler.dadui.controller;

import com.idata3d.scheduler.dadui.service.DaduiDataSync;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 答对数据同步
 * yangzhiguo on 2017/8/22.
 */
@RestController
@RequestMapping("/dadui_sync")
@Slf4j
public class DaduiDataSyncController {

    private final DaduiDataSync newQuestionnaireSync;

    private final DaduiDataSync updateQuestionnaireSync;

    private final DaduiDataSync fullAmountDetectionSync;

    @Autowired
    public DaduiDataSyncController(DaduiDataSync newQuestionnaireSync,DaduiDataSync updateQuestionnaireSync,
                                   DaduiDataSync fullAmountDetectionSync) {
        this.newQuestionnaireSync = newQuestionnaireSync;
        this.updateQuestionnaireSync = updateQuestionnaireSync;
        this.fullAmountDetectionSync = fullAmountDetectionSync;
    }

    /**
     * 增量同步套题数据
     */
    //@RequestMapping(value = "conversion_que")
    //@Scheduled(fixedRate = 600000,       // 间隔执行时间       10分钟
    //        initialDelay = 180000)       // 启动后多长时间执行  3分钟
    public void conversionQue(){
        log.info("增量同步套题数据");
        newQuestionnaireSync.conversionQue();
    }

    /**
     * 答对套题&问题&选项名称更新
     *      注:1.不可在原套题中添加或删除问题&选项,只能修改名称
     *         2.执行时间必须在同步套题信息后执行,否则更新后的数据在检测时会漏掉
     *          (原因:检测数据是否更新,是根据转换后的问卷&问题&选项的最新时间到答对中检测)
     */
    //@Scheduled(fixedRate = 1800000,      // 间隔执行时间        30分钟
    //        initialDelay = 240000)       // 启动后多长时间执行  4分钟
    //@RequestMapping(value = "update_questionnaire")
    public void updateQuestionnaire(){
        log.info("答对套题&问题&选项名称更新");
        updateQuestionnaireSync.updateQuestionnaire();
    }

    /**
     * 全量检测答对数据
     *      检测答对与同步后的问卷&问题&选项是否一致,如不一致将问题(Error)数据同步:
     *          1:套题&问题&选项不一致(注:如果同步后的某套问卷被误删,那么问卷下所对应的问题&选项&答题表成为死数据)
     *          2:发布数据不一致
     *          3:答题数据不一致
     */
    @Scheduled(cron = "0 0 1 * * ?")       // 每天凌晨1点触发
    @RequestMapping(value = "inspection_data")
    public void inspectionDaduiData(){
        log.info("全量检测");
        fullAmountDetectionSync.inspectionDaduiData();
    }
}
