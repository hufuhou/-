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
    private String orderId;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 物品数量 */
    @Excel(name = "本次出库数量")
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
    /** 货品编码 */
    @Excel(name = "货品编码")
    private String gCode;

    /** 货品id */
    @Excel(name = "货品信息")
    private Long gId;
    /** 已入库数量 */
    @Excel(name = "已出库数量")
    private Long outBound;

    /** 未入库数量 */
    @Excel(name = "未出库数量")
    private Long notShipped;
    /** 入库金额 */
    @Excel(name = "出库金额")
    private Long money;
    /** 库存数量 */
    @Excel(name = "库存数量")
    private Long kcsl;
    @Excel(name = "货品名称")
    private String gName;

    /** 单位 */
    @Excel(name = "单位")
    private Long gUnit;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specCode;

    /** 销售数量 */
    @Excel(name = "销售数量")
    private Long salesVolume;
    public Long getKcsl() {
        return kcsl;
    }

    public void setKcsl(Long kcsl) {
        this.kcsl = kcsl;
    }

    public String getgCode() {
        return gCode;
    }

    public void setgCode(String gCode) {
        this.gCode = gCode;
    }

    public Long getgId() {
        return gId;
    }

    public void setgId(Long gId) {
        this.gId = gId;
    }

    public Long getOutBound() {
        return outBound;
    }

    public void setOutBound(Long outBound) {
        this.outBound = outBound;
    }

    public Long getNotShipped() {
        return notShipped;
    }

    public void setNotShipped(Long notShipped) {
        this.notShipped = notShipped;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    public Long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public Long getgUnit() {
        return gUnit;
    }

    public void setgUnit(Long gUnit) {
        this.gUnit = gUnit;
    }

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public Long getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Long salesVolume) {
        this.salesVolume = salesVolume;
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
