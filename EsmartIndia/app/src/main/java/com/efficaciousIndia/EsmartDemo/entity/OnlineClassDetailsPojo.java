package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 14,May,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OnlineClassDetailsPojo {

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

    @SerializedName("Notice")
    @Expose
    private String notice;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("vchStandard_name")
    @Expose
    private String vchStandardName;
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
    @SerializedName("intSubject_id")
    @Expose
    private Integer intSubjectId;
    @SerializedName("intOnlineNotice_id")
    @Expose
    private Integer intOnlineNoticeId;
    @SerializedName("vchMeetingId")
    @Expose
    private String vchMeetingId;
    @SerializedName("vchpassword")
    @Expose
    private String vchpassword;
    @SerializedName("intOnlinelecture_id")
    @Expose
    private Integer intOnlinelectureId;
    @SerializedName("Teacher_name")
    @Expose
    private String teacherName;

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getVchStandardName() {
        return vchStandardName;
    }

    public void setVchStandardName(String vchStandardName) {
        this.vchStandardName = vchStandardName;
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

    public Integer getIntSubjectId() {
        return intSubjectId;
    }

    public void setIntSubjectId(Integer intSubjectId) {
        this.intSubjectId = intSubjectId;
    }

    public Integer getIntOnlineNoticeId() {
        return intOnlineNoticeId;
    }

    public void setIntOnlineNoticeId(Integer intOnlineNoticeId) {
        this.intOnlineNoticeId = intOnlineNoticeId;
    }

    public String getVchMeetingId() {
        return vchMeetingId;
    }

    public void setVchMeetingId(String vchMeetingId) {
        this.vchMeetingId = vchMeetingId;
    }

    public String getVchpassword() {
        return vchpassword;
    }

    public void setVchpassword(String vchpassword) {
        this.vchpassword = vchpassword;
    }

    public Integer getIntOnlinelectureId() {
        return intOnlinelectureId;
    }

    public void setIntOnlinelectureId(Integer intOnlinelectureId) {
        this.intOnlinelectureId = intOnlinelectureId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

}
}
