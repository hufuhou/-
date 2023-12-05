package com.cx.wms.domain;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 进货明细对象 order_purchase_details
 *
 * @author 熊睿宸
 * @date 2023-11-21
 */
public class OrderPurchaseDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 进货明细单id
     */
    private Long pdId;

    /**
     * 进货单号
     */
    @Excel(name = "进货单号")
    private String poCode;

    /**
     * 货品id
     */
    @Excel(name = "货品id")
    private Long goodsId;

    /**
     * 进货数量
     */
    @Excel(name = "进货数量")
    private Long purchaseQuantity;

    /**
     * 总金额
     */
    @Excel(name = "总金额")
    private Long money;

    /**
     * 0：存在；1：已删除，不存在
     */
    @Excel(name = "0：存在；1：已删除，不存在")
    private Integer isDelte;

    //创建人用户名
    private String create_user_name;
    //更新人用户名
    private String update_user_name;
    //商品名称
    private String goods_name;

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getCreate_user_name() {
        return create_user_name;
    }

    public void setCreate_user_name(String create_user_name) {
        this.create_user_name = create_user_name;
    }

    public String getUpdate_user_name() {
        return update_user_name;
    }

    public void setUpdate_user_name(String update_user_name) {
        this.update_user_name = update_user_name;
    }

    public void setPdId(Long pdId) {
        this.pdId = pdId;
    }

    public Long getPdId() {
        return pdId;
    }

    public void setPoCode(String poCode) {
        this.poCode = poCode;
    }

    public String getPoCode() {
        return poCode;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setPurchaseQuantity(Long purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public Long getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public Long getMoney() {
        return money;
    }

    public void setIsDelte(Integer isDelte) {
        this.isDelte = isDelte;
    }

    public Integer getIsDelte() {
        return isDelte;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("pdId", getPdId())
                .append("poCode", getPoCode())
                .append("goodsId", getGoodsId())
                .append("purchaseQuantity", getPurchaseQuantity())
                .append("money", getMoney())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("isDelte", getIsDelte())
                .append("update_user_name", getUpdate_user_name())
                .append("create_user_name", getCreate_user_name())
                .append("goods_name", getGoods_name())
                .toString();
    }
}
