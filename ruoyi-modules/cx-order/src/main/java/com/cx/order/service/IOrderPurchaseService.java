package com.cx.order.service;

import java.util.List;
import com.cx.order.domain.OrderPurchase;

/**
 * 进货订单Service接口
 * 
 * @author 刘志昂
 * @date 2023-11-20
 */
public interface IOrderPurchaseService 
{
    /**
     * 查询进货订单
     * 
     * @param poId 进货订单主键
     * @return 进货订单
     */
    public OrderPurchase selectOrderPurchaseByPoId(Long poId);

    /**
     * 查询进货订单列表
     * 
     * @param orderPurchase 进货订单
     * @return 进货订单集合
     */
    public List<OrderPurchase> selectOrderPurchaseList(OrderPurchase orderPurchase);

    /**
     * 新增进货订单
     * 
     * @param orderPurchase 进货订单
     * @return 结果
     */
    public int insertOrderPurchase(OrderPurchase orderPurchase);

    /**
     * 修改进货订单
     * 
     * @param orderPurchase 进货订单
     * @return 结果
     */
    public int updateOrderPurchase(OrderPurchase orderPurchase);

    /**
     * 批量删除进货订单
     * 
     * @param poIds 需要删除的进货订单主键集合
     * @return 结果
     */
    public int deleteOrderPurchaseByPoIds(Long[] poIds);

    /**
     * 删除进货订单信息
     * 
     * @param poId 进货订单主键
     * @return 结果
     */
    public int deleteOrderPurchaseByPoId(Long poId);
}
