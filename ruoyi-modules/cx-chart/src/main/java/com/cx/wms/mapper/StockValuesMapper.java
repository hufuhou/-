package com.cx.wms.mapper;

import com.cx.wms.domain.StockValues;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface StockValuesMapper {

    //查询所有库存信息
    public List<StockValues> findAllInfo();

    //查询库存货品数总金额
    public StockValues findCountValue(@RequestParam(required = false) Integer wId);


}
