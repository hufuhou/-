package com.cx.wms.controller;

import com.cx.wms.domain.OrderPurchaseDetails;
import com.cx.wms.service.IOrderPurchaseDetailsService;
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
 * 进货明细Controller
 *
 * @author 熊睿宸
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/opdm")
public class OrderPurchaseDetailsController extends BaseController {
    @Autowired
    private IOrderPurchaseDetailsService orderPurchaseDetailsService;

    /**
     * 查询进货明细列表0
     */
    @RequiresPermissions("opdm:opdm:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderPurchaseDetails orderPurchaseDetails) {
        startPage();
        List<OrderPurchaseDetails> list = orderPurchaseDetailsService.selectOrderPurchaseDetailsList(orderPurchaseDetails);
        return getDataTable(list);
    }

    /**
     * 查询进货明细列表0
     */
    @RequiresPermissions("opdm:opdm:list")
    @GetMapping("/listUG")
    public TableDataInfo listWithUserGoodName(OrderPurchaseDetails orderPurchaseDetails) {
        startPage();
        List<OrderPurchaseDetails> list = orderPurchaseDetailsService.findWithUserAndGoodName(orderPurchaseDetails);
        return getDataTable(list);
    }

    /**
     * 导出进货明细列表
     */
    @RequiresPermissions("opdm:opdm:export")
    @Log(title = "进货明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderPurchaseDetails orderPurchaseDetails) {
        List<OrderPurchaseDetails> list = orderPurchaseDetailsService.selectOrderPurchaseDetailsList(orderPurchaseDetails);
        ExcelUtil<OrderPurchaseDetails> util = new ExcelUtil<OrderPurchaseDetails>(OrderPurchaseDetails.class);
        util.exportExcel(response, list, "进货明细数据");
    }
    /**
     * 获取进货明细详细信息
     */
    @RequiresPermissions("opdm:opdm:query")
    @GetMapping(value = "/{pdId}")
    public AjaxResult getInfo(@PathVariable("pdId") Long pdId) {
        return success(orderPurchaseDetailsService.selectOrderPurchaseDetailsByPdId(pdId));
    }

    /**
     * 新增进货明细
     */
    @RequiresPermissions("opdm:opdm:add")
    @Log(title = "进货明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderPurchaseDetails orderPurchaseDetails) {
        return toAjax(orderPurchaseDetailsService.insertOrderPurchaseDetails(orderPurchaseDetails));
    }

    /**
     * 修改进货明细
     */
    @RequiresPermissions("opdm:opdm:edit")
    @Log(title = "进货明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderPurchaseDetails orderPurchaseDetails) {
        return toAjax(orderPurchaseDetailsService.updateOrderPurchaseDetails(orderPurchaseDetails));
    }

    /**
     * 删除进货明细
     */
    @RequiresPermissions("opdm:opdm:remove")
    @Log(title = "进货明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pdIds}")
    public AjaxResult remove(@PathVariable Long[] pdIds) {
        return toAjax(orderPurchaseDetailsService.deleteOrderPurchaseDetailsByPdIds(pdIds));
    }

    /**
     * 查询进货信息数
     * no param
     * @return 未删除总进货订单数
     */
    @GetMapping(value = "/count")
    @Log(title = "进货明细信息总数", businessType = BusinessType.OTHER)
    public AjaxResult count(){
        //System.out.println(orderPurchaseDetailsService.selectOrderPurchaseDetailsCount());
        return success(orderPurchaseDetailsService.selectOrderPurchaseDetailsCount());
    }
}
