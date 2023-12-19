package com.cx.order.controller;

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
import com.cx.order.domain.OrderPurchaseDetails;
import com.cx.order.service.IOrderPurchaseDetailsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 进货明细Controller
 *
 * @author LZA
 * @date 2023-12-07
 */
@RestController
@RequestMapping("/details")
public class OrderPurchaseDetailsController extends BaseController {
    @Autowired
    private IOrderPurchaseDetailsService orderPurchaseDetailsService;

    /**
     * 查询进货明细列表
     */
    @RequiresPermissions("order:details:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderPurchaseDetails orderPurchaseDetails) {
        startPage();
        List<OrderPurchaseDetails> list = orderPurchaseDetailsService.selectOrderPurchaseDetailsList(orderPurchaseDetails);
        return getDataTable(list);
    }

    /**
     * 导出进货明细列表
     */
    @RequiresPermissions("order:details:export")
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
    @RequiresPermissions("order:details:query")
    @GetMapping(value = "/{pdId}")
    public AjaxResult getInfo(@PathVariable("pdId") Long pdId) {
        return success(orderPurchaseDetailsService.selectOrderPurchaseDetailsByPdId(pdId));
    }

    /**
     * 新增进货明细
     */
    @RequiresPermissions("order:details:add")
    @Log(title = "进货明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody List<OrderPurchaseDetails> orderPurchaseDetails) {
        int i = 0;
        for (OrderPurchaseDetails detail : orderPurchaseDetails) {
            i = orderPurchaseDetailsService.insertOrderPurchaseDetails(detail);
        }
        return toAjax(i);
    }

    /**
     * 修改进货明细
     */
    @RequiresPermissions("order:details:edit")
    @Log(title = "进货明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderPurchaseDetails orderPurchaseDetails) {
        return toAjax(orderPurchaseDetailsService.updateOrderPurchaseDetails(orderPurchaseDetails));
    }

    /**
     * 删除进货明细
     */
    @RequiresPermissions("order:details:remove")
    @Log(title = "进货明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{pdIds}")
    public AjaxResult remove(@PathVariable Long[] pdIds) {
        return toAjax(orderPurchaseDetailsService.deleteOrderPurchaseDetailsByPdIds(pdIds));
    }

    /**
     * 查询进货信息和货品
     *
     * @param poCode
     * @param oldPoCode
     * @return
     */
    @GetMapping("/detailAndProduct")
    public AjaxResult detailAndProduct(String poCode, String oldPoCode) {
        List<OrderPurchaseDetails> details = orderPurchaseDetailsService.detailAndProduct(poCode, oldPoCode);
        return success(details);
    }
}
