package com.cx.units.service;

import java.util.List;

import com.cx.units.domain.Finance;
import com.cx.units.domain.Supplier;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 供应商列表Service接口
 * 
 * @author 刘志昂
 * @date 2023-11-24
 */
public interface ISupplierService 
{
    /**
     * 查询供应商列表
     * 
     * @param sId 供应商列表主键
     * @return 供应商列表
     */
    public Supplier selectSupplierBySId(Long sId);

    /**
     * 查询供应商列表列表
     * 
     * @param supplier 供应商列表
     * @return 供应商列表集合
     */
    public List<Supplier> selectSupplierList(Supplier supplier);

    /**
     * 新增供应商列表
     * 
     * @param supplier 供应商列表
     * @return 结果
     */
    public int insertSupplier(Supplier supplier);

    /**
     * 修改供应商列表
     * 
     * @param supplier 供应商列表
     * @return 结果
     */
    public int updateSupplier(Supplier supplier);

    /**
     * 批量删除供应商列表
     * 
     * @param sIds 需要删除的供应商列表主键集合
     * @return 结果
     */
    public int deleteSupplierBySIds(Long[] sIds,String[] sCode);

    /**
     * 删除供应商列表信息
     * 
     * @param sId 供应商列表主键
     * @return 结果
     */
    public int deleteSupplierBySId(Long sId,String sCode);

    /**
     * 生成供应商编码
     */
    public Supplier getSupplierCode();

    /**
     * 新增财务信息
     * @param finance
     * @return
     */
    public int addFinance(Finance finance);


    /**
     * 修改财务信息
     * @param finance
     * @return
     */
    public int updateFinance(Finance finance);
}
