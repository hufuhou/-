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
import com.cx.order.domain.OrderSalesDetails;
import com.cx.order.service.IOrderSalesDetailsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 销售明细Controller
 * 
 * @author LZA
 * @date 2023-12-19
 */
@RestController
@RequestMapping("/salesDetails")
public class OrderSalesDetailsController extends BaseController
{
    @Autowired
    private IOrderSalesDetailsService orderSalesDetailsService;

    /**
     * 查询销售明细列表
     */
    @RequiresPermissions("order:salesDetails:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderSalesDetails orderSalesDetails)
    {
        startPage();
        List<OrderSalesDetails> list = orderSalesDetailsService.selectOrderSalesDetailsList(orderSalesDetails);
        return getDataTable(list);
    }

    /**
     * 导出销售明细列表
     */
    @RequiresPermissions("order:salesDetails:export")
    @Log(title = "销售明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderSalesDetails orderSalesDetails)
    {
        List<OrderSalesDetails> list = orderSalesDetailsService.selectOrderSalesDetailsList(orderSalesDetails);
        ExcelUtil<OrderSalesDetails> util = new ExcelUtil<OrderSalesDetails>(OrderSalesDetails.class);
        util.exportExcel(response, list, "销售明细数据");
    }

    /**
     * 获取销售明细详细信息
     */
    @RequiresPermissions("order:salesDetails:query")
    @GetMapping(value = "/{sdId}")
    public AjaxResult getInfo(@PathVariable("sdId") Long sdId)
    {
        return success(orderSalesDetailsService.selectOrderSalesDetailsBySdId(sdId));
    }

    /**
     * 新增销售明细
     */
    @RequiresPermissions("order:salesDetails:add")
    @Log(title = "销售明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderSalesDetails orderSalesDetails)
    {
        return toAjax(orderSalesDetailsService.insertOrderSalesDetails(orderSalesDetails));
    }

    /**
     * 修改销售明细
     */
    @RequiresPermissions("order:salesDetails:edit")
    @Log(title = "销售明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderSalesDetails orderSalesDetails)
    {
        return toAjax(orderSalesDetailsService.updateOrderSalesDetails(orderSalesDetails));
    }

    /**
     * 删除销售明细
     */
    @RequiresPermissions("order:salesDetails:remove")
    @Log(title = "销售明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sdIds}")
    public AjaxResult remove(@PathVariable Long[] sdIds)
    {
        return toAjax(orderSalesDetailsService.deleteOrderSalesDetailsBySdIds(sdIds));
    }
}
