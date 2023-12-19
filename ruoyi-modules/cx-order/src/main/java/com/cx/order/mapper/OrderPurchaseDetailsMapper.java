package com.cx.order.mapper;

import java.util.List;

import com.cx.order.domain.OrderPurchaseDetails;
import org.apache.ibatis.annotations.Param;

/**
 * 进货明细Mapper接口
 *
 * @author LZA
 * @date 2023-12-07
 */
public interface OrderPurchaseDetailsMapper {
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
     * 批量新增进货明细
     *
     * @param orderPurchaseDetails 进货明细
     * @return 结果
     */
    public int insertOrderPurchaseDetails(List<OrderPurchaseDetails> orderPurchaseDetails);

    /**
     * 修改进货明细
     *
     * @param orderPurchaseDetails 进货明细
     * @return 结果
     */
    public int updateOrderPurchaseDetails(OrderPurchaseDetails orderPurchaseDetails);

    /**
     * 删除进货明细
     *
     * @param pdId 进货明细主键
     * @return 结果
     */
    public int deleteOrderPurchaseDetailsByPdId(Long pdId);

    /**
     * 批量删除进货明细
     *
     * @param pdIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderPurchaseDetailsByPdIds(Long[] pdIds);

    /**
     * 查询进货明细和货品
     *
     * @param poCode
     * @param oldPoCode
     * @return
     */
    public List<OrderPurchaseDetails> detailAndProduct(@Param("poCode") String poCode, @Param("oldPoCode") String oldPoCode);
}
