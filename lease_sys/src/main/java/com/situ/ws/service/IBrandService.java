package com.situ.ws.service;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.entries.Brand;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ws
 * @since 2024-08-10
 */
public interface IBrandService {

    List<Brand> getAllBrands(String name);


    List<Brand> PageQuery(Brand brand, Page page);

    int getCount(Brand brand);

    int update(Brand brand);

    int insert(Brand brand);

    int logicalDeletion(Long[] ids);
}
