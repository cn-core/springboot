package com.common.utils;

import java.util.UUID;

/**
 * @author yangzhiguo
 */
public class DbTool
{
    public static String uuid()
    {
        final StringBuilder sb = new StringBuilder(UUID.randomUUID().toString());
        sb.deleteCharAt(8);
        sb.deleteCharAt(12);
        sb.deleteCharAt(16);
        sb.deleteCharAt(20);
        return sb.toString();
    }
}
