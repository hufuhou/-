package com.cx.wms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 入库明细对象 crk_im_details
 *
 * @author XRC
 * @date 2023-11-21
 */
public class CrkImDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 入库明细单ID
     */
    private Long imdId;

    /**
     * 入库ID
     */
    @Excel(name = "入库ID")
    private Long inId;

    /**
     * 入库单号
     */
    @Excel(name = "入库单号")
    private String inCode;

    /**
     * 明细ID
     */
    @Excel(name = "明细ID")
    private String odId;

    /**
     * 单位
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 已入库数
     */
    @Excel(name = "已入库数")
    private Long quantityInStock;

    /**
     * 未入库数
     */
    @Excel(name = "未入库数")
    private Long unstockedQuantity;

    /**
     * 入库仓位
     */
    @Excel(name = "入库仓位")
    private Long slId;

    /**
     * 本次入库数
     */
    @Excel(name = "本次入库数")
    private Long thisQuantity;

    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionDate;

    /**
     * 条形码
     */
    @Excel(name = "条形码")
    private String barcode;

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

    public void setImdId(Long imdId) {
        this.imdId = imdId;
    }

    public Long getImdId() {
        return imdId;
    }

    public void setInId(Long inId) {
        this.inId = inId;
    }

    public Long getInId() {
        return inId;
    }

    public void setInCode(String inCode) {
        this.inCode = inCode;
    }

    public String getInCode() {
        return inCode;
    }

    public void setOdId(String odId) {
        this.odId = odId;
    }

    public String getOdId() {
        return odId;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setQuantityInStock(Long quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public Long getQuantityInStock() {
        return quantityInStock;
    }

    public void setUnstockedQuantity(Long unstockedQuantity) {
        this.unstockedQuantity = unstockedQuantity;
    }

    public Long getUnstockedQuantity() {
        return unstockedQuantity;
    }

    public void setSlId(Long slId) {
        this.slId = slId;
    }

    public Long getSlId() {
        return slId;
    }

    public void setThisQuantity(Long thisQuantity) {
        this.thisQuantity = thisQuantity;
    }

    public Long getThisQuantity() {
        return thisQuantity;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return barcode;
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
                .append("imdId", getImdId())
                .append("inId", getInId())
                .append("inCode", getInCode())
                .append("odId", getOdId())
                .append("unit", getUnit())
                .append("quantityInStock", getQuantityInStock())
                .append("unstockedQuantity", getUnstockedQuantity())
                .append("slId", getSlId())
                .append("thisQuantity", getThisQuantity())
                .append("productionDate", getProductionDate())
                .append("barcode", getBarcode())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelte", getIsDelte())
                .append("create_user_name", getCreate_user_name())
                .append("update_user_name", getUpdate_user_name())
                .toString();
    }
}
