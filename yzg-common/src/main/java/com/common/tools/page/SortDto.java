package com.common.tools.page;

import lombok.Data;

/**
 * 排序Dto对象
 * @author yangzhiguo
 */
@Data
public class SortDto
{
    //排序方式
    private String orderType;

    //排序字段
    private String orderField;

    public SortDto(String orderType, String orderField) {
        this.orderType = orderType;
        this.orderField = orderField;
    }

    //默认为DESC排序
    public SortDto(String orderField) {
        this.orderField = orderField;
        this.orderType = "desc";
    }
}
