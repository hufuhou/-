package com.cx.ledger.controller;

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
import com.cx.ledger.domain.HpGoods;
import com.cx.ledger.service.IHpGoodsService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.page.TableDataInfo;

/**
 * 保质期查询Controller
 * 
 * @author hfh
 * @date 2023-12-11
 */
@RestController
@RequestMapping("/shelflife")
public class HpGoodsController extends BaseController
{
    @Autowired
    private IHpGoodsService hpGoodsService;

    /**
     * 查询保质期查询列表
     */
    @RequiresPermissions("cx-ledger:shelflife:list")
    @GetMapping("/list")
    public TableDataInfo list(HpGoods hpGoods)
    {
        startPage();
        List<HpGoods> list = hpGoodsService.selectHpGoodsList(hpGoods);
        return getDataTable(list);
    }

    /**
     * 导出保质期查询列表
     */
    @RequiresPermissions("cx-ledger:shelflife:export")
    @Log(title = "保质期查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HpGoods hpGoods)
    {
        List<HpGoods> list = hpGoodsService.selectHpGoodsList(hpGoods);
        ExcelUtil<HpGoods> util = new ExcelUtil<HpGoods>(HpGoods.class);
        util.exportExcel(response, list, "保质期查询数据");
    }

    /**
     * 获取保质期查询详细信息
     */
    @RequiresPermissions("cx-ledger:shelflife:query")
    @GetMapping(value = "/{gId}")
    public AjaxResult getInfo(@PathVariable("gId") Long gId)
    {
        return success(hpGoodsService.selectHpGoodsByGId(gId));
    }

    /**
     * 新增保质期查询
     */
    @RequiresPermissions("cx-ledger:shelflife:add")
    @Log(title = "保质期查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HpGoods hpGoods)
    {
        return toAjax(hpGoodsService.insertHpGoods(hpGoods));
    }

    /**
     * 修改保质期查询
     */
    @RequiresPermissions("cx-ledger:shelflife:edit")
    @Log(title = "保质期查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HpGoods hpGoods)
    {
        return toAjax(hpGoodsService.updateHpGoods(hpGoods));
    }

    /**
     * 删除保质期查询
     */
    @RequiresPermissions("cx-ledger:shelflife:remove")
    @Log(title = "保质期查询", businessType = BusinessType.DELETE)
	@DeleteMapping("/{gIds}")
    public AjaxResult remove(@PathVariable Long[] gIds)
    {
        return toAjax(hpGoodsService.deleteHpGoodsByGIds(gIds));
    }
}
