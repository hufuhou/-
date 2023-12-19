package com.cx.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 销售明细对象 order_d_sales
 *
 * @author LZA
 * @date 2023-12-19
 */
public class OrderSalesDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 销售明细id */
    private Long sdId;

    /** 退货明细id */
    @Excel(name = "退货明细id")
    private Long srdId;

    /** 销售单号 */
    @Excel(name = "销售单号")
    private String sCode;

    /** 货品编号 */
    @Excel(name = "货品编号")
    private String gCode;

    /** 进货数量 */
    @Excel(name = "进货数量")
    private Long purchaseQuantity;

    /** 已入库 */
    @Excel(name = "已入库")
    private Long aiStock;

    /** 销售单价 */
    @Excel(name = "销售单价")
    private Long suPrice;

    /** 销售数量 */
    @Excel(name = "销售数量")
    private Long salesVolume;

    /** 销售金额 */
    @Excel(name = "销售金额")
    private Long salesAmount;

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
    public void setSrdId(Long srdId)
    {
        this.srdId = srdId;
    }

    public Long getSrdId()
    {
        return srdId;
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
    public void setPurchaseQuantity(Long purchaseQuantity)
    {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Long getPurchaseQuantity()
    {
        return purchaseQuantity;
    }
    public void setAiStock(Long aiStock)
    {
        this.aiStock = aiStock;
    }

    public Long getAiStock()
    {
        return aiStock;
    }
    public void setSuPrice(Long suPrice)
    {
        this.suPrice = suPrice;
    }

    public Long getSuPrice()
    {
        return suPrice;
    }
    public void setSalesVolume(Long salesVolume)
    {
        this.salesVolume = salesVolume;
    }

    public Long getSalesVolume()
    {
        return salesVolume;
    }
    public void setSalesAmount(Long salesAmount)
    {
        this.salesAmount = salesAmount;
    }

    public Long getSalesAmount()
    {
        return salesAmount;
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
                .append("srdId", getSrdId())
                .append("sCode", getsCode())
                .append("gCode", getgCode())
                .append("purchaseQuantity", getPurchaseQuantity())
                .append("aiStock", getAiStock())
                .append("suPrice", getSuPrice())
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
