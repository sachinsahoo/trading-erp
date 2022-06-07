package com.example.entity.dto.report;

import com.example.entity.report.ProductMonthlyReport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BarChart implements Serializable {

        List<String> label;
        List<ChartDataSet> dataSets;


    public BarChart() {
        label = new ArrayList<>();
        dataSets = new ArrayList<>();
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public List<ChartDataSet> getDataSets() {
        return dataSets;
    }

    public void setDataSets(List<ChartDataSet> dataSets) {
        this.dataSets = dataSets;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object object) {
        return EqualsBuilder.reflectionEquals(this, object, false);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


}
