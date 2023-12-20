package com.cx.crkgl.mapper;

import java.util.List;
import com.cx.crkgl.domain.CrkInboundManagement;
import com.cx.crkgl.domain.CrkImDetails;
import com.cx.crkgl.domain.OrderPurchaseDetails;
import com.cx.crkgl.domain.TzStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 入库管理Mapper接口
 * 
 * @author hfh
 * @date 2023-12-01
 */
@Mapper
public interface CrkInboundManagementMapper 
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
     * 查询货品id
     *
     * @param inId 入库管理
     */
    public List<CrkImDetails> selectCrkImDetails1(Long inId);


    public List<CrkImDetails> selectCrkImDetail(List<String> inIds);

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
     * 删除入库管理
     * 
     * @param inId 入库管理主键
     * @return 结果
     */
    public int deleteCrkInboundManagementByInId(Long inId);

    /**
     * 批量删除入库管理
     * 
     * @param inIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkInboundManagementByInIds(Long[] inIds);

    /**
     * 批量删除入库明细
     * 
     * @param inIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkImDetailsByInIds(Long[] inIds);

    /**
     * 批量删除库存
     *
     * @param
     * @return 结果
     */
    public int deleteReviews(@Param("item")List<CrkImDetails> crkImDetailsList);
    
    /**
     * 批量新增入库明细
     * 
     * @param crkImDetailsList 入库明细列表
     * @return 结果
     */
    public int batchCrkImDetails(List<CrkImDetails> crkImDetailsList);

    /**
     * 批量新增库存
     *
     * @param tzStockList
     * @return 结果
     */
    public int addInventory(List<TzStock> tzStockList);


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
     * @param crkInboundManagement
     * @return 结果
     */
    public int InventoryReviews(@Param("crkInboundManagement") List<CrkInboundManagement> crkInboundManagement , @Param("isApproved") boolean isApproved);

    /**
     * 批量审核入库,增加库存
     *
     * @param
     * @return 结果
     */
    public int productReviews(@Param("item")List<CrkImDetails> crkImDetailsList);

    /**
     * 批量撤销入库,减少库存
     *
     * @param
     * @return 结果
     */
    public int reduceReviews(@Param("item")List<CrkImDetails> crkImDetailsList);

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

    /**
     * 通过入库管理主键删除入库明细信息
     * 
     * @param inId 入库管理ID
     * @return 结果
     */
    public int deleteCrkImDetailsByInId(Long inId);
}
