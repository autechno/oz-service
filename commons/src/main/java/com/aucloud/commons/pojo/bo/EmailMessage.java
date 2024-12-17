package com.aucloud.commons.pojo.bo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
public class EmailMessage {

    private String email;

    private String title;

    private String content;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
