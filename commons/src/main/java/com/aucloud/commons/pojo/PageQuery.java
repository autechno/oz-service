package com.aucloud.commons.pojo;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PageQuery<T>{

    @Min(1)
    private int pageNo = 1;//默认第1页
    @Min(1)
    private int pageSize = 10;//默认每页10条

    private T conditions;

    private String orderByColumn;
    private String orderType;

}
