package com.cx.order.service.impl;

import java.util.List;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.order.mapper.OrderPurchaseDetailsMapper;
import com.cx.order.domain.OrderPurchaseDetails;
import com.cx.order.service.IOrderPurchaseDetailsService;

/**
 * 进货明细Service业务层处理
 *
 * @author LZA
 * @date 2023-12-07
 */
@Service
public class OrderPurchaseDetailsServiceImpl implements IOrderPurchaseDetailsService {
    @Autowired
    private OrderPurchaseDetailsMapper orderPurchaseDetailsMapper;

    /**
     * 查询进货明细
     *
     * @param pdId 进货明细主键
     * @return 进货明细
     */
    @Override
    public OrderPurchaseDetails selectOrderPurchaseDetailsByPdId(Long pdId) {
        return orderPurchaseDetailsMapper.selectOrderPurchaseDetailsByPdId(pdId);
    }

    /**
     * 查询进货明细列表
     *
     * @param orderPurchaseDetails 进货明细
     * @return 进货明细
     */
    @Override
    public List<OrderPurchaseDetails> selectOrderPurchaseDetailsList(OrderPurchaseDetails orderPurchaseDetails) {
        return orderPurchaseDetailsMapper.selectOrderPurchaseDetailsList(orderPurchaseDetails);
    }

    /**
     * 新增进货明细
     *
     * @param orderPurchaseDetails 进货明细
     * @return 结果
     */
    @Override
    public int insertOrderPurchaseDetails(OrderPurchaseDetails orderPurchaseDetails) {
        orderPurchaseDetails.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        orderPurchaseDetails.setCreateTime(DateUtils.getNowDate());
        //return orderPurchaseDetailsMapper.insertOrderPurchaseDetails(orderPurchaseDetails);
        return 0;
    }

    /**
     * 修改进货明细
     *
     * @param orderPurchaseDetails 进货明细
     * @return 结果
     */
    @Override
    public int updateOrderPurchaseDetails(OrderPurchaseDetails orderPurchaseDetails) {
        orderPurchaseDetails.setUpdateTime(DateUtils.getNowDate());
        return orderPurchaseDetailsMapper.updateOrderPurchaseDetails(orderPurchaseDetails);
    }

    /**
     * 批量删除进货明细
     *
     * @param pdIds 需要删除的进货明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderPurchaseDetailsByPdIds(Long[] pdIds) {
        return orderPurchaseDetailsMapper.deleteOrderPurchaseDetailsByPdIds(pdIds);
    }

    /**
     * 删除进货明细信息
     *
     * @param pdId 进货明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderPurchaseDetailsByPdId(Long pdId) {
        return orderPurchaseDetailsMapper.deleteOrderPurchaseDetailsByPdId(pdId);
    }

    /**
     * 查询进货明细和货品
     *
     * @param poCode
     * @param oldPoCode
     * @return
     */
    @Override
    public List<OrderPurchaseDetails> detailAndProduct(String poCode, String oldPoCode) {
        return orderPurchaseDetailsMapper.detailAndProduct(poCode, oldPoCode);
    }
}
