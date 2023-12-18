package com.cx.wms.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

public class WareHouse extends BaseEntity {
    private static final long serialVersionUID = 2L;

    private String w_id;
    private String w_name;

    public String getW_id() {
        return w_id;
    }

    public void setW_id(String w_id) {
        this.w_id = w_id;
    }

    public String getW_name() {
        return w_name;
    }

    public void setW_name(String w_name) {
        this.w_name = w_name;
    }

    @Override
    public String toString() {
        return "WareHouse{" +
                "w_id='" + w_id + '\'' +
                ", w_name='" + w_name + '\'' +
                '}';
    }
}
