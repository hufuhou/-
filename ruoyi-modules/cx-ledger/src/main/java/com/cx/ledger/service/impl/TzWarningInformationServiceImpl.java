package com.cx.ledger.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cx.ledger.mapper.TzWarningInformationMapper;
import com.cx.ledger.domain.TzWarningInformation;
import com.cx.ledger.service.ITzWarningInformationService;

/**
 * 库存预警Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
@Service
public class TzWarningInformationServiceImpl implements ITzWarningInformationService 
{
    @Autowired
    private TzWarningInformationMapper tzWarningInformationMapper;

    /**
     * 查询库存预警
     * 
     * @param alertId 库存预警主键
     * @return 库存预警
     */
    @Override
    public TzWarningInformation selectTzWarningInformationByAlertId(Long alertId)
    {
        return tzWarningInformationMapper.selectTzWarningInformationByAlertId(alertId);
    }

    /**
     * 查询库存预警列表
     * 
     * @param tzWarningInformation 库存预警
     * @return 库存预警
     */
    @Override
    public List<TzWarningInformation> selectTzWarningInformationList(TzWarningInformation tzWarningInformation)
    {
        return tzWarningInformationMapper.selectTzWarningInformationList(tzWarningInformation);
    }

    /**
     * 新增库存预警
     * 
     * @param tzWarningInformation 库存预警
     * @return 结果
     */
    @Override
    public int insertTzWarningInformation(TzWarningInformation tzWarningInformation)
    {
        return tzWarningInformationMapper.insertTzWarningInformation(tzWarningInformation);
    }

    /**
     * 修改库存预警
     * 
     * @param tzWarningInformation 库存预警
     * @return 结果
     */
    @Override
    public int updateTzWarningInformation(TzWarningInformation tzWarningInformation)
    {
        return tzWarningInformationMapper.updateTzWarningInformation(tzWarningInformation);
    }

    /**
     * 批量删除库存预警
     * 
     * @param alertIds 需要删除的库存预警主键
     * @return 结果
     */
    @Override
    public int deleteTzWarningInformationByAlertIds(Long[] alertIds)
    {
        return tzWarningInformationMapper.deleteTzWarningInformationByAlertIds(alertIds);
    }

    /**
     * 删除库存预警信息
     * 
     * @param alertId 库存预警主键
     * @return 结果
     */
    @Override
    public int deleteTzWarningInformationByAlertId(Long alertId)
    {
        return tzWarningInformationMapper.deleteTzWarningInformationByAlertId(alertId);
    }
}
