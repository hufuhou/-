package com.cx.ledger.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.ledger.mapper.TzStockMapper;
import com.cx.ledger.domain.TzStock;
import com.cx.ledger.service.ITzStockService;

/**
 * 库存查询Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
@Service
public class TzStockServiceImpl implements ITzStockService 
{
    @Autowired
    private TzStockMapper tzStockMapper;

    /**
     * 查询库存查询
     * 
     * @param sId 库存查询主键
     * @return 库存查询
     */
    @Override
    public TzStock selectTzStockBySId(Long sId)
    {
        return tzStockMapper.selectTzStockBySId(sId);
    }

    /**
     * 查询库存查询列表
     * 
     * @param tzStock 库存查询
     * @return 库存查询
     */
    @Override
    public List<TzStock> selectTzStockList(TzStock tzStock)
    {
        return tzStockMapper.selectTzStockList(tzStock);
    }

    /**
     * 新增库存查询
     * 
     * @param tzStock 库存查询
     * @return 结果
     */
    @Override
    public int insertTzStock(TzStock tzStock)
    {
        tzStock.setCreateTime(DateUtils.getNowDate());
        return tzStockMapper.insertTzStock(tzStock);
    }

    /**
     * 修改库存查询
     * 
     * @param tzStock 库存查询
     * @return 结果
     */
    @Override
    public int updateTzStock(TzStock tzStock)
    {
        return tzStockMapper.updateTzStock(tzStock);
    }

    /**
     * 批量删除库存查询
     * 
     * @param sIds 需要删除的库存查询主键
     * @return 结果
     */
    @Override
    public int deleteTzStockBySIds(Long[] sIds)
    {
        return tzStockMapper.deleteTzStockBySIds(sIds);
    }

    /**
     * 删除库存查询信息
     * 
     * @param sId 库存查询主键
     * @return 结果
     */
    @Override
    public int deleteTzStockBySId(Long sId)
    {
        return tzStockMapper.deleteTzStockBySId(sId);
    }

    @Override
    public List<TzStock> listOutGoods(TzStock tz) {
        List<TzStock> list=tzStockMapper.listOutGoods(tz);
        return list;
    }
}
