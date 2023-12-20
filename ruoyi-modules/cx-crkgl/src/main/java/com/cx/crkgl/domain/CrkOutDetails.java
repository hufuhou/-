package com.cx.crkgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 出库明细
对象 crk_out_details
 * 
 * @author hfh
 * @date 2023-12-20
 */
public class CrkOutDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 出库明细ID */
    private Long omId;

    /**  */
    private Long outOutId;

    /** 出库id */
    private Long outId;

    /** 关联单号 */
    @Excel(name = "关联单号")
    private Long orderId;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 物品数量 */
    @Excel(name = "物品数量")
    private Long itemQuantity;

    /** 已发货数量 */
    @Excel(name = "已发货数量")
    private Long quantityShipped;

    /** 未交付数量 */
    @Excel(name = "未交付数量")
    private Long undeliveredQuantity;

    /** 当前产出数量 */
    @Excel(name = "当前产出数量")
    private Long currOutQuantity;

    /** 入库仓位 */
    @Excel(name = "入库仓位")
    private Long slId;
    /** 批号 */
    private Long batchNumber;

    /** 条形码 */
    private String barcode;

    /** 0：存在；1：已删除，不存在 */
    private Integer isDelte;

    public void setOmId(Long omId) 
    {
        this.omId = omId;
    }

    public Long getOmId() 
    {
        return omId;
    }
    public void setOutOutId(Long outOutId) 
    {
        this.outOutId = outOutId;
    }

    public Long getOutOutId() 
    {
        return outOutId;
    }
    public void setOutId(Long outId) 
    {
        this.outId = outId;
    }

    public Long getOutId() 
    {
        return outId;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void setItemQuantity(Long itemQuantity) 
    {
        this.itemQuantity = itemQuantity;
    }

    public Long getItemQuantity() 
    {
        return itemQuantity;
    }
    public void setQuantityShipped(Long quantityShipped) 
    {
        this.quantityShipped = quantityShipped;
    }

    public Long getQuantityShipped() 
    {
        return quantityShipped;
    }
    public void setUndeliveredQuantity(Long undeliveredQuantity) 
    {
        this.undeliveredQuantity = undeliveredQuantity;
    }

    public Long getUndeliveredQuantity() 
    {
        return undeliveredQuantity;
    }
    public void setCurrOutQuantity(Long currOutQuantity) 
    {
        this.currOutQuantity = currOutQuantity;
    }

    public Long getCurrOutQuantity() 
    {
        return currOutQuantity;
    }
    public void setBatchNumber(Long batchNumber) 
    {
        this.batchNumber = batchNumber;
    }

    public Long getBatchNumber() 
    {
        return batchNumber;
    }
    public void setBarcode(String barcode) 
    {
        this.barcode = barcode;
    }

    public String getBarcode() 
    {
        return barcode;
    }
    public void setIsDelte(Integer isDelte) 
    {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() 
    {
        return isDelte;
    }

    public Long getSlId() {
        return slId;
    }

    public void setSlId(Long slId) {
        this.slId = slId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("omId", getOmId())
            .append("outOutId", getOutOutId())
            .append("outId", getOutId())
            .append("orderId", getOrderId())
            .append("unit", getUnit())
            .append("itemQuantity", getItemQuantity())
            .append("quantityShipped", getQuantityShipped())
            .append("undeliveredQuantity", getUndeliveredQuantity())
            .append("currOutQuantity", getCurrOutQuantity())
            .append("batchNumber", getBatchNumber())
            .append("remark", getRemark())
            .append("barcode", getBarcode())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelte", getIsDelte())
            .toString();
    }
}
