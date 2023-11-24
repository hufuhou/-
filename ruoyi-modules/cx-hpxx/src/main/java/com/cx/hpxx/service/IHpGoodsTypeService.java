package com.cx.hpxx.service;

import java.util.List;
import com.cx.hpxx.domain.HpGoodsType;

/**
 * 货品类型Service接口
 * 
 * @author hfh
 * @date 2023-11-23
 */
public interface IHpGoodsTypeService 
{
    /**
     * 查询货品类型
     * 
     * @param gtId 货品类型主键
     * @return 货品类型
     */
    public HpGoodsType selectHpGoodsTypeByGtId(Long gtId);

    /**
     * 查询货品类型列表
     * 
     * @param hpGoodsType 货品类型
     * @return 货品类型集合
     */
    public List<HpGoodsType> selectHpGoodsTypeList(HpGoodsType hpGoodsType);

    /**
     * 新增货品类型
     * 
     * @param hpGoodsType 货品类型
     * @return 结果
     */
    public int insertHpGoodsType(HpGoodsType hpGoodsType);

    /**
     * 修改货品类型
     * 
     * @param hpGoodsType 货品类型
     * @return 结果
     */
    public int updateHpGoodsType(HpGoodsType hpGoodsType);

    /**
     * 批量删除货品类型
     * 
     * @param gtIds 需要删除的货品类型主键集合
     * @return 结果
     */
    public int deleteHpGoodsTypeByGtIds(Long[] gtIds);

    /**
     * 删除货品类型信息
     * 
     * @param gtId 货品类型主键
     * @return 结果
     */
    public int deleteHpGoodsTypeByGtId(Long gtId);
}
