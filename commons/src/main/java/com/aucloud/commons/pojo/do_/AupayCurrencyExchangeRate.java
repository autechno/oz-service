package com.aucloud.commons.pojo.do_;

import java.io.Serializable;

public class AupayCurrencyExchangeRate implements Serializable {
    private Integer currencyId;

    private String name;

    private String abbreviation;

    private Integer type;

    private String apiUrl;

    private static final long serialVersionUID = 1L;

    public Integer getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}