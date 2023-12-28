package com.cx.crkgl.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.cx.crkgl.domain.*;
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
        CrkOutManagement crkOutManagement=crkOutManagementService.selectCrkOutManagementByOutId(outId);
        System.out.println(crkOutManagement);
        return success(crkOutManagement);
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

    /**
     * 查询销售明细
     */
    @GetMapping(value = "/mx")
    public TableDataInfo getOutDetail(OrderSalesDetails orderSalesDetails) {
        startPage();
        List<OrderSalesDetails> list = crkOutManagementService.selectSalesDetails(orderSalesDetails);
        return getDataTable(list);
    }

    /**
     * 获取当前库存
     */
    @GetMapping(value = "/kcsl/{slId}/{gId}")
    public AjaxResult getKcsl(@PathVariable("slId") Long slId,@PathVariable("gId") Long gId) {
        return success(crkOutManagementService.currentInventory(slId,gId));
    }
    /**
     * 查询货品id
     */
    @GetMapping(value = "/hp/{outId}")
    public TableDataInfo getCrkOutDetails(@PathVariable("outId") Long[] outId) {
        List<CrkOutDetails> list = crkOutManagementService.selectCrkOutDetails(outId);
        return getDataTable(list);
    }
    /**
     * 审核出库
     */
    @RequiresPermissions("cx-ckgl:outbound:examine")
    @PostMapping("/sh")
    public AjaxResult examine(@RequestBody CombinedData combinedData) {
        List<CrkOutManagement> list = crkOutManagementService.selectCrkOutManagementList1(combinedData.getOutCode());
        return toAjax(crkOutManagementService.AuditOutbounds(list, combinedData.getOutIds(),  combinedData.getReviewStatus()));
    }

    /**
     * 撤销入库
     */
    @RequiresPermissions("cx-ckgl:outbound:revoke")
    @PostMapping("/zx")
    public AjaxResult revoke(@RequestBody CombinedData combinedData) {
        return toAjax(crkOutManagementService.WithdrawalStorages(combinedData.getOutIds()));
    }
}
