package com.cx.order.service;

import java.util.List;
import com.cx.order.domain.OrderSalesDetails;

/**
 * 销售明细Service接口
 * 
 * @author LZA
 * @date 2023-12-19
 */
public interface IOrderSalesDetailsService 
{
    /**
     * 查询销售明细
     * 
     * @param sdId 销售明细主键
     * @return 销售明细
     */
    public OrderSalesDetails selectOrderSalesDetailsBySdId(Long sdId);

    /**
     * 查询销售明细列表
     * 
     * @param orderSalesDetails 销售明细
     * @return 销售明细集合
     */
    public List<OrderSalesDetails> selectOrderSalesDetailsList(OrderSalesDetails orderSalesDetails);

    /**
     * 新增销售明细
     * 
     * @param orderSalesDetails 销售明细
     * @return 结果
     */
    public int insertOrderSalesDetails(OrderSalesDetails orderSalesDetails);

    /**
     * 修改销售明细
     * 
     * @param orderSalesDetails 销售明细
     * @return 结果
     */
    public int updateOrderSalesDetails(OrderSalesDetails orderSalesDetails);

    /**
     * 批量删除销售明细
     * 
     * @param sdIds 需要删除的销售明细主键集合
     * @return 结果
     */
    public int deleteOrderSalesDetailsBySdIds(Long[] sdIds);

    /**
     * 删除销售明细信息
     * 
     * @param sdId 销售明细主键
     * @return 结果
     */
    public int deleteOrderSalesDetailsBySdId(Long sdId);
}
