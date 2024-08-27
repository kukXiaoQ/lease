package com.situ.ws.service;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.entries.Rentals;

import java.util.List;

/**
 * <p>
 * 租赁管理模块 服务类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
public interface IRentalsService {

    /**
     * 分页+条件查询
     * @param rentals 条件
     * @param page 当前页码
     * @return 统一结果
     */
    List<Rentals> PageQuery(Rentals rentals, Page page);

    int getCount(Rentals rentals);

    /**
     * 保存租赁信息
     * @param rentals 租赁信息
     * @return 修改行数
     */
    int addPayments(Rentals rentals);

    /**
     * 批量删除（逻辑删除）
     * @param ids id数组
     * @return 统一结果
     */
    int logicalDeletion(Long[] ids);

    /**
     * 修改租赁信息
     * @param rentals 修改租赁信息
     * @return 统一格式
     */
    int updateById(Rentals rentals);

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
}
