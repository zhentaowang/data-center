package com.adatafun.base.data.center.mapper;

import com.adatafun.base.data.center.po.CustomerPO;
import org.apache.ibatis.annotations.Param;

public interface CustomerPOMapper {
    int deleteByPrimaryKey(Long customerId);

    int insert(CustomerPO record);

    int insertSelective(CustomerPO record);

    CustomerPO selectByPrimaryKey(Long customerId);

    int updateByPrimaryKeySelective(CustomerPO record);

    int updateByPrimaryKey(CustomerPO record);

    /**
     * 根据APPID查找客户
     * @param appId
     * @return
     */
    CustomerPO selectByAppId(@Param("appId") Integer appId);

    /**
     * 插入单条记录
     * @param customerPO
     * @return
     */
    int insertOne(CustomerPO customerPO);
}