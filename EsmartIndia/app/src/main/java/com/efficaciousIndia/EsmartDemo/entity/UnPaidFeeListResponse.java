package com.efficaciousIndia.EsmartDemo.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul on 13,July,2020
 */
public class UnPaidFeeListResponse {
    @SerializedName("MonthWiseFee")
    @Expose
    private List<MonthWiseFee> monthWiseFee = null;

    public List<MonthWiseFee> getMonthWiseFee() {
        return monthWiseFee;
    }

    public void setMonthWiseFee(List<MonthWiseFee> monthWiseFee) {
        this.monthWiseFee = monthWiseFee;
    }

    public class MonthWiseFee {

        @SerializedName("Month")
        @Expose
        private String month;
        @SerializedName("Sum")
        @Expose
        private String sum;

        public boolean isSelected() {
            return IsSelected;
        }

        public void setSelected(boolean selected) {
            IsSelected = selected;
        }

        private boolean IsSelected=false;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

    }
}
