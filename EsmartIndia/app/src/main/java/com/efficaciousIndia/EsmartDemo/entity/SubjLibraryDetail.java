package com.efficaciousIndia.EsmartDemo.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 30,May,2020
 */
public class SubjLibraryDetail {

    @SerializedName("intBookLanguage_id")
    @Expose
    private Integer intBookLanguageId;
    @SerializedName("vchBookLanguage_name")
    @Expose
    private String vchBookLanguageName;

    public Integer getIntBookLanguageId() {
        return intBookLanguageId;
    }

    public void setIntBookLanguageId(Integer intBookLanguageId) {
        this.intBookLanguageId = intBookLanguageId;
    }

    public String getVchBookLanguageName() {
        return vchBookLanguageName;
    }

    public void setVchBookLanguageName(String vchBookLanguageName) {
        this.vchBookLanguageName = vchBookLanguageName;
    }

    public SubjLibraryDetail(Integer intBookLanguageId, String vchBookLanguageName) {
        this.intBookLanguageId = intBookLanguageId;
        this.vchBookLanguageName = vchBookLanguageName;
    }

    @Override
    public String toString() {
        return vchBookLanguageName;
    }
}
