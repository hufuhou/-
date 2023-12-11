package com.cx.wms.service.impl;

import com.cx.wms.domain.OrderPurchaseDetails;
import com.cx.wms.mapper.OrderPurchaseDetailsMapper;
import com.cx.wms.service.IOrderPurchaseDetailsService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 进货明细Service业务层处理
 *
 * @author 熊睿宸
 * @date 2023-11-21
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
        orderPurchaseDetails.setCreateTime(DateUtils.getNowDate());
        orderPurchaseDetails.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        return orderPurchaseDetailsMapper.insertOrderPurchaseDetails(orderPurchaseDetails);
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
        orderPurchaseDetails.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
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
     * 查询进货信息数
     * no param
     *
     * @return 未删除总进货信息数
     */
    @Override
    public int selectOrderPurchaseDetailsCount() {
        return orderPurchaseDetailsMapper.selectOrderPurchaseDetailsCount();
    }

    /**
     * 查询进货列表带用户名商品名称
     *
     * @return 进货明细
     */
    @Override
    public List<OrderPurchaseDetails> findWithUserAndGoodName(OrderPurchaseDetails orderPurchaseDetails) {
        return orderPurchaseDetailsMapper.findWithUserAndGoodName(orderPurchaseDetails);
    }

    /**
     * 查询进货列表按时间段
     * @param TodayOrYesterday 今日/昨日
     * @param BeginDay 本周/月 开始日
     * @param EndDay 本周/月 结束日
     * @return 按时间段进货列表
     * @auther xrc
     */
    @Override
    public List<OrderPurchaseDetails> findInfoByDate(@Param("TodayOrYesterday") String TodayOrYesterday, @Param("BeginDay") String BeginDay, @Param("EndDay") String EndDay) {
        return orderPurchaseDetailsMapper.findInfoByDate(TodayOrYesterday, BeginDay, EndDay);
    }
}
