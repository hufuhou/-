package com.cx.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 调拨明细对象 crk_transfer_details
 *
 * @author xrc
 * @date 2023-11-29
 */
public class CrkTransferDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 调拨明细ID */
    private Long tdId;

    /** 调拨明细对象 */
    private CrkTransferDetails crkTransferDetails;

    public CrkTransferDetails getCrkTransferDetails() {
        return crkTransferDetails;
    }

    public void setCrkTransferDetails(CrkTransferDetails crkTransferDetails) {
        this.crkTransferDetails = crkTransferDetails;
    }

    /** 主表id */
    @Excel(name = "主表id")
    private Long tId;

    /** 调拨单号 */
    @Excel(name = "调拨单号")
    private String tdCode;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specCode;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 调拨数量 */
    @Excel(name = "调拨数量")
    private Long tQuantity;

    /** 入库单价 */
    @Excel(name = "入库单价")
    private Long iuPrice;

    /** 调拨金额 */
    @Excel(name = "调拨金额")
    private Long tAmount;

    /** 货品 ID */
    @Excel(name = "货品 ID")
    private Long goodsId;

    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Integer isDelte;

    public void setTdId(Long tdId)
    {
        this.tdId = tdId;
    }

    public Long getTdId()
    {
        return tdId;
    }
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
    public void setSpecCode(String specCode)
    {
        this.specCode = specCode;
    }

    public String getSpecCode()
    {
        return specCode;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void settQuantity(Long tQuantity)
    {
        this.tQuantity = tQuantity;
    }

    public Long gettQuantity()
    {
        return tQuantity;
    }
    public void setIuPrice(Long iuPrice)
    {
        this.iuPrice = iuPrice;
    }

    public Long getIuPrice()
    {
        return iuPrice;
    }
    public void settAmount(Long tAmount)
    {
        this.tAmount = tAmount;
    }

    public Long gettAmount()
    {
        return tAmount;
    }
    public void setGoodsId(Long goodsId)
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId()
    {
        return goodsId;
    }
    public void setIsDelte(Integer isDelte)
    {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte()
    {
        return isDelte;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("tdId", getTdId())
                .append("tId", gettId())
                .append("tdCode", getTdCode())
                .append("specCode", getSpecCode())
                .append("unit", getUnit())
                .append("tQuantity", gettQuantity())
                .append("iuPrice", getIuPrice())
                .append("tAmount", gettAmount())
                .append("goodsId", getGoodsId())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelte", getIsDelte())
                .toString();
    }
}
