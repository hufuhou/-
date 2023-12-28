package com.cx.crkgl.service.impl;

import java.util.Arrays;
import java.util.List;

import com.cx.crkgl.domain.OrderPurchaseDetails;
import com.cx.crkgl.domain.TzStock;
import com.cx.crkgl.unit.NumberGenerator;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;

import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.cx.crkgl.domain.CrkImDetails;
import com.cx.crkgl.mapper.CrkInboundManagementMapper;
import com.cx.crkgl.domain.CrkInboundManagement;
import com.cx.crkgl.service.ICrkInboundManagementService;

/**
 * 入库管理Service业务层处理
 * 
 * @author hfh
 * @date 2023-12-01
 */
@Service
public class CrkInboundManagementServiceImpl implements ICrkInboundManagementService 
{
    @Autowired
    private CrkInboundManagementMapper crkInboundManagementMapper;

    /**
     * 查询入库管理
     * 
     * @param inId 入库管理主键
     * @return 入库管理
     */
    @Override
    public CrkInboundManagement selectCrkInboundManagementByInId(Long inId)
    {
        return crkInboundManagementMapper.selectCrkInboundManagementByInId(inId);
    }

    @Override
    public long InventoryQuantity(String gCode,String odId) {
        return crkInboundManagementMapper.InventoryQuantity(gCode,odId);
    }

    /**
     * 查询进货明细
     *
     */
    @Override
    public List<OrderPurchaseDetails> selectInventoryDetails(OrderPurchaseDetails orderPurchaseDetails) {
        return crkInboundManagementMapper.selectInventoryDetails( orderPurchaseDetails);
    }

    /**
     * 查询入库管理列表
     * 
     * @param crkInboundManagement 入库管理
     * @return 入库管理
     */
    @Override
    public List<CrkInboundManagement> selectCrkInboundManagementList(CrkInboundManagement crkInboundManagement)
    {
        return crkInboundManagementMapper.selectCrkInboundManagementList(crkInboundManagement);
    }

    @Override
    public List<CrkImDetails> selectCrkImDetails(Long[] inId) {
        return crkInboundManagementMapper.selectCrkImDetails(inId);
    }

    @Override
    public List<CrkInboundManagement> selectCrkInboundManagementList1(List<String> inCodes) {
        return crkInboundManagementMapper.selectCrkInboundManagementList1(inCodes);
    }

    @Override
    public CrkInboundManagement getCrkInboundManagementBySlCode() {
        return crkInboundManagementMapper.getCrkInboundManagementBySlCode();
    }

