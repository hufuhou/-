package com.cx.ckgl.controller;

import java.util.List;
import java.io.IOException;
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
import com.cx.ckgl.domain.Warehouse;
import com.cx.ckgl.service.IWarehouseService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 仓库管理Controller
 * 
 * @author hfh
 * @date 2023-11-24
 */
@RestController
@RequestMapping("/warehouse")
public class WarehouseController extends BaseController
{
    @Autowired
    private IWarehouseService warehouseService;

    /**
     * 查询仓库管理列表
     */
    @RequiresPermissions("cx-ckgl:warehouse:list")
    @GetMapping("/list")
    public TableDataInfo list(Warehouse warehouse)
    {
        startPage();
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        return getDataTable(list);
    }

    /**
     * 导出仓库管理列表
     */
    @RequiresPermissions("cx-ckgl:warehouse:export")
    @Log(title = "仓库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Warehouse warehouse)
    {
        List<Warehouse> list = warehouseService.selectWarehouseList(warehouse);
        ExcelUtil<Warehouse> util = new ExcelUtil<Warehouse>(Warehouse.class);
        util.exportExcel(response, list, "仓库管理数据");
    }

    /**
     * 获取仓库管理详细信息
     */
    @RequiresPermissions("cx-ckgl:warehouse:query")
    @GetMapping(value = "/{wId}")
    public AjaxResult getInfo(@PathVariable("wId") Long wId)
    {
        return success(warehouseService.selectWarehouseByWId(wId));
    }

    /**
     * 新增仓库管理
     */
    @RequiresPermissions("cx-ckgl:warehouse:add")
    @Log(title = "仓库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Warehouse warehouse)
    {
        return toAjax(warehouseService.insertWarehouse(warehouse));
    }

    /**
     * 修改仓库管理
     */
    @RequiresPermissions("cx-ckgl:warehouse:edit")
    @Log(title = "仓库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Warehouse warehouse)
    {
        return toAjax(warehouseService.updateWarehouse(warehouse));
    }

    /**
     * 删除仓库管理
     */
    @RequiresPermissions("cx-ckgl:warehouse:remove")
    @Log(title = "仓库管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{wIds}")
    public AjaxResult remove(@PathVariable Long[] wIds)
    {
        return toAjax(warehouseService.deleteWarehouseByWIds(wIds));
    }
}
