package com.cx.crkgl.service;

import java.util.List;

import com.cx.crkgl.domain.CrkInboundManagement;
import com.cx.crkgl.domain.CrkOutDetails;
import com.cx.crkgl.domain.CrkOutManagement;
import com.cx.crkgl.domain.OrderSalesDetails;
import org.apache.ibatis.annotations.Param;

/**
 * 出库管理Service接口
 * 
 * @author hfh
 * @date 2023-12-20
 */
public interface ICrkOutManagementService 
{
    /**
     * 查询出库管理
     * 
     * @param outId 出库管理主键
     * @return 出库管理
     */
    public CrkOutManagement selectCrkOutManagementByOutId(Long outId);

    /**
     * 查询出库管理列表
     * 
     * @param crkOutManagement 出库管理
     * @return 出库管理集合
     */
    public List<CrkOutManagement> selectCrkOutManagementList(CrkOutManagement crkOutManagement);

    /**
     * 新增出库管理
     * 
     * @param crkOutManagement 出库管理
     * @return 结果
     */
    public int insertCrkOutManagement(CrkOutManagement crkOutManagement);

    /**
     * 修改出库管理
     * 
     * @param crkOutManagement 出库管理
     * @return 结果
     */
    public int updateCrkOutManagement(CrkOutManagement crkOutManagement);

    /**
     * 批量删除出库管理
     * 
     * @param outIds 需要删除的出库管理主键集合
     * @return 结果
     */
    public int deleteCrkOutManagementByOutIds(Long[] outIds);

    /**
     * 删除出库管理信息
     * 
     * @param outId 出库管理主键
     * @return 结果
     */
    public int deleteCrkOutManagementByOutId(Long outId);
    /**
     * 查询销售明细
     *
     */
    public List<OrderSalesDetails> selectSalesDetails(OrderSalesDetails orderSalesDetails);

    /**
     * 查询当前库存数量
     *
     * @param
     */
    public long currentInventory(@Param("slId")Long slId, @Param("gId")Long gId);
    /**
     * 查询货品id
     *
     * @param outId 出库管理
     */
    public List<CrkOutDetails> selectCrkOutDetails(Long[] outId);

    /**
     * 查询出库管理列表
     *
     * @param outCodes 入库管理
     * @return 入库管理集合
     */
    public List<CrkOutManagement> selectCrkOutManagementList1(List<String> outCodes);

    /**
     * 批量审核出库
     *
     * @param
     * @return 结果
     */
    public int AuditOutbounds(List<CrkOutManagement> crkOutManagements, List<String> gId , boolean isApproved);

    /**
     * 批量撤销出库
     *
     * @param outIds 需要删除的数据主键集合
     * @return 结果
     */
    public int WithdrawalStorages(List<String> outIds);
}
