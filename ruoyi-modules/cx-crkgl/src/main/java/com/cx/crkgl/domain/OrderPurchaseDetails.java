package com.cx.crkgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 进货明细对象 order_purchase_details
 *
 * @author hfh
 * @date 2023-12-07
 */
public class OrderPurchaseDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 进货明细单id */
    private Long pdId;

    /** 货品信息id */
    private Long gId;

    private Long rksl;
    private Long wrksl;

    public Long getRksl() {
        return rksl;
    }

    public void setRksl(Long rksl) {
        this.rksl = rksl;
    }

    public Long getWrksl() {
        return wrksl;
    }

    public void setWrksl(Long wrksl) {
        this.wrksl = wrksl;
    }

    public Long getgId() {
        return gId;
    }

    public void setgId(Long gId) {
        this.gId = gId;
    }

    /** 进货单号 */
    @Excel(name = "进货单号")
    private String poCode;

    /** 货品编码 */
    @Excel(name = "货品编码")
    private String gCode;

    /** 进货数量 */
    @Excel(name = "进货数量")
    private Long purchaseQuantity;

    /** 总金额 */
    @Excel(name = "总金额")
    private Long money;

    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Integer isDelte;
    /** 货品类型 */
    @Excel(name = "货品类型")
    private Long gtId;

    /** 货品名称 */
    @Excel(name = "货品名称")
    private String gName;

    /** 单位 */
    @Excel(name = "单位")
    private Long gUnit;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specCode;

    public Long getGtId() {
        return gtId;
    }

    public void setGtId(Long gtId) {
        this.gtId = gtId;
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

    public void setPdId(Long pdId)
    {
        this.pdId = pdId;
    }

    public Long getPdId()
    {
        return pdId;
    }
    public void setPoCode(String poCode)
    {
        this.poCode = poCode;
    }

    public String getPoCode()
    {
        return poCode;
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
    public void setMoney(Long money)
    {
        this.money = money;
    }

    public Long getMoney()
    {
        return money;
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
                .append("pdId", getPdId())
                .append("poCode", getPoCode())
                .append("gtId", getGtId())
                .append("gCode", getgCode())
                .append("gName", getgName())
                .append("gUnit", getgUnit())
                .append("specCode", getSpecCode())
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

