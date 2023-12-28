package com.cx.crkgl.controller;

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
import com.cx.crkgl.domain.CrkTransfer;
import com.cx.crkgl.service.ICrkTransferService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 库存调拨Controller
 * 
 * @author ruoyi
 * @date 2023-12-13
 */
@RestController
@RequestMapping("/transfer")
public class CrkTransferController extends BaseController
{
    @Autowired
    private ICrkTransferService crkTransferService;

    /**
     * 查询库存调拨列表
     */
    @RequiresPermissions("cx-crkgl:transfer:list")
    @GetMapping("/list")
    public TableDataInfo list(CrkTransfer crkTransfer)
    {
        startPage();
        List<CrkTransfer> list = crkTransferService.selectCrkTransferList(crkTransfer);
        return getDataTable(list);
    }

    /**
     * 导出库存调拨列表
     */
    @RequiresPermissions("cx-crkgl:transfer:export")
    @Log(title = "库存调拨", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrkTransfer crkTransfer)
    {
        List<CrkTransfer> list = crkTransferService.selectCrkTransferList(crkTransfer);
        ExcelUtil<CrkTransfer> util = new ExcelUtil<CrkTransfer>(CrkTransfer.class);
        util.exportExcel(response, list, "库存调拨数据");
    }

    /**
     * 获取库存调拨详细信息
     */
    @RequiresPermissions("cx-crkgl:transfer:query")
    @GetMapping(value = "/{tId}")
    public AjaxResult getInfo(@PathVariable("tId") Long tId)
    {
        return success(crkTransferService.selectCrkTransferByTId(tId));
    }

    /**
     * 新增库存调拨
     */
    @RequiresPermissions("cx-crkgl:transfer:add")
    @Log(title = "库存调拨", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrkTransfer crkTransfer)
    {
        return toAjax(crkTransferService.insertCrkTransfer(crkTransfer));
    }

    /**
     * 修改库存调拨
     */
    @RequiresPermissions("cx-crkgl:transfer:edit")
    @Log(title = "库存调拨", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrkTransfer crkTransfer)
    {
        System.out.println(crkTransfer);
        return toAjax(crkTransferService.updateCrkTransfer(crkTransfer));
    }

    /**
     * 删除库存调拨
     */
    @RequiresPermissions("cx-crkgl:transfer:remove")
    @Log(title = "库存调拨", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tIds}")
    public AjaxResult remove(@PathVariable Long[] tIds)
    {
        return toAjax(crkTransferService.deleteCrkTransferByTIds(tIds));
    }

    /**
     * 生成调拨编码
     */
    @GetMapping("/getTranCode")
    public AjaxResult getTranCode() {
        return success(crkTransferService.getTranCode());
    }

    /**
     * 修改计划数量
     * @param wId
     * @param goodsId
     * @param tQuantity
     * @return
     */
    @GetMapping("/{wId}/{goodsId}/{tQuantity}")
    public AjaxResult updateNumberofPlans(@PathVariable("wId") Long wId,@PathVariable("goodsId") Long goodsId,@PathVariable("tQuantity") Long tQuantity){
        return toAjax(crkTransferService.updateNumberofPlans(wId, goodsId, tQuantity));
    }
}
