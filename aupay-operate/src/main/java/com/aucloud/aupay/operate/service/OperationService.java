package com.aucloud.aupay.operate.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.aucloud.commons.constant.RedisCacheKeys;
import com.aucloud.commons.pojo.bo.HuobiTicker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OperationService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private RestTemplate restTemplate;

//    public List<AupayAnnouncement> findAnnouncementList(Pager pager) {
//        return operationDao.findAnnouncementList(pager);
//    }
//
//    public void addAnnouncement(AupayAnnouncement aupayAnnouncement) {
//        aupayAnnouncement.setCreateTime(new Date());
//        aupayAnnouncement.setIsDel(Boolean.FALSE);
//        operationDao.addAnnouncement(aupayAnnouncement);
//    }
//
//    public void updateAnnouncement(AupayAnnouncement aupayAnnouncement) {
//        operationDao.updateAnnouncement(aupayAnnouncement);
//    }
//
//    public void deleteAnnouncement(String announcementId) {
//        operationDao.deleteAnnouncement(announcementId);
//    }
//
//    public List<AupayAnnouncement> findViewAnnouncementList(Pager pager) {
//        return operationDao.findViewAnnouncementList(pager);
//    }

    public Map<String, String> getUSDTRates() {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        Map<String, String> entries = hashOperations.entries(RedisCacheKeys.USDT_RATE);
        if (entries.isEmpty()) {
            //https://api.coinbase.com/v2/exchange-rates?currency=USDT
            Map<String, String> params = new HashMap<>();
            params.put("currency", "USDT");
            String responseStr = restTemplate.getForObject("https://api.coinbase.com/v2/exchange-rates", String.class, params);
            JSONObject jsonObject = JSON.parseObject(responseStr);
            if (jsonObject != null) {
                jsonObject = jsonObject.getJSONObject("data");
                if (jsonObject != null) {
                    entries = jsonObject.getObject("rates", new TypeReference<>() {
                    });
                    if (entries != null && !entries.isEmpty()) {
                        hashOperations.putAll(RedisCacheKeys.USDT_RATE, entries);
                    }
                }
            }
        }
        return entries;
    }

    public List<HuobiTicker> getUSDTTickers() {
        HashOperations<String, String, HuobiTicker> hashOperations = redisTemplate.opsForHash();
        return hashOperations.values(RedisCacheKeys.USDT_TICKET);
    }

}
