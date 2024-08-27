package com.situ.ws.service;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.VehiclesDto;
import com.situ.ws.bean.entries.Vehicles;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆管理模块 服务类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
public interface IVehiclesService {


    /**
     * 分页查询
     * @param vehicles 查询条件
     * @param page  分页查询条件
     * @return 符合条件集合
     */
    List<VehiclesDto> PageQuery(Vehicles vehicles, Page page);

    /**
     * 获取总条数
     * @return 总条数
     */
    int getCount(Vehicles vehicles);


    /**
     * 逻辑删除
     * @param ids id数组
     * @return 删除条数
     */
    int logicalDeletion(Long[] ids);


    /**
     * 真实删除
     * @param ids id数组
     * @return 删除条数
     */
    int removeByIds(Long[] ids);

    /**
     * 修改记录
     * @param vehicles 修改数据
     * @return 修改行数
     */
    int updateById(Vehicles vehicles);

    /**
     * 保存订单
     * @param vehicles 订单信息
     * @return 修改行数
     */
    int addVehicles(Vehicles vehicles);

    /**
     * 恢复数据
     * @param ids id数组
     * @return 恢复条数
     */
    int restoreData(Long[] ids);

    /**
     * 根据品牌，查询车牌号
     * @param id
     * @return
     */
    List<Vehicles> getVehiclesFromNumber(Integer id);

    Integer[] getCountByStatus();

    Map<String, Object[]> getSalesChart();

}
