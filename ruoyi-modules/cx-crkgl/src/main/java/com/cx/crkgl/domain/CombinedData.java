package com.cx.crkgl.domain;

import java.util.List;

public class CombinedData {
    private List<String> gIds;
    private List<String> inCode;
    private List<String> outCode;

    private List<String> inIds;

    private List<String> outIds;
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

    public List<String> getOutCode() {
        return outCode;
    }

    public void setOutCode(List<String> outCode) {
        this.outCode = outCode;
    }

    public List<String> getOutIds() {
        return outIds;
    }

    public void setOutIds(List<String> outIds) {
        this.outIds = outIds;
    }

    public void setReviewStatus(Boolean reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}

