package com.cx.units.service;

import java.util.List;
import com.cx.units.domain.Consumer;
import com.cx.units.domain.Finance;
import com.cx.units.domain.Supplier;

/**
 * 客户Service接口
 * 
 * @author 刘志昂
 * @date 2023-11-29
 */
public interface IConsumerService 
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
    public int insertConsumer(Consumer consumer, Finance finance);

    /**
     * 修改客户
     * 
     * @param consumer 客户
     * @return 结果
     */
    public int updateConsumer(Consumer consumer,Finance finance);

    /**
     * 批量删除客户
     * 
     * @param cIds 需要删除的客户主键集合
     * @return 结果
     */
    public int deleteConsumerByCIds(Long[] cIds,Long[] fIds);

    /**
     * 删除客户信息
     * 
     * @param cId 客户主键
     * @return 结果
     */
    public int deleteConsumerByCId(Long cId);

    /**
     * 生成供应商编码
     */
    public Consumer getConsumerCode();

    /**
     * 修改财务信息
     * @param finance
     * @return
     */
    public int updateFinance(Finance finance);
}
