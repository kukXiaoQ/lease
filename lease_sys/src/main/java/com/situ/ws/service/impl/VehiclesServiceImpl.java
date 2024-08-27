package com.situ.ws.service.impl;

import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.ReportThreeDto;
import com.situ.ws.bean.dto.VehiclesDto;
import com.situ.ws.bean.entries.Brand;
import com.situ.ws.bean.entries.ReportForm;
import com.situ.ws.bean.entries.Vehicles;
import com.situ.ws.mapper.BrandMapper;
import com.situ.ws.mapper.VehiclesMapper;
import com.situ.ws.service.IVehiclesService;
import com.situ.ws.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 车辆管理模块 服务实现类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VehiclesServiceImpl implements IVehiclesService {


    private final VehiclesMapper mapper;
    private final BrandMapper brandMapper;

    @Override
    public List<VehiclesDto> PageQuery(Vehicles vehicles, Page page) {
        List<Vehicles> list = mapper.selectPage(vehicles, page);

        List<VehiclesDto> dtos = new ArrayList<>();
        for (Vehicles v : list) {
            VehiclesDto dto = new VehiclesDto();
            Integer brandId = v.getBrandId();
            // 根据id获取品牌名称
            Brand brand = brandMapper.getById(brandId);
            dto.setVehicles(v);
            dto.setBrandName(brand.getName());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public int getCount(Vehicles vehicles) {
        return mapper.getCount(vehicles);
    }

    @Override
    public int logicalDeletion(Long[] ids) {
        return mapper.logicalDeletion(ids);
    }

    @Override
    public int removeByIds(Long[] ids) {
        return mapper.deleteByIds(ids);
    }

    @Override
    public int updateById(Vehicles vehicles) {

        vehicles = (Vehicles) FileUtils.getLastName(vehicles);

        return mapper.update(vehicles);
    }

    @Override
    public int addVehicles(Vehicles vehicles) {

        vehicles = (Vehicles) FileUtils.getLastName(vehicles);

        return mapper.insert(vehicles);
    }

    @Override
    public int restoreData(Long[] ids) {
        return mapper.restoreData(ids);
    }

    @Override
    public List<Vehicles> getVehiclesFromNumber(Integer id) {
        return mapper.getVehiclesForNumber(id);
    }

    @Override
    public Map<String, Object[]> getSalesChart() {
        // 查询销量前三的品牌
        List<ReportThreeDto> list = mapper.getSalesChart();

        Map<String, Object[]> map = new HashMap<>();

        Integer[] counts = new Integer[list.size()];
        String[] brands = new String[list.size()];
        int j = list.size() - 1;
        for (int i = 0; i < list.size(); i++,j--) {
            counts[i] = list.get(j).getCount();
            brands[i] = list.get(j).getName();
        }

        map.put("counts", counts);
        map.put("brands", brands);

        return map;
    }

    @Override
    public Integer[] getCountByStatus() {
        // 根据状态查询数量
        List<ReportForm> list = mapper.getCountByStatus();
        Integer sum = 0;

        Integer[] ids = new Integer[list.size() + 1];
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i).getCount();
            ids[i] = list.get(i).getCount();
        }
        ids[ids.length - 1] = sum;
        System.out.println(Arrays.toString(ids));

        System.out.println("=============================");

        return ids;
    }
}
