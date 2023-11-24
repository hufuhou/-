package com.cx.hpxx.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.TreeEntity;

/**
 * 货品类型对象 hp_goods_type
 * 
 * @author hfh
 * @date 2023-11-23
 */
public class HpGoodsType extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 货品类型id */
    private Long gtId;

    /** 货品类型编码 */
    @Excel(name = "货品类型编码")
    private String gtCode;

    /** 货品类型名称 */
    @Excel(name = "货品类型名称")
    private String gtName;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 货品状态 */
    @Excel(name = "货品状态")
    private Integer status;

    /** 父级类型 */
    @Excel(name = "父级类型")
    private String parent;

    /** 是否删除 */
    private Integer isDelte;

    public void setGtId(Long gtId) 
    {
        this.gtId = gtId;
    }

    public Long getGtId() 
    {
        return gtId;
    }
    public void setGtCode(String gtCode) 
    {
        this.gtCode = gtCode;
    }

    public String getGtCode() 
    {
        return gtCode;
    }
    public void setGtName(String gtName) 
    {
        this.gtName = gtName;
    }

    public String getGtName() 
    {
        return gtName;
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
    public void setParent(String parent) 
    {
        this.parent = parent;
    }

    public String getParent() 
    {
        return parent;
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
            .append("gtId", getGtId())
            .append("gtCode", getGtCode())
            .append("gtName", getGtName())
            .append("sort", getSort())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("parent", getParent())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("isDelte", getIsDelte())
            .toString();
    }
}
