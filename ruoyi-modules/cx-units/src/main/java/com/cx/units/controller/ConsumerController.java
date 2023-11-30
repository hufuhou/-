package com.cx.units.controller;

import java.util.List;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.cx.units.domain.Finance;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.cx.units.domain.Consumer;
import com.cx.units.service.IConsumerService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 客户Controller
 *
 * @author 刘志昂
 * @date 2023-11-29
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController extends BaseController {
    @Autowired
    private IConsumerService consumerService;

    /**
     * 查询客户列表
     */
    @RequiresPermissions("units:consumer:list")
    @GetMapping("/list")
    public TableDataInfo list(Consumer consumer) {
        startPage();
        List<Consumer> list = consumerService.selectConsumerList(consumer);
        return getDataTable(list);
    }

    /**
     * 导出客户列表
     */
    @RequiresPermissions("units:consumer:export")
    @Log(title = "客户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Consumer consumer) {
        List<Consumer> list = consumerService.selectConsumerList(consumer);
        ExcelUtil<Consumer> util = new ExcelUtil<Consumer>(Consumer.class);
        util.exportExcel(response, list, "客户数据");
    }

    /**
     * 获取客户详细信息
     */
    @RequiresPermissions("units:consumer:query")
    @GetMapping(value = "/{cId}")
    public AjaxResult getInfo(@PathVariable("cId") Long cId) {
        return success(consumerService.selectConsumerByCId(cId));
    }

    /**
     * 新增客户
     */
    @RequiresPermissions("units:consumer:add")
    @Log(title = "客户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Map<String, Object> requestData) {
        ObjectMapper mapper = new ObjectMapper();
        Consumer consumer = mapper.convertValue(requestData.get("consumer"), Consumer.class);
        Finance finance = mapper.convertValue(requestData.get("finance"), Finance.class);
        return toAjax(consumerService.insertConsumer(consumer, finance));
    }

    /**
     * 修改客户
     */
    @RequiresPermissions("units:consumer:edit")
    @Log(title = "客户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Map<String, Object> requestData) {
        ObjectMapper mapper = new ObjectMapper();
        Consumer consumer = mapper.convertValue(requestData.get("consumer"), Consumer.class);
        Finance finance = mapper.convertValue(requestData.get("finance"), Finance.class);
        return toAjax(consumerService.updateConsumer(consumer, finance));
    }

    /**
     * 删除客户
     */
    @RequiresPermissions("units:consumer:remove")
    @Log(title = "客户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{cIds}")
    public AjaxResult remove(@PathVariable Long[] cIds) {
        return toAjax(consumerService.deleteConsumerByCIds(cIds));
    }

    /**
     * 生成客户编码
     */
    @GetMapping("/getConsumerCode")
    public AjaxResult getConsumerCode() {
        return success(consumerService.getConsumerCode());
    }
}
