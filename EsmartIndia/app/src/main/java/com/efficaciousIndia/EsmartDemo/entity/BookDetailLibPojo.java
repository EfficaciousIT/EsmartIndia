package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 21,May,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookDetailLibPojo {

    @SerializedName("LibraryDetail")
    @Expose
    private List<LibraryDetail> libraryDetail = null;

    public List<LibraryDetail> getLibraryDetail() {
        return libraryDetail;
    }

    public void setLibraryDetail(List<LibraryDetail> libraryDetail) {
        this.libraryDetail = libraryDetail;
    }


    public class LibraryDetail {

        @SerializedName("intBookDetails_id")
        @Expose
        private Integer intBookDetailsId;
        @SerializedName("vchAccessionNo")
        @Expose
        private String vchAccessionNo;
        @SerializedName("vchBookDetails_bookName")
        @Expose
        private String vchBookDetailsBookName;
        @SerializedName("intBookQuantity")
        @Expose
        private Object intBookQuantity;
        @SerializedName("intBookPrice")
        @Expose
        private String intBookPrice;
        @SerializedName("intStandard_id")
        @Expose
        private String intStandardId;
        @SerializedName("intCategory_id")
        @Expose
        private String intCategoryId;
        @SerializedName("intBookEdition_id")
        @Expose
        private String intBookEditionId;
        @SerializedName("intBook_publication_id")
        @Expose
        private String intBookPublicationId;
        @SerializedName("intBook_Author_id")
        @Expose
        private String intBookAuthorId;
        @SerializedName("intBookLanguage_id")
        @Expose
        private String intBookLanguageId;
        @SerializedName("vchBookDetails_Status")
        @Expose
        private String vchBookDetailsStatus;
        @SerializedName("vchBookDetails_Remark")
        @Expose
        private Object vchBookDetailsRemark;

        @SerializedName("isOpen")
        @Expose
        private Boolean isOpen;

        public Integer getIntBookDetailsId() {
            return intBookDetailsId;
        }

        public void setIntBookDetailsId(Integer intBookDetailsId) {
            this.intBookDetailsId = intBookDetailsId;
        }

        public String getVchAccessionNo() {
            return vchAccessionNo;
        }

        public void setVchAccessionNo(String vchAccessionNo) {
            this.vchAccessionNo = vchAccessionNo;
        }

        public String getVchBookDetailsBookName() {
            return vchBookDetailsBookName;
        }

        public void setVchBookDetailsBookName(String vchBookDetailsBookName) {
            this.vchBookDetailsBookName = vchBookDetailsBookName;
        }

        public Object getIntBookQuantity() {
            return intBookQuantity;
        }

        public void setIntBookQuantity(Object intBookQuantity) {
            this.intBookQuantity = intBookQuantity;
        }

        public String getIntBookPrice() {
            return intBookPrice;
        }

        public void setIntBookPrice(String intBookPrice) {
            this.intBookPrice = intBookPrice;
        }

        public String getIntStandardId() {
            return intStandardId;
        }

        public void setIntStandardId(String intStandardId) {
            this.intStandardId = intStandardId;
        }

        public String getIntCategoryId() {
            return intCategoryId;
        }

        public void setIntCategoryId(String intCategoryId) {
            this.intCategoryId = intCategoryId;
        }

        public String getIntBookEditionId() {
            return intBookEditionId;
        }

        public void setIntBookEditionId(String intBookEditionId) {
            this.intBookEditionId = intBookEditionId;
        }

        public String getIntBookPublicationId() {
            return intBookPublicationId;
        }

        public void setIntBookPublicationId(String intBookPublicationId) {
            this.intBookPublicationId = intBookPublicationId;
        }

        public String getIntBookAuthorId() {
            return intBookAuthorId;
        }

        public void setIntBookAuthorId(String intBookAuthorId) {
            this.intBookAuthorId = intBookAuthorId;
        }

        public String getIntBookLanguageId() {
            return intBookLanguageId;
        }

        public void setIntBookLanguageId(String intBookLanguageId) {
            this.intBookLanguageId = intBookLanguageId;
        }

        public String getVchBookDetailsStatus() {
            return vchBookDetailsStatus;
        }

        public void setVchBookDetailsStatus(String vchBookDetailsStatus) {
            this.vchBookDetailsStatus = vchBookDetailsStatus;
        }

        public Object getVchBookDetailsRemark() {
            return vchBookDetailsRemark;
        }

        public void setVchBookDetailsRemark(Object vchBookDetailsRemark) {
            this.vchBookDetailsRemark = vchBookDetailsRemark;
        }

        public Boolean getOpen() {
            return isOpen;
        }

        public void setOpen(Boolean open) {
            isOpen = open;
        }
    }
}
