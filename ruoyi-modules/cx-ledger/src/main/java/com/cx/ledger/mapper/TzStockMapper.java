package com.cx.ledger.mapper;

import java.util.List;
import com.cx.ledger.domain.TzStock;

/**
 * 库存查询Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
public interface TzStockMapper 
{
    /**
     * 查询库存查询
     * 
     * @param sId 库存查询主键
     * @return 库存查询
     */
    public TzStock selectTzStockBySId(Long sId);

    /**
     * 查询库存查询列表
     * 
     * @param tzStock 库存查询
     * @return 库存查询集合
     */
    public List<TzStock> selectTzStockList(TzStock tzStock);

    /**
     * 新增库存查询
     * 
     * @param tzStock 库存查询
     * @return 结果
     */
    public int insertTzStock(TzStock tzStock);

    /**
     * 修改库存查询
     * 
     * @param tzStock 库存查询
     * @return 结果
     */
    public int updateTzStock(TzStock tzStock);

    /**
     * 删除库存查询
     * 
     * @param sId 库存查询主键
     * @return 结果
     */
    public int deleteTzStockBySId(Long sId);

    /**
     * 批量删除库存查询
     * 
     * @param sIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTzStockBySIds(Long[] sIds);

    public List<TzStock> listOutGoods(TzStock tz);
}
