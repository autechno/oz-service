package com.aucloud.constant;

public interface QueueConstant {

    String SEND_EMAIL = "aupay_send_email";

    String TRANSFER_DEAL = "transfer_deal";

    String CHAIN_SCAN = "chain_scan";

    String RECHARGE_DEAL = "recharge_deal";//充值到账

    String USER_RECHARGE_DEAL = "user_recharge_deal";//充值到账 更新用户数据

    String CHECK_TRANSFER_FEE = "check_transfer_fee";//gas费
//    String SUPPLEMENT_TRANSFER_FEE = "supplement_transfer_fee";

    String CHECK_USER_ASSETS = "check_user_assets";//资金归集 用户->中转
    String USER_TO_TRANSFER = "user_to_transfer_";//资金归集 用户->中转
//    String CHECK_USER_ASSETS_TRANSFER_SYNC_STATE = "check_user_assets_transfer_sync_state";

//    String COLLECT_USER_ASSETS_PRE = "collect_user_assets_pre";

//    String CHECK_TRANSFER_ADDRES_ASSETS = "check_transfer_address_assets";//资金归集 中转->储备 改为定时任务触发
//    String RESERVE_TRANSFER_ADDRESS_ASSETS = "reserve_transfer_address_assets";

    String CHECK_WITHDRAW_ADDRESS_ASSETS = "check_withdraw_address_address";//资金归集 中转->提币
//    String SUPPLEMENT_WITHDRAW_ADDRESS_ASSETS = "supplement_withdraw_address_assets";

    String STATISTICAL = "statistical";

    String WITHDRAW_PROCESS = "withdraw_process";

    String WITHDRAW_COMPLATE = "withdraw_complate";
    String WITHDRAW_FAIL = "withdraw_fail";

    String AFTER_GAS_TRANSFER = "after_gas_transfer";
}
