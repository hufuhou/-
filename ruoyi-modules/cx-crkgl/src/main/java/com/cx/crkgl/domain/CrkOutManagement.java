package com.cx.crkgl.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 出库管理对象 crk_out_management
 * 
 * @author hfh
 * @date 2023-12-20
 */
public class CrkOutManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 出库 ID */
    private Long outId;

    /** 出库单号 */
    @Excel(name = "出库单号")
    private String outCode;

    /** 出库类型 */
    @Excel(name = "出库类型")
    private String outType;

    /** 出库状态 */
    @Excel(name = "出库状态")
    private Integer outStatus;

    /** 出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outDate;

    /** 关联订单 ID */
    @Excel(name = "关联订单 ID")
    private Long orderId;

    /** 客户 ID */
    @Excel(name = "客户 ID")
    private Long cId;

    /** 联系人 */
    @Excel(name = "联系人")
    private Long personId;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactDetails;

    /** 出库负责人 */
    @Excel(name = "出库负责人")
    private Long outManager;

    /** 出库仓库 Id */
    @Excel(name = "出库仓库 Id")
    private Long wId;

    /** 审核人 */
    @Excel(name = "审核人")
    private Long reviewer;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewerTime;

    /** 0：存在；1：已删除，不存在 */
    private Integer isDelte;

    /** 出库明细
信息 */
    private List<CrkOutDetails> crkOutDetailsList;

    public void setOutId(Long outId) 
    {
        this.outId = outId;
    }

    public Long getOutId() 
    {
        return outId;
    }
    public void setOutCode(String outCode) 
    {
        this.outCode = outCode;
    }

    public String getOutCode() 
    {
        return outCode;
    }
    public void setOutType(String outType) 
    {
        this.outType = outType;
    }

    public String getOutType() 
    {
        return outType;
    }
    public void setOutStatus(Integer outStatus) 
    {
        this.outStatus = outStatus;
    }

    public Integer getOutStatus() 
    {
        return outStatus;
    }
    public void setOutDate(Date outDate) 
    {
        this.outDate = outDate;
    }

    public Date getOutDate() 
    {
        return outDate;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setcId(Long cId) 
    {
        this.cId = cId;
    }

    public Long getcId() 
    {
        return cId;
    }
    public void setPersonId(Long personId) 
    {
        this.personId = personId;
    }

    public Long getPersonId() 
    {
        return personId;
    }
    public void setContactDetails(String contactDetails) 
    {
        this.contactDetails = contactDetails;
    }

    public String getContactDetails() 
    {
        return contactDetails;
    }
    public void setOutManager(Long outManager) 
    {
        this.outManager = outManager;
    }

    public Long getOutManager() 
    {
        return outManager;
    }
    public void setwId(Long wId) 
    {
        this.wId = wId;
    }

    public Long getwId() 
    {
        return wId;
    }
    public void setReviewer(Long reviewer) 
    {
        this.reviewer = reviewer;
    }

    public Long getReviewer() 
    {
        return reviewer;
    }
    public void setReviewerTime(Date reviewerTime) 
    {
        this.reviewerTime = reviewerTime;
    }

    public Date getReviewerTime() 
    {
        return reviewerTime;
    }
    public void setIsDelte(Integer isDelte) 
    {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() 
    {
        return isDelte;
    }

    public List<CrkOutDetails> getCrkOutDetailsList()
    {
        return crkOutDetailsList;
    }

    public void setCrkOutDetailsList(List<CrkOutDetails> crkOutDetailsList)
    {
        this.crkOutDetailsList = crkOutDetailsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("outId", getOutId())
            .append("outCode", getOutCode())
            .append("outType", getOutType())
            .append("outStatus", getOutStatus())
            .append("outDate", getOutDate())
            .append("orderId", getOrderId())
            .append("cId", getcId())
            .append("personId", getPersonId())
            .append("contactDetails", getContactDetails())
            .append("remark", getRemark())
            .append("outManager", getOutManager())
            .append("wId", getwId())
            .append("reviewer", getReviewer())
            .append("reviewerTime", getReviewerTime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelte", getIsDelte())
            .append("crkOutDetailsList", getCrkOutDetailsList())
            .toString();
    }
}
