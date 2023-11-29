package com.cx.ckgl.service.impl;

import java.util.List;

import com.cx.ckgl.unit.NumberGenerator;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.ckgl.mapper.TjStorageLocationMapper;
import com.cx.ckgl.domain.TjStorageLocation;
import com.cx.ckgl.service.ITjStorageLocationService;

/**
 * 仓库详情Service业务层处理
 * 
 * @author hfh
 * @date 2023-11-29
 */
@Service
public class TjStorageLocationServiceImpl implements ITjStorageLocationService 
{
    @Autowired
    private TjStorageLocationMapper tjStorageLocationMapper;

    /**
     * 查询仓库详情
     * 
     * @param slId 仓库详情主键
     * @return 仓库详情
     */
    @Override
    public TjStorageLocation selectTjStorageLocationBySlId(Long slId)
    {
        return tjStorageLocationMapper.selectTjStorageLocationBySlId(slId);
    }

    /**
     * 查询仓库详情列表
     * 
     * @param tjStorageLocation 仓库详情
     * @return 仓库详情
     */
    @Override
    public List<TjStorageLocation> selectTjStorageLocationList(TjStorageLocation tjStorageLocation)
    {
        return tjStorageLocationMapper.selectTjStorageLocationList(tjStorageLocation);
    }

    @Override
    public TjStorageLocation getTjStorageLocationBySlCode() {
        return tjStorageLocationMapper.getTjStorageLocationBySlCode();
    }

    /**
     * 新增仓库详情
     * 
     * @param tjStorageLocation 仓库详情
     * @return 结果
     */
    @Override
    public int insertTjStorageLocation(TjStorageLocation tjStorageLocation)
    {
        tjStorageLocation.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        tjStorageLocation.setCreateTime(DateUtils.getNowDate());
        NumberGenerator num=new NumberGenerator("KWXX");
        TjStorageLocation tjStorageLocation1=tjStorageLocationMapper.getTjStorageLocationBySlCode();
        String code=null;
        if (tjStorageLocation1!=null){
            code=tjStorageLocation1.getSlCode();
        }
        tjStorageLocation.setSlCode(String.valueOf(num.generateNumber(code)));
        return tjStorageLocationMapper.insertTjStorageLocation(tjStorageLocation);
    }

    /**
     * 修改仓库详情
     * 
     * @param tjStorageLocation 仓库详情
     * @return 结果
     */
    @Override
    public int updateTjStorageLocation(TjStorageLocation tjStorageLocation)
    {
        tjStorageLocation.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        tjStorageLocation.setUpdateTime(DateUtils.getNowDate());
        return tjStorageLocationMapper.updateTjStorageLocation(tjStorageLocation);
    }

    /**
     * 批量删除仓库详情
     * 
     * @param slIds 需要删除的仓库详情主键
     * @return 结果
     */
    @Override
    public int deleteTjStorageLocationBySlIds(Long[] slIds)
    {
        return tjStorageLocationMapper.deleteTjStorageLocationBySlIds(slIds);
    }

    /**
     * 删除仓库详情信息
     * 
     * @param slId 仓库详情主键
     * @return 结果
     */
    @Override
    public int deleteTjStorageLocationBySlId(Long slId)
    {
        return tjStorageLocationMapper.deleteTjStorageLocationBySlId(slId);
    }
}
