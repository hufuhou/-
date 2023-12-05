package com.cx.order.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 进货订单对象 order_purchase_order
 *
 * @author 刘志昂
 * @date 2023-11-21
 */
public class OrderPurchase extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 进货订单id
     */
    private Long poId;

    /**
     * 进货单号
     */
    @Excel(name = "进货单号")
    private String poCode;

    /**
     * 单据状态
     */
    @Excel(name = "单据状态")
    private Integer status;

    /**
     * 进货日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /**
     * 供应商
     */
    @Excel(name = "供应商")
    private Long sId;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contactPerson;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String contactDetails;

    /**
     * 进货人
     */
    @Excel(name = "进货人")
    private Long purchaserId;

    /**
     * 进货部门
     */
    @Excel(name = "进货部门")
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
     * 是否存在
     */
    private Integer isDelte;

    public void setPoId(Long poId) {
        this.poId = poId;
    }

    public Long getPoId() {
        return poId;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public Long getsId() {
        return sId;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setPurchaserId(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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
                .append("poId", getPoId())
                .append("poCode", getPoCode())
                .append("status", getStatus())
                .append("purchaseDate", getPurchaseDate())
                .append("sId", getsId())
                .append("contactPerson", getContactPerson())
                .append("contactDetails", getContactDetails())
                .append("purchaserId", getPurchaserId())
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
