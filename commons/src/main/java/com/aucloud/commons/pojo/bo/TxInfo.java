package com.aucloud.commons.pojo.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class TxInfo {

    private String txId;

    private Date date;

    private Integer status;

    private BigDecimal transferFee;

    private List<Transfer> transfer;

    private String from;

    private Long confirmCount;

    private String error;

    private Long blockCount;

    @Data
    public static class Transfer {

        private String to;

        private BigDecimal amount;

    }

    public static Transfer createTransfer(String to, BigDecimal amount) {
        Transfer transfer = new Transfer();
        transfer.setTo(to);
        transfer.setAmount(amount);
        return transfer;
    }

    public Transfer getTransfer(String toAddress,BigDecimal amount) {
        List<Transfer> transferList = this.getTransfer();
        if(transferList!=null) {
            for(Transfer transfer : transferList) {
                if(transfer.getTo().equals(toAddress) && transfer.getAmount().compareTo(amount)==0) {
                    return transfer;
                }
            }
        }
        return null;
    }
}
