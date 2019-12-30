package com.star.e_learning.bean;

public class MultiComment {

    private int max;
    private int medium;
    private int min;
    private String maxDesc;
    private String mediumDesc;
    private String minDesc;

    public MultiComment(int max, String maxDesc, int medium, String mediumDesc, int min, String minDesc){
        this.max = max;
        this.medium = medium;
        this.min = min;
        this.maxDesc = maxDesc;
        this.mediumDesc = mediumDesc;
        this.minDesc = minDesc;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getMaxDesc() {
        return maxDesc;
    }

    public void setMaxDesc(String maxDesc) {
        this.maxDesc = maxDesc;
    }

    public String getMediumDesc() {
        return mediumDesc;
    }

    public void setMediumDesc(String mediumDesc) {
        this.mediumDesc = mediumDesc;
    }

    public String getMinDesc() {
        return minDesc;
    }

    public void setMinDesc(String minDesc) {
        this.minDesc = minDesc;
    }
}
