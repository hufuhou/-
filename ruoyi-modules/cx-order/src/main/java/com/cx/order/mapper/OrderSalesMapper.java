package com.cx.order.mapper;

import java.util.List;
import com.cx.order.domain.OrderSales;

/**
 * 销售订单Mapper接口
 * 
 * @author LZA
 * @date 2023-12-19
 */
public interface OrderSalesMapper 
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
    public int insertOrderSales(OrderSales orderSales);

    /**
     * 修改销售订单
     * 
     * @param orderSales 销售订单
     * @return 结果
     */
    public int updateOrderSales(OrderSales orderSales);

    /**
     * 删除销售订单
     * 
     * @param sId 销售订单主键
     * @return 结果
     */
    public int deleteOrderSalesBySId(Long sId);

    /**
     * 批量删除销售订单
     * 
     * @param sIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderSalesBySIds(Long[] sIds);
}
