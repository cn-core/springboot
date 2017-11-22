package com.idata3d.scheduler.dadui.service.impl;

import com.idata3d.core.util.DbTool;
import org.dalesbred.Database;

import java.util.Random;
import java.util.StringJoiner;

/**
 * YZG on 2017/9/14.
 */
public class CreateTestData {

    public static void main(String[] args) {
        Database dbAnalyze = Database.forUrlAndCredentials(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "root");

        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO dadui.dc_dm_answer_sheet0006 (ID, questionnaire_id, user_id, info_id, " +
                "publish_id, entry_id, answer_time, que001_opt_codes, que002_opt_codes, que003_opt_codes, " +
                "que004_opt_codes )\n" +
                "VALUES");
        // 生成答题数据
        for (int i = 0; i < 100000; i++) {
            insertSql.append("('" + DbTool.uuid() + "',");          // 主键
            insertSql.append("'662cd7f9672843e1890eac5e9a47ac9c',");// 套题ID
            insertSql.append("'" + DbTool.uuid() + "',");           // 用户ID
            insertSql.append("'',");                                // info_id
            insertSql.append("'d7bj29',");                          // 发布ID
            insertSql.append("'',");                                // entry_id
            insertSql.append("'2017-09-14 10:48:01',");             // 答题时间
            insertSql.append(multiple() + ",");                     // 单选
            insertSql.append(multiSelect() + ",");                  // 多选
            insertSql.append("'" + scoringOne() + "',");            // 单选打分
            insertSql.append(multipleGrading());                    // 多选打分
            insertSql.append("),");
        }
        String insertInto = insertSql.toString();
        String sql = insertInto.substring(0,insertInto.length() - 1);
        // System.out.println(sql);
        dbAnalyze.update(sql);
    }

    /**
     * 单选
     */
    public static String multiple(){
        String[] b = {"1347", "1348"};
        Random rand = new Random();
        int num = rand.nextInt(2);
        return "'" + b[num] + "'";
    }

    /**
     * 多选
     */
    public static String multiSelect(){
        StringJoiner stringJoiner = new StringJoiner(",","'{","}'");
        String[] a = {"1349","1350","1351","1352","1353", "NULL"};
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            int num = rand.nextInt(6);
            stringJoiner.add(a[num]);
        }
        return stringJoiner.toString();
    }

    /**
     * 单选打分题
     */
    public static String scoringOne(){
        int max=100;
        int min=0;
        Random random = new Random();
        int num = random.nextInt(max)%(max-min+1) + min;
        return num + "";
    }

    /**
     * 多选打分题
     */
    public static String multipleGrading(){
        StringJoiner stringJoiner = new StringJoiner(",","'{","}'");
        for (int i = 0; i < 3; i++) {
            stringJoiner.add(scoringOne());
        }
        return stringJoiner.toString();
    }
}
