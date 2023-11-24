package com.cx.hpxx.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.hpxx.mapper.HpGoodsMapper;
import com.cx.hpxx.domain.HpGoods;
import com.cx.hpxx.service.IHpGoodsService;

/**
 * 货品信息Service业务层处理
 * 
 * @author hfh
 * @date 2023-11-22
 */
@Service
public class HpGoodsServiceImpl implements IHpGoodsService 
{
    @Autowired
    private HpGoodsMapper hpGoodsMapper;

    /**
     * 查询货品信息
     * 
     * @param gId 货品信息主键
     * @return 货品信息
     */
    @Override
    public HpGoods selectHpGoodsByGId(Long gId)
    {
        return hpGoodsMapper.selectHpGoodsByGId(gId);
    }

    /**
     * 查询货品信息列表
     * 
     * @param hpGoods 货品信息
     * @return 货品信息
     */
    @Override
    public List<HpGoods> selectHpGoodsList(HpGoods hpGoods)
    {
        return hpGoodsMapper.selectHpGoodsList(hpGoods);
    }

    /**
     * 新增货品信息
     * 
     * @param hpGoods 货品信息
     * @return 结果
     */
    @Override
    public int insertHpGoods(HpGoods hpGoods)
    {
        hpGoods.setCreateBy(SecurityUtils.getUsername());
        hpGoods.setCreateTime(DateUtils.getNowDate());
        return hpGoodsMapper.insertHpGoods(hpGoods);
    }

    /**
     * 修改货品信息
     * 
     * @param hpGoods 货品信息
     * @return 结果
     */
    @Override
    public int updateHpGoods(HpGoods hpGoods)
    {
        return hpGoodsMapper.updateHpGoods(hpGoods);
    }

    /**
     * 批量删除货品信息
     * 
     * @param gIds 需要删除的货品信息主键
     * @return 结果
     */
    @Override
    public int deleteHpGoodsByGIds(Long[] gIds)
    {
        return hpGoodsMapper.deleteHpGoodsByGIds(gIds);
    }

    /**
     * 删除货品信息信息
     * 
     * @param gId 货品信息主键
     * @return 结果
     */
    @Override
    public int deleteHpGoodsByGId(Long gId)
    {
        return hpGoodsMapper.deleteHpGoodsByGId(gId);
    }
}
