package com.cx.units.mapper;

import java.util.List;
import com.cx.units.domain.Supplier;
import com.cx.units.domain.Finance;
import org.apache.ibatis.annotations.Mapper;

/**
 * 供应商列表Mapper接口
 * 
 * @author 刘志昂
 * @date 2023-11-24
 */
@Mapper
public interface SupplierMapper 
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
     * 删除供应商列表
     * 
     * @param sId 供应商列表主键
     * @return 结果
     */
    public int deleteSupplierBySId(Long sId);

    /**
     * 批量删除供应商列表
     * 
     * @param sIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSupplierBySIds(Long[] sIds);

    /**
     * 批量删除财务
     * 
     * @param sCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFinanceBySCodes(String[] sCodes);
    
    /**
     * 新增财务信息
     * 
     * @param finance 财务列表
     * @return 结果
     */
    public int addFinance(Finance finance);
    

    /**
     * 通过供应商编码删除财务信息
     * 
     * @param sCode 供应商编码
     * @return 结果
     */
    public int deleteFinanceBySCode(String sCode);

    /**
     * 生成供应商编码
     */
    public Supplier getSupplierCode();

    /**
     * 修改财务信息
     * @param finance
     * @return
     */
    public int updateFinance(Finance finance);
}
