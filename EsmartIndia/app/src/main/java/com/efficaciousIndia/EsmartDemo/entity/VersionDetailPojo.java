package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 25,May,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class VersionDetailPojo {

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

        @SerializedName("intVersion_id")
        @Expose
        private Integer intVersionId;
        @SerializedName("vchVersion_name")
        @Expose
        private String vchVersionName;

        public Integer getIntVersionId() {
            return intVersionId;
        }

        public void setIntVersionId(Integer intVersionId) {
            this.intVersionId = intVersionId;
        }

        public String getVchVersionName() {
            return vchVersionName;
        }

        public void setVchVersionName(String vchVersionName) {
            this.vchVersionName = vchVersionName;
        }

    }

}
