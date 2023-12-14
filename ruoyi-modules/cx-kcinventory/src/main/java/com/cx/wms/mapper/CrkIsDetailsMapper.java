package com.cx.wms.mapper;

import com.cx.wms.domain.CrkIsDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 盘点明细Mapper接口
 *
 * @author xrc
 * @date 2023-12-14
 */
@Mapper
public interface CrkIsDetailsMapper {
    /**
     * 查询盘点明细
     *
     * @param isdId 盘点明细主键
     * @return 盘点明细
     */
    public CrkIsDetails selectCrkIsDetailsByIsdId(Long isdId);

    /**
     * 查询盘点明细列表
     *
     * @param crkIsDetails 盘点明细
     * @return 盘点明细集合
     */
    public List<CrkIsDetails> selectCrkIsDetailsList(CrkIsDetails crkIsDetails);

    /**
     * 新增盘点明细
     *
     * @param crkIsDetails 盘点明细
     * @return 结果
     */
    public int insertCrkIsDetails(CrkIsDetails crkIsDetails);

    /**
     * 修改盘点明细
     *
     * @param crkIsDetails 盘点明细
     * @return 结果
     */
    public int updateCrkIsDetails(CrkIsDetails crkIsDetails);

    /**
     * 删除盘点明细
     *
     * @param isdId 盘点明细主键
     * @return 结果
     */
    public int deleteCrkIsDetailsByIsdId(Long isdId);

    /**
     * 批量删除盘点明细
     *
     * @param isdIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCrkIsDetailsByIsdIds(Long[] isdIds);
}
