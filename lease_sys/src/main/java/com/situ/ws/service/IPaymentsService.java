package com.situ.ws.service;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.entries.Orders;
import com.situ.ws.bean.entries.Payments;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 支付管理模块 服务类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
public interface IPaymentsService {


    /**
     * 分页查询
     * @param payments 支付查询条件
     * @param page  分页查询条件
     * @return 符合条件支付集合
     */
    List<Payments> PageQuery(Payments payments, Page page);


    /**
     * 获取总条数
     * @return 总条数
     */
    int getCount(Payments payments);

    /**
     * 保存支付信息
     * @param payments 支付信息
     * @return 修改行数
     */
    int addPayments(Payments payments);


    /**
     * 批量删除（逻辑删除）
     * @param ids id数组
     * @return 统一结果
     */
    int logicalDeletion(Long[] ids);

    /**
     * 修改支付信息
     * @param payments 修改信息
     * @return 统一格式
     */
    int updateById(Payments payments);

    /**
     * 恢复数据
     * @param ids id数组
     * @return 恢复条数
     */
    int restoreData(Long[] ids);

    /**
     * 真实删除
     * @param ids id数组
     * @return 修改行数
     */
    int removeByIds(Long[] ids);

    BigDecimal[] getAmount();

    Map<String, Object> getOneSalesChart();

}
