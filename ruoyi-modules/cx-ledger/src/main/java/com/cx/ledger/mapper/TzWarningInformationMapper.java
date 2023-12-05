package com.cx.ledger.mapper;

import java.util.List;
import com.cx.ledger.domain.TzWarningInformation;

/**
 * 库存预警Mapper接口
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
public interface TzWarningInformationMapper 
{
    /**
     * 查询库存预警
     * 
     * @param alertId 库存预警主键
     * @return 库存预警
     */
    public TzWarningInformation selectTzWarningInformationByAlertId(Long alertId);

    /**
     * 查询库存预警列表
     * 
     * @param tzWarningInformation 库存预警
     * @return 库存预警集合
     */
    public List<TzWarningInformation> selectTzWarningInformationList(TzWarningInformation tzWarningInformation);

    /**
     * 新增库存预警
     * 
     * @param tzWarningInformation 库存预警
     * @return 结果
     */
    public int insertTzWarningInformation(TzWarningInformation tzWarningInformation);

    /**
     * 修改库存预警
     * 
     * @param tzWarningInformation 库存预警
     * @return 结果
     */
    public int updateTzWarningInformation(TzWarningInformation tzWarningInformation);

    /**
     * 删除库存预警
     * 
     * @param alertId 库存预警主键
     * @return 结果
     */
    public int deleteTzWarningInformationByAlertId(Long alertId);

    /**
     * 批量删除库存预警
     * 
     * @param alertIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTzWarningInformationByAlertIds(Long[] alertIds);
}
