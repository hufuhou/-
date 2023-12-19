package com.cx.wms.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;

public class HpGood extends BaseEntity {
    private String g_code;
    private String g_name;

    @Override
    public String toString() {
        return "HpGood{" +
                "g_code='" + g_code + '\'' +
                ", g_name='" + g_name + '\'' +
                '}';
    }

    public String getG_code() {
        return g_code;
    }

    public void setG_code(String g_code) {
        this.g_code = g_code;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }
}
