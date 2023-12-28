package com.cx.ckgl.mapper;

import java.util.List;
import com.cx.ckgl.domain.TjStorageLocation;
import com.cx.ckgl.domain.Warehouse;

/**
 * 仓库详情Mapper接口
 * 
 * @author hfh
 * @date 2023-11-29
 */
public interface TjStorageLocationMapper 
{
    /**
     * 查询仓库详情
     * 
     * @param slId 仓库详情主键
     * @return 仓库详情
     */
    public TjStorageLocation selectTjStorageLocationBySlId(Long slId);

    /**
     * 根据仓库ID查询数据
     * @param wId
     * @return
     */
    public List<TjStorageLocation> selectTjStorageLocationBywId(Long wId);
    /**
     * 查询仓库详情列表
     * 
     * @param tjStorageLocation 仓库详情
     * @return 仓库详情集合
     */
    public List<TjStorageLocation> selectTjStorageLocationList(TjStorageLocation tjStorageLocation);

    /**
     * 获取最新的仓位编码
     * @return
     */
    public TjStorageLocation getTjStorageLocationBySlCode();
    /**
     * 新增仓库详情
     * 
     * @param tjStorageLocation 仓库详情
     * @return 结果
     */
    public int insertTjStorageLocation(TjStorageLocation tjStorageLocation);

    /**
     * 修改仓库详情
     * 
     * @param tjStorageLocation 仓库详情
     * @return 结果
     */
    public int updateTjStorageLocation(TjStorageLocation tjStorageLocation);

    /**
     * 删除仓库详情
     * 
     * @param slId 仓库详情主键
     * @return 结果
     */
    public int deleteTjStorageLocationBySlId(Long slId);

    /**
     * 批量删除仓库详情
     * 
     * @param slIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTjStorageLocationBySlIds(Long[] slIds);
}
