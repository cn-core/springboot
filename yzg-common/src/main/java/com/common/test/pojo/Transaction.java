package com.common.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yangzhiguo
 */
@Data
@AllArgsConstructor
public class Transaction
{
    private Trader trader;
    private int year;
    private int value;
}
