package com.cx.crkgl.controller;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.cx.crkgl.domain.CombinedData;
import com.cx.crkgl.domain.CrkImDetails;
import com.cx.crkgl.domain.OrderPurchaseDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.cx.crkgl.domain.CrkInboundManagement;
import com.cx.crkgl.service.ICrkInboundManagementService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 入库管理Controller
 *
 * @author hfh
 * @date 2023-12-01
 */
@RestController
@RequestMapping("/management")
public class CrkInboundManagementController extends BaseController {
    @Autowired
    private ICrkInboundManagementService crkInboundManagementService;

    /**
     * 查询入库管理列表
     */
    @RequiresPermissions("cx-crkgl:management:list")
    @GetMapping("/list")
    public TableDataInfo list(CrkInboundManagement crkInboundManagement) {
        startPage();
        List<CrkInboundManagement> list = crkInboundManagementService.selectCrkInboundManagementList(crkInboundManagement);
        return getDataTable(list);
    }

    /**
     * 查询入库管理列表
     */
    @GetMapping("/list1/{inCodes}")
    public TableDataInfo list1(@PathVariable("inCodes") String[] inCodes) {
        List<CrkInboundManagement> list = crkInboundManagementService.selectCrkInboundManagementList1(Arrays.asList(inCodes));
        return getDataTable(list);
    }

    /**
     * 导出入库管理列表
     */
    @RequiresPermissions("cx-crkgl:management:export")
    @Log(title = "入库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrkInboundManagement crkInboundManagement) {
        List<CrkInboundManagement> list = crkInboundManagementService.selectCrkInboundManagementList(crkInboundManagement);
        ExcelUtil<CrkInboundManagement> util = new ExcelUtil<CrkInboundManagement>(CrkInboundManagement.class);
        util.exportExcel(response, list, "入库管理数据");
    }

    /**
     * 获取入库管理详细信息
     */
    @RequiresPermissions("cx-crkgl:management:query")
    @GetMapping(value = "/{inId}")
    public AjaxResult getInfo(@PathVariable("inId") Long inId) {
        return success(crkInboundManagementService.selectCrkInboundManagementByInId(inId));
    }

    /**
     * 获取入库数量
     */
    @GetMapping(value = "/rksl/{gCode}")
    public AjaxResult getRksl(@PathVariable("gCode") String gCode) {
        return success(crkInboundManagementService.InventoryQuantity(gCode));
    }

    /**
     * 查询进货明细
     */
    @GetMapping(value = "/mx")
    public TableDataInfo getInventoryDetail(OrderPurchaseDetails orderPurchaseDetails) {
        startPage();
        List<OrderPurchaseDetails> list = crkInboundManagementService.selectInventoryDetails(orderPurchaseDetails);
        return getDataTable(list);
    }

    /**
     * 查询货品id
     */
    @GetMapping(value = "/hp/{inId}")
    public TableDataInfo getCrkImDetails(@PathVariable("inId") Long[] inId) {
        List<CrkImDetails> list = crkInboundManagementService.selectCrkImDetails(inId);
        return getDataTable(list);
    }

    /**
     * 新增入库管理
     */
    @RequiresPermissions("cx-crkgl:management:add")
    @Log(title = "入库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrkInboundManagement crkInboundManagement) {
        return toAjax(crkInboundManagementService.insertCrkInboundManagement(crkInboundManagement));
    }

    /**
     * 修改入库管理
     */
    @RequiresPermissions("cx-crkgl:management:edit")
    @Log(title = "入库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrkInboundManagement crkInboundManagement) {
        return toAjax(crkInboundManagementService.updateCrkInboundManagement(crkInboundManagement));
    }

    /**
     * 删除入库管理
     */
    @RequiresPermissions("cx-crkgl:management:remove")
    @Log(title = "入库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{inIds}")
    public AjaxResult remove(@PathVariable Long[] inIds) {
        return toAjax(crkInboundManagementService.deleteCrkInboundManagementByInIds(inIds));
    }

    /**
     * 审核入库
     */
    @RequiresPermissions("cx-ckgl:warehouse:examine")
    @PostMapping("/sh")
    public AjaxResult examine(@RequestBody CombinedData combinedData) {
        List<CrkInboundManagement> list = crkInboundManagementService.selectCrkInboundManagementList1(combinedData.getInCode());
        return toAjax(crkInboundManagementService.InventoryReviews(list, combinedData.getInIds(),  combinedData.getReviewStatus()));
    }

    /**
     * 撤销入库
     */
    @RequiresPermissions("cx-ckgl:warehouse:revoke")
    @PostMapping("/zx")
    public AjaxResult revoke(@RequestBody CombinedData combinedData) {
        return toAjax(crkInboundManagementService.WithdrawalStorages(combinedData.getInIds()));
    }
}
