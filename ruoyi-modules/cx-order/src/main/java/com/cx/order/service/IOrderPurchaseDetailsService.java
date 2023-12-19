package com.cx.order.service;

import java.util.List;
import com.cx.order.domain.OrderPurchaseDetails;

/**
 * 进货明细Service接口
 * 
 * @author LZA
 * @date 2023-12-07
 */
public interface IOrderPurchaseDetailsService 
{
    /**
     * 查询进货明细
     * 
     * @param pdId 进货明细主键
     * @return 进货明细
     */
    public OrderPurchaseDetails selectOrderPurchaseDetailsByPdId(Long pdId);

    /**
     * 查询进货明细列表
     * 
     * @param orderPurchaseDetails 进货明细
     * @return 进货明细集合
     */
    public List<OrderPurchaseDetails> selectOrderPurchaseDetailsList(OrderPurchaseDetails orderPurchaseDetails);

    /**
     * 新增进货明细
     * 
     * @param orderPurchaseDetails 进货明细
     * @return 结果
     */
    public int insertOrderPurchaseDetails(OrderPurchaseDetails orderPurchaseDetails);

    /**
     * 修改进货明细
     * 
     * @param orderPurchaseDetails 进货明细
     * @return 结果
     */
    public int updateOrderPurchaseDetails(OrderPurchaseDetails orderPurchaseDetails);

    /**
     * 批量删除进货明细
     * 
     * @param pdIds 需要删除的进货明细主键集合
     * @return 结果
     */
    public int deleteOrderPurchaseDetailsByPdIds(Long[] pdIds);

    /**
     * 删除进货明细信息
     * 
     * @param pdId 进货明细主键
     * @return 结果
     */
    public int deleteOrderPurchaseDetailsByPdId(Long pdId);

    /**
     * 查询进货明细和货品
     * @param poCode
     * @param oldPoCode
     * @return
     */
    public List<OrderPurchaseDetails> detailAndProduct(String poCode,String oldPoCode);
}
