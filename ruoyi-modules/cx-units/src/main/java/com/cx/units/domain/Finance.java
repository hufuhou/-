package com.cx.units.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 财务对象 finance
 *
 * @author 刘志昂
 * @date 2023-11-24
 */
public class Finance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 财务ID */
    private Long fId;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    private String sCode;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long cId;

    /** 帐户名称 */
    @Excel(name = "帐户名称")
    private String accountName;

    /** 纳税人识别号 */
    @Excel(name = "纳税人识别号")
    private String tiNumber;

    /** 银行账户 */
    @Excel(name = "银行账户")
    private String bankAccount;

    /** 开户银行 */
    @Excel(name = "开户银行")
    private String bankDeposit;

    /** 开户行地址 */
    @Excel(name = "开户行地址")
    private String aobAddress;

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
    public void setcId(Long cId)
    {
        this.cId = cId;
    }

    public Long getcId()
    {
        return cId;
    }
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getAccountName()
    {
        return accountName;
    }
    public void setTiNumber(String tiNumber)
    {
        this.tiNumber = tiNumber;
    }

    public String getTiNumber()
    {
        return tiNumber;
    }
    public void setBankAccount(String bankAccount)
    {
        this.bankAccount = bankAccount;
    }

    public String getBankAccount()
    {
        return bankAccount;
    }
    public void setBankDeposit(String bankDeposit)
    {
        this.bankDeposit = bankDeposit;
    }

    public String getBankDeposit()
    {
        return bankDeposit;
    }
    public void setAobAddress(String aobAddress)
    {
        this.aobAddress = aobAddress;
    }

    public String getAobAddress()
    {
        return aobAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fId", getfId())
            .append("sCode", getsCode())
            .append("cId", getcId())
            .append("accountName", getAccountName())
            .append("tiNumber", getTiNumber())
            .append("bankAccount", getBankAccount())
            .append("bankDeposit", getBankDeposit())
            .append("aobAddress", getAobAddress())
            .toString();
    }
}
