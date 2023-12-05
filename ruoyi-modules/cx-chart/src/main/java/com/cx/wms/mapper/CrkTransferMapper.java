package com.cx.wms.mapper;

import com.cx.wms.domain.CrkTransfer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 调拨明细Mapper接口
 *
 * @author 熊睿宸
 * @date 2023-11-28
 */
@Mapper
public interface CrkTransferMapper {
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
     * 删除调拨明细
     *
     * @param tId 调拨明细主键
     * @return 结果
     */
    public int deleteCrkTransferByTId(Long tId);

    /**
     * 批量删除调拨明细
     *
     * @param tIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkTransferByTIds(Long[] tIds);
}
