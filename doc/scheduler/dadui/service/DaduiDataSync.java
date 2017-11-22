package com.idata3d.scheduler.dadui.service;

/**
 * 答对数据同步
 * yangzhiguo on 2017/8/22.
 */
public interface DaduiDataSync {

    /**
     * 同步套题信息(新增数据)
     */
    default void conversionQue(){}

    /**
     * 套题更新(问卷)  注:不可在原套题中添加或删除问题&选项,只能修改
     */
    default void updateQuestionnaire(){}

    /**
     * 全量检测答对数据
     */
    default void inspectionDaduiData(){}
}
