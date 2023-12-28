package com.cx.wms.controller;

import com.cx.wms.domain.CrkImDetails;
import com.cx.wms.service.ICrkImDetailsService;
import com.cx.wms.service.IStockValuesService;
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
 * 入库明细Controller
 *
 * @author XRC
 * @date 2023-11-21
 */
@RestController
@RequestMapping("/checkin")
@Slf4j
public class CrkImDetailsController extends BaseController {
    @Autowired
    private ICrkImDetailsService crkImDetailsService;

    @Autowired
    private IStockValuesService stockValuesService;

    /**
     * 查询入库明细列表
     */
    @RequiresPermissions("checkin:checkin:list")
    @GetMapping("/list")
    public TableDataInfo list(CrkImDetails crkImDetails) {
        startPage();
        List<CrkImDetails> list = crkImDetailsService.selectCrkImDetailsList(crkImDetails);
        return getDataTable(list);
    }

    /**
     * 查询入库明细列表带用户名
     */
    @RequiresPermissions("checkin:checkin:list")
    @GetMapping("/listWithUser")
    public TableDataInfo listWithUser(CrkImDetails crkImDetails) {
        startPage();
        List<CrkImDetails> list = crkImDetailsService.findListWithUser(crkImDetails);
        return getDataTable(list);
    }

    /**
     * 导出入库明细列表
     */
    @RequiresPermissions("checkin:checkin:export")
    @Log(title = "入库明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CrkImDetails crkImDetails) {
        List<CrkImDetails> list = crkImDetailsService.selectCrkImDetailsList(crkImDetails);
        ExcelUtil<CrkImDetails> util = new ExcelUtil<CrkImDetails>(CrkImDetails.class);
        util.exportExcel(response, list, "入库明细数据");
    }

    /**
     * 获取入库明细详细信息
     */
    @RequiresPermissions("checkin:checkin:query")
    @GetMapping(value = "/{imdId}")
    public AjaxResult getInfo(@PathVariable("imdId") Long imdId) {
        return success(crkImDetailsService.selectCrkImDetailsByImdId(imdId));
    }

    /**
     * 新增入库明细
     */
    @RequiresPermissions("checkin:checkin:add")
    @Log(title = "入库明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CrkImDetails crkImDetails) {
        return toAjax(crkImDetailsService.insertCrkImDetails(crkImDetails));
    }

    /**
     * 修改入库明细
     */
    @RequiresPermissions("checkin:checkin:edit")
    @Log(title = "入库明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CrkImDetails crkImDetails) {
        return toAjax(crkImDetailsService.updateCrkImDetails(crkImDetails));
    }

    /**
     * 删除入库明细
     */
    @RequiresPermissions("checkin:checkin:remove")
    @Log(title = "入库明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{imdIds}")
    public AjaxResult remove(@PathVariable Long[] imdIds) {
        return toAjax(crkImDetailsService.deleteCrkImDetailsByImdIds(imdIds));
    }

    /**
     * 入库明细订货总数
     *
     * @return 入库明细订货总数
     */
    @GetMapping("/count")
    @Log(title = "入库明细总数", businessType = BusinessType.OTHER)
    public AjaxResult findCheckInNumber() {
        Integer res = crkImDetailsService.findCheckInNumber();
        if (res > 0) {
            return success(res);
        } else {
            return error("查询失败");
        }
    }

    /**
     * @param TodayOrYesterday 今日/昨日
     * @param BeginDay         开始日
     * @param EndDay           结束日
     * @return 按时间段进货列表
     */
    @GetMapping("/findInfoByDate")
    @Log(title = "入库明细总数", businessType = BusinessType.OTHER)
    public AjaxResult findInfoByDate(@Param("TodayOrYesterday") String TodayOrYesterday, @Param("BeginDay") String BeginDay, @Param("EndDay") String EndDay) {
        try {
            List<CrkImDetails> list = crkImDetailsService.findInfoByDate(TodayOrYesterday, BeginDay, EndDay);
            //System.out.println(list);'
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

    /**
     * 查询库存货品数总金额
     *
     * @return 查询库存货品数总金额
     */
    @GetMapping("/findCountValue")
    public AjaxResult findCountValue(@RequestParam(required = false) Integer wId) {
        return AjaxResult.success(stockValuesService.findCountValue(wId));
    }
}
