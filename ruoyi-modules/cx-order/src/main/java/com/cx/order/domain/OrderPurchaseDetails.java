package com.cx.order.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 进货明细对象 order_purchase_details
 *
 * @author ruoyi
 * @date 2023-12-06
 */
public class OrderPurchaseDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 进货明细单id
     */
    private Long pdId;

    /**
     * 进货单号
     */
    @Excel(name = "进货单号")
    private String poCode;

    /**
     * 货品编码
     */
    @Excel(name = "货品id")
    private String gCode;

    /**
     * 进货数量
     */
    @Excel(name = "进货数量")
    private Long purchaseQuantity;

    /**
     * 总金额
     */
    @Excel(name = "总金额")
    private BigDecimal money;

    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Integer isDelte;

    private HpGoods hpGoods;

    public HpGoods getHpGoods() {
        return hpGoods;
    }

    public void setHpGoods(HpGoods hpGoods) {
        this.hpGoods = hpGoods;
    }

    public void setPdId(Long pdId) {
        this.pdId = pdId;
    }

    public Long getPdId() {
        return pdId;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getPoCode() {
        return poCode;
    }

    public String getgCode() {
        return gCode;
    }

    public void setgCode(String gCode) {
        this.gCode = gCode;
    }

    public void setPurchaseQuantity(Long purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Long getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setIsDelte(Integer isDelte) {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() {
        return isDelte;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("pdId", getPdId())
                .append("poCode", getPoCode())
                .append("gCode", getgCode())
                .append("purchaseQuantity", getPurchaseQuantity())
                .append("money", getMoney())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelte", getIsDelte())
                .toString();
    }
}