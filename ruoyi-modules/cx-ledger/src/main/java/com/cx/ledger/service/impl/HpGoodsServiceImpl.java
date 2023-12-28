package com.cx.ledger.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.ledger.mapper.HpGoodsMapper;
import com.cx.ledger.domain.HpGoods;
import com.cx.ledger.service.IHpGoodsService;

/**
 * 保质期查询Service业务层处理
 * 
 * @author hfh
 * @date 2023-12-11
 */
@Service
public class HpGoodsServiceImpl implements IHpGoodsService 
{
    @Autowired
    private HpGoodsMapper hpGoodsMapper;

    /**
     * 查询保质期查询
     * 
     * @param gId 保质期查询主键
     * @return 保质期查询
     */
    @Override
    public HpGoods selectHpGoodsByGId(Long gId)
    {
        return hpGoodsMapper.selectHpGoodsByGId(gId);
    }

    /**
     * 查询保质期查询列表
     * 
     * @param hpGoods 保质期查询
     * @return 保质期查询
     */
    @Override
    public List<HpGoods> selectHpGoodsList(HpGoods hpGoods)
    {
        return hpGoodsMapper.selectHpGoodsList(hpGoods);
    }

    /**
     * 新增保质期查询
     * 
     * @param hpGoods 保质期查询
     * @return 结果
     */
    @Override
    public int insertHpGoods(HpGoods hpGoods)
    {
        hpGoods.setCreateTime(DateUtils.getNowDate());
        return hpGoodsMapper.insertHpGoods(hpGoods);
    }

    /**
     * 修改保质期查询
     * 
     * @param hpGoods 保质期查询
     * @return 结果
     */
    @Override
    public int updateHpGoods(HpGoods hpGoods)
    {
        return hpGoodsMapper.updateHpGoods(hpGoods);
    }

    /**
     * 批量删除保质期查询
     * 
     * @param gIds 需要删除的保质期查询主键
     * @return 结果
     */
    @Override
    public int deleteHpGoodsByGIds(Long[] gIds)
    {
        return hpGoodsMapper.deleteHpGoodsByGIds(gIds);
    }

    /**
     * 删除保质期查询信息
     * 
     * @param gId 保质期查询主键
     * @return 结果
     */
    @Override
    public int deleteHpGoodsByGId(Long gId)
    {
        return hpGoodsMapper.deleteHpGoodsByGId(gId);
    }
}
