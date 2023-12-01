package com.cx.units.mapper;

import java.util.List;
import com.cx.units.domain.Consumer;
import com.cx.units.domain.Finance;
import com.cx.units.domain.Supplier;

/**
 * 客户Mapper接口
 * 
 * @author 刘志昂
 * @date 2023-11-29
 */
public interface ConsumerMapper 
{
    /**
     * 查询客户
     * 
     * @param cId 客户主键
     * @return 客户
     */
    public Consumer selectConsumerByCId(Long cId);

    /**
     * 查询客户列表
     * 
     * @param consumer 客户
     * @return 客户集合
     */
    public List<Consumer> selectConsumerList(Consumer consumer);

    /**
     * 新增客户
     * 
     * @param consumer 客户
     * @return 结果
     */
    public int insertConsumer(Consumer consumer);

    /**
     * 修改客户
     * 
     * @param consumer 客户
     * @return 结果
     */
    public int updateConsumer(Consumer consumer);

    /**
     * 删除客户
     * 
     * @param cId 客户主键
     * @return 结果
     */
    public int deleteConsumerByCId(Long cId);

    /**
     * 批量删除客户
     * 
     * @param cIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConsumerByCIds(Long[] cIds);

    /**
     * 批量删除财务
     * 
     * @param fIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceByFIds(Long[] fIds);
    
    /**
     * 新增财务
     * 
     * @param finance 财务信息
     * @return 结果
     */
    public int insertFinance(Finance finance);
    

    /**
     * 通过客户主键删除财务信息
     * 
     * @param cId 客户ID
     * @return 结果
     */
    public int deleteFinanceByFId(Long cId);

    /**
     * 生成客户编码
     */
    public Consumer getConsumerCode();

    /**
     * 修改财务信息
     * @param finance
     * @return
     */
    public int updateFinance(Finance finance);
}
