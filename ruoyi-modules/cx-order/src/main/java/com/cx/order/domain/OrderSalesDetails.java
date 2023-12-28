package com.cx.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 销售明细对象 order_d_sales
 *
 * @author LZA
 * @date 2023-12-21
 */
public class OrderSalesDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 销售明细id */
    private Long sdId;

    /** 销售单号 */
    @Excel(name = "销售单号")
    private String sCode;

    /** 货品编号 */
    @Excel(name = "货品编号")
    private String gCode;

    /** 销售数量 */
    @Excel(name = "销售数量")
    private Long salesVolume;

    /** 销售金额 */
    @Excel(name = "销售金额")
    private BigDecimal salesAmount;

    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Integer isDelte;

    private HpGoods hpGoods;

    public HpGoods getHpGoods() {
        return hpGoods;
    }

    public void setHpGoods(HpGoods hpGoods) {
        this.hpGoods = hpGoods;
    }

    public void setSdId(Long sdId)
    {
        this.sdId = sdId;
    }

    public Long getSdId()
    {
        return sdId;
    }
    public void setsCode(String sCode)
    {
        this.sCode = sCode;
    }

    public String getsCode()
    {
        return sCode;
    }
    public void setgCode(String gCode)
    {
        this.gCode = gCode;
    }

    public String getgCode()
    {
        return gCode;
    }
    public void setSalesVolume(Long salesVolume)
    {
        this.salesVolume = salesVolume;
    }

    public Long getSalesVolume()
    {
        return salesVolume;
    }

    public BigDecimal getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(BigDecimal salesAmount) {
        this.salesAmount = salesAmount;
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
                .append("sdId", getSdId())
                .append("sCode", getsCode())
                .append("gCode", getgCode())
                .append("salesVolume", getSalesVolume())
                .append("salesAmount", getSalesAmount())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelte", getIsDelte())
                .toString();
    }
}
