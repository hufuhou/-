package com.cx.order.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.order.mapper.OrderSalesMapper;
import com.cx.order.domain.OrderSales;
import com.cx.order.service.IOrderSalesService;

/**
 * 销售订单Service业务层处理
 * 
 * @author LZA
 * @date 2023-12-19
 */
@Service
public class OrderSalesServiceImpl implements IOrderSalesService 
{
    @Autowired
    private OrderSalesMapper orderSalesMapper;

    /**
     * 查询销售订单
     * 
     * @param sId 销售订单主键
     * @return 销售订单
     */
    @Override
    public OrderSales selectOrderSalesBySId(Long sId)
    {
        return orderSalesMapper.selectOrderSalesBySId(sId);
    }

    /**
     * 查询销售订单列表
     * 
     * @param orderSales 销售订单
     * @return 销售订单
     */
    @Override
    public List<OrderSales> selectOrderSalesList(OrderSales orderSales)
    {
        return orderSalesMapper.selectOrderSalesList(orderSales);
    }

    /**
     * 新增销售订单
     * 
     * @param orderSales 销售订单
     * @return 结果
     */
    @Override
    public int insertOrderSales(OrderSales orderSales)
    {
        orderSales.setCreateTime(DateUtils.getNowDate());
        return orderSalesMapper.insertOrderSales(orderSales);
    }

    /**
     * 修改销售订单
     * 
     * @param orderSales 销售订单
     * @return 结果
     */
    @Override
    public int updateOrderSales(OrderSales orderSales)
    {
        orderSales.setUpdateTime(DateUtils.getNowDate());
        return orderSalesMapper.updateOrderSales(orderSales);
    }

    /**
     * 批量删除销售订单
     * 
     * @param sIds 需要删除的销售订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderSalesBySIds(Long[] sIds)
    {
        return orderSalesMapper.deleteOrderSalesBySIds(sIds);
    }

    /**
     * 删除销售订单信息
     * 
     * @param sId 销售订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderSalesBySId(Long sId)
    {
        return orderSalesMapper.deleteOrderSalesBySId(sId);
    }
}
