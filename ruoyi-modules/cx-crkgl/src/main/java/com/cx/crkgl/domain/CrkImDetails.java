package com.cx.crkgl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 入库明细对象 crk_im_details
 * 
 * @author hfh
 * @date 2023-12-01
 */
public class CrkImDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入库明细单ID */
    private Long imdId;

    /** 入库ID */
    @Excel(name = "入库ID")
    private Long inId;

    /** 入库单号 */
    @Excel(name = "入库单号")
    private String inCode;

    /** 进货明细 ID 或销售退货明细 ID（相关订单明细 ID） */
    @Excel(name = "进货明细 ID 或销售退货明细 ID", readConverterExp = "相=关订单明细,I=D")
    private String odId;

    /** 货品信息 */
    @Excel(name = "货品信息")
    private String gCode;

    /** 货品id */
    @Excel(name = "货品信息")
    private Long gId;

    /** 已入库数量 */
    @Excel(name = "已入库数量")
    private Long quantityInStock;

    /** 未入库数量 */
    @Excel(name = "未入库数量")
    private Long unstockedQuantity;

    /** 入库仓位 */
    @Excel(name = "入库仓位")
    private Long slId;

    /** 本次入库数量 */
    @Excel(name = "本次入库数量")
    private Long thisQuantity;

    /** 入库金额 */
    @Excel(name = "入库金额")
    private Long money;

    /** 生产日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionDate;

    /** 条形码 */
    @Excel(name = "条形码")
    private String barcode;

    @Excel(name = "进货数量")
    private Long purchaseQuantity;

    /** 货品名称 */
    @Excel(name = "货品名称")
    private String gName;

    /** 单位 */
    @Excel(name = "单位")
    private Long gUnit;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specCode;

    public Long getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(Long purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
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

    public String getSpecCode() {
        return specCode;
    }

    public void setSpecCode(String specCode) {
        this.specCode = specCode;
    }

    /** 是否删除 */
    private Integer isDelte;

    public Long getgId() {
        return gId;
    }

    public void setgId(Long gId) {
        this.gId = gId;
    }

    public void setImdId(Long imdId)
    {
        this.imdId = imdId;
    }

    public Long getImdId() 
    {
        return imdId;
    }
    public void setInId(Long inId) 
    {
        this.inId = inId;
    }

    public Long getInId() 
    {
        return inId;
    }
    public void setInCode(String inCode) 
    {
        this.inCode = inCode;
    }

    public String getInCode() 
    {
        return inCode;
    }

    public String getOdId() {
        return odId;
    }

    public void setOdId(String odId) {
        this.odId = odId;
    }

    public String getgCode() {
        return gCode;
    }

    public void setgCode(String gCode) {
        this.gCode = gCode;
    }

    public void setQuantityInStock(Long quantityInStock)
    {
        this.quantityInStock = quantityInStock;
    }

    public Long getQuantityInStock() 
    {
        return quantityInStock;
    }
    public void setUnstockedQuantity(Long unstockedQuantity) 
    {
        this.unstockedQuantity = unstockedQuantity;
    }

    public Long getUnstockedQuantity() 
    {
        return unstockedQuantity;
    }
    public void setSlId(Long slId) 
    {
        this.slId = slId;
    }

    public Long getSlId() 
    {
        return slId;
    }
    public void setThisQuantity(Long thisQuantity) 
    {
        this.thisQuantity = thisQuantity;
    }

    public Long getThisQuantity() 
    {
        return thisQuantity;
    }
    public void setProductionDate(Date productionDate) 
    {
        this.productionDate = productionDate;
    }

    public Date getProductionDate() 
    {
        return productionDate;
    }
    public void setBarcode(String barcode) 
    {
        this.barcode = barcode;
    }

    public String getBarcode() 
    {
        return barcode;
    }
    public void setIsDelte(Integer isDelte) 
    {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() 
    {
        return isDelte;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("imdId", getImdId())
            .append("inId", getInId())
            .append("inCode", getInCode())
            .append("odId", getOdId())
            .append("gCode", getgCode())
            .append("quantityInStock", getQuantityInStock())
            .append("unstockedQuantity", getUnstockedQuantity())
            .append("slId", getSlId())
            .append("thisQuantity", getThisQuantity())
            .append("productionDate", getProductionDate())
            .append("barcode", getBarcode())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isDelte", getIsDelte())
            .append("gId",getgId())
            .toString();
    }
}
