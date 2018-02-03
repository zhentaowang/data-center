package com.adatafun.base.data.center.mapper;

import com.adatafun.base.data.center.common.PageEntity;
import com.adatafun.base.data.center.po.DataCenterApiPO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DataCenterApiPOMapper {
    int deleteByPrimaryKey(Long flightCenterApiId);

    int insert(DataCenterApiPO record);

    int insertSelective(DataCenterApiPO record);

    DataCenterApiPO selectByPrimaryKey(Long flightCenterApiId);

    int updateByPrimaryKeySelective(DataCenterApiPO record);

    int updateByPrimaryKey(DataCenterApiPO record);

    /**
     * 插入一条记录
     *
     * @param dataCenterApiPO
     * @return
     */
    int insertOne(DataCenterApiPO dataCenterApiPO);

    /**
     * 查询客户端请求的数据
     *
     * @param customerCode
     * @return
     */
    List<DataCenterApiPO> selectByCustomerCode(String customerCode);

    /**
     * 根据方法名查询请求的方法
     *
     * @param name
     * @param startTime
     * @param endTime
     * @param pageEntity
     * @return
     */
    List<DataCenterApiPO> selectMethodName(@Param("name") String name, @Param("startTime") Date startTime,
                                           @Param("endTime") Date endTime, @Param("pageEntity") PageEntity pageEntity);

    /**
     * 根据方法名查询请求的方法数量
     *
     * @param update
     * @param date
     * @param nextDate
     * @return
     */
    int countMethodName(String update, Date date, Date nextDate);

    /**
     * 查询请求的接口列表
     *
     * @param dataCenterApiPO
     * @param pageEntity
     * @return
     */
    List<DataCenterApiPO> findDataCenterApiPOSByConditionAndPage(@Param("dataCenterApiPO") DataCenterApiPO dataCenterApiPO,
                                                                 @Param("pageEntity") PageEntity pageEntity);

    /**
     * @param dataCenterApiPO
     * @return
     */
    int countDataCenterApiPOSByCondition(@Param("dataCenterApiPO") DataCenterApiPO dataCenterApiPO);

}