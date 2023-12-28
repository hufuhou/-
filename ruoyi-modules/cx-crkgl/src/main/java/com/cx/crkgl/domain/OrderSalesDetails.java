package com.cx.crkgl.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 销售明细对象 order_d_sales
 *
 * @author hfh
 * @date 2023-12-19
 */
public class OrderSalesDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 销售明细id */
    private Long sdId;


    /** 货品信息id */
    private Long gId;

    /** 销售单号 */
    @Excel(name = "销售单号")
    private String sCode;

    /** 货品编号 */
    @Excel(name = "货品编号")
    private String gCode;

    /** 进货数量 */
    @Excel(name = "未出库")
    private Long wcksl;

    /** 已入库 */
    @Excel(name = "已出库")
    private Long cksl;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Long kcsl;

    /** 销售数量 */
    @Excel(name = "销售数量")
    private Long salesVolume;

    /** 销售金额 */
    @Excel(name = "销售金额")
    private Long salesAmount;
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


    @Excel(name = "仓库")
    private Long wId;

    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Integer isDelte;

    public Long getKcsl() {
        return kcsl;
    }

    public void setKcsl(Long kcsl) {
        this.kcsl = kcsl;
    }

    public Long getSdId() {
        return sdId;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public void setSdId(Long sdId)
    {
        this.sdId = sdId;
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

    public Long getWcksl() {
        return wcksl;
    }

    public void setWcksl(Long wcksl) {
        this.wcksl = wcksl;
    }

    public Long getCksl() {
        return cksl;
    }

    public void setCksl(Long cksl) {
        this.cksl = cksl;
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

    public Long getgId() {
        return gId;
    }

    public void setgId(Long gId) {
        this.gId = gId;
    }

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

    public Long getwId() {
        return wId;
    }

    public void setwId(Long wId) {
        this.wId = wId;
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
