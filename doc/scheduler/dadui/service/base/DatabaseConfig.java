package com.idata3d.scheduler.dadui.service.base;

import com.idata3d.scheduler.dadui.bean.DataSources;
import org.dalesbred.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * YZG on 2017/9/22
 */
@Component
public class DatabaseConfig {

    public final Database db;
    public final Database dbAnalyze;

    @Autowired
    public DatabaseConfig(DataSources dataSources) {
        this.db = Database.forUrlAndCredentials(dataSources.getDaduiUrl(),dataSources.getDaduiUsername(),dataSources.getDauiPassword());
        this.dbAnalyze = Database.forUrlAndCredentials(dataSources.getAliUrl(),dataSources.getAliUsername(),dataSources.getAliPassword());
    }
}
