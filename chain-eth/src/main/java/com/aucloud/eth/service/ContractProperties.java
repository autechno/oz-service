package com.aucloud.eth.service;

import com.aucloud.constant.CurrencyEnum;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.exception.ServiceRuntimeException;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "address.contract")
public class ContractProperties {
    private String usdt;
    private String ozc;
    private String toto;
    private String transfer;
    private long chainId;

    public String getContractAddress(CurrencyEnum currencyEnum) {
        String contractAddress = "0x0000000000000000000000000000000000000000";
        switch (currencyEnum) {
            case OZC: contractAddress = ozc;break;
            case TOTO:contractAddress = toto;break;
            case USDT:contractAddress = usdt;break;
            default:
                throw new ServiceRuntimeException(ResultCodeEnum.FAIL);
        }
        return contractAddress;
    }

}
