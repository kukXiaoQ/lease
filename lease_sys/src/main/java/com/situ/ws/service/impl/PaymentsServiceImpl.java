package com.situ.ws.service.impl;


import com.situ.ws.bean.common.Page;
import com.situ.ws.bean.dto.ReportTwoDto;
import com.situ.ws.bean.entries.Payments;
import com.situ.ws.mapper.PaymentsMapper;
import com.situ.ws.service.IPaymentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * <p>
 * 支付管理模块 服务实现类
 * </p>
 *
 * @author ws
 * @since 2024-08-07
 */
@Service
@RequiredArgsConstructor
public class PaymentsServiceImpl implements IPaymentsService {

    private final PaymentsMapper mapper;

    @Override
    public List<Payments> PageQuery(Payments payments, Page page) {
        return mapper.selectPage(payments,page);
    }

    @Override
    public int getCount(Payments payments) {
        return mapper.getCount(payments);
    }

    @Override
    public int addPayments(Payments payments) {
        return mapper.insert(payments);
    }

    @Override
    public int logicalDeletion(Long[] ids) {
        return mapper.logicalDeletion(ids);
    }

    @Override
    public int updateById(Payments payments) {
        return mapper.update(payments);
    }

    @Override
    public int restoreData(Long[] ids) {
        return mapper.restoreData(ids);
    }

    @Override
    public Map<String, Object> getOneSalesChart() {
        LocalDate now = LocalDate.now();

        Map<String, Object> map = new HashMap<>();

        String[] days = new String[7];
        Integer[] count = new Integer[7];
        int sum = -7;
        for (int i = 0; i < 7; i++) {
            LocalDate localDate = now.plusDays(sum++);
            // 获取今天产生的订单
            Integer cos = mapper.getPayCount(localDate);

            count[i] = cos;
            days[i] = localDate.toString().substring(5);
        }

        map.put("days", days);
        map.put("count", count);

        return map;
    }

    @Override
    public BigDecimal[] getAmount() {
        // 按季度查询金额

        ReportTwoDto season = mapper.getAmountForSeason();

        BigDecimal[] bigDecimals = new BigDecimal[4];

        bigDecimals[0] = season.getSpring();
        bigDecimals[1] = season.getSummer();
        bigDecimals[2] = season.getAutumn();
        bigDecimals[3] = season.getWinter();

        return bigDecimals;
    }

    @Override
    public int removeByIds(Long[] ids) {
        return mapper.deleteByIds(ids);
    }
}
