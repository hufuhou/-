package com.cx.wms.controller;

import com.cx.wms.domain.CrkOutDetails;
import com.cx.wms.service.ICrkOutDetailsService;
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
 * 出库明细
 * Controller
 *
 * @author XRC
 * @date 2023-11-20
 */
@RestController
@RequestMapping("/details")
public class CrkOutDetailsController extends BaseController {
    @Autowired
    private ICrkOutDetailsService crkOutDetailsService;

    /**
     * 查询出库明细
     * 列表
     */
    @RequiresPermissions("out_details:details:list")
    @GetMapping("/list")
    public TableDataInfo list(CrkOutDetails crkOutDetails) {
        startPage();
        List<CrkOutDetails> list = crkOutDetailsService.selectCrkOutDetailsList(crkOutDetails);
        return getDataTable(list);
    }

    /**
     * 查询出库明细带用户名
     * 列表
     */
    @RequiresPermissions("out_details:details:list")
    @GetMapping("/listAndUser")
    public TableDataInfo listAndUser(CrkOutDetails crkOutDetails) {
        startPage();
        List<CrkOutDetails> list = crkOutDetailsService.selectAllWithUser(crkOutDetails);
        return getDataTable(list);
    }

    /**
     * 导出出库明细
     * 列表
     */
    @RequiresPermissions("out_details:details:export")
    @Log(title = "出库明细 ", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrkOutDetails crkOutDetails) {
        List<CrkOutDetails> list = crkOutDetailsService.selectCrkOutDetailsList(crkOutDetails);
        ExcelUtil<CrkOutDetails> util = new ExcelUtil<CrkOutDetails>(CrkOutDetails.class);
        util.exportExcel(response, list, "出库明细 数据");
    }

    /**
     * 获取出库明细
     * 详细信息
     */
    @RequiresPermissions("out_details:details:query")
    @GetMapping(value = "/{omId}")
    public AjaxResult getInfo(@PathVariable("omId") Long omId) {
        return success(crkOutDetailsService.selectCrkOutDetailsByOmId(omId));
    }

    /**
     * 新增出库明细
     */
    @RequiresPermissions("out_details:details:add")
    @Log(title = "出库明细 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrkOutDetails crkOutDetails) {
        return toAjax(crkOutDetailsService.insertCrkOutDetails(crkOutDetails));
    }

    /**
     * 修改出库明细
     */
    @RequiresPermissions("out_details:details:edit")
    @Log(title = "出库明细 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrkOutDetails crkOutDetails) {
        return toAjax(crkOutDetailsService.updateCrkOutDetails(crkOutDetails));
    }

    /**
     * 删除出库明细
     */
    @RequiresPermissions("out_details:details:remove")
    @Log(title = "出库明细 ", businessType = BusinessType.DELETE)
    @DeleteMapping("/{omIds}")
    public AjaxResult remove(@PathVariable Long[] omIds) {
        return toAjax(crkOutDetailsService.deleteCrkOutDetailsByOmIds(omIds));
    }

    /**
     * 查询出库订单数
     *
     * @return 未删除出库订单数
     */
    @GetMapping("/numbers")
    @Log(title = "出库明细总数 ", businessType = BusinessType.OTHER)
    public AjaxResult findCrkOutNumber() {
        Integer res = crkOutDetailsService.findCrkOutNumber();
        if (res > 0) {
            return success(res);
        } else {
            return error("查询失败");
        }
    }
}
