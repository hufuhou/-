package com.cx.crkgl.service;

import java.util.List;

import com.cx.crkgl.domain.CrkImDetails;
import com.cx.crkgl.domain.CrkInboundManagement;
import com.cx.crkgl.domain.OrderPurchaseDetails;

/**
 * 入库管理Service接口
 * 
 * @author hfh
 * @date 2023-12-01
 */
public interface ICrkInboundManagementService 
{
    /**
     * 查询入库管理
     * 
     * @param inId 入库管理主键
     * @return 入库管理
     */
    public CrkInboundManagement selectCrkInboundManagementByInId(Long inId);

    /**
     * 查询入库数量
     *
     * @param gCode
     */
    public long InventoryQuantity(String gCode);

    /**
     * 查询进货明细
     *
     */
    public List<OrderPurchaseDetails> selectInventoryDetails(OrderPurchaseDetails orderPurchaseDetails);

    /**
     * 查询入库管理列表
     * 
     * @param crkInboundManagement 入库管理
     * @return 入库管理集合
     */
    public List<CrkInboundManagement> selectCrkInboundManagementList(CrkInboundManagement crkInboundManagement);

    /**
     * 查询货品id
     *
     * @param inId 入库管理
     */
    public List<CrkImDetails> selectCrkImDetails(Long[] inId);

    /**
     * 查询入库管理列表
     *
     * @param inCodes 入库管理
     * @return 入库管理集合
     */
    public List<CrkInboundManagement> selectCrkInboundManagementList1(List<String> inCodes);
    /**
     * 获取最新的入库编码
     * @return
     */
    public CrkInboundManagement getCrkInboundManagementBySlCode();

    /**
     * 新增入库管理
     * 
     * @param crkInboundManagement 入库管理
     * @return 结果
     */
    public int insertCrkInboundManagement(CrkInboundManagement crkInboundManagement);

    /**
     * 修改入库管理
     * 
     * @param crkInboundManagement 入库管理
     * @return 结果
     */
    public int updateCrkInboundManagement(CrkInboundManagement crkInboundManagement);

    /**
     * 批量删除入库管理
     * 
     * @param inIds 需要删除的入库管理主键集合
     * @return 结果
     */
    public int deleteCrkInboundManagementByInIds(Long[] inIds);

    /**
     * 删除入库管理信息
     * 
     * @param inId 入库管理主键
     * @return 结果
     */
    public int deleteCrkInboundManagementByInId(Long inId);
    /**
     * 审核入库
     *
     * @param  crkInboundManagement
     * @return 结果
     */
    public int InventoryReview(CrkInboundManagement crkInboundManagement,boolean isApproved);
    /**
     * 批量审核入库
     *
     * @param
     * @return 结果
     */
    public int InventoryReviews(List<CrkInboundManagement> crkInboundManagements,List<String> gId ,boolean isApproved);


    /**
     * 撤销入库
     *
     * @param  inId 撤销管理主键
     * @return 结果
     */
    public int WithdrawalStorage(Long inId);
    /**
     * 批量撤销入库
     *
     * @param inIds 需要删除的数据主键集合
     * @return 结果
     */
    public int WithdrawalStorages(List<String> inIds);

}
