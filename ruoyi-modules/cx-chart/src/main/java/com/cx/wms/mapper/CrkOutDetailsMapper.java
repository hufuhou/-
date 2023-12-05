package com.cx.wms.mapper;

import com.cx.wms.domain.CrkOutDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 出库明细
 * Mapper接口
 *
 * @author XRC
 * @date 2023-11-20
 */
@Mapper
public interface CrkOutDetailsMapper {
    /**
     * 查询出库明细
     *
     * @param omId 出库明细
     *             主键
     * @return 出库明细
     */
    public CrkOutDetails selectCrkOutDetailsByOmId(Long omId);

    /**
     * 查询出库明细
     * 列表
     *
     * @param crkOutDetails 出库明细
     * @return 出库明细
     * 集合
     */
    public List<CrkOutDetails> selectCrkOutDetailsList(CrkOutDetails crkOutDetails);

    /**
     * 查询出库明细带用户名
     * 列表
     *
     * @param crkOutDetails 出库明细
     * @return 出库明细
     * 集合
     */
    public List<CrkOutDetails> selectAllWithUser(CrkOutDetails crkOutDetails);

    /**
     * 新增出库明细
     *
     * @param crkOutDetails 出库明细
     * @return 结果
     */
    public int insertCrkOutDetails(CrkOutDetails crkOutDetails);

    /**
     * 修改出库明细
     *
     * @param crkOutDetails 出库明细
     * @return 结果
     */
    public int updateCrkOutDetails(CrkOutDetails crkOutDetails);

    /**
     * 删除出库明细
     *
     * @param omId 出库明细
     *             主键
     * @return 结果
     */
    public int deleteCrkOutDetailsByOmId(Long omId);

    /**
     * 批量删除出库明细
     *
     * @param omIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkOutDetailsByOmIds(Long[] omIds);

    /**
     * 查询出库订单数
     * @return 未删除出库订单数
     */
    public Integer findCrkOutNumber();
}
