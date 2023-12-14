package com.cx.wms.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 盘点明细对象 crk_is_details
 * 
 * @author xrc
 * @date 2023-12-14
 */
public class CrkIsDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 盘点明细ID */
    private Long isdId;

    /** 盘点主表id */
    @Excel(name = "盘点主表id")
    private Long isId;

    /** 盘点单号 */
    @Excel(name = "盘点单号")
    private String isCode;

    /** 进货退货明细 ID 或销售订单 ID(相关订单 ID) */
    @Excel(name = "进货退货明细 ID 或销售订单 ID(相关订单 ID)")
    private Long orderId;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specCode;

    /** 单位 */
    private String unit;

    /** 盘点数量 */
    @Excel(name = "盘点数量")
    private Long countQuantity;

    /** 盈亏数量 */
    @Excel(name = "盈亏数量")
    private Long profitLossQuantity;

    /** 盘点状态 0:无盈亏 1:盘盈 2:盘亏 */
    @Excel(name = "盘点状态 0:无盈亏 1:盘盈 2:盘亏")
    private Integer isStatus;

    /** 盘点金额 */
    @Excel(name = "盘点金额")
    private Long countAmount;

    /** 入库单价 */
    @Excel(name = "入库单价")
    private Long iuPrice;

    /** 0：存在；1：已删除，不存在 */
    private Integer isDelte;

    public void setIsdId(Long isdId) 
    {
        this.isdId = isdId;
    }

    public Long getIsdId() 
    {
        return isdId;
    }
    public void setIsId(Long isId) 
    {
        this.isId = isId;
    }

    public Long getIsId() 
    {
        return isId;
    }
    public void setIsCode(String isCode) 
    {
        this.isCode = isCode;
    }

    public String getIsCode() 
    {
        return isCode;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
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
    public void setCountQuantity(Long countQuantity) 
    {
        this.countQuantity = countQuantity;
    }

    public Long getCountQuantity() 
    {
        return countQuantity;
    }
    public void setProfitLossQuantity(Long profitLossQuantity) 
    {
        this.profitLossQuantity = profitLossQuantity;
    }

    public Long getProfitLossQuantity() 
    {
        return profitLossQuantity;
    }
    public void setIsStatus(Integer isStatus) 
    {
        this.isStatus = isStatus;
    }

    public Integer getIsStatus() 
    {
        return isStatus;
    }
    public void setCountAmount(Long countAmount) 
    {
        this.countAmount = countAmount;
    }

    public Long getCountAmount() 
    {
        return countAmount;
    }
    public void setIuPrice(Long iuPrice) 
    {
        this.iuPrice = iuPrice;
    }

    public Long getIuPrice() 
    {
        return iuPrice;
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
            .append("isdId", getIsdId())
            .append("isId", getIsId())
            .append("isCode", getIsCode())
            .append("orderId", getOrderId())
            .append("specCode", getSpecCode())
            .append("unit", getUnit())
            .append("countQuantity", getCountQuantity())
            .append("profitLossQuantity", getProfitLossQuantity())
            .append("isStatus", getIsStatus())
            .append("countAmount", getCountAmount())
            .append("iuPrice", getIuPrice())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelte", getIsDelte())
            .toString();
    }
}
