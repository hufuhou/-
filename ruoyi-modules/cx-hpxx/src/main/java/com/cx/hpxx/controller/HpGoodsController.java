package com.cx.hpxx.controller;

import java.util.List;
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
import com.cx.hpxx.domain.HpGoods;
import com.cx.hpxx.service.IHpGoodsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 货品信息Controller
 * 
 * @author hfh
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/goods")
public class HpGoodsController extends BaseController
{
    @Autowired
    private IHpGoodsService hpGoodsService;

    /**
     * 查询货品信息列表
     */
    @RequiresPermissions("cx-hpxx:goods:list")
    @GetMapping("/list")
    public TableDataInfo list(HpGoods hpGoods)
    {
        startPage();
        List<HpGoods> list = hpGoodsService.selectHpGoodsList(hpGoods);
        return getDataTable(list);
    }

    /**
     * 导出货品信息列表
     */
    @RequiresPermissions("cx-hpxx:goods:export")
    @Log(title = "货品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HpGoods hpGoods)
    {
        List<HpGoods> list = hpGoodsService.selectHpGoodsList(hpGoods);
        ExcelUtil<HpGoods> util = new ExcelUtil<HpGoods>(HpGoods.class);
        util.exportExcel(response, list, "货品信息数据");
    }

    /**
     * 获取货品信息详细信息
     */
    @RequiresPermissions("cx-hpxx:goods:query")
    @GetMapping(value = "/{gId}")
    public AjaxResult getInfo(@PathVariable("gId") Long gId)
    {
        return success(hpGoodsService.selectHpGoodsByGId(gId));
    }

    /**
     * 新增货品信息
     */
    @RequiresPermissions("cx-hpxx:goods:add")
    @Log(title = "货品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HpGoods hpGoods)
    {
        return toAjax(hpGoodsService.insertHpGoods(hpGoods));
    }

    /**
     * 修改货品信息
     */
    @RequiresPermissions("cx-hpxx:goods:edit")
    @Log(title = "货品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HpGoods hpGoods)
    {
        return toAjax(hpGoodsService.updateHpGoods(hpGoods));
    }

    /**
     * 删除货品信息
     */
    @RequiresPermissions("cx-hpxx:goods:remove")
    @Log(title = "货品信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{gIds}")
    public AjaxResult remove(@PathVariable Long[] gIds)
    {
        return toAjax(hpGoodsService.deleteHpGoodsByGIds(gIds));
    }
}
