package com.cx.crkgl.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 库存调拨对象 crk_transfer
 * 
 * @author ruoyi
 * @date 2023-12-13
 */
public class CrkTransfer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 调拨ID */
    private Long tId;

    /** 调拨单号 */
    @Excel(name = "调拨单号")
    private String tdCode;

    /** 单据状态 */
    @Excel(name = "单据状态")
    private Integer docStatus;

    /** 调拨申请日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "调拨申请日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tDate;

    /** 调出仓库 ID */
    @Excel(name = "调出仓库 ID")
    private String outWId;

    /** 调入仓库 */
    @Excel(name = "调入仓库")
    private String inWId;

    /** 出库状态 */
    @Excel(name = "出库状态")
    private Integer outStatus;

    /** 数据字典（调拨类型） */
    @Excel(name = "数据字典", readConverterExp = "调=拨类型")
    private Integer tType;

    /** 入库状态 */
    @Excel(name = "入库状态")
    private Integer inStatus;

    /** 外键，关联用户表（调拨经办人） */
    @Excel(name = "外键，关联用户表", readConverterExp = "调=拨经办人")
    private String tManager;

    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Integer isDelte;

    /** 调拨明细信息 */
    private List<CrkTransferDetails> crkTransferDetailsList;

    public void settId(Long tId) 
    {
        this.tId = tId;
    }

    public Long gettId() 
    {
        return tId;
    }
    public void setTdCode(String tdCode) 
    {
        this.tdCode = tdCode;
    }

    public String getTdCode() 
    {
        return tdCode;
    }
    public void setDocStatus(Integer docStatus) 
    {
        this.docStatus = docStatus;
    }

    public Integer getDocStatus() 
    {
        return docStatus;
    }
    public void settDate(Date tDate) 
    {
        this.tDate = tDate;
    }

    public Date gettDate() 
    {
        return tDate;
    }
    public void setOutWId(String outWId) 
    {
        this.outWId = outWId;
    }

    public String getOutWId() 
    {
        return outWId;
    }
    public void setInWId(String inWId) 
    {
        this.inWId = inWId;
    }

    public String getInWId() 
    {
        return inWId;
    }
    public void setOutStatus(Integer outStatus) 
    {
        this.outStatus = outStatus;
    }

    public Integer getOutStatus() 
    {
        return outStatus;
    }
    public void settType(Integer tType) 
    {
        this.tType = tType;
    }

    public Integer gettType() 
    {
        return tType;
    }
    public void setInStatus(Integer inStatus) 
    {
        this.inStatus = inStatus;
    }

    public Integer getInStatus() 
    {
        return inStatus;
    }
    public void settManager(String tManager) 
    {
        this.tManager = tManager;
    }

    public String gettManager() 
    {
        return tManager;
    }
    public void setIsDelte(Integer isDelte) 
    {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() 
    {
        return isDelte;
    }

    public List<CrkTransferDetails> getCrkTransferDetailsList()
    {
        return crkTransferDetailsList;
    }

    public void setCrkTransferDetailsList(List<CrkTransferDetails> crkTransferDetailsList)
    {
        this.crkTransferDetailsList = crkTransferDetailsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tId", gettId())
            .append("tdCode", getTdCode())
            .append("docStatus", getDocStatus())
            .append("tDate", gettDate())
            .append("outWId", getOutWId())
            .append("inWId", getInWId())
            .append("outStatus", getOutStatus())
            .append("tType", gettType())
            .append("remark", getRemark())
            .append("inStatus", getInStatus())
            .append("tManager", gettManager())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelte", getIsDelte())
            .append("crkTransferDetailsList", getCrkTransferDetailsList())
            .toString();
    }
}
