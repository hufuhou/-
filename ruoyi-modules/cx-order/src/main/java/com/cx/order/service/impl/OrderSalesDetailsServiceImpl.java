package com.cx.order.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.order.mapper.OrderSalesDetailsMapper;
import com.cx.order.domain.OrderSalesDetails;
import com.cx.order.service.IOrderSalesDetailsService;

/**
 * 销售明细Service业务层处理
 * 
 * @author LZA
 * @date 2023-12-19
 */
@Service
public class OrderSalesDetailsServiceImpl implements IOrderSalesDetailsService 
{
    @Autowired
    private OrderSalesDetailsMapper orderSalesDetailsMapper;

    /**
     * 查询销售明细
     * 
     * @param sdId 销售明细主键
     * @return 销售明细
     */
    @Override
    public OrderSalesDetails selectOrderSalesDetailsBySdId(Long sdId)
    {
        return orderSalesDetailsMapper.selectOrderSalesDetailsBySdId(sdId);
    }

    /**
     * 查询销售明细列表
     * 
     * @param orderSalesDetails 销售明细
     * @return 销售明细
     */
    @Override
    public List<OrderSalesDetails> selectOrderSalesDetailsList(OrderSalesDetails orderSalesDetails)
    {
        return orderSalesDetailsMapper.selectOrderSalesDetailsList(orderSalesDetails);
    }

    /**
     * 新增销售明细
     * 
     * @param orderSalesDetails 销售明细
     * @return 结果
     */
    @Override
    public int insertOrderSalesDetails(OrderSalesDetails orderSalesDetails)
    {
        orderSalesDetails.setCreateTime(DateUtils.getNowDate());
        return orderSalesDetailsMapper.insertOrderSalesDetails(orderSalesDetails);
    }

    /**
     * 修改销售明细
     * 
     * @param orderSalesDetails 销售明细
     * @return 结果
     */
    @Override
    public int updateOrderSalesDetails(OrderSalesDetails orderSalesDetails)
    {
        orderSalesDetails.setUpdateTime(DateUtils.getNowDate());
        return orderSalesDetailsMapper.updateOrderSalesDetails(orderSalesDetails);
    }

    /**
     * 批量删除销售明细
     * 
     * @param sdIds 需要删除的销售明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderSalesDetailsBySdIds(Long[] sdIds)
    {
        return orderSalesDetailsMapper.deleteOrderSalesDetailsBySdIds(sdIds);
    }

    /**
     * 删除销售明细信息
     * 
     * @param sdId 销售明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderSalesDetailsBySdId(Long sdId)
    {
        return orderSalesDetailsMapper.deleteOrderSalesDetailsBySdId(sdId);
    }
}
