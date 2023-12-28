package com.cx.ledger.mapper;

import java.util.List;
import com.cx.ledger.domain.HpGoods;

/**
 * 保质期查询Mapper接口
 * 
 * @author hfh
 * @date 2023-12-11
 */
public interface HpGoodsMapper 
{
    /**
     * 查询保质期查询
     * 
     * @param gId 保质期查询主键
     * @return 保质期查询
     */
    public HpGoods selectHpGoodsByGId(Long gId);

    /**
     * 查询保质期查询列表
     * 
     * @param hpGoods 保质期查询
     * @return 保质期查询集合
     */
    public List<HpGoods> selectHpGoodsList(HpGoods hpGoods);

    /**
     * 新增保质期查询
     * 
     * @param hpGoods 保质期查询
     * @return 结果
     */
    public int insertHpGoods(HpGoods hpGoods);

    /**
     * 修改保质期查询
     * 
     * @param hpGoods 保质期查询
     * @return 结果
     */
    public int updateHpGoods(HpGoods hpGoods);

    /**
     * 删除保质期查询
     * 
     * @param gId 保质期查询主键
     * @return 结果
     */
    public int deleteHpGoodsByGId(Long gId);

    /**
     * 批量删除保质期查询
     * 
     * @param gIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHpGoodsByGIds(Long[] gIds);
}
