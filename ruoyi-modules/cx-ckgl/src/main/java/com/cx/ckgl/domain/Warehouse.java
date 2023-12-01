package com.cx.ckgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 仓库管理对象 warehouse
 * 
 * @author hfh
 * @date 2023-11-24
 */
public class Warehouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 仓库id */
    private Long wId;

    /** 库位id */
    @Excel(name = "库位id")
    private Long tId;

    /** 入库id */
    private Long inId;

    /** 仓库编号 */
    @Excel(name = "仓库编号")
    private String wCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String wName;

    /** 仓库容量 */
    @Excel(name = "仓库容量")
    private String wCapacity;

    /** 状态 */
    @Excel(name = "状态")
    private Integer wStatus;

    /** 仓库地址 */
    @Excel(name = "仓库地址")
    private String wAddress;

    /** 0：不是默认的；1：是默认的 */
    private Integer isDefault;

    /** 仓库主管 */
    @Excel(name = "仓库主管")
    private Long wSupervisor;

    /** 排序 */
    private Long sort;

    /** 0：存在；1：已删除，不存在 */
    private Integer isDelte;

    /** 仓管部门 */
    @Excel(name = "仓管部门")
    private Long warehouseMD;

    public void setwId(Long wId) 
    {
        this.wId = wId;
    }

    public Long getwId() 
    {
        return wId;
    }
    public void settId(Long tId) 
    {
        this.tId = tId;
    }

    public Long gettId() 
    {
        return tId;
    }
    public void setInId(Long inId) 
    {
        this.inId = inId;
    }

    public Long getInId() 
    {
        return inId;
    }
    public void setwCode(String wCode) 
    {
        this.wCode = wCode;
    }

    public String getwCode() 
    {
        return wCode;
    }
    public void setwName(String wName) 
    {
        this.wName = wName;
    }

    public String getwName() 
    {
        return wName;
    }
    public void setwCapacity(String wCapacity) 
    {
        this.wCapacity = wCapacity;
    }

    public String getwCapacity() 
    {
        return wCapacity;
    }
    public void setwStatus(Integer wStatus) 
    {
        this.wStatus = wStatus;
    }

    public Integer getwStatus() 
    {
        return wStatus;
    }
    public void setwAddress(String wAddress) 
    {
        this.wAddress = wAddress;
    }

    public String getwAddress() 
    {
        return wAddress;
    }
    public void setIsDefault(Integer isDefault) 
    {
        this.isDefault = isDefault;
    }

    public Integer getIsDefault() 
    {
        return isDefault;
    }
    public void setwSupervisor(Long wSupervisor) 
    {
        this.wSupervisor = wSupervisor;
    }

    public Long getwSupervisor() 
    {
        return wSupervisor;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setIsDelte(Integer isDelte) 
    {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() 
    {
        return isDelte;
    }
    public void setWarehouseMD(Long warehouseMD)
    {
        this.warehouseMD = warehouseMD;
    }

    public Long getWarehouseMD()
    {
        return warehouseMD;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("wId", getwId())
            .append("tId", gettId())
            .append("inId", getInId())
            .append("wCode", getwCode())
            .append("wName", getwName())
            .append("wCapacity", getwCapacity())
            .append("wStatus", getwStatus())
            .append("wAddress", getwAddress())
            .append("isDefault", getIsDefault())
            .append("wSupervisor", getwSupervisor())
            .append("remark", getRemark())
            .append("sort", getSort())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelte", getIsDelte())
            .append("warehouseMD", getWarehouseMD())
            .toString();
    }
}
