package com.cx.wms.controller;

import com.cx.wms.domain.CrkIsDetails;
import com.cx.wms.service.ICrkIsDetailsService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 盘点明细Controller
 *
 * @author xrc
 * @date 2023-12-14
 */
@RestController
@RequestMapping("/inventoryDetail")
public class CrkIsDetailsController extends BaseController {
    @Autowired
    private ICrkIsDetailsService crkIsDetailsService;

    /**
     * 查询盘点明细列表
     */
    @RequiresPermissions("kcinventory:inventoryDetail:list")
    @GetMapping("/list")
    public TableDataInfo list(CrkIsDetails crkIsDetails) {
        startPage();
        List<CrkIsDetails> list = crkIsDetailsService.selectCrkIsDetailsList(crkIsDetails);
        return getDataTable(list);
    }

    /**
     * 导出盘点明细列表
     */
    @RequiresPermissions("kcinventory:inventoryDetail:export")
    @Log(title = "盘点明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrkIsDetails crkIsDetails) {
        List<CrkIsDetails> list = crkIsDetailsService.selectCrkIsDetailsList(crkIsDetails);
        ExcelUtil<CrkIsDetails> util = new ExcelUtil<CrkIsDetails>(CrkIsDetails.class);
        util.exportExcel(response, list, "盘点明细数据");
    }

    /**
     * 获取盘点明细详细信息
     */
    @RequiresPermissions("kcinventory:inventoryDetail:query")
    @GetMapping(value = "/{isdId}")
    public AjaxResult getInfo(@PathVariable("isdId") Long isdId) {
        return success(crkIsDetailsService.selectCrkIsDetailsByIsdId(isdId));
    }

    /**
     * 新增盘点明细
     */
    @RequiresPermissions("kcinventory:inventoryDetail:add")
    @Log(title = "盘点明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrkIsDetails crkIsDetails) {
        return toAjax(crkIsDetailsService.insertCrkIsDetails(crkIsDetails));
    }

    /**
     * 修改盘点明细
     */
    @RequiresPermissions("kcinventory:inventoryDetail:edit")
    @Log(title = "盘点明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrkIsDetails crkIsDetails) {
        return toAjax(crkIsDetailsService.updateCrkIsDetails(crkIsDetails));
    }

    /**
     * 删除盘点明细
     */
    @RequiresPermissions("kcinventory:inventoryDetail:remove")
    @Log(title = "盘点明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{isdIds}")
    public AjaxResult remove(@PathVariable Long[] isdIds) {
        return toAjax(crkIsDetailsService.deleteCrkIsDetailsByIsdIds(isdIds));
    }
}
