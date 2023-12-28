package com.cx.ledger.controller;

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
import com.cx.ledger.domain.TzStock;
import com.cx.ledger.service.ITzStockService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 库存查询Controller
 * 
 * @author ruoyi
 * @date 2023-11-27
 */
@RestController
@RequestMapping("/stock")
public class TzStockController extends BaseController
{
    @Autowired
    private ITzStockService tzStockService;

    /**
     * 查询库存查询列表
     */
    @RequiresPermissions("cx-ledger:stock:list")
    @GetMapping("/list")
    public TableDataInfo list(TzStock tzStock)
    {
        startPage();
        List<TzStock> list = tzStockService.selectTzStockList(tzStock);
        return getDataTable(list);
    }

    /**
     * 导出库存查询列表
     */
    @RequiresPermissions("cx-ledger:stock:export")
    @Log(title = "库存查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TzStock tzStock)
    {
        List<TzStock> list = tzStockService.selectTzStockList(tzStock);
        ExcelUtil<TzStock> util = new ExcelUtil<TzStock>(TzStock.class);
        util.exportExcel(response, list, "库存查询数据");
    }

    /**
     * 获取库存查询详细信息
     */
    @RequiresPermissions("cx-ledger:stock:query")
    @GetMapping(value = "/{sId}")
    public AjaxResult getInfo(@PathVariable("sId") Long sId)
    {
        return success(tzStockService.selectTzStockBySId(sId));
    }

    /**
     * 新增库存查询
     */
    @RequiresPermissions("cx-ledger:stock:add")
    @Log(title = "库存查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TzStock tzStock)
    {
        return toAjax(tzStockService.insertTzStock(tzStock));
    }

    /**
     * 修改库存查询
     */
    @RequiresPermissions("cx-ledger:stock:edit")
    @Log(title = "库存查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TzStock tzStock)
    {
        return toAjax(tzStockService.updateTzStock(tzStock));
    }

    /**
     * 删除库存查询
     */
    @RequiresPermissions("cx-ledger:stock:remove")
    @Log(title = "库存查询", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sIds}")
    public AjaxResult remove(@PathVariable Long[] sIds)
    {
        return toAjax(tzStockService.deleteTzStockBySIds(sIds));
    }

    @RequiresPermissions("cx-ledger:stock:listOutGoods")
    @GetMapping("/listOutGoods")
    public TableDataInfo listOutGoods(TzStock tz){
        startPage();
        List<TzStock> list =tzStockService.listOutGoods(tz);
        return getDataTable(list);
    }
}
