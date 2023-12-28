package com.cx.order.controller;

import java.util.List;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.cx.order.domain.OrderPurchase;
import com.cx.order.domain.OrderPurchaseDetails;
import com.cx.order.domain.OrderSalesDetails;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ruoyi.common.security.utils.SecurityUtils;
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
import com.cx.order.domain.OrderSales;
import com.cx.order.service.IOrderSalesService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 销售订单Controller
 *
 * @author LZA
 * @date 2023-12-19
 */
@RestController
@RequestMapping("/sales")
public class OrderSalesController extends BaseController {
    @Autowired
    private IOrderSalesService orderSalesService;

    /**
     * 查询销售订单列表
     */
    @RequiresPermissions("order:sales:list")
    @GetMapping("/list")
    public TableDataInfo list(OrderSales orderSales) {
        startPage();
        List<OrderSales> list = orderSalesService.selectOrderSalesList(orderSales);
        return getDataTable(list);
    }

    /**
     * 导出销售订单列表
     */
    @RequiresPermissions("order:sales:export")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderSales orderSales) {
        List<OrderSales> list = orderSalesService.selectOrderSalesList(orderSales);
        ExcelUtil<OrderSales> util = new ExcelUtil<OrderSales>(OrderSales.class);
        util.exportExcel(response, list, "销售订单数据");
    }

    /**
     * 获取销售订单详细信息
     */
    @RequiresPermissions("order:sales:query")
    @GetMapping(value = "/{sId}")
    public AjaxResult getInfo(@PathVariable("sId") Long sId) {
        return success(orderSalesService.selectOrderSalesBySId(sId));
    }

    /**
     * 新增销售订单
     */
    @RequiresPermissions("order:sales:add")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Map<String, Object> requestData) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OrderSales orderSales = mapper.convertValue(requestData.get("orderSales"), OrderSales.class);
        List<OrderSalesDetails> orderSalesDetails = mapper.convertValue(requestData.get("orderSalesDetails"), TypeFactory.defaultInstance().constructCollectionType(List.class, OrderSalesDetails.class));
        return toAjax(orderSalesService.insertOrderSales(orderSales, orderSalesDetails));
    }

    /**
     * 修改销售订单
     */
    @RequiresPermissions("order:sales:edit")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Map<String, Object> requestData) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OrderSales orderSales = mapper.convertValue(requestData.get("orderSales"), OrderSales.class);
        List<OrderSalesDetails> orderSalesDetails = mapper.convertValue(requestData.get("orderSalesDetails"), TypeFactory.defaultInstance().constructCollectionType(List.class, OrderSalesDetails.class));
        List<OrderSalesDetails> updateDetails = mapper.convertValue(requestData.get("updateDetails"), TypeFactory.defaultInstance().constructCollectionType(List.class, OrderSalesDetails.class));
        return toAjax(orderSalesService.updateOrderSales(orderSales, orderSalesDetails, updateDetails));
    }

    /**
     * 删除销售订单
     */
    @RequiresPermissions("order:sales:remove")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{sIds}")
    public AjaxResult remove(@PathVariable Long[] sIds) {
        return toAjax(orderSalesService.deleteOrderSalesBySIds(sIds));
    }

    /**
     * 生成销售编码
     */
    @GetMapping("/getsCode")
    public AjaxResult getsCode() {
        return success(orderSalesService.getsCode());
    }

    @GetMapping("/{status}/{sId}")
    public AjaxResult updateAudit(@PathVariable Integer status, @PathVariable Long sId) {
        String reviewer = String.valueOf(SecurityUtils.getUserId());
        return success(orderSalesService.updateAudit(status, sId, reviewer));
    }
}
