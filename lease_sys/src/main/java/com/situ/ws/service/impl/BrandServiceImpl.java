package com.situ.ws.service.impl;

import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.document.Operation;
import com.situ.ws.bean.entries.Brand;
import com.situ.ws.bean.entries.Vehicles;
import com.situ.ws.bean.enums.OperationStatus;
import com.situ.ws.mapper.BrandMapper;
import com.situ.ws.mapper.VehiclesMapper;
import com.situ.ws.service.IBrandService;
import com.situ.ws.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ws
 * @since 2024-08-10
 */
@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements IBrandService {

    private final VehiclesMapper vehiclesMapper;
    private final BrandMapper mapper;

    @Override
    public List<Brand> getAllBrands(String name) {
        return mapper.selectAllByName(name);
    }

    @Override
    public List<Brand> PageQuery(Brand brand, Page page) {
        return mapper.selectPage(brand,page);
    }

    @Override
    public int getCount(Brand brand) {
        return mapper.getCount(brand);
    }

    @Override
    public int update(Brand brand) {
        brand = (Brand) FileUtils.getLastName(brand);
        brand.setUpdateTime(LocalDate.now());
        return mapper.update(brand);
    }

    @Override
    public int insert(Brand brand) {
        brand = (Brand) FileUtils.getLastName(brand);
        brand.setUpdateTime(LocalDate.now());
        return mapper.insert(brand);
    }

    @Override
    public int logicalDeletion(Long[] ids) {


        // 查询当前品牌下，是否有车辆
        List<Integer> count = vehiclesMapper.getCountByGroup(ids);


        if (count != null && !count.isEmpty()) {
            throw new RuntimeException("该品牌下，还存在车辆！");
        }

        return mapper.logicalDeletion(ids);
    }
}
