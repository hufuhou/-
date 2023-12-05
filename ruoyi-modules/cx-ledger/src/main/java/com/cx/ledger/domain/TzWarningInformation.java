package com.cx.ledger.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 库存预警对象 tz_warning_information
 * 
 * @author ruoyi
 * @date 2023-11-23
 */
public class TzWarningInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预警id */
    private Long alertId;

    /** 预警天数 */
    @Excel(name = "预警天数")
    private Long alertDays;

    /** 预警负责人（关联用户表） */
    @Excel(name = "预警负责人", readConverterExp = "关=联用户表")
    private Long alertNotifier;

    /** 是否预警 */
    @Excel(name = "是否预警")
    private Long warning;

    /** 预警信息 */
    @Excel(name = "预警信息")
    private String warningInformation;

    public void setAlertId(Long alertId)
    {
        this.alertId = alertId;
    }

    public Long getAlertId()
    {
        return alertId;
    }
    public void setAlertDays(Long alertDays) 
    {
        this.alertDays = alertDays;
    }

    public Long getAlertDays() 
    {
        return alertDays;
    }
    public void setAlertNotifier(Long alertNotifier) 
    {
        this.alertNotifier = alertNotifier;
    }

    public Long getAlertNotifier() 
    {
        return alertNotifier;
    }
    public void setWarning(Long warning) 
    {
        this.warning = warning;
    }

    public Long getWarning() 
    {
        return warning;
    }
    public void setWarningInformation(String warningInformation) 
    {
        this.warningInformation = warningInformation;
    }

    public String getWarningInformation() 
    {
        return warningInformation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("alertId", getAlertId())
            .append("alertDays", getAlertDays())
            .append("alertNotifier", getAlertNotifier())
            .append("warning", getWarning())
            .append("warningInformation", getWarningInformation())
            .toString();
    }
}
