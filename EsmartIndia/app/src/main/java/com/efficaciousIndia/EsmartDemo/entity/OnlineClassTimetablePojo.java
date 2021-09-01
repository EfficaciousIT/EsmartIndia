package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 12,May,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnlineClassTimetablePojo {

    @SerializedName("OnlineTimetable")
    @Expose
    private List<OnlineTimetable> onlineTimetable = null;

    public List<OnlineTimetable> getOnlineTimetable() {
        return onlineTimetable;
    }

    public void setOnlineTimetable(List<OnlineTimetable> onlineTimetable) {
        this.onlineTimetable = onlineTimetable;
    }

    public class OnlineTimetable {

        @SerializedName("intOnlinelecture_id")
        @Expose
        private Integer intOnlinelectureId;
        @SerializedName("vchLecture_name")
        @Expose
        private String vchLectureName;
        @SerializedName("vchstandard_name")
        @Expose
        private String vchstandardName;
        @SerializedName("vchDivisionName")
        @Expose
        private String vchDivisionName;
        @SerializedName("dtLecture_date")
        @Expose
        private String dtLectureDate;
        @SerializedName("dtFromTime")
        @Expose
        private String dtFromTime;
        @SerializedName("dtToTime")
        @Expose
        private String dtToTime;
        @SerializedName("Teacher_name")
        @Expose
        private String teacherName;
        @SerializedName("vchSubjectName")
        @Expose
        private String vchSubjectName;

        public Integer getIntOnlinelectureId() {
            return intOnlinelectureId;
        }

        public void setIntOnlinelectureId(Integer intOnlinelectureId) {
            this.intOnlinelectureId = intOnlinelectureId;
        }

        public String getVchLectureName() {
            return vchLectureName;
        }

        public void setVchLectureName(String vchLectureName) {
            this.vchLectureName = vchLectureName;
        }

        public String getVchstandardName() {
            return vchstandardName;
        }

        public void setVchstandardName(String vchstandardName) {
            this.vchstandardName = vchstandardName;
        }

        public String getVchDivisionName() {
            return vchDivisionName;
        }

        public void setVchDivisionName(String vchDivisionName) {
            this.vchDivisionName = vchDivisionName;
        }

        public String getDtLectureDate() {
            return dtLectureDate;
        }

        public void setDtLectureDate(String dtLectureDate) {
            this.dtLectureDate = dtLectureDate;
        }

        public String getDtFromTime() {
            return dtFromTime;
        }

        public void setDtFromTime(String dtFromTime) {
            this.dtFromTime = dtFromTime;
        }

        public String getDtToTime() {
            return dtToTime;
        }

        public void setDtToTime(String dtToTime) {
            this.dtToTime = dtToTime;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getVchSubjectName() {
            return vchSubjectName;
        }

        public void setVchSubjectName(String vchSubjectName) {
            this.vchSubjectName = vchSubjectName;
        }

    }
}