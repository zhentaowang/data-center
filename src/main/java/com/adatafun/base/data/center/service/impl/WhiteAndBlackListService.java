package com.adatafun.base.data.center.service.impl;

import com.adatafun.base.data.center.mapper.BlackListPOMapper;
import com.adatafun.base.data.center.mapper.WhiteListPOMapper;
import com.adatafun.base.data.center.po.BlackListPO;
import com.adatafun.base.data.center.po.WhiteListPO;
import com.adatafun.base.data.center.service.WhiteAndBlackListServiceInterface;
import com.adatafun.base.data.center.util.RedisUtils;
import com.adatafun.base.data.center.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tiecheng
 * @date 2018/1/22
 */
@Service
public class WhiteAndBlackListService implements WhiteAndBlackListServiceInterface {

    private static final String WHITE_LIST = "white_list";

    private static final String BLACK_LIST = "black_list";

    @Autowired
    private WhiteListPOMapper whiteListPOMapper;

    @Autowired
    private BlackListPOMapper blackListPOMapper;

    @Override
    public List<WhiteListPO> queryWhiteLists() {
        return whiteListPOMapper.findWhiteList();
    }

    @Override
    public List<BlackListPO> queryBlackLists() {
        return blackListPOMapper.findBlackList();
    }

    @Override
    public long createWhiteList(WhiteListPO whiteListPO) {
        int i = whiteListPOMapper.insertOne(whiteListPO);
        List<String> whiteIps = new ArrayList<>();
        if (i > 0) {
            freshCache(whiteIps, WHITE_LIST, whiteListPO.getIp());
        }
        return whiteListPO.getWhiteId();
    }

    @Override
    public long createBlcakList(BlackListPO blackListPO) {
        int i = blackListPOMapper.insertOne(blackListPO);
        List<String> blackIps = new ArrayList<>();
        if (i > 0) {
            freshCache(blackIps, BLACK_LIST, blackListPO.getIp());
        }
        return blackListPO.getBlackId();
    }

    private void freshCache(List<String> ips, String redisKey, String ip) {
        String cache = RedisUtils.get(redisKey);
        if (StringUtils.isBlank(cache)) {
            ips.add(ip);
            RedisUtils.set(redisKey, JSON.toJSONString(ips));
        } else {
            ips = JSONArray.parseArray(cache, String.class);
            ips.add(ip);
            RedisUtils.set(redisKey, JSON.toJSONString(ips));
        }

    }

    @Override
    public long deleteWhiteList(Long whiteId) {
        return 0;
    }

    @Override
    public long deleteBlcakList(Long blackId) {
        return 0;
    }

    @Override
    public void freshWhiteAndBlackLists() {
        List<BlackListPO> blackListPOS = queryBlackLists();
        List<String> blackIps = new ArrayList<>();
        for (BlackListPO blackListPO : blackListPOS) {
            blackIps.add(blackListPO.getIp());
        }
        List<WhiteListPO> whiteListPOS = queryWhiteLists();
        List<String> whiteIps = new ArrayList<>();
        for (WhiteListPO whiteListPO : whiteListPOS) {
            whiteIps.add(whiteListPO.getIp());
        }
        RedisUtils.set(BLACK_LIST, JSON.toJSONString(blackIps));
        RedisUtils.set(WHITE_LIST, JSON.toJSONString(whiteIps));
    }

}
