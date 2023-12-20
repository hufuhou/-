package com.cx.crkgl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 库存查询对象 tz_stock
 *
 * @author hfh
 * @date 2023-12-13
 */
public class TzStock extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库存id */
    private Long sId;

    /** 仓库id */
    @Excel(name = "仓库id")
    private Long wId;

    /** 库位id */
    @Excel(name = "库位id")
    private Long slId;

    /** 货品数量 */
    @Excel(name = "货品数量")
    private Long itemQuantity;

    /** 计划数量 */
    @Excel(name = "计划数量")
    private Long numberPlans;

    /** 货品id */
    @Excel(name = "货品id")
    private Long gId;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createBys;

    /** 0：存在；1：已删除，不存在 */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Integer isDelte;

    public void setsId(Long sId)
    {
        this.sId = sId;
    }

    public Long getsId()
    {
        return sId;
    }
    public void setwId(Long wId)
    {
        this.wId = wId;
    }

    public Long getwId()
    {
        return wId;
    }
    public void setSlId(Long slId)
    {
        this.slId = slId;
    }

    public Long getSlId()
    {
        return slId;
    }
    public void setItemQuantity(Long itemQuantity)
    {
        this.itemQuantity = itemQuantity;
    }

    public Long getItemQuantity()
    {
        return itemQuantity;
    }
    public void setNumberPlans(Long numberPlans)
    {
        this.numberPlans = numberPlans;
    }

    public Long getNumberPlans()
    {
        return numberPlans;
    }
    public void setgId(Long gId)
    {
        this.gId = gId;
    }

    public Long getgId()
    {
        return gId;
    }
    public void setCreateBys(Long createBys)
    {
        this.createBys = createBys;
    }

    public Long getCreateBys()
    {
        return createBys;
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
                .append("sId", getsId())
                .append("wId", getwId())
                .append("slId", getSlId())
                .append("itemQuantity", getItemQuantity())
                .append("numberPlans", getNumberPlans())
                .append("gId", getgId())
                .append("remark", getRemark())
                .append("createBys", getCreateBys())
                .append("createTime", getCreateTime())
                .append("isDelte", getIsDelte())
                .toString();
    }
}
