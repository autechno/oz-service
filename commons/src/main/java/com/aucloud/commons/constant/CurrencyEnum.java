package com.aucloud.commons.constant;

import com.aucloud.commons.exception.ServiceRuntimeException;

import java.math.BigDecimal;

public enum CurrencyEnum {

    BTC("比特币","Bitcoin",1,18,"0", 6, new BigDecimal("0.0001")),
    ETH("以太币","Ethereum",2,18,"2", 2, new BigDecimal("0.0001")),
    USDT("泰达币","Tether USD",3,18,"2,3",2, new BigDecimal("5")),
    TRX("波场币","TRON",4,18,"3",6, new BigDecimal("5")),
    OZC("OZCoin","OZCoin",5,18,"2",2, new BigDecimal("5")),
    TOTO("TOTO","TOTO",6,18,"2",2, new BigDecimal("10")),
    ;

    public Integer id;

    public String name_zh_cn;

    public String name_en_us;

    public Integer precision;

    public String supportChain;

    public Integer confirmBlockNumber;

    public BigDecimal minRechargeAmount;

    CurrencyEnum(String name_zh_cn, String name_en_us, Integer id, Integer precision, String supportChain, Integer confirmBlockNumber, BigDecimal minRechargeAmount) {
        this.name_zh_cn = name_zh_cn;
        this.name_en_us = name_en_us;
        this.id = id;
        this.precision = precision;
        this.supportChain = supportChain;
        this.confirmBlockNumber = confirmBlockNumber;
        this.minRechargeAmount = minRechargeAmount;
    }

    public static CurrencyEnum findById(Integer id) {
        CurrencyEnum[] currencyEnums = CurrencyEnum.values();
        for (CurrencyEnum currencyEnum : currencyEnums) {
            if (currencyEnum.id.equals(id)) {
                return currencyEnum;
            }
        }
        throw new ServiceRuntimeException(ResultCodeEnum.NON_SUPPORTED_CURRENCY);
    }

    public enum CurrencyChainEnum {

//        DEFAULT(0,"DEFAULT",0),
//        ERC20(2,"ERC20",2),
//        TRC20(3,"TRC20",4),

        BTC(0,"BTC",1),
        ETH(2,"ERC20",2),
        TRC(3,"TRC20",4)
        ;


        public Integer id;

        public String name;

        public Integer chainCurrencyId;

        CurrencyChainEnum(Integer id, String name, Integer chainCurrencyId) {
            this.id = id;
            this.name = name;
            this.chainCurrencyId = chainCurrencyId;
        }

        public static CurrencyChainEnum findById(Integer id){
            CurrencyChainEnum[] currencyEnums = CurrencyChainEnum.values();
            for(CurrencyChainEnum currencyChainEnumEnum : currencyEnums){
                if(currencyChainEnumEnum.id.equals(id)) {
                    return currencyChainEnumEnum;
                }
            }
            throw new ServiceRuntimeException(ResultCodeEnum.NON_SUPPORTED_CHAIN);
        }

    }


}
