package com.cx.crkgl.mapper;

import java.util.List;

import com.cx.crkgl.domain.CrkInboundManagement;
import com.cx.crkgl.domain.CrkOutManagement;
import com.cx.crkgl.domain.CrkOutDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * 出库管理Mapper接口
 * 
 * @author hfh
 * @date 2023-12-20
 */
@Mapper
public interface CrkOutManagementMapper 
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
     * 获取最新的出库编码
     * @return
     */
    public CrkOutManagement getCrkOutManagementBySlCode();

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
     * 删除出库管理
     * 
     * @param outId 出库管理主键
     * @return 结果
     */
    public int deleteCrkOutManagementByOutId(Long outId);

    /**
     * 批量删除出库管理
     * 
     * @param outIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkOutManagementByOutIds(Long[] outIds);

    /**
     * 批量删除出库明细

     * 
     * @param outIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkOutDetailsByOutIds(Long[] outIds);
    
    /**
     * 批量新增出库明细

     * 
     * @param crkOutDetailsList 出库明细
列表
     * @return 结果
     */
    public int batchCrkOutDetails(List<CrkOutDetails> crkOutDetailsList);
    

    /**
     * 通过出库管理主键删除出库明细
信息
     * 
     * @param outId 出库管理ID
     * @return 结果
     */
    public int deleteCrkOutDetailsByOutId(Long outId);
}
