package com.cx.wms.controller;

import com.cx.wms.domain.OrderPurchaseDetails;
import com.cx.wms.service.IOrderPurchaseDetailsService;
import com.cx.wms.utils.DateUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
@Slf4j
public class OrderPurchaseDetailsController extends BaseController {
    @Autowired
    private IOrderPurchaseDetailsService orderPurchaseDetailsService;

    /**
     * 查询进货明细列表
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
     *
     * @return 未删除总进货订单数
     */
    @GetMapping(value = "/count")
    @Log(title = "进货明细信息总数", businessType = BusinessType.OTHER)
    public AjaxResult count() {
        //System.out.println(orderPurchaseDetailsService.selectOrderPurchaseDetailsCount());
        return success(orderPurchaseDetailsService.selectOrderPurchaseDetailsCount());
    }

    /**
     * 查询日期参数
     *
     * @param param 前段传递选择时间段参数
     * @return 时间段开始结束数组
     */
    @GetMapping(value = "/dateParam")
    public AjaxResult getDateParam(String param) {
        if ("today".equals(param)) {
            //获取今天日期
            return AjaxResult.success("获取今日参数成功", DateUtils.getToday());
        } else if ("yesterday".equals(param)) {
            //获取昨天日期
            return AjaxResult.success("获取昨日参数成功", DateUtils.getYesterday());
        } else if ("thisWeek".equals(param)) {
            //获取本周 开始/结束日
            return AjaxResult.success("获取本周开始/结束日参数成功", DateUtils.getWeekRange());
        } else if ("lastWeek".equals(param)) {
            //获取上周 开始/结束日
            return AjaxResult.success("获取上周开始/结束日参数成功", DateUtils.getLastWeekRange());
        } else if ("thisMonth".equals(param)) {
            //获取本月 开始/结束日
            return AjaxResult.success("获取本月开始/结束日参数成功", DateUtils.getMonthRange());
        } else if ("lastMonth".equals(param)) {
            //获取上月 开始/结束日
            return AjaxResult.success("获取上月开始/结束日参数成功", DateUtils.getLastMonthRange());
        }
        return warn("查询失败");
    }

    /**
     * 按时间段查询进货列表
     *
     * @return 按时间段进货列表
     */
    @GetMapping(value = "/findInfoByDate")
    @Log(title = "按时间段查询进货明细信息", businessType = BusinessType.OTHER)
    public AjaxResult findInfoByDate(@Param("TodayOrYesterday") String TodayOrYesterday, @Param("BeginDay") String BeginDay, @Param("EndDay") String EndDay) {
        try {
            List<OrderPurchaseDetails> list = orderPurchaseDetailsService.findInfoByDate(TodayOrYesterday, BeginDay, EndDay);
            if (list != null && !list.isEmpty()) {
                return AjaxResult.success("查询成功,查询到" + list.size() + "条数据", list);
            } else {
                return AjaxResult.warn("查询不到数据");
            }
        } catch (Exception e) {
            log.error("查询异常", e);
            return AjaxResult.error("查询异常");
        }
    }


}
