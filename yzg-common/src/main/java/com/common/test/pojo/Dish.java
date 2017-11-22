package com.common.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yangzhiguo
 */
@Data
@AllArgsConstructor
public class Dish
{
    private final String   name;
    private final boolean  vegetarian;
    private final Integer  calories;
    private final Type     type;


    // 菜的类型
    public enum Type
    {
        MEAT, FISH, OTHER
    }
}
