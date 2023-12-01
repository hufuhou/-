package com.cx.ckgl.service;

import java.util.List;
import com.cx.ckgl.domain.TjStorageLocation;

/**
 * 仓库详情Service接口
 * 
 * @author hfh
 * @date 2023-11-29
 */
public interface ITjStorageLocationService 
{
    /**
     * 查询仓库详情
     * 
     * @param slId 仓库详情主键
     * @return 仓库详情
     */
    public TjStorageLocation selectTjStorageLocationBySlId(Long slId);

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
     * 批量删除仓库详情
     * 
     * @param slIds 需要删除的仓库详情主键集合
     * @return 结果
     */
    public int deleteTjStorageLocationBySlIds(Long[] slIds);

    /**
     * 删除仓库详情信息
     * 
     * @param slId 仓库详情主键
     * @return 结果
     */
    public int deleteTjStorageLocationBySlId(Long slId);
}
