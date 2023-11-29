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
import com.cx.hpxx.domain.HpGoodsType;
import com.cx.hpxx.service.IHpGoodsTypeService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.utils.poi.ExcelUtil;

/**
 * 货品类型Controller
 * 
 * @author hfh
 * @date 2023-11-27
 */
@RestController
@RequestMapping("/type")
public class HpGoodsTypeController extends BaseController
{
    @Autowired
    private IHpGoodsTypeService hpGoodsTypeService;

    /**
     * 查询货品类型列表
     */
    @RequiresPermissions("cx-hpxx:type:list")
    @GetMapping("/list")
    public AjaxResult list(HpGoodsType hpGoodsType)
    {
        List<HpGoodsType> list = hpGoodsTypeService.selectHpGoodsTypeList(hpGoodsType);
        return success(list);
    }

    /**
     * 导出货品类型列表
     */
    @RequiresPermissions("cx-hpxx:type:export")
    @Log(title = "货品类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HpGoodsType hpGoodsType)
    {
        List<HpGoodsType> list = hpGoodsTypeService.selectHpGoodsTypeList(hpGoodsType);
        ExcelUtil<HpGoodsType> util = new ExcelUtil<HpGoodsType>(HpGoodsType.class);
        util.exportExcel(response, list, "货品类型数据");
    }

    /**
     * 获取货品类型详细信息
     */
    @RequiresPermissions("cx-hpxx:type:query")
    @GetMapping(value = "/{gtId}")
    public AjaxResult getInfo(@PathVariable("gtId") Long gtId)
    {
        return success(hpGoodsTypeService.selectHpGoodsTypeByGtId(gtId));
    }

    /**
     * 新增货品类型
     */
    @RequiresPermissions("cx-hpxx:type:add")
    @Log(title = "货品类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HpGoodsType hpGoodsType)
    {
        return toAjax(hpGoodsTypeService.insertHpGoodsType(hpGoodsType));
    }

    /**
     * 修改货品类型
     */
    @RequiresPermissions("cx-hpxx:type:edit")
    @Log(title = "货品类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HpGoodsType hpGoodsType)
    {
        return toAjax(hpGoodsTypeService.updateHpGoodsType(hpGoodsType));
    }

    /**
     * 删除货品类型
     */
    @RequiresPermissions("cx-hpxx:type:remove")
    @Log(title = "货品类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{gtIds}")
    public AjaxResult remove(@PathVariable Long[] gtIds)
    {
        return toAjax(hpGoodsTypeService.deleteHpGoodsTypeByGtIds(gtIds));
    }
}
