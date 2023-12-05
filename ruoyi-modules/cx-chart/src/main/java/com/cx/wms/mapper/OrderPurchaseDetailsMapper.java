package com.cx.wms.mapper;

import com.cx.wms.domain.OrderPurchaseDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 进货明细Mapper接口
 *
 * @author 熊睿宸
 * @date 2023-11-21
 */
@Mapper
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


}
