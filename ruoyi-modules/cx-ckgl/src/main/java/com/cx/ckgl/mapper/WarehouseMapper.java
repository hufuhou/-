package com.cx.ckgl.mapper;

import java.util.List;
import com.cx.ckgl.domain.Warehouse;

/**
 * 仓库管理Mapper接口
 * 
 * @author hfh
 * @date 2023-11-24
 */
public interface WarehouseMapper 
{
    /**
     * 查询仓库管理
     * 
     * @param wId 仓库管理主键
     * @return 仓库管理
     */
    public Warehouse selectWarehouseByWId(Long wId);

    /**
     * 查询仓库管理列表
     * 
     * @param warehouse 仓库管理
     * @return 仓库管理集合
     */
    public List<Warehouse> selectWarehouseList(Warehouse warehouse);
    /**
     * 获取最新的仓库编码
     * @return
     */
    public Warehouse getWarehouseByWCode();
    /**
     * 新增仓库管理
     * 
     * @param warehouse 仓库管理
     * @return 结果
     */
    public int insertWarehouse(Warehouse warehouse);

    /**
     * 修改仓库管理
     * 
     * @param warehouse 仓库管理
     * @return 结果
     */
    public int updateWarehouse(Warehouse warehouse);

    /**
     * 删除仓库管理
     * 
     * @param wId 仓库管理主键
     * @return 结果
     */
    public int deleteWarehouseByWId(Long wId);

    /**
     * 批量删除仓库管理
     * 
     * @param wIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseByWIds(Long[] wIds);
}
