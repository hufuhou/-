package com.cx.order.service;

import java.util.List;

import com.cx.order.domain.OrderPurchase;
import com.cx.order.domain.OrderPurchaseDetails;
import com.cx.order.domain.OrderSales;
import com.cx.order.domain.OrderSalesDetails;

/**
 * 销售订单Service接口
 * 
 * @author LZA
 * @date 2023-12-19
 */
public interface IOrderSalesService 
{
    /**
     * 查询销售订单
     * 
     * @param sId 销售订单主键
     * @return 销售订单
     */
    public OrderSales selectOrderSalesBySId(Long sId);

    /**
     * 查询销售订单列表
     * 
     * @param orderSales 销售订单
     * @return 销售订单集合
     */
    public List<OrderSales> selectOrderSalesList(OrderSales orderSales);

    /**
     * 新增销售订单
     * 
     * @param orderSales 销售订单
     * @return 结果
     */
    public int insertOrderSales(OrderSales orderSales, List<OrderSalesDetails> orderSalesDetails);

    /**
     * 修改销售订单
     * 
     * @param orderSales 销售订单
     * @return 结果
     */
    public int updateOrderSales(OrderSales orderSales);

    /**
     * 批量删除销售订单
     * 
     * @param sIds 需要删除的销售订单主键集合
     * @return 结果
     */
    public int deleteOrderSalesBySIds(Long[] sIds);

    /**
     * 删除销售订单信息
     * 
     * @param sId 销售订单主键
     * @return 结果
     */
    public int deleteOrderSalesBySId(Long sId);

    /**
     * 生成销售编码
     */
    public OrderSales getsCode();
}
