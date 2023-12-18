package com.cx.wms.controller;

import com.cx.wms.domain.CrkInventorySheet;
import com.cx.wms.service.ICrkInventorySheetService;
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
 * 库存盘点Controller
 *
 * @author xrc
 * @date 2023-12-14
 */
@RestController
@RequestMapping("/kcinventory")
public class CrkInventorySheetController extends BaseController {
    @Autowired
    private ICrkInventorySheetService crkInventorySheetService;

    /**
     * 查询库存盘点列表
     */
    @RequiresPermissions("kcinventory:kcinventory:list")
    @GetMapping("/list")
    public TableDataInfo list(CrkInventorySheet crkInventorySheet) {
        startPage();
        List<CrkInventorySheet> list = crkInventorySheetService.selectCrkInventorySheetList(crkInventorySheet);
        return getDataTable(list);
    }

    /**
     * 导出库存盘点列表
     */
    @RequiresPermissions("kcinventory:kcinventory:export")
    @Log(title = "库存盘点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrkInventorySheet crkInventorySheet) {
        List<CrkInventorySheet> list = crkInventorySheetService.selectCrkInventorySheetList(crkInventorySheet);
        ExcelUtil<CrkInventorySheet> util = new ExcelUtil<CrkInventorySheet>(CrkInventorySheet.class);
        util.exportExcel(response, list, "库存盘点数据");
    }

    /**
     * 获取库存盘点详细信息
     */
    @RequiresPermissions("kcinventory:kcinventory:query")
    @GetMapping(value = "/{isId}")
    public AjaxResult getInfo(@PathVariable("isId") Long isId) {
        return success(crkInventorySheetService.selectCrkInventorySheetByIsId(isId));
    }

    /**
     * 新增库存盘点
     */
    @RequiresPermissions("kcinventory:kcinventory:add")
    @Log(title = "库存盘点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrkInventorySheet crkInventorySheet) {
        return toAjax(crkInventorySheetService.insertCrkInventorySheet(crkInventorySheet));
    }

    /**
     * 修改库存盘点
     */
    @RequiresPermissions("kcinventory:kcinventory:edit")
    @Log(title = "库存盘点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrkInventorySheet crkInventorySheet) {
        return toAjax(crkInventorySheetService.updateCrkInventorySheet(crkInventorySheet));
    }

    /**
     * 删除库存盘点
     */
    @RequiresPermissions("kcinventory:kcinventory:remove")
    @Log(title = "库存盘点", businessType = BusinessType.DELETE)
    @DeleteMapping("/{isIds}")
    public AjaxResult remove(@PathVariable Long[] isIds) {
        return toAjax(crkInventorySheetService.deleteCrkInventorySheetByIsIds(isIds));
    }

    /**
     * 查询最新的is_code
     * @return 最新的is_code
     * @auther xrc
     */
    @GetMapping("/findIsCode")
    public AjaxResult findIsCode() {
        return new AjaxResult(200,"查询成功",crkInventorySheetService.findIsCode());
    }

    /**
     * 生成is_code
     * @return 生成的is_code
     * @auther xrc
     */
    @GetMapping("/genIsCode")
    public AjaxResult genIsCode() {
        return new AjaxResult(200,"生成成功",crkInventorySheetService.genIsCode());
    }


    /**
     * 查询仓库name 仓库id
     *
     * @return 仓库name 仓库id
     */
    @GetMapping("/findWareHouse")
    public AjaxResult findWareHouse() {
        return new AjaxResult(200,"获取成功",crkInventorySheetService.findWareHouse());
    }




}
