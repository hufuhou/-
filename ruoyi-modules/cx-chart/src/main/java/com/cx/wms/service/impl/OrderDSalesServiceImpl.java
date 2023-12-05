package com.cx.wms.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.wms.mapper.OrderDSalesMapper;
import com.cx.wms.domain.OrderDSales;
import com.cx.wms.service.IOrderDSalesService;

/**
 * 销售明细Service业务层处理
 * 
 * @author 熊睿宸
 * @date 2023-11-23
 */
@Service
public class OrderDSalesServiceImpl implements IOrderDSalesService 
{
    @Autowired
    private OrderDSalesMapper orderDSalesMapper;

    /**
     * 查询销售明细
     * 
     * @param sdId 销售明细主键
     * @return 销售明细
     */
    @Override
    public OrderDSales selectOrderDSalesBySdId(Long sdId)
    {
        return orderDSalesMapper.selectOrderDSalesBySdId(sdId);
    }

    /**
     * 查询销售明细列表
     * 
     * @param orderDSales 销售明细
     * @return 销售明细
     */
    @Override
    public List<OrderDSales> selectOrderDSalesList(OrderDSales orderDSales)
    {
        return orderDSalesMapper.selectOrderDSalesList(orderDSales);
    }

    /**
     * 查询销售明细列表带用户名
     *
     * @param orderDSales 销售明细
     * @return 销售明细集合
     */
    @Override
    public List<OrderDSales> findListWithUser(OrderDSales orderDSales) {
        return orderDSalesMapper.findListWithUser(orderDSales);
    }

    /**
     * 新增销售明细
     * 
     * @param orderDSales 销售明细
     * @return 结果
     */
    @Override
    public int insertOrderDSales(OrderDSales orderDSales)
    {
        orderDSales.setCreateTime(DateUtils.getNowDate());
        orderDSales.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        return orderDSalesMapper.insertOrderDSales(orderDSales);
    }

    /**
     * 修改销售明细
     * 
     * @param orderDSales 销售明细
     * @return 结果
     */
    @Override
    public int updateOrderDSales(OrderDSales orderDSales)
    {
        orderDSales.setUpdateTime(DateUtils.getNowDate());
        orderDSales.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        return orderDSalesMapper.updateOrderDSales(orderDSales);
    }

    /**
     * 批量删除销售明细
     * 
     * @param sdIds 需要删除的销售明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderDSalesBySdIds(Long[] sdIds)
    {
        return orderDSalesMapper.deleteOrderDSalesBySdIds(sdIds);
    }

    /**
     * 删除销售明细信息
     * 
     * @param sdId 销售明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderDSalesBySdId(Long sdId)
    {
        return orderDSalesMapper.deleteOrderDSalesBySdId(sdId);
    }

    /**
     * 查询总销售订单数
     *
     * @return 未删除订单数
     */
    @Override
    public Integer findOrderSalesNumber() {
        return orderDSalesMapper.findOrderSalesNumber();
    }

    /**
     * 查询销售货品金额
     * @return 总销售货品金额
     */
    @Override
    public Integer sumSaleAmount() {
        return orderDSalesMapper.sumSaleAmount();
    }
}
