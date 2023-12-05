package com.cx.wms.controller;

import com.cx.wms.domain.CrkTransfer;
import com.cx.wms.service.ICrkTransferService;
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
 * ( - _ - )
 * 调拨明细Controller
 *
 * @author 熊睿宸
 * {@code @date} 2023-11-28
 */
@RestController
@RequestMapping("/allocate")
public class CrkTransferController extends BaseController {
    @Autowired
    private ICrkTransferService crkTransferService;

    /**
     * 查询调拨明细列表
     */
    @RequiresPermissions("allocate:allocate:list")
    @GetMapping("/list")
    public TableDataInfo list(CrkTransfer crkTransfer) {
        startPage();
        List<CrkTransfer> list = crkTransferService.selectCrkTransferList(crkTransfer);
        return getDataTable(list);
    }

    /**
     * 导出调拨明细列表
     */
    @RequiresPermissions("allocate:allocate:export")
    @Log(title = "调拨明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrkTransfer crkTransfer) {
        List<CrkTransfer> list = crkTransferService.selectCrkTransferList(crkTransfer);
        ExcelUtil<CrkTransfer> util = new ExcelUtil<CrkTransfer>(CrkTransfer.class);
        util.exportExcel(response, list, "调拨明细数据");
    }

    /**
     * 获取调拨明细详细信息
     */
    @RequiresPermissions("allocate:allocate:query")
    @GetMapping(value = "/{tId}")
    public AjaxResult getInfo(@PathVariable("tId") Long tId) {
        return success(crkTransferService.selectCrkTransferByTId(tId));
    }

    /**
     * 新增调拨明细
     */
    @RequiresPermissions("allocate:allocate:add")
    @Log(title = "调拨明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrkTransfer crkTransfer) {
        return toAjax(crkTransferService.insertCrkTransfer(crkTransfer));
    }

    /**
     * 修改调拨明细
     */
    @RequiresPermissions("allocate:allocate:edit")
    @Log(title = "调拨明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrkTransfer crkTransfer) {
        return toAjax(crkTransferService.updateCrkTransfer(crkTransfer));
    }

    /**
     * 删除调拨明细
     */
    @RequiresPermissions("allocate:allocate:remove")
    @Log(title = "调拨明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tIds}")
    public AjaxResult remove(@PathVariable Long[] tIds) {
        return toAjax(crkTransferService.deleteCrkTransferByTIds(tIds));
    }
}
