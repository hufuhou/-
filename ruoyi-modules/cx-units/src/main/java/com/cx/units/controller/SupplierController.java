package com.cx.units.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.cx.units.domain.Finance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.cx.units.domain.Supplier;
import com.cx.units.service.ISupplierService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 供应商列表Controller
 *
 * @author 刘志昂
 * @date 2023-11-24
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController extends BaseController {
    @Autowired
    private ISupplierService supplierService;

    /**
     * 查询供应商列表列表
     */
    @RequiresPermissions("units:supplier:list")
    @GetMapping("/list")
    public TableDataInfo list(Supplier supplier) {
        startPage();
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        return getDataTable(list);
    }

    /**
     * 导出供应商列表列表
     */
    @RequiresPermissions("units:supplier:export")
    @Log(title = "供应商列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Supplier supplier) {
        List<Supplier> list = supplierService.selectSupplierList(supplier);
        ExcelUtil<Supplier> util = new ExcelUtil<Supplier>(Supplier.class);
        util.exportExcel(response, list, "供应商列表数据");
    }

    /**
     * 获取供应商列表详细信息
     */
    @RequiresPermissions("units:supplier:query")
    @GetMapping(value = "/{sId}")
    public AjaxResult getInfo(@PathVariable("sId") Long sId) {
        return success(supplierService.selectSupplierBySId(sId));
    }

    /**
     * 新增供应商列表
     */
    @RequiresPermissions("units:supplier:add")
    @Log(title = "供应商列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Supplier supplier) {
        return toAjax(supplierService.insertSupplier(supplier));
    }

    /**
     * 修改供应商列表
     */
    @RequiresPermissions("units:supplier:edit")
    @Log(title = "供应商列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Supplier supplier) {
        return toAjax(supplierService.updateSupplier(supplier));
    }

    /**
     * 修改财务信息
     */
    @PutMapping("/updateFinance")
    public AjaxResult edit(@RequestBody Finance finance) {
        return toAjax(supplierService.updateFinance(finance));
    }

    /**
     * 删除供应商列表
     */
    @RequiresPermissions("units:supplier:remove")
    @Log(title = "供应商列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sIds}/{sCodes}")
    public AjaxResult remove(@PathVariable Long[] sIds, @PathVariable String[] sCodes) {
        return toAjax(supplierService.deleteSupplierBySIds(sIds, sCodes));
    }

//    /**
//     * 删除财务信息
//     */
//    @DeleteMapping("/{sCode}")
//    public AjaxResult remove(@PathVariable String sCode) {
//        return toAjax(supplierService.deleteSupplierBySIds(sIds));
//    }

    /**
     * 生成供应商编码
     */
    @GetMapping("/getSupplierCode")
    public AjaxResult getSupplierCode() {
        return success(supplierService.getSupplierCode());
    }


    /**
     * 新增财务信息
     */
    @PostMapping("/addFinance")
    public AjaxResult addFinance(@RequestBody Finance finance) {
        return toAjax(supplierService.addFinance(finance));
    }
}
