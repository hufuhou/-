package com.cx.wms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出库明细
 * 对象 crk_out_details
 *
 * @author hfh
 * @date 2023-12-20
 */
public class CrkOutDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    // 出库管理ID
    private Long omId;

    // 出库单ID
    private Long outId;

    // 订单ID
    private String orderId;

    // 外部出库ID
    private Long outOutId;

    // 删除标志，1表示已删除，0表示未删除
    private Integer isDelete;

    // 计量单位
    private String unit;

    private Integer isDelte;

    // 商品数量
    private Integer itemQuantity;

    // 已发货数量
    private Integer quantityShipped;

    // 未交付数量
    private Integer undeliveredQuantity;

    // 当前出库数量
    private Integer currOutQuantity;

    // 批次号
    private String batchNumber;

    // 出库详情备注
    private String odRemark;

    // 商品条码
    private String barcode;

    // 创建者ID
    private Long odCreateBy;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date odCreateTime;

    // 更新者ID
    private Long odUpdateBy;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date odUpdateTime;

    // 删除标志，false表示未删除，true表示已删除
    private Boolean odIsDelete;

    // 销售单ID
    private Long slId;

    // 商品编码
    private String gCode;

    // 商品ID
    private Long gId;

    // 出库类型
    private Integer outBound;

    // 未发货数量
    private Integer notShipped;

    // 金额
    private BigDecimal money;

    // 创建者用户名
    private String createUser;

    // 更新者用户名
    private String updateUser;

    // 商品名称
    private String goods;

    // 仓库名称
    private String warehouse;


    public Integer getIsDelte() {
        return isDelte;
    }

    public void setIsDelte(Integer isDelte) {
        this.isDelte = isDelte;
    }

    public Long getOutOutId() {
        return outOutId;
    }

    public void setOutOutId(Long outOutId) {
        this.outOutId = outOutId;
    }

    public Long getOmId() {
        return omId;
    }

    public void setOmId(Long omId) {
        this.omId = omId;
    }

    public Long getOutId() {
        return outId;
    }

    public void setOutId(Long outId) {
        this.outId = outId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Integer getQuantityShipped() {
        return quantityShipped;
    }

    public void setQuantityShipped(Integer quantityShipped) {
        this.quantityShipped = quantityShipped;
    }

    public Integer getUndeliveredQuantity() {
        return undeliveredQuantity;
    }

    public void setUndeliveredQuantity(Integer undeliveredQuantity) {
        this.undeliveredQuantity = undeliveredQuantity;
    }

    public Integer getCurrOutQuantity() {
        return currOutQuantity;
    }

    public void setCurrOutQuantity(Integer currOutQuantity) {
        this.currOutQuantity = currOutQuantity;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getOdRemark() {
        return odRemark;
    }

    public void setOdRemark(String odRemark) {
        this.odRemark = odRemark;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Long getOdCreateBy() {
        return odCreateBy;
    }

    public void setOdCreateBy(Long odCreateBy) {
        this.odCreateBy = odCreateBy;
    }

    public Date getOdCreateTime() {
        return odCreateTime;
    }

    public void setOdCreateTime(Date odCreateTime) {
        this.odCreateTime = odCreateTime;
    }

    public Long getOdUpdateBy() {
        return odUpdateBy;
    }

    public void setOdUpdateBy(Long odUpdateBy) {
        this.odUpdateBy = odUpdateBy;
    }

    public Date getOdUpdateTime() {
        return odUpdateTime;
    }

    public void setOdUpdateTime(Date odUpdateTime) {
        this.odUpdateTime = odUpdateTime;
    }

    public Boolean getOdIsDelete() {
        return odIsDelete;
    }

    public void setOdIsDelete(Boolean odIsDelete) {
        this.odIsDelete = odIsDelete;
    }

    public Long getSlId() {
        return slId;
    }

    public void setSlId(Long slId) {
        this.slId = slId;
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

    public Integer getOutBound() {
        return outBound;
    }

    public void setOutBound(Integer outBound) {
        this.outBound = outBound;
    }

    public Integer getNotShipped() {
        return notShipped;
    }

    public void setNotShipped(Integer notShipped) {
        this.notShipped = notShipped;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("omId", getOmId())
                .append("outOutId", getOutId())
                .append("outId", getOutId())
                .append("orderId", getOrderId())
                .append("unit", getUnit())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("CreateUser", getCreateUser())
                .append("updateTime", getUpdateTime())
                .append("updateUserName", getUpdateUser())
                .append("UpdateUser", getUpdateUser())
                .append("sCode", getgCode())
                .append("isDelte", getOdIsDelete())
                .toString();
    }
}
