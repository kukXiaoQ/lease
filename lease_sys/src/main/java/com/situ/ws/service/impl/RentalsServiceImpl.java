package com.situ.ws.service.impl;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.entries.Rentals;
import com.situ.ws.mapper.RentalsMapper;
import com.situ.ws.service.IRentalsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 租赁管理模块 服务实现类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Service
@RequiredArgsConstructor
public class RentalsServiceImpl implements IRentalsService {

    private final RentalsMapper mapper;

    @Override
    public List<Rentals> PageQuery(Rentals rentals, Page page) {
        return mapper.selectPage(rentals,page);
    }

    @Override
    public int getCount(Rentals rentals) {
        return mapper.getCount(rentals);
    }

    @Override
    public int addPayments(Rentals rentals) {
        return mapper.insert(rentals);
    }

    @Override
    public int logicalDeletion(Long[] ids) {
        return mapper.logicalDeletion(ids);
    }

    @Override
    public int updateById(Rentals rentals) {
        return mapper.update(rentals);
    }

    @Override
    public int restoreData(Long[] ids) {
        return mapper.restoreData(ids);
    }

    @Override
    public int removeByIds(Long[] ids) {
        return mapper.deleteByIds(ids);
    }
}
