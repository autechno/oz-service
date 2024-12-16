package com.aucloud.aupay.feign.config;

import com.aucloud.commons.constant.ApplicationConstant;
import com.aucloud.commons.utils.RequestProofManage;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
//        String xid = RootContext.getXID();
//        if(!StringUtils.isEmpty(xid)) {
//            requestTemplate.header(RootContext.KEY_XID, xid);
//        }
        String proof = RequestProofManage.sign();
        requestTemplate.header(ApplicationConstant.AUPAY_REQUEST_PROOF, proof);
    }

}
