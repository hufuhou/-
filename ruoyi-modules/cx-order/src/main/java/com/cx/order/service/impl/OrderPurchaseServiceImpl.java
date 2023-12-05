package com.cx.order.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.order.mapper.OrderPurchaseMapper;
import com.cx.order.domain.OrderPurchase;
import com.cx.order.service.IOrderPurchaseService;

/**
 * 进货订单Service业务层处理
 * 
 * @author LZA
 * @date 2023-11-20
 */
@Service
public class OrderPurchaseServiceImpl implements IOrderPurchaseService 
{
    @Autowired
    private OrderPurchaseMapper orderPurchaseMapper;

    /**
     * 查询进货订单
     * 
     * @param poId 进货订单主键
     * @return 进货订单
     */
    @Override
    public OrderPurchase selectOrderPurchaseByPoId(Long poId)
    {
        return orderPurchaseMapper.selectOrderPurchaseByPoId(poId);
    }

    /**
     * 查询进货订单列表
     * 
     * @param orderPurchase 进货订单
     * @return 进货订单
     */
    @Override
    public List<OrderPurchase> selectOrderPurchaseList(OrderPurchase orderPurchase)
    {
        return orderPurchaseMapper.selectOrderPurchaseList(orderPurchase);
    }

    /**
     * 新增进货订单
     * 
     * @param orderPurchase 进货订单
     * @return 结果
     */
    @Override
    public int insertOrderPurchase(OrderPurchase orderPurchase)
    {
        orderPurchase.setCreateTime(DateUtils.getNowDate());
        return orderPurchaseMapper.insertOrderPurchase(orderPurchase);
    }

    /**
     * 修改进货订单
     * 
     * @param orderPurchase 进货订单
     * @return 结果
     */
    @Override
    public int updateOrderPurchase(OrderPurchase orderPurchase)
    {
        orderPurchase.setUpdateTime(DateUtils.getNowDate());
        return orderPurchaseMapper.updateOrderPurchase(orderPurchase);
    }

    /**
     * 批量删除进货订单
     * 
     * @param poIds 需要删除的进货订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderPurchaseByPoIds(Long[] poIds)
    {
        return orderPurchaseMapper.deleteOrderPurchaseByPoIds(poIds);
    }

    /**
     * 删除进货订单信息
     * 
     * @param poId 进货订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderPurchaseByPoId(Long poId)
    {
        return orderPurchaseMapper.deleteOrderPurchaseByPoId(poId);
    }
}
