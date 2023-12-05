package com.cx.ckgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 仓库详情对象 tj_storage_location
 * 
 * @author hfh
 * @date 2023-11-29
 */
public class TjStorageLocation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库位ID */
    private Long slId;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private Long wId;

    /** 库位编码 */
    @Excel(name = "库位编码")
    private String slCode;

    /** 库位名称 */
    @Excel(name = "库位名称")
    private String slName;

    /** 库位容量/立方 */
    @Excel(name = "库位容量/立方")
    private Long locationCapacity;

    /** 仓位主管 */
    @Excel(name = "仓位主管")
    private Long positionManager;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 0：存在；1：已删除，不存在 */
    private Integer isDelte;

    public void setSlId(Long slId) 
    {
        this.slId = slId;
    }

    public Long getSlId() 
    {
        return slId;
    }
    public void setwId(Long wId) 
    {
        this.wId = wId;
    }

    public Long getwId() 
    {
        return wId;
    }
    public void setSlCode(String slCode) 
    {
        this.slCode = slCode;
    }

    public String getSlCode() 
    {
        return slCode;
    }
    public void setSlName(String slName) 
    {
        this.slName = slName;
    }

    public String getSlName() 
    {
        return slName;
    }
    public void setLocationCapacity(Long locationCapacity) 
    {
        this.locationCapacity = locationCapacity;
    }

    public Long getLocationCapacity() 
    {
        return locationCapacity;
    }
    public void setPositionManager(Long positionManager) 
    {
        this.positionManager = positionManager;
    }

    public Long getPositionManager() 
    {
        return positionManager;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setIsDelte(Integer isDelte) 
    {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() 
    {
        return isDelte;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("slId", getSlId())
            .append("wId", getwId())
            .append("slCode", getSlCode())
            .append("slName", getSlName())
            .append("locationCapacity", getLocationCapacity())
            .append("positionManager", getPositionManager())
            .append("sort", getSort())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelte", getIsDelte())
            .toString();
    }
}
