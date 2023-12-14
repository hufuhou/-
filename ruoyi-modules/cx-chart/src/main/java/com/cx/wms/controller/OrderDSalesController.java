package com.cx.wms.controller;

import com.cx.wms.domain.OrderDSales;
import com.cx.wms.service.IOrderDSalesService;
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
 * 销售明细Controller
 *
 * @author 熊睿宸
 * @date 2023-11-23
 */
@RestController
@RequestMapping("/sales")
public class OrderDSalesController extends BaseController {
    @Autowired
    private IOrderDSalesService orderDSalesService;

    /**
     * 查询销售明细列表
     */
    @GetMapping("/list")
    public TableDataInfo list(OrderDSales orderDSales) {
        startPage();
        List<OrderDSales> list = orderDSalesService.selectOrderDSalesList(orderDSales);
        return getDataTable(list);
    }

    /**
     * 查询销售明细列表带用户名
     */
    @GetMapping("/listWithUser")
    public TableDataInfo listWithUser(OrderDSales orderDSales) {
        startPage();
        List<OrderDSales> list = orderDSalesService.findListWithUser(orderDSales);
        return getDataTable(list);
    }

    /**
     * 导出销售明细列表
     */
    @RequiresPermissions("sales:sales:export")
    @Log(title = "销售明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderDSales orderDSales) {
        List<OrderDSales> list = orderDSalesService.selectOrderDSalesList(orderDSales);
        ExcelUtil<OrderDSales> util = new ExcelUtil<OrderDSales>(OrderDSales.class);
        util.exportExcel(response, list, "销售明细数据");
    }

    /**
     * 获取销售明细详细信息
     */
    @GetMapping(value = "/{sdId}")
    public AjaxResult getInfo(@PathVariable("sdId") Long sdId) {
        return success(orderDSalesService.selectOrderDSalesBySdId(sdId));
    }

    /**
     * 新增销售明细
     */
    @RequiresPermissions("sales:sales:add")
    @Log(title = "销售明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderDSales orderDSales) {
        return toAjax(orderDSalesService.insertOrderDSales(orderDSales));
    }

    /**
     * 修改销售明细
     */
    @RequiresPermissions("sales:sales:edit")
    @Log(title = "销售明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderDSales orderDSales) {
        return toAjax(orderDSalesService.updateOrderDSales(orderDSales));
    }

    /**
     * 删除销售明细
     */
    @RequiresPermissions("sales:sales:remove")
    @Log(title = "销售明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sdIds}")
    public AjaxResult remove(@PathVariable Long[] sdIds) {
        return toAjax(orderDSalesService.deleteOrderDSalesBySdIds(sdIds));
    }

    /**
     * 查询总销售订单数
     *
     * @return 未删除订单数
     */
    @GetMapping("/numbers")
    public AjaxResult findOrderSalesNumber() {
        Integer res = orderDSalesService.findOrderSalesNumber();
        if (res > 0) {
            return success(res);
        } else {
            return warn("发生错误!");
        }
    }

    /**
     * 查询总销售订单数
     *
     * @return 未删除订单数
     */
    @GetMapping("/sumSale")
    public AjaxResult sumSaleAmount() {
        Integer res = orderDSalesService.sumSaleAmount();
        if (res > 0) {
            return success(res);
        } else {
            return warn("发生错误!");
        }
    }

}
