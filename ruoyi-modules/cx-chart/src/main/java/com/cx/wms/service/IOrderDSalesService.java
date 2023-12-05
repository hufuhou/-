package com.cx.wms.service;

import java.util.List;
import com.cx.wms.domain.OrderDSales;

/**
 * 销售明细Service接口
 * 
 * @author 熊睿宸
 * @date 2023-11-23
 */
public interface IOrderDSalesService 
{
    /**
     * 查询销售明细
     * 
     * @param sdId 销售明细主键
     * @return 销售明细
     */
    public OrderDSales selectOrderDSalesBySdId(Long sdId);

    /**
     * 查询销售明细列表
     * 
     * @param orderDSales 销售明细
     * @return 销售明细集合
     */
    public List<OrderDSales> selectOrderDSalesList(OrderDSales orderDSales);

    /**
     * 查询销售明细列表带用户名
     *
     * @param orderDSales 销售明细
     * @return 销售明细集合
     */
    public List<OrderDSales> findListWithUser(OrderDSales orderDSales);

    /**
     * 新增销售明细
     * 
     * @param orderDSales 销售明细
     * @return 结果
     */
    public int insertOrderDSales(OrderDSales orderDSales);

    /**
     * 修改销售明细
     * 
     * @param orderDSales 销售明细
     * @return 结果
     */
    public int updateOrderDSales(OrderDSales orderDSales);

    /**
     * 批量删除销售明细
     * 
     * @param sdIds 需要删除的销售明细主键集合
     * @return 结果
     */
    public int deleteOrderDSalesBySdIds(Long[] sdIds);

    /**
     * 删除销售明细信息
     * 
     * @param sdId 销售明细主键
     * @return 结果
     */
    public int deleteOrderDSalesBySdId(Long sdId);

    /**
     * 查询总销售订单数
     * @return 未删除订单数
     */
    public Integer findOrderSalesNumber();

    /**
     * 查询销售货品金额
     * @return 总销售货品金额
     */
    public Integer sumSaleAmount();
}
