package com.cx.wms.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 销售明细对象 order_d_sales
 *
 * @author 熊睿宸
 * @date 2023-11-23
 */
public class OrderDSales extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 销售明细id
     */
    private Long sdId;

    /**
     * 退货明细id
     */
    @Excel(name = "退货明细id")
    private Long srdId;

    /**
     * 销售订单id
     */
    @Excel(name = "销售订单id")
    private Long sId;

    /**
     * 销售单号
     */
    @Excel(name = "销售单号")
    private String sCode;

    /**
     * 货品编号
     */
    @Excel(name = "货品编号")
    private String goodsNumber;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String specCode;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 货品类型
     */
    @Excel(name = "货品类型")
    private String goodsType;

    /**
     * 进货数量
     */
    @Excel(name = "进货数量")
    private Long purchaseQuantity;

    /**
     * 已入库
     */
    @Excel(name = "已入库")
    private Long aiStock;

    /**
     * 销售单价
     */
    @Excel(name = "销售单价")
    private Long suPrice;

    /**
     * 销售金额
     */
    @Excel(name = "销售金额")
    private Long salesAmount;

    /**
     * 销售数量
     */
    @Excel(name = "销售数量")
    private Long salesVolume;

    /**
     * 0：存在；1：已删除，不存在
     */
    private Integer isDelte;

    //创建人用户名
    private String create_user_name;
    //更新人用户名
    private String update_user_name;

    public String getCreate_user_name() {
        return create_user_name;
    }

    public void setCreate_user_name(String create_user_name) {
        this.create_user_name = create_user_name;
    }

    public String getUpdate_user_name() {
        return update_user_name;
    }

    public void setUpdate_user_name(String update_user_name) {
        this.update_user_name = update_user_name;
    }

    public void setSdId(Long sdId) {
        this.sdId = sdId;
    }

    public Long getSdId() {
        return sdId;
    }

    public void setSrdId(Long srdId) {
        this.srdId = srdId;
    }

    public Long getSrdId() {
        return srdId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public Long getsId() {
        return sId;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getsCode() {
        return sCode;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setPurchaseQuantity(Long purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Long getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setAiStock(Long aiStock) {
        this.aiStock = aiStock;
    }

    public Long getAiStock() {
        return aiStock;
    }

    public void setSuPrice(Long suPrice) {
        this.suPrice = suPrice;
    }

    public Long getSuPrice() {
        return suPrice;
    }

    public void setSalesAmount(Long salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Long getSalesAmount() {
        return salesAmount;
    }

    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Long getSalesVolume() {
        return salesVolume;
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
                .append("sdId", getSdId())
                .append("srdId", getSrdId())
                .append("sId", getsId())
                .append("sCode", getsCode())
                .append("goodsNumber", getGoodsNumber())
                .append("specCode", getSpecCode())
                .append("unit", getUnit())
                .append("goodsType", getGoodsType())
                .append("purchaseQuantity", getPurchaseQuantity())
                .append("aiStock", getAiStock())
                .append("suPrice", getSuPrice())
                .append("salesAmount", getSalesAmount())
                .append("salesVolume", getSalesVolume())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelte", getIsDelte())
                .append("update_user_name", getUpdate_user_name())
                .append("create_user_name", getCreate_user_name())
                .toString();
    }
}
