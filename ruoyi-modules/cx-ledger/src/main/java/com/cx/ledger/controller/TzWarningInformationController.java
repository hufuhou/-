package com.cx.ledger.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.cx.ledger.domain.TzWarningInformation;
import com.cx.ledger.service.ITzWarningInformationService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 库存预警Controller
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/information")
public class TzWarningInformationController extends BaseController
{
    @Autowired
    private ITzWarningInformationService tzWarningInformationService;

    /**
     * 查询库存预警列表
     */
    @RequiresPermissions("cx-ledger:information:list")
    @GetMapping("/list")
    public TableDataInfo list(TzWarningInformation tzWarningInformation)
    {
        startPage();
        List<TzWarningInformation> list = tzWarningInformationService.selectTzWarningInformationList(tzWarningInformation);
        return getDataTable(list);
    }

    /**
     * 导出库存预警列表
     */
    @RequiresPermissions("cx-ledger:information:export")
    @Log(title = "库存预警", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TzWarningInformation tzWarningInformation)
    {
        List<TzWarningInformation> list = tzWarningInformationService.selectTzWarningInformationList(tzWarningInformation);
        ExcelUtil<TzWarningInformation> util = new ExcelUtil<TzWarningInformation>(TzWarningInformation.class);
        util.exportExcel(response, list, "库存预警数据");
    }

    /**
     * 获取库存预警详细信息
     */
    @RequiresPermissions("cx-ledger:information:query")
    @GetMapping(value = "/{alertId}")
    public AjaxResult getInfo(@PathVariable("alertId") Long alertId)
    {
        return success(tzWarningInformationService.selectTzWarningInformationByAlertId(alertId));
    }

    /**
     * 新增库存预警
     */
    @RequiresPermissions("cx-ledger:information:add")
    @Log(title = "库存预警", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TzWarningInformation tzWarningInformation)
    {
        return toAjax(tzWarningInformationService.insertTzWarningInformation(tzWarningInformation));
    }

    /**
     * 修改库存预警
     */
    @RequiresPermissions("cx-ledger:information:edit")
    @Log(title = "库存预警", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TzWarningInformation tzWarningInformation)
    {
        return toAjax(tzWarningInformationService.updateTzWarningInformation(tzWarningInformation));
    }

    /**
     * 删除库存预警
     */
    @RequiresPermissions("cx-ledger:information:remove")
    @Log(title = "库存预警", businessType = BusinessType.DELETE)
	@DeleteMapping("/{alertIds}")
    public AjaxResult remove(@PathVariable Long[] alertIds)
    {
        return toAjax(tzWarningInformationService.deleteTzWarningInformationByAlertIds(alertIds));
    }
}
