package com.idata3d.scheduler.dadui.service.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * YZG on 2017/9/22
 */
@Component
public class MethodBase {

    /**
     * SQL拼接
     */
    public  <T> void answerJoinSql(List<T> ids, StringBuilder sql) {
        int num = 1;
        for (T id : ids) {
            if (num == ids.size()) {
                sql.append("'").append(id).append("'").append(") ");
            } else {
                sql.append("'").append(id).append("'").append(",");
            }
            num++;
        }
    }

    /**
     * 获取时间戳
     */
    public Long getTimestamp(final String datetime) {

        if (!StringUtils.isEmpty(datetime)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = null;
            try {
                parse = simpleDateFormat.parse(datetime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert parse != null;
            Long time = parse.getTime();
            final String times = String.valueOf(time).substring(0, 10);
            return Long.parseLong(times);
        }
        return null;
    }

    /**
     * 时间戳转为yyyy-MM-dd HH:mm:ss
     */
    public String dateTimeFormat(Long timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(timestamp * 1000L);
    }
}
