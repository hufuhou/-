package com.cx.ckgl.service.impl;

import java.util.List;

import com.cx.ckgl.unit.NumberGenerator;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.ckgl.mapper.WarehouseMapper;
import com.cx.ckgl.domain.Warehouse;
import com.cx.ckgl.service.IWarehouseService;

/**
 * 仓库管理Service业务层处理
 * 
 * @author hfh
 * @date 2023-11-24
 */
@Service
public class WarehouseServiceImpl implements IWarehouseService 
{
    @Autowired
    private WarehouseMapper warehouseMapper;

    /**
     * 查询仓库管理
     * 
     * @param wId 仓库管理主键
     * @return 仓库管理
     */
    @Override
    public Warehouse selectWarehouseByWId(Long wId)
    {
        return warehouseMapper.selectWarehouseByWId(wId);
    }

    /**
     * 查询仓库管理列表
     * 
     * @param warehouse 仓库管理
     * @return 仓库管理
     */
    @Override
    public List<Warehouse> selectWarehouseList(Warehouse warehouse)
    {
        return warehouseMapper.selectWarehouseList(warehouse);
    }

    @Override
    public Warehouse getWarehouseByWCode() {
        return warehouseMapper.getWarehouseByWCode();
    }

    /**
     * 新增仓库管理
     * 
     * @param warehouse 仓库管理
     * @return 结果
     */
    @Override
    public int insertWarehouse(Warehouse warehouse)
    {
        warehouse.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        warehouse.setCreateTime(DateUtils.getNowDate());
        NumberGenerator num=new NumberGenerator("CKGL");
        Warehouse warehouse1=warehouseMapper.getWarehouseByWCode();
        String code=null;
        if (warehouse1!=null){
             code=warehouse1.getwCode();
        }
        warehouse.setwCode(String.valueOf(num.generateNumber(code)));
        return warehouseMapper.insertWarehouse(warehouse);
    }

    /**
     * 修改仓库管理
     * 
     * @param warehouse 仓库管理
     * @return 结果
     */
    @Override
    public int updateWarehouse(Warehouse warehouse)
    {
        warehouse.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        warehouse.setUpdateTime(DateUtils.getNowDate());
        return warehouseMapper.updateWarehouse(warehouse);
    }

    /**
     * 批量删除仓库管理
     * 
     * @param wIds 需要删除的仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseByWIds(Long[] wIds)
    {
        return warehouseMapper.deleteWarehouseByWIds(wIds);
    }

    /**
     * 删除仓库管理信息
     * 
     * @param wId 仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseByWId(Long wId)
    {
        return warehouseMapper.deleteWarehouseByWId(wId);
    }
}
