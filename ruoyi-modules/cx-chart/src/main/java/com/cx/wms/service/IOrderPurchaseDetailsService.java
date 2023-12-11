package com.cx.wms.service;

import com.cx.wms.domain.OrderPurchaseDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 进货明细Service接口
 *
 * @author 熊睿宸
 * @date 2023-11-21
 */
public interface IOrderPurchaseDetailsService {
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
     * 查询进货信息数
     * no param
     * @return 未删除总进货信息数
     */
    public int selectOrderPurchaseDetailsCount();

    /**
     * 查询进货列表带用户名商品名称
     * no param
     * @return 未删除总进货信息数
     */
    public List<OrderPurchaseDetails> findWithUserAndGoodName(OrderPurchaseDetails orderPurchaseDetails);

    /**
     * 查询进货列表按时间段
     * @param TodayOrYesterday 今日/昨日
     * @param BeginDay 本周/月 开始日
     * @param EndDay 本周/月 结束日
     * @return 按时间段进货列表
     */
    public List<OrderPurchaseDetails> findInfoByDate(@Param("TodayOrYesterday") String TodayOrYesterday, @Param("BeginDay") String BeginDay, @Param("EndDay") String EndDay);
}
