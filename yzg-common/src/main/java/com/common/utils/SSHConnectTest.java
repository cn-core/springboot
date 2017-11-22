package com.common.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.*;

/**
 * @author yangzhiguo
 */
public class SSHConnectTest
{
    public static void go() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession("root", "123.56.188.31", 22);
            session.setPassword("Horizon0127");
            session.setConfig("StrictHostKeyChecking", "on");
            session.connect();
            System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息

            //ssh -L 192.168.0.102:5555:192.168.0.101:3306 yunshouhu@192.168.0.102  正向代理
            int assinged_port = session.setPortForwardingL(3307,"rds53773q4pcp13nw5dr.mysql.rds.aliyuncs.com", 3306);//端口映射 转发

            System.out.println("localhost:" + assinged_port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException
    {
        try {
            //1、加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //2、创建连接
        Connection conn;
        try{
            System.out.println("=============");
            go();
            conn = DriverManager
                    .getConnection("jdbc:mysql://localhost:3307/datacube?" +
                    "useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC" +
                    "&autoReconnect=true&failOverReadOnly=false", "datacube","Horizon0324");
            getData(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getData(Connection conn) throws SQLException {
        // 获取所有表名
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement
                .executeQuery("SELECT * FROM dc_meal_business_district d WHERE d.id = 'bafddcafc0674c5d9d2c0cc674e4e09f'");
        // 获取列名
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            // resultSet数据下标从1开始
            String columnName = metaData.getColumnName(i + 1);
            int type = metaData.getColumnType(i + 1);
            if (Types.INTEGER == type) {
                // int
            } else if (Types.VARCHAR == type) {
                // String
            }
            System.out.print(columnName + "\t");
        }
        System.out.println();
        // 获取数据
        while (resultSet.next()) {
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                // resultSet数据下标从1开始
                System.out.print(resultSet.getString(i + 1) + "\t");
            }
            System.out.println();
        }
        statement.close();
        conn.close();
    }
}
