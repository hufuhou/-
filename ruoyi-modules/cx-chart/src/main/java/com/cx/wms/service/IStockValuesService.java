package com.cx.wms.service;

import com.cx.wms.domain.StockValues;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IStockValuesService {

    //查询所有库存信息
    public List<StockValues> findAllInfo();

    //查询库存货品数总金额
    public StockValues findCountValue(@RequestParam(required = false)Integer wId);

}
