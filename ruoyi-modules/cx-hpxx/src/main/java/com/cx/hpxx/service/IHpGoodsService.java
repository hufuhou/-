package com.cx.hpxx.service;

import java.util.List;
import com.cx.hpxx.domain.HpGoods;

/**
 * 货品信息Service接口
 * 
 * @author hfh
 * @date 2023-11-22
 */
public interface IHpGoodsService 
{
    /**
     * 查询货品信息
     * 
     * @param gId 货品信息主键
     * @return 货品信息
     */
    public HpGoods selectHpGoodsByGId(Long gId);

    /**
     * 查询货品信息列表
     * 
     * @param hpGoods 货品信息
     * @return 货品信息集合
     */
    public List<HpGoods> selectHpGoodsList(HpGoods hpGoods);
    /**
     * 获取最新的货品编码
     * @return
     */
    public HpGoods getHpGoodsByGCode();
    /**
     * 新增货品信息
     * 
     * @param hpGoods 货品信息
     * @return 结果
     */
    public int insertHpGoods(HpGoods hpGoods);

    /**
     * 修改货品信息
     * 
     * @param hpGoods 货品信息
     * @return 结果
     */
    public int updateHpGoods(HpGoods hpGoods);

    /**
     * 批量删除货品信息
     * 
     * @param gIds 需要删除的货品信息主键集合
     * @return 结果
     */
    public int deleteHpGoodsByGIds(Long[] gIds);

    /**
     * 删除货品信息信息
     * 
     * @param gId 货品信息主键
     * @return 结果
     */
    public int deleteHpGoodsByGId(Long gId);
}
