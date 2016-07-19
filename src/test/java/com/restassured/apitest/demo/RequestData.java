package com.restassured.apitest.demo;

import java.util.List;

public class RequestData {
    private String range;
    private String majorDimension;
    private List values;

    public String getRange() {
        return this.range;
    }

    public String getMajorDimension() {
        return this.majorDimension;
    }

    public List getValues() {
        return this.values;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public void setMajorDimension(String majorDimension) {
        this.majorDimension = majorDimension;
    }

    public void setValues(List values) {
        this.values = values;
    }
}
