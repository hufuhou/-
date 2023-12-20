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
import com.cx.crkgl.domain.CrkOutManagement;
import com.cx.crkgl.service.ICrkOutManagementService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 出库管理Controller
 * 
 * @author hfh
 * @date 2023-12-20
 */
@RestController
@RequestMapping("/outbound")
public class CrkOutManagementController extends BaseController
{
    @Autowired
    private ICrkOutManagementService crkOutManagementService;

    /**
     * 查询出库管理列表
     */
    @RequiresPermissions("cx-crkgl:outbound:list")
    @GetMapping("/list")
    public TableDataInfo list(CrkOutManagement crkOutManagement)
    {
        startPage();
        List<CrkOutManagement> list = crkOutManagementService.selectCrkOutManagementList(crkOutManagement);
        return getDataTable(list);
    }

    /**
     * 导出出库管理列表
     */
    @RequiresPermissions("cx-crkgl:outbound:export")
    @Log(title = "出库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrkOutManagement crkOutManagement)
    {
        List<CrkOutManagement> list = crkOutManagementService.selectCrkOutManagementList(crkOutManagement);
        ExcelUtil<CrkOutManagement> util = new ExcelUtil<CrkOutManagement>(CrkOutManagement.class);
        util.exportExcel(response, list, "出库管理数据");
    }

    /**
     * 获取出库管理详细信息
     */
    @RequiresPermissions("cx-crkgl:outbound:query")
    @GetMapping(value = "/{outId}")
    public AjaxResult getInfo(@PathVariable("outId") Long outId)
    {
        return success(crkOutManagementService.selectCrkOutManagementByOutId(outId));
    }

    /**
     * 新增出库管理
     */
    @RequiresPermissions("cx-crkgl:outbound:add")
    @Log(title = "出库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrkOutManagement crkOutManagement)
    {
        return toAjax(crkOutManagementService.insertCrkOutManagement(crkOutManagement));
    }

    /**
     * 修改出库管理
     */
    @RequiresPermissions("cx-crkgl:outbound:edit")
    @Log(title = "出库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrkOutManagement crkOutManagement)
    {
        return toAjax(crkOutManagementService.updateCrkOutManagement(crkOutManagement));
    }

    /**
     * 删除出库管理
     */
    @RequiresPermissions("cx-crkgl:outbound:remove")
    @Log(title = "出库管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{outIds}")
    public AjaxResult remove(@PathVariable Long[] outIds)
    {
        return toAjax(crkOutManagementService.deleteCrkOutManagementByOutIds(outIds));
    }
}