    /**
     * 新增入库管理
     * 
     * @param crkInboundManagement 入库管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertCrkInboundManagement(CrkInboundManagement crkInboundManagement)
    {
        crkInboundManagement.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
        crkInboundManagement.setCreateTime(DateUtils.getNowDate());
        NumberGenerator num=new NumberGenerator("RK");
        CrkInboundManagement crkInboundManagement1=crkInboundManagementMapper.getCrkInboundManagementBySlCode();
        String code=null;
        if (crkInboundManagement1!=null){
            code=crkInboundManagement1.getInCode();
        }
        crkInboundManagement.setInCode(String.valueOf(num.generateNumber(code)));
        int rows = crkInboundManagementMapper.insertCrkInboundManagement(crkInboundManagement);
        insertCrkImDetails(crkInboundManagement);
        insertTzStock(crkInboundManagement);
        return rows;
    }

    /**
     * 修改入库管理
     * 
     * @param crkInboundManagement 入库管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateCrkInboundManagement(CrkInboundManagement crkInboundManagement)
    {
        crkInboundManagement.setUpdateTime(DateUtils.getNowDate());
        crkInboundManagement.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
        crkInboundManagementMapper.deleteCrkImDetailsByInId(crkInboundManagement.getInId());
        insertCrkImDetails(crkInboundManagement);
        insertTzStock(crkInboundManagement);
        return crkInboundManagementMapper.updateCrkInboundManagement(crkInboundManagement);
    }

    /**
     * 批量删除入库管理
     * 
     * @param inIds 需要删除的入库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteCrkInboundManagementByInIds(Long[] inIds)
    {
        crkInboundManagementMapper.deleteCrkImDetailsByInIds(inIds);
        return crkInboundManagementMapper.deleteCrkInboundManagementByInIds(inIds);
    }

    /**
     * 删除入库管理信息
     * 
     * @param inId 入库管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteCrkInboundManagementByInId(Long inId)
    {
        crkInboundManagementMapper.deleteCrkImDetailsByInId(inId);
        return crkInboundManagementMapper.deleteCrkInboundManagementByInId(inId);
    }
    /**
     * 审核入库
     *
     * @param  crkInboundManagement 入库管理主键
     * @return 结果
     */
    @Override
    public int InventoryReview(CrkInboundManagement crkInboundManagement, boolean isApproved) {

        crkInboundManagement.setReviewer(SecurityUtils.getUsername());
        crkInboundManagement.setReviewerTime(DateUtils.getNowDate());
        return crkInboundManagementMapper.InventoryReview(crkInboundManagement,isApproved);
    }
//审核入库
    @Override
    @Transactional
    public int InventoryReviews(List<CrkInboundManagement> crkInboundManagement,List<String> inIds, boolean isApproved) {
        List<CrkInboundManagement> list = new ArrayList<CrkInboundManagement>();
        for (CrkInboundManagement crkInboundManagement1 : crkInboundManagement)
        {
            crkInboundManagement1.setReviewer(SecurityUtils.getUsername());
            crkInboundManagement1.setReviewerTime(DateUtils.getNowDate());
            list.add(crkInboundManagement1);
        }
        if (list.size() > 0)
        {
            if (isApproved){
              List<CrkImDetails> crkImDetailsList= crkInboundManagementMapper.selectCrkImDetail(inIds);
              for (CrkImDetails crkImDetails:crkImDetailsList){
                  crkInboundManagementMapper.productReviews(crkImDetails);
              }
                Long rksls= Long.valueOf(0);
                Long wrksls= Long.valueOf(0);
                for (CrkImDetails crkImDetails : crkImDetailsList)
                {
                    rksls+=crkImDetails.getQuantityInStock();
                    wrksls+=crkImDetails.getUnstockedQuantity();
                }
                String[] poCd = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    CrkInboundManagement crkInboundManagement1 = list.get(i);
                    poCd[i] = crkInboundManagement1.getOrderId();
                }
                if (rksls == wrksls){
                    crkInboundManagementMapper.purchasingStatus(poCd);
                }else {
                    crkInboundManagementMapper.purchasingStatus1(poCd);
                }

            }
            int row=0;
            for (CrkInboundManagement crkInboundManagement1:list){
                row+=crkInboundManagementMapper.InventoryReviews(crkInboundManagement1,isApproved);
            }
            return  row;
        }
        return 0;
    }
    /**
     * 撤销入库
     *
     * @param  inId 撤销管理主键
     * @return 结果
     */
    @Override
    public int WithdrawalStorage(Long inId) {
        return crkInboundManagementMapper.WithdrawalStorage(inId);
    }

    @Override
    @Transactional
    public int WithdrawalStorages(List<String> inIds) {
        List<CrkImDetails> crkImDetailsList= crkInboundManagementMapper.selectCrkImDetail(inIds);
        List<CrkInboundManagement> list=crkInboundManagementMapper.selectCrkInboundManagementList2(inIds);
        String[] poCd = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            CrkInboundManagement crkInboundManagement1 = list.get(i);
            poCd[i] = crkInboundManagement1.getOrderId();
        }
        crkInboundManagementMapper.purchasingStatus2(poCd);
        for (CrkImDetails crkImDetails:crkImDetailsList){
            crkInboundManagementMapper.reduceReviews(crkImDetails);
        }
        return crkInboundManagementMapper.WithdrawalStorages(inIds);
    }

    /**
     * 新增入库明细信息
     * 
     * @param crkInboundManagement 入库管理对象
     */
    public void insertCrkImDetails(CrkInboundManagement crkInboundManagement)
    {
        List<CrkImDetails> crkImDetailsList = crkInboundManagement.getCrkImDetailsList();
        Long inId = crkInboundManagement.getInId();
        if (StringUtils.isNotNull(crkImDetailsList))
        {
            List<CrkImDetails> list = new ArrayList<CrkImDetails>();
            for (CrkImDetails crkImDetails : crkImDetailsList)
            {
                crkImDetails.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
                crkImDetails.setCreateTime(DateUtils.getNowDate());
                crkImDetails.setInId(inId);
                list.add(crkImDetails);
            }
            if (list.size() > 0)
            {
                crkInboundManagementMapper.batchCrkImDetails(list);
            }
        }
    }
    /**
     * 新增库存入库
     *
     * @param crkInboundManagement 入库管理对象
     */
    public void insertTzStock(CrkInboundManagement crkInboundManagement)
    {
        List<TzStock> tzStocksList = crkInboundManagement.getTzStockList();
        if (StringUtils.isNotNull(tzStocksList))
        {
            List<TzStock> list = new ArrayList<TzStock>();
            for (TzStock tzStock : tzStocksList)
            {
                tzStock.setCreateBys(SecurityUtils.getUserId());
                tzStock.setCreateTime(DateUtils.getNowDate());
                crkInboundManagementMapper.addInventory(tzStock);
            }
        }
    }
}
