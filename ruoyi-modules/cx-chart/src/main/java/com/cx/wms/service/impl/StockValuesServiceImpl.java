package com.cx.wms.service.impl;

import com.cx.wms.domain.StockValues;
import com.cx.wms.mapper.StockValuesMapper;
import com.cx.wms.service.IStockValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class StockValuesServiceImpl implements IStockValuesService {

    @Autowired
    private StockValuesMapper stockValuesMapper;

    //查询所有库存信息
    @Override
    public List<StockValues> findAllInfo() {
        return stockValuesMapper.findAllInfo();
    }

    @Override
    public StockValues findCountValue(@RequestParam(required = false) Integer wId) {
        return stockValuesMapper.findCountValue(wId);
    }


}
