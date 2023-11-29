package com.cx.hpxx.service.impl;

import java.util.List;

import com.cx.hpxx.NumberGenerator;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.hpxx.mapper.HpGoodsTypeMapper;
import com.cx.hpxx.domain.HpGoodsType;
import com.cx.hpxx.service.IHpGoodsTypeService;

/**
 * 货品类型Service业务层处理
 * 
 * @author hfh
 * @date 2023-11-27
 */
@Service
public class HpGoodsTypeServiceImpl implements IHpGoodsTypeService 
{
    @Autowired
    private HpGoodsTypeMapper hpGoodsTypeMapper;

    /**
     * 查询货品类型
     * 
     * @param gtId 货品类型主键
     * @return 货品类型
     */
    @Override
    public HpGoodsType selectHpGoodsTypeByGtId(Long gtId)
    {
        return hpGoodsTypeMapper.selectHpGoodsTypeByGtId(gtId);
    }

    /**
     * 查询货品类型列表
     * 
     * @param hpGoodsType 货品类型
     * @return 货品类型
     */
    @Override
    public List<HpGoodsType> selectHpGoodsTypeList(HpGoodsType hpGoodsType)
    {
        return hpGoodsTypeMapper.selectHpGoodsTypeList(hpGoodsType);
    }

    @Override
    public HpGoodsType getHpGoodsTypeByGtCode() {
        return hpGoodsTypeMapper.getHpGoodsTypeByGtCode();
    }

    /**
     * 新增货品类型
     * 
     * @param hpGoodsType 货品类型
     * @return 结果
     */
    @Override
    public int insertHpGoodsType(HpGoodsType hpGoodsType)
    {
        hpGoodsType.setCreateBy(SecurityUtils.getUsername());
        hpGoodsType.setCreateTime(DateUtils.getNowDate());
        NumberGenerator num=new NumberGenerator("HPLX");
        HpGoodsType hpGoodsType1=hpGoodsTypeMapper.getHpGoodsTypeByGtCode();
        String code=null;
        if (hpGoodsType1!=null){
            code=hpGoodsType1.getGtCode();
        }
        hpGoodsType.setGtCode(String.valueOf(num.generateNumber(code)));
        return hpGoodsTypeMapper.insertHpGoodsType(hpGoodsType);
    }

    /**
     * 修改货品类型
     * 
     * @param hpGoodsType 货品类型
     * @return 结果
     */
    @Override
    public int updateHpGoodsType(HpGoodsType hpGoodsType)
    {
        return hpGoodsTypeMapper.updateHpGoodsType(hpGoodsType);
    }

    /**
     * 批量删除货品类型
     * 
     * @param gtIds 需要删除的货品类型主键
     * @return 结果
     */
    @Override
    public int deleteHpGoodsTypeByGtIds(Long[] gtIds)
    {
        return hpGoodsTypeMapper.deleteHpGoodsTypeByGtIds(gtIds);
    }

    /**
     * 删除货品类型信息
     * 
     * @param gtId 货品类型主键
     * @return 结果
     */
    @Override
    public int deleteHpGoodsTypeByGtId(Long gtId)
    {
        return hpGoodsTypeMapper.deleteHpGoodsTypeByGtId(gtId);
    }
}
