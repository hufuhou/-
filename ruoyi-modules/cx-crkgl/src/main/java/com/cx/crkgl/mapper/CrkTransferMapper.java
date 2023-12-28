package com.cx.crkgl.mapper;

import java.util.List;
import com.cx.crkgl.domain.CrkTransfer;
import com.cx.crkgl.domain.CrkTransferDetails;

/**
 * 库存调拨Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-13
 */
public interface CrkTransferMapper 
{
    /**
     * 查询库存调拨
     *
     * @param tId 库存调拨主键
     * @return 库存调拨
     */
    public CrkTransfer selectCrkTransferByTId(Long tId);

    /**
     * 查询库存调拨列表
     *
     * @param crkTransfer 库存调拨
     * @return 库存调拨集合
     */
    public List<CrkTransfer> selectCrkTransferList(CrkTransfer crkTransfer);

    /**
     * 新增库存调拨
     *
     * @param crkTransfer 库存调拨
     * @return 结果
     */
    public int insertCrkTransfer(CrkTransfer crkTransfer);

    /**
     * 修改库存调拨
     *
     * @param crkTransfer 库存调拨
     * @return 结果
     */
    public int updateCrkTransfer(CrkTransfer crkTransfer);

    /**
     * 删除库存调拨
     *
     * @param tId 库存调拨主键
     * @return 结果
     */
    public int deleteCrkTransferByTId(Long tId);

    /**
     * 批量删除库存调拨
     *
     * @param tIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkTransferByTIds(Long[] tIds);

    /**
     * 批量删除调拨明细
     *
     * @param tIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkTransferDetailsByTIds(Long[] tIds);

    /**
     * 批量新增调拨明细
     *
     * @param crkTransferDetailsList 调拨明细列表
     * @return 结果
     */
    public int batchCrkTransferDetails(List<CrkTransferDetails> crkTransferDetailsList);


    /**
     * 通过库存调拨主键删除调拨明细信息
     *
     * @param tId 库存调拨ID
     * @return 结果
     */
    public int deleteCrkTransferDetailsByTId(Long tId);

    /**
    生成调拨编码
     */
    public CrkTransfer getTranCode();

    /**
     * 审核修改库存的计划数量
     */
    public int updateNumberofPlans(Long wId,Long goodsId,Long tQuantity);
}
