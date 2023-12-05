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
import com.cx.ckgl.domain.TjStorageLocation;
import com.cx.ckgl.service.ITjStorageLocationService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 仓库详情Controller
 * 
 * @author hfh
 * @date 2023-11-29
 */
@RestController
@RequestMapping("/location")
public class TjStorageLocationController extends BaseController
{
    @Autowired
    private ITjStorageLocationService tjStorageLocationService;

    /**
     * 查询仓库详情列表
     */
    @RequiresPermissions("cx-ckgl:location:list")
    @GetMapping("/list")
    public TableDataInfo list(TjStorageLocation tjStorageLocation)
    {
        startPage();
        List<TjStorageLocation> list = tjStorageLocationService.selectTjStorageLocationList(tjStorageLocation);
        return getDataTable(list);
    }

    /**
     * 导出仓库详情列表
     */
    @RequiresPermissions("cx-ckgl:location:export")
    @Log(title = "仓库详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TjStorageLocation tjStorageLocation)
    {
        List<TjStorageLocation> list = tjStorageLocationService.selectTjStorageLocationList(tjStorageLocation);
        ExcelUtil<TjStorageLocation> util = new ExcelUtil<TjStorageLocation>(TjStorageLocation.class);
        util.exportExcel(response, list, "仓库详情数据");
    }

    /**
     * 获取仓库详情详细信息
     */
    @RequiresPermissions("cx-ckgl:location:query")
    @GetMapping(value = "/{slId}")
    public AjaxResult getInfo(@PathVariable("slId") Long slId)
    {
        return success(tjStorageLocationService.selectTjStorageLocationBySlId(slId));
    }

    /**
     * 新增仓库详情
     */
    @RequiresPermissions("cx-ckgl:location:add")
    @Log(title = "仓库详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TjStorageLocation tjStorageLocation)
    {
        return toAjax(tjStorageLocationService.insertTjStorageLocation(tjStorageLocation));
    }

    /**
     * 修改仓库详情
     */
    @RequiresPermissions("cx-ckgl:location:edit")
    @Log(title = "仓库详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TjStorageLocation tjStorageLocation)
    {
        return toAjax(tjStorageLocationService.updateTjStorageLocation(tjStorageLocation));
    }

    /**
     * 删除仓库详情
     */
    @RequiresPermissions("cx-ckgl:location:remove")
    @Log(title = "仓库详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{slIds}")
    public AjaxResult remove(@PathVariable Long[] slIds)
    {
        return toAjax(tjStorageLocationService.deleteTjStorageLocationBySlIds(slIds));
    }
}
