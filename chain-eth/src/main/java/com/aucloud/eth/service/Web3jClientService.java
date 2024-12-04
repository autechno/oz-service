package com.aucloud.eth.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Getter
@Service
public class Web3jClientService {

    private Web3j web3j;

    @Value("${address.web3j.url}")
    public void setWeb3jUrl(String addressWeb3jUrl) {
        web3j = Web3j.build(new HttpService(addressWeb3jUrl));
    }

}
