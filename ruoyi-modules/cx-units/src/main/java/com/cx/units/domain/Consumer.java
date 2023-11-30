package com.cx.units.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 客户对象 wldw_consumer
 *
 * @author 刘志昂
 * @date 2023-11-29
 */
public class Consumer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private Long cId;

    /**
     * 财务id
     */
    private Long fId;

    /**
     * 客户编码
     */
    @Excel(name = "客户编码")
    private String cCode;

    /**
     * 客户名称
     */
    @Excel(name = "客户名称")
    private String cName;

    /**
     * 所属行业
     */
    @Excel(name = "所属行业")
    private Long industry;

    /**
     * 客户来源
     */
    @Excel(name = "客户来源")
    private Long cSource;

    /**
     * 联系人
     */
    @Excel(name = "联系人")
    private String contactPerson;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String contactNumber;

    /**
     * 电子邮箱
     */
    @Excel(name = "电子邮箱")
    private String email;

    /**
     * 销售负责人
     */
    @Excel(name = "销售负责人")
    private Long principalId;

    /**
     * 客户地址
     */
    @Excel(name = "客户地址")
    private String cAddress;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Integer status;

    /**
     * 是否删除
     */
    private Integer isDelte;

    /**
     * 财务信息
     */
    private Finance finance;

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long getcId() {
        return cId;
    }

    public void setfId(Long fId) {
        this.fId = fId;
    }

    public Long getfId() {
        return fId;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcName() {
        return cName;
    }

    public void setIndustry(Long industry) {
        this.industry = industry;
    }

    public Long getIndustry() {
        return industry;
    }

    public void setcSource(Long cSource) {
        this.cSource = cSource;
    }

    public Long getcSource() {
        return cSource;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPrincipalId(Long principalId) {
        this.principalId = principalId;
    }

    public Long getPrincipalId() {
        return principalId;
    }

    public void setcAddress(String cAddress) {
        this.cAddress = cAddress;
    }

    public String getcAddress() {
        return cAddress;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setIsDelte(Integer isDelte) {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() {
        return isDelte;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cId", getcId())
                .append("fId", getfId())
                .append("cCode", getcCode())
                .append("cName", getcName())
                .append("industry", getIndustry())
                .append("cSource", getcSource())
                .append("contactPerson", getContactPerson())
                .append("contactNumber", getContactNumber())
                .append("email", getEmail())
                .append("principalId", getPrincipalId())
                .append("cAddress", getcAddress())
                .append("remark", getRemark())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelte", getIsDelte())
                .append("finance", getFinance())
                .toString();
    }
}
