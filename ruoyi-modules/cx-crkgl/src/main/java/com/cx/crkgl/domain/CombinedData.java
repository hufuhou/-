package com.cx.crkgl.domain;

import java.util.List;

public class CombinedData {
    private List<String> gIds;
    private List<String> inCode;

    private List<String> inIds;


    private Boolean reviewStatus;

    public List<String> getgIds() {
        return gIds;
    }

    public void setgIds(List<String> gIds) {
        this.gIds = gIds;
    }

    public List<String> getInCode() {
        return inCode;
    }

    public void setInCode(List<String> inCode) {
        this.inCode = inCode;
    }

    public List<String> getInIds() {
        return inIds;
    }

    public void setInIds(List<String> inIds) {
        this.inIds = inIds;
    }

    public Boolean getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(Boolean reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}

