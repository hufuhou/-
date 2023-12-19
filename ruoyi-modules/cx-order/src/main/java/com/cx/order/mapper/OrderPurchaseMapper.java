package com.cx.order.mapper;

import java.util.List;

import com.cx.order.domain.OrderPurchase;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 进货订单Mapper接口
 *
 * @author LZA
 * @date 2023-11-20
 */
public interface OrderPurchaseMapper {
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
     * 删除进货订单
     *
     * @param poId 进货订单主键
     * @return 结果
     */
    public int deleteOrderPurchaseByPoId(Long poId);

    /**
     * 批量删除进货订单
     *
     * @param poIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderPurchaseByPoIds(Long[] poIds);

    /**
     * 生成客户编码
     */
    public OrderPurchase getPoCode();

    public int updateAudit(@Param("status") Integer status, @Param("poIds") Long[] poIds,@Param("reviewer") String reviewer);
}
