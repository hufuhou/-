package com.cx.ledger.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * 库存查询对象 tz_stock
 *
 * @author ruoyi
 * @date 2023-11-27
 */
public class TzStock extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 库存id
     */
    private Long sId;

    /**
     * 仓库id
     */
    @Excel(name = "仓库id")
    private Long wId;

    /**
     * 库位id
     */
    @Excel(name = "库位id")
    private Long slId;

    /**
     * 货品数量
     */
    @Excel(name = "货品数量")
    private Long itemQuantity;

    /**
     * 计划数量
     */
    @Excel(name = "计划数量")
    private Long numberPlans;

    /**
     * 货品id
     */
    @Excel(name = "货品id")
    private Long gId;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private Long createBys;

    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Integer isDelte;
    private Long gtId;

    private String gCode;

    /**
     * 货品名称
     */
    private String gName;

    /**
     * 单位
     */
    private Long gUnit;


    private List<HpGoods> HpGoodsList;

    private HpGoods hpGoods;

    private String specCode;

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    public HpGoods getHpGoods() {
        return hpGoods;
    }

    public void setHpGoods(HpGoods hpGoods) {
        this.hpGoods = hpGoods;
    }
    public Long getsId() {
        return sId;
    }

    public void setsId(Long sId) {
        this.sId = sId;
    }

    public Long getwId() {
        return wId;
    }

    public void setwId(Long wId) {
        this.wId = wId;
    }

    public Long getSlId() {
        return slId;
    }

    public void setSlId(Long slId) {
        this.slId = slId;
    }

    public Long getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Long itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Long getNumberPlans() {
        return numberPlans;
    }

    public void setNumberPlans(Long numberPlans) {
        this.numberPlans = numberPlans;
    }

    public Long getgId() {
        return gId;
    }

    public void setgId(Long gId) {
        this.gId = gId;
    }

    public Long getCreateBys() {
        return createBys;
    }

    public void setCreateBys(Long createBys) {
        this.createBys = createBys;
    }

    public Integer getIsDelte() {
        return isDelte;
    }

    public void setIsDelte(Integer isDelte) {
        this.isDelte = isDelte;
    }

    public Long getGtId() {
        return gtId;
    }

    public void setGtId(Long gtId) {
        this.gtId = gtId;
    }

    public String getgCode() {
        return gCode;
    }

    public void setgCode(String gCode) {
        this.gCode = gCode;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public Long getgUnit() {
        return gUnit;
    }

    public void setgUnit(Long gUnit) {
        this.gUnit = gUnit;
    }

    public List<HpGoods> getHpGoodsList() {
        return HpGoodsList;
    }

    public void setHpGoodsList(List<HpGoods> hpGoodsList) {
        HpGoodsList = hpGoodsList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
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
//                .append("HpGoodsList", getHpGoodsList())
                .toString();
    }
}
