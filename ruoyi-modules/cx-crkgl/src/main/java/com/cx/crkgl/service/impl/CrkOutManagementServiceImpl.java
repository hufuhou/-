package com.cx.crkgl.service.impl;

import java.util.List;

import com.cx.crkgl.domain.CrkInboundManagement;
import com.cx.crkgl.unit.NumberGenerator;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.cx.crkgl.domain.CrkOutDetails;
import com.cx.crkgl.mapper.CrkOutManagementMapper;
import com.cx.crkgl.domain.CrkOutManagement;
import com.cx.crkgl.service.ICrkOutManagementService;

/**
 * 出库管理Service业务层处理
 * 
 * @author hfh
 * @date 2023-12-20
 */
@Service
public class CrkOutManagementServiceImpl implements ICrkOutManagementService 
{
    @Autowired
    private CrkOutManagementMapper crkOutManagementMapper;

    /**
     * 查询出库管理
     * 
     * @param outId 出库管理主键
     * @return 出库管理
     */
    @Override
    public CrkOutManagement selectCrkOutManagementByOutId(Long outId)
    {
        return crkOutManagementMapper.selectCrkOutManagementByOutId(outId);
    }

    /**
     * 查询出库管理列表
     * 
     * @param crkOutManagement 出库管理
     * @return 出库管理
     */
    @Override
    public List<CrkOutManagement> selectCrkOutManagementList(CrkOutManagement crkOutManagement)
    {
        return crkOutManagementMapper.selectCrkOutManagementList(crkOutManagement);
    }

    /**
     * 新增出库管理
     * 
     * @param crkOutManagement 出库管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertCrkOutManagement(CrkOutManagement crkOutManagement)
    {
        crkOutManagement.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        crkOutManagement.setCreateTime(DateUtils.getNowDate());
        NumberGenerator num=new NumberGenerator("CK");
        CrkOutManagement crkOutManagement1=crkOutManagementMapper.getCrkOutManagementBySlCode();
        String code=null;
        if (crkOutManagement1!=null){
            code=crkOutManagement1.getOutCode();
        }
        crkOutManagement.setOutCode(String.valueOf(num.generateNumber(code)));
        int rows = crkOutManagementMapper.insertCrkOutManagement(crkOutManagement);
        insertCrkOutDetails(crkOutManagement);
        return rows;
    }

    /**
     * 修改出库管理
     * 
     * @param crkOutManagement 出库管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateCrkOutManagement(CrkOutManagement crkOutManagement)
    {
        crkOutManagement.setUpdateTime(DateUtils.getNowDate());
        crkOutManagementMapper.deleteCrkOutDetailsByOutId(crkOutManagement.getOutId());
        insertCrkOutDetails(crkOutManagement);
        return crkOutManagementMapper.updateCrkOutManagement(crkOutManagement);
    }

    /**
     * 批量删除出库管理
     * 
     * @param outIds 需要删除的出库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteCrkOutManagementByOutIds(Long[] outIds)
    {
        crkOutManagementMapper.deleteCrkOutDetailsByOutIds(outIds);
        return crkOutManagementMapper.deleteCrkOutManagementByOutIds(outIds);
    }

    /**
     * 删除出库管理信息
     * 
     * @param outId 出库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteCrkOutManagementByOutId(Long outId)
    {
        crkOutManagementMapper.deleteCrkOutDetailsByOutId(outId);
        return crkOutManagementMapper.deleteCrkOutManagementByOutId(outId);
    }

    /**
     * 新增出库明细
信息
     * 
     * @param crkOutManagement 出库管理对象
     */
    public void insertCrkOutDetails(CrkOutManagement crkOutManagement)
    {
        List<CrkOutDetails> crkOutDetailsList = crkOutManagement.getCrkOutDetailsList();
        Long outId = crkOutManagement.getOutId();
        if (StringUtils.isNotNull(crkOutDetailsList))
        {
            List<CrkOutDetails> list = new ArrayList<CrkOutDetails>();
            for (CrkOutDetails crkOutDetails : crkOutDetailsList)
            {
                crkOutDetails.setOutId(outId);
                list.add(crkOutDetails);
            }
            if (list.size() > 0)
            {
                crkOutManagementMapper.batchCrkOutDetails(list);
            }
        }
    }
}
