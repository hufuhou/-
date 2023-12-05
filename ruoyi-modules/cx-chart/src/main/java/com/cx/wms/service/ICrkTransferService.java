package com.cx.wms.service;

import com.cx.wms.domain.CrkTransfer;

import java.util.List;

/**
 * 调拨明细Service接口
 *
 * @author 熊睿宸
 * @date 2023-11-28
 */
public interface ICrkTransferService {
    /**
     * 查询调拨明细
     *
     * @param tId 调拨明细主键
     * @return 调拨明细
     */
    public CrkTransfer selectCrkTransferByTId(Long tId);

    /**
     * 查询调拨明细列表
     *
     * @param crkTransfer 调拨明细
     * @return 调拨明细集合
     */
    public List<CrkTransfer> selectCrkTransferList(CrkTransfer crkTransfer);

    /**
     * 新增调拨明细
     *
     * @param crkTransfer 调拨明细
     * @return 结果
     */
    public int insertCrkTransfer(CrkTransfer crkTransfer);

    /**
     * 修改调拨明细
     *
     * @param crkTransfer 调拨明细
     * @return 结果
     */
    public int updateCrkTransfer(CrkTransfer crkTransfer);

    /**
     * 批量删除调拨明细
     *
     * @param tIds 需要删除的调拨明细主键集合
     * @return 结果
     */
    public int deleteCrkTransferByTIds(Long[] tIds);

    /**
     * 删除调拨明细信息
     *
     * @param tId 调拨明细主键
     * @return 结果
     */
    public int deleteCrkTransferByTId(Long tId);
}
