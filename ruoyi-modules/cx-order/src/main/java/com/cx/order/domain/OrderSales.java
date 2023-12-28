package com.cx.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 销售订单对象 order_sales_order
 *
 * @author LZA
 * @date 2023-12-19
 */
public class OrderSales extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 销售订单ID
     */
    private Long sId;

    /**
     * 销售单号
     */
    @Excel(name = "销售单号")
    private String sCode;

    /**
     * 进货审核状态：0：草稿；1：待审核；2：驳回；3：未入库；4：部分入库；5：已完成；
     */
    @Excel(name = "进货审核状态：0：草稿；1：待审核；2：驳回；3：未入库；4：部分入库；5：已完成；")
    private Integer status;

    /**
     * 销售日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "销售日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date saleDate;

    /**
     * 客户id
     */
    @Excel(name = "客户id")
    private Long cId;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contactPerson;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String contactNumber;

    /**
     * 销售负责人
     */
    @Excel(name = "销售负责人")
    private Long principalId;

    /**
     * 进货部门id
     */
    @Excel(name = "进货部门id")
    private Long deptId;

    /**
     * 审核人
     */
    @Excel(name = "审核人")
    private String reviewer;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewerDate;

    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Integer isDelte;

    /**
     * 总数量
     */
    private BigDecimal totalPurchaseQuantity;

    /**
     * 总金额
     */
    private BigDecimal totalMoney;

    /**
     * 总货品名称
     */
    private String totalGoodsName;

    /**
     * 销售明细信息
     */
    private List<OrderSalesDetails> detailsList;

    public List<OrderSalesDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<OrderSalesDetails> detailsList) {
        this.detailsList = detailsList;
    }

    public BigDecimal getTotalPurchaseQuantity() {
        return totalPurchaseQuantity;
    }

    public void setTotalPurchaseQuantity(BigDecimal totalPurchaseQuantity) {
        this.totalPurchaseQuantity = totalPurchaseQuantity;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getTotalGoodsName() {
        return totalGoodsName;
    }

    public void setTotalGoodsName(String totalGoodsName) {
        this.totalGoodsName = totalGoodsName;
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

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getcId() {
        return cId;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setPrincipalId(Long principalId) {
        this.principalId = principalId;
    }

    public Long getPrincipalId() {
        return principalId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewerDate(Date reviewerDate) {
        this.reviewerDate = reviewerDate;
    }

    public Date getReviewerDate() {
        return reviewerDate;
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
                .append("sId", getsId())
                .append("sCode", getsCode())
                .append("status", getStatus())
                .append("saleDate", getSaleDate())
                .append("cId", getcId())
                .append("contactPerson", getContactPerson())
                .append("contactNumber", getContactNumber())
                .append("principalId", getPrincipalId())
                .append("deptId", getDeptId())
                .append("remark", getRemark())
                .append("reviewer", getReviewer())
                .append("reviewerDate", getReviewerDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelte", getIsDelte())
                .toString();
    }
}
