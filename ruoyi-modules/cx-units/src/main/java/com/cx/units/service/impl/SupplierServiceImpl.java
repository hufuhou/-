package com.cx.units.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.cx.units.domain.Finance;
import com.cx.units.mapper.SupplierMapper;
import com.cx.units.domain.Supplier;
import com.cx.units.service.ISupplierService;

/**
 * 供应商列表Service业务层处理
 *
 * @author 刘志昂
 * @date 2023-11-24
 */
@Service
public class SupplierServiceImpl implements ISupplierService {
    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 查询供应商列表
     *
     * @param sId 供应商列表主键
     * @return 供应商列表
     */
    @Override
    public Supplier selectSupplierBySId(Long sId) {
        return supplierMapper.selectSupplierBySId(sId);
    }

    /**
     * 查询供应商列表列表
     *
     * @param supplier 供应商列表
     * @return 供应商列表
     */
    @Override
    public List<Supplier> selectSupplierList(Supplier supplier) {
        List<Supplier> suppliers = supplierMapper.selectSupplierList(supplier);
        //System.out.println(suppliers);
        return suppliers;
    }

    /**
     * 新增供应商列表
     *
     * @param supplier 供应商列表
     * @return 结果
     */
    @Transactional
    @Override
    public int insertSupplier(Supplier supplier) {
        supplier.setCreateTime(DateUtils.getNowDate());
        supplier.setCreateBy(String.valueOf((SecurityUtils.getUserId())));
        int rows = supplierMapper.insertSupplier(supplier);
        //insertFinance(supplier);
        return rows;
    }

    /**
     * 修改供应商列表
     *
     * @param supplier 供应商列表
     * @return 结果
     */
    @Transactional
    @Override
    public int updateSupplier(Supplier supplier) {
        supplier.setUpdateTime(DateUtils.getNowDate());
        supplier.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        //supplierMapper.deleteFinanceByFId(supplier.getsId());
        //insertFinance(supplier);
        return supplierMapper.updateSupplier(supplier);
    }

    /**
     * 批量删除供应商列表
     *
     * @param sIds 需要删除的供应商列表主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSupplierBySIds(Long[] sIds,String[] sCodes) {
        supplierMapper.deleteFinanceBySCodes(sCodes);
        return supplierMapper.deleteSupplierBySIds(sIds);
    }

    /**
     * 删除供应商列表信息
     *
     * @param sId 供应商列表主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteSupplierBySId(Long sId,String sCode) {
        supplierMapper.deleteFinanceBySCode(sCode);
        return supplierMapper.deleteSupplierBySId(sId);
    }

    /**
     * 新增财务信息
     *
     * @param finance 财务信息对象
     */
    public int addFinance(Finance finance) {
        return supplierMapper.addFinance(finance);
    }


    /**
     * 修改财务信息
     *
     * @param finance
     * @return
     */
    @Override
    public int updateFinance(Finance finance) {
        return supplierMapper.updateFinance(finance);
    }

    /**
     * 生成供应商代码
     *
     * @return
     */
    @Override
    public Supplier getSupplierCode() {
        //前缀 (供应商)
        String prefix = "GYS";

        Supplier supplier = supplierMapper.getSupplierCode();
        System.out.println(supplier);
        String code = supplier.getsCode();
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        // 从字符串中提取数字
        Pattern pattern = Pattern.compile("\\d{3}$");
        Matcher matcher = pattern.matcher(code);
        String lastThreeDigits = null;
        if (matcher.find()) {
            lastThreeDigits = matcher.group();
        }
        // 将提取的数字转换为整数并加1
        int incrementedNumber = Integer.parseInt(lastThreeDigits) + 1;

        // 格式化为与原数字相同长度的字符串
        String formattedIncrementedNumber = String.format("%0" + lastThreeDigits.length() + "d", incrementedNumber);

        // 输出结果
        String resultString = prefix + timestamp + formattedIncrementedNumber;
        System.out.println(resultString);

        supplier.setsCode(resultString);

        return supplier;
    }
}
