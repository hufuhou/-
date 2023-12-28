package com.cx.crkgl.service.impl;

import java.util.List;

import com.cx.crkgl.domain.*;
import com.cx.crkgl.unit.NumberGenerator;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.cx.crkgl.mapper.CrkOutManagementMapper;
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
        List<CrkOutDetails> crkOutDetailsList = crkOutManagement.getCrkOutDetailsList();
        for (CrkOutDetails crkOutDetails : crkOutDetailsList) {
            crkOutManagementMapper.InventoryOutbound(crkOutDetails);
        }
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
        crkOutManagement.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        List<CrkOutDetails> crkOutDetailsList=crkOutManagementMapper.selectCrkOutDetails2(crkOutManagement.getOutId());
        for (CrkOutDetails crkOutDetails:crkOutDetailsList){
            crkOutManagementMapper.InventoryOutbound1(crkOutDetails);
        }
        crkOutManagementMapper.deleteCrkOutDetailsByOutId(crkOutManagement.getOutId());
        insertCrkOutDetails(crkOutManagement);
        List<CrkOutDetails> crkOutDetailsList1 = crkOutManagement.getCrkOutDetailsList();
        for (CrkOutDetails crkOutDetails : crkOutDetailsList1) {
            crkOutManagementMapper.InventoryOutbound(crkOutDetails);
        }
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
        List<CrkOutDetails> crkOutDetailsList= crkOutManagementMapper.selectCrkOutDetails(outIds);
        for (CrkOutDetails crkOutDetails:crkOutDetailsList){
            crkOutManagementMapper.InventoryOutbound2(crkOutDetails);
        }
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

    @Override
    public List<OrderSalesDetails> selectSalesDetails(OrderSalesDetails orderSalesDetails) {
        return crkOutManagementMapper.selectSalesDetails(orderSalesDetails);
    }

    @Override
    public long currentInventory(Long slId, Long gId) {
        return crkOutManagementMapper.currentInventory(slId,gId);
    }

    @Override
    public List<CrkOutDetails> selectCrkOutDetails(Long[] outId) {
        return crkOutManagementMapper.selectCrkOutDetails(outId);
    }

    @Override
    public List<CrkOutManagement> selectCrkOutManagementList1(List<String> outCodes) {
        return crkOutManagementMapper.selectCrkOutManagementList1(outCodes);
    }
    //审核出库
    @Transactional
    @Override
    public int AuditOutbounds(List<CrkOutManagement> crkOutManagements, List<String> outIds, boolean isApproved) {
        List<CrkOutManagement> list = new ArrayList<CrkOutManagement>();
        for (CrkOutManagement crkOutManagement1 : crkOutManagements)
        {
            crkOutManagement1.setReviewer(SecurityUtils.getUsername());
            crkOutManagement1.setReviewerTime(DateUtils.getNowDate());
            list.add(crkOutManagement1);
        }
        if (list.size() > 0)
        {
            if (isApproved){
                List<CrkOutDetails> crkOutDetailsList= crkOutManagementMapper.selectCrkOutDetail(outIds);
                for (CrkOutDetails crkOutDetails:crkOutDetailsList){
                    crkOutManagementMapper.reduceReviews(crkOutDetails);
                }
                Long cksls= Long.valueOf(0);
                Long wcksls= Long.valueOf(0);
                for (CrkOutDetails crkOutDetails : crkOutDetailsList)
                {
                    cksls+=crkOutDetails.getOutBound();
                    wcksls+=crkOutDetails.getNotShipped();
                }
                String[] sCode = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    CrkOutManagement crkOutManagement = list.get(i);
                    sCode[i] = crkOutManagement.getOrderId();
                }
                if (cksls == wcksls){
                    crkOutManagementMapper.saleStatus(sCode);
                }else {
                    crkOutManagementMapper.saleStatus1(sCode);
                }

            }
            int row=0;
            for (CrkOutManagement crkOutManagement:list){
                row+= crkOutManagementMapper.AuditOutbounds(crkOutManagement,isApproved);
            }
            return  row;
        }
        return 0;
    }
/*
* 撤销出库
* */
    @Override
    @Transactional
    public int WithdrawalStorages(List<String> outIds) {
        List<CrkOutDetails> crkOutDetailsList= crkOutManagementMapper.selectCrkOutDetail(outIds);
        List<CrkOutManagement> list=crkOutManagementMapper.selectCrkOutManagementList2(outIds);
        String[] sCode = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            CrkOutManagement crkOutManagement = list.get(i);
            sCode[i] = crkOutManagement.getOrderId();
        }
        crkOutManagementMapper.saleStatus2(sCode);
        for (CrkOutDetails crkOutDetails:crkOutDetailsList){
            crkOutManagementMapper.productReviews(crkOutDetails);
        }
        return crkOutManagementMapper.WithdrawalStorages(outIds);
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
                crkOutDetails.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
                crkOutDetails.setCreateTime(DateUtils.getNowDate());
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
