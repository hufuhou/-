package com.cx.units.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 供应商列表对象 wldw_supplier
 * 
 * @author 刘志昂
 * @date 2023-11-24
 */
public class Supplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 供应商id */
    private Long sId;

    /** 财务id */
    private Long fId;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    private String sCode;

    /** 供应商 */
    @Excel(name = "供应商")
    private String sName;

    /** 所属行业 */
    @Excel(name = "所属行业")
    private String industry;

    /** 供应商分类 */
    @Excel(name = "供应商分类")
    private String supplierType;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contactPerson;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactNumber;

    /** 电子邮箱 */
    @Excel(name = "电子邮箱")
    private String email;

    /** 进货负责人 */
    @Excel(name = "进货负责人")
    private Long principalId;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 供应商地址 */
    @Excel(name = "供应商地址")
    private String sAddress;

    /** 是否存在 */
    private Integer isDelete;

    /** 财务信息 */
    private Finance finance;

    public void setsId(Long sId) 
    {
        this.sId = sId;
    }

    public Long getsId() 
    {
        return sId;
    }
    public void setfId(Long fId) 
    {
        this.fId = fId;
    }

    public Long getfId() 
    {
        return fId;
    }
    public void setsCode(String sCode) 
    {
        this.sCode = sCode;
    }

    public String getsCode() 
    {
        return sCode;
    }
    public void setsName(String sName) 
    {
        this.sName = sName;
    }

    public String getsName() 
    {
        return sName;
    }
    public void setIndustry(String industry) 
    {
        this.industry = industry;
    }

    public String getIndustry() 
    {
        return industry;
    }
    public void setSupplierType(String supplierType) 
    {
        this.supplierType = supplierType;
    }

    public String getSupplierType() 
    {
        return supplierType;
    }
    public void setContactPerson(String contactPerson) 
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() 
    {
        return contactPerson;
    }
    public void setContactNumber(String contactNumber) 
    {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() 
    {
        return contactNumber;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setPrincipalId(Long principalId) 
    {
        this.principalId = principalId;
    }

    public Long getPrincipalId() 
    {
        return principalId;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setsAddress(String sAddress) 
    {
        this.sAddress = sAddress;
    }

    public String getsAddress() 
    {
        return sAddress;
    }
    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }

    public Integer getIsDelete()
    {
        return isDelete;
    }

    public Finance getFinance()
    {
        return finance;
    }

    public void setFinance(Finance finance)
    {
        this.finance = finance;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sId", getsId())
            .append("fId", getfId())
            .append("sCode", getsCode())
            .append("sName", getsName())
            .append("industry", getIndustry())
            .append("supplierType", getSupplierType())
            .append("contactPerson", getContactPerson())
            .append("contactNumber", getContactNumber())
            .append("email", getEmail())
            .append("principalId", getPrincipalId())
            .append("status", getStatus())
            .append("sAddress", getsAddress())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelte", getIsDelete())
            .append("finance", getFinance())
            .toString();
    }
}
