package com.cx.wms.domain;

import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PurchaseDateQuery extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String TodayOrYesterday;
    private String BeginDay;
    private String EndDay;

    public String getTodayOrYesterday() {
        return TodayOrYesterday;
    }

    public void setTodayOrYesterday(String todayOrYesterday) {
        TodayOrYesterday = todayOrYesterday;
    }

    public String getBeginDay() {
        return BeginDay;
    }

    public void setBeginDay(String beginDay) {
        BeginDay = beginDay;
    }

    public String getEndDay() {
        return EndDay;
    }

    public void setEndDay(String endDay) {
        EndDay = endDay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("TodayOrYesterday", getTodayOrYesterday())
                .append("BeginDay", getBeginDay())
                .append("EndDay", getEndDay())
                .toString();
    }
}
