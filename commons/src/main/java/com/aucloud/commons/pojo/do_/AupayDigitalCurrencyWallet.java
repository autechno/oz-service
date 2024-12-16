package com.aucloud.commons.pojo.do_;//package com.aucloud.aupay.pojo.do_;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.Date;
//
//public class AupayDigitalCurrencyWallet implements Serializable {
//
//    private String walletId;
//
//    private Date createTime;
//
//    private Boolean isDel;
//
//    private Integer currencyId;
//
//    private Integer currencyChain;
//
//    private String address;
//
//    private String path;
//
//    private BigDecimal balance;
//
//    private BigDecimal feeBalance;
//
//    private Integer useWay;
//
//    private static final long serialVersionUID = 1L;
//
//    public String getWalletId() {
//        return walletId;
//    }
//
//    public void setWalletId(String walletId) {
//        this.walletId = walletId;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Boolean getIsDel() {
//        return isDel;
//    }
//
//    public void setIsDel(Boolean isDel) {
//        this.isDel = isDel;
//    }
//
//    public Integer getCurrencyId() {
//        return currencyId;
//    }
//
//    public void setCurrencyId(Integer currencyId) {
//        this.currencyId = currencyId;
//    }
//
//    public Integer getCurrencyChain() {
//        return currencyChain;
//    }
//
//    public void setCurrencyChain(Integer currencyChain) {
//        this.currencyChain = currencyChain;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public BigDecimal getBalance() {
//        return balance;
//    }
//
//    public void setBalance(BigDecimal balance) {
//        this.balance = balance;
//    }
//
//    public BigDecimal getFeeBalance() {
//        return feeBalance;
//    }
//
//    public void setFeeBalance(BigDecimal feeBalance) {
//        this.feeBalance = feeBalance;
//    }
//
//    public Integer getUseWay() {
//        return useWay;
//    }
//
//    public void setUseWay(Integer useWay) {
//        this.useWay = useWay;
//    }
//
//    @Override
//    public String toString() {
//        return "AupayDigitalCurrencyWallet{" +
//                "walletId='" + walletId + '\'' +
//                ", createTime=" + createTime +
//                ", isDel=" + isDel +
//                ", currencyId=" + currencyId +
//                ", currencyChain=" + currencyChain +
//                ", address='" + address + '\'' +
//                ", path='" + path + '\'' +
//                ", balance=" + balance +
//                ", feeBalance=" + feeBalance +
//                ", useWay=" + useWay +
//                '}';
//    }
//}