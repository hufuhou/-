package com.cx.order.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 货品信息对象 hp_goods
 *
 * @author hfh
 * @date 2023-11-22
 */
public class HpGoods extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 货品ID
     */
    private Long gId;

    /**
     * 货品类型
     */
    @Excel(name = "货品类型")
    private Long gtId;

    /**
     * 货品编码
     */
    @Excel(name = "货品编码")
    private String gCode;

    /**
     * 货品名称
     */
    @Excel(name = "货品名称")
    private String gName;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private Long gUnit;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String specCode;

    /**
     * 供应商
     */
    @Excel(name = "供应商")
    private Long sId;

    /**
     * 出库参考价
     */
    @Excel(name = "出库参考价")
    private BigDecimal orPrice;

    /**
     * 入库参考价
     */
    @Excel(name = "入库参考价")
    private BigDecimal wrPrice;

    /**
     * 保质期管理
     */
    @Excel(name = "保质期管理")
    private Integer hasShelfLife;

    /**
     * 预警天数
     */
    @Excel(name = "预警天数")
    private Long warningDays;

    /**
     * 货品上限
     */
    @Excel(name = "货品上限")
    private Long itemLimit;

    /**
     * 货品下限
     */
    @Excel(name = "货品下限")
    private Long lowerLimit;

    /**
     * 是否删除
     */
    private Integer isDelte;

    /**
     * 保质期
     */
    @Excel(name = "保质期")
    private String qualityG;

    public void setgId(Long gId) {
        this.gId = gId;
    }

    public Long getgId() {
        return gId;
    }

    public void setGtId(Long gtId) {
        this.gtId = gtId;
    }

    public Long getGtId() {
        return gtId;
    }

    public void setgCode(String gCode) {
        this.gCode = gCode;
    }

    public String getgCode() {
        return gCode;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgName() {
        return gName;
    }

    public void setgUnit(Long gUnit) {
        this.gUnit = gUnit;
    }

    public Long getgUnit() {
        return gUnit;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Long getSort() {
        return sort;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public String getSpecCode() {
        return specCode;
    }

    public BigDecimal getOrPrice() {
        return orPrice;
    }

    public void setOrPrice(BigDecimal orPrice) {
        this.orPrice = orPrice;
    }

    public BigDecimal getWrPrice() {
        return wrPrice;
    }

    public void setWrPrice(BigDecimal wrPrice) {
        this.wrPrice = wrPrice;
    }

    public void setHasShelfLife(Integer hasShelfLife) {
        this.hasShelfLife = hasShelfLife;
    }

    public Integer getHasShelfLife() {
        return hasShelfLife;
    }

    public void setWarningDays(Long warningDays) {
        this.warningDays = warningDays;
    }

    public Long getWarningDays() {
        return warningDays;
    }

    public void setItemLimit(Long itemLimit) {
        this.itemLimit = itemLimit;
    }

    public Long getItemLimit() {
        return itemLimit;
    }

    public void setLowerLimit(Long lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public Long getLowerLimit() {
        return lowerLimit;
    }

    public void setIsDelte(Integer isDelte) {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() {
        return isDelte;
    }

    public void setQualityG(String qualityG) {
        this.qualityG = qualityG;
    }

    public String getQualityG() {
        return qualityG;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("gId", getgId())
                .append("gtId", getGtId())
                .append("gCode", getgCode())
                .append("gName", getgName())
                .append("gUnit", getgUnit())
                .append("sort", getSort())
                .append("status", getStatus())
                .append("specCode", getSpecCode())
                .append("sId", getsId())
                .append("remark", getRemark())
                .append("orPrice", getOrPrice())
                .append("wrPrice", getWrPrice())
                .append("hasShelfLife", getHasShelfLife())
                .append("warningDays", getWarningDays())
                .append("itemLimit", getItemLimit())
                .append("lowerLimit", getLowerLimit())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("isDelte", getIsDelte())
                .append("qualityG", getQualityG())
                .toString();
    }
}
