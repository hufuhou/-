package com.cx.wms.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 获取全部仓库和库位的库存信息专用pojo对象。
 *
 * @author XRC
 * @date 2023-12-22
 * @return 包含库存信息的列表，每个元素包括货品名称、数量和总价值
 */
public class StockValues extends BaseEntity {
    private static final long serialVersionUID = 114514L;

    /**
     * 仓库ID
     */
    private String SVw_id;

    /**
     * 仓库名称
     */
    private String SVw_name;

    /**
     * 库位ID
     */
    private String SVsl_id;

    /**
     * 库位名称
     */
    private String SVsl_name;

    /**
     * 货品ID
     */
    private String SVg_id;

    /**
     * 货品名称
     */
    private String SVg_name;

    /**
     * 货品数量
     */
    private String SVitem_quantity;

    /**
     * 货品总价值
     */
    private Long SVValues;


    /**
     * 货品总价值
     */
    private Long AllCount;

    /**
     * 货品总价值
     */
    private BigDecimal AllValue;

    public Long getAllCount() {
        return AllCount;
    }

    public void setAllCount(Long allCount) {
        AllCount = allCount;
    }

    public BigDecimal getAllValue() {
        return AllValue;
    }

    public void setAllValue(BigDecimal allValue) {
        AllValue = allValue;
    }

    public String getSVw_id() {
        return SVw_id;
    }

    public void setSVw_id(String SVw_id) {
        this.SVw_id = SVw_id;
    }

    public String getSVw_name() {
        return SVw_name;
    }

    public void setSVw_name(String SVw_name) {
        this.SVw_name = SVw_name;
    }

    public String getSVsl_id() {
        return SVsl_id;
    }

    public void setSVsl_id(String SVsl_id) {
        this.SVsl_id = SVsl_id;
    }

    public String getSVsl_name() {
        return SVsl_name;
    }

    public void setSVsl_name(String SVsl_name) {
        this.SVsl_name = SVsl_name;
    }

    public String getSVg_id() {
        return SVg_id;
    }

    public void setSVg_id(String SVg_id) {
        this.SVg_id = SVg_id;
    }

    public String getSVg_name() {
        return SVg_name;
    }

    public void setSVg_name(String SVg_name) {
        this.SVg_name = SVg_name;
    }

    public String getSVitem_quantity() {
        return SVitem_quantity;
    }

    public void setSVitem_quantity(String SVitem_quantity) {
        this.SVitem_quantity = SVitem_quantity;
    }

    public Long getSVValues() {
        return SVValues;
    }

    public void setSVValues(Long SVValues) {
        this.SVValues = SVValues;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("SVw_id", getSVw_id())
                .append("SVw_name", getSVw_name())
                .append("SVsl_id", getSVsl_id())
                .append("SVsl_name", getSVsl_name())
                .append("SVg_id", getSVg_id())
                .append("SVg_name", getSVg_name())
                .append("SVitem_quantity", getSVitem_quantity())
                .append("SVValues", getSVValues())
                .append("AllCount", getAllCount())
                .append("AllValue", getAllValue())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }

}
