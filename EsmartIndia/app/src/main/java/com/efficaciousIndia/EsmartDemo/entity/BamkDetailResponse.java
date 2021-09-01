package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 14,September,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BamkDetailResponse {

    @SerializedName("APKVersion")
    @Expose
    private List<APKVersion> aPKVersion = null;

    public List<APKVersion> getAPKVersion() {
        return aPKVersion;
    }

    public void setAPKVersion(List<APKVersion> aPKVersion) {
        this.aPKVersion = aPKVersion;
    }
    public class APKVersion {

        @SerializedName("int_ID")
        @Expose
        private Integer intID;
        @SerializedName("vchBank_name")
        @Expose
        private String vchBankName;
        @SerializedName("vchIFSC")
        @Expose
        private String vchIFSC;
        @SerializedName("vchBankAc_no")
        @Expose
        private String vchBankAcNo;
        @SerializedName("vchAccountholder_name")
        @Expose
        private String vchAccountholderName;

        public Integer getIntID() {
            return intID;
        }

        public void setIntID(Integer intID) {
            this.intID = intID;
        }

        public String getVchBankName() {
            return vchBankName;
        }

        public void setVchBankName(String vchBankName) {
            this.vchBankName = vchBankName;
        }

        public String getVchIFSC() {
            return vchIFSC;
        }

        public void setVchIFSC(String vchIFSC) {
            this.vchIFSC = vchIFSC;
        }

        public String getVchBankAcNo() {
            return vchBankAcNo;
        }

        public void setVchBankAcNo(String vchBankAcNo) {
            this.vchBankAcNo = vchBankAcNo;
        }

        public String getVchAccountholderName() {
            return vchAccountholderName;
        }

        public void setVchAccountholderName(String vchAccountholderName) {
            this.vchAccountholderName = vchAccountholderName;
        }

    }
}
