package com.cx.crkgl.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 入库管理对象 crk_inbound_management
 * 
 * @author hfh
 * @date 2023-12-01
 */
public class CrkInboundManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入库ID */
    private Long inId;

    /** 入库单号 */
    @Excel(name = "入库单号")
    private String inCode;

    /** 入库类型 */
    @Excel(name = "入库类型")
    private String inType;

    /** 仓库 ID */
    @Excel(name = "仓库 ID")
    private Long wId;

    /** 单据状态 */
    @Excel(name = "单据状态")
    private Integer status;

    /** 供应商 ID */
    @Excel(name = "供应商 ID")
    private Long sId;

    /** 联系人 */
    @Excel(name = "联系人")
    private String personId;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactDetails;

    /** 是否关联 */
    @Excel(name = "是否关联")
    private boolean iS;

    /** 入库经办人 */
    @Excel(name = "入库经办人")
    private Long wManager;

    /** 审核人 */
    @Excel(name = "审核人")
    private String reviewer;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewerTime;

    /** 入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date storageDate;

    /** 进货退货明细 ID 或销售订单 ID（相关订单 ID） */
    @Excel(name = "进货退货明细 ID 或销售订单 ID", readConverterExp = "相=关订单,I=D")
    private String orderId;

    /** 0：存在；1：已删除，不存在 */
    private Integer isDelte;

    /** 入库明细信息 */
    private List<CrkImDetails> crkImDetailsList;

    /** 库存 */
    private List<TzStock> tzStockList;

    public List<TzStock> getTzStockList() {
        return tzStockList;
    }

    public void setTzStockList(List<TzStock> tzStockList) {
        this.tzStockList = tzStockList;
    }

    public void setInId(Long inId)
    {
        this.inId = inId;
    }

    public Long getInId() 
    {
        return inId;
    }
    public void setInCode(String inCode) 
    {
        this.inCode = inCode;
    }

    public String getInCode() 
    {
        return inCode;
    }
    public void setInType(String inType) 
    {
        this.inType = inType;
    }

    public String getInType() 
    {
        return inType;
    }
    public void setwId(Long wId) 
    {
        this.wId = wId;
    }

    public Long getwId() 
    {
        return wId;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setsId(Long sId) 
    {
        this.sId = sId;
    }

    public Long getsId() 
    {
        return sId;
    }
    public void setPersonId(String personId)
    {
        this.personId = personId;
    }

    public String getPersonId()
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

    public void setwManager(Long wManager) 
    {
        this.wManager = wManager;
    }

    public Long getwManager() 
    {
        return wManager;
    }
    public void setReviewer(String reviewer) 
    {
        this.reviewer = reviewer;
    }

    public String getReviewer() 
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
    public void setStorageDate(Date storageDate) 
    {
        this.storageDate = storageDate;
    }

    public Date getStorageDate() 
    {
        return storageDate;
    }

    public boolean isiS() {
        return iS;
    }

    public void setiS(boolean iS) {
        this.iS = iS;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setIsDelte(Integer isDelte)
    {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() 
    {
        return isDelte;
    }

    public List<CrkImDetails> getCrkImDetailsList()
    {
        return crkImDetailsList;
    }

    public void setCrkImDetailsList(List<CrkImDetails> crkImDetailsList)
    {
        this.crkImDetailsList = crkImDetailsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("inId", getInId())
            .append("inCode", getInCode())
            .append("inType", getInType())
            .append("wId", getwId())
            .append("status", getStatus())
            .append("sId", getsId())
            .append("personId", getPersonId())
            .append("contactDetails", getContactDetails())
            .append("iS", isiS())
            .append("wManager", getwManager())
            .append("remark", getRemark())
            .append("reviewer", getReviewer())
            .append("reviewerTime", getReviewerTime())
            .append("storageDate", getStorageDate())
            .append("orderId", getOrderId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelte", getIsDelte())
            .append("crkImDetailsList", getCrkImDetailsList())
            .append("tzStockList",getTzStockList())
            .toString();
    }
}
