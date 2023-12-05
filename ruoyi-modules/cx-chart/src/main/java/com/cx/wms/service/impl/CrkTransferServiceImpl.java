package com.cx.wms.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.wms.mapper.CrkTransferMapper;
import com.cx.wms.domain.CrkTransfer;
import com.cx.wms.service.ICrkTransferService;

/**
 * 调拨明细Service业务层处理
 * 
 * @author 熊睿宸
 * @date 2023-11-28
 */
@Service
public class CrkTransferServiceImpl implements ICrkTransferService 
{
    @Autowired
    private CrkTransferMapper crkTransferMapper;

    /**
     * 查询调拨明细
     * 
     * @param tId 调拨明细主键
     * @return 调拨明细
     */
    @Override
    public CrkTransfer selectCrkTransferByTId(Long tId)
    {
        return crkTransferMapper.selectCrkTransferByTId(tId);
    }

    /**
     * 查询调拨明细列表
     * 
     * @param crkTransfer 调拨明细
     * @return 调拨明细
     */
    @Override
    public List<CrkTransfer> selectCrkTransferList(CrkTransfer crkTransfer)
    {
        return crkTransferMapper.selectCrkTransferList(crkTransfer);
    }

    /**
     * 新增调拨明细
     * 
     * @param crkTransfer 调拨明细
     * @return 结果
     */
    @Override
    public int insertCrkTransfer(CrkTransfer crkTransfer)
    {
        crkTransfer.setCreateTime(DateUtils.getNowDate());
        crkTransfer.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        return crkTransferMapper.insertCrkTransfer(crkTransfer);
    }

    /**
     * 修改调拨明细vvv
     * 
     * @param crkTransfer 调拨明细
     * @return 结果
     */
    @Override
    public int updateCrkTransfer(CrkTransfer crkTransfer)
    {
        crkTransfer.setUpdateTime(DateUtils.getNowDate());
        crkTransfer.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        return crkTransferMapper.updateCrkTransfer(crkTransfer);
    }

    /**
     * 批量删除调拨明细
     * 
     * @param tIds 需要删除的调拨明细主键
     * @return 结果
     */
    @Override
    public int deleteCrkTransferByTIds(Long[] tIds)
    {
        return crkTransferMapper.deleteCrkTransferByTIds(tIds);
    }

    /**
     * 删除调拨明细信息
     * 
     * @param tId 调拨明细主键
     * @return 结果
     */
    @Override
    public int deleteCrkTransferByTId(Long tId)
    {
        return crkTransferMapper.deleteCrkTransferByTId(tId);
    }
}
