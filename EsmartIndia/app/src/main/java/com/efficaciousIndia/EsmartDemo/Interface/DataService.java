package com.efficaciousIndia.EsmartDemo.Interface;


import com.efficaciousIndia.EsmartDemo.entity.AssignBookDetailLibPojo;
import com.efficaciousIndia.EsmartDemo.entity.AttendanceDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.BamkDetailResponse;
import com.efficaciousIndia.EsmartDemo.entity.BookDetailLibPojo;
import com.efficaciousIndia.EsmartDemo.entity.CategoryDetailLibPojo;
import com.efficaciousIndia.EsmartDemo.entity.ChatDetail;
import com.efficaciousIndia.EsmartDemo.entity.ChatDetailsPojo;
import com.efficaciousIndia.EsmartDemo.entity.DashboardDetailsPojo;
import com.efficaciousIndia.EsmartDemo.entity.DeptDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.EventDetail;
import com.efficaciousIndia.EsmartDemo.entity.EventDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.LeaveDetail;
import com.efficaciousIndia.EsmartDemo.entity.LeaveDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.LibraryDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.LoginDetail;
import com.efficaciousIndia.EsmartDemo.entity.LoginDetailsPojo;
import com.efficaciousIndia.EsmartDemo.entity.MarkAttendence;
import com.efficaciousIndia.EsmartDemo.entity.MonthFeeDetailsResponse;
import com.efficaciousIndia.EsmartDemo.entity.NoticeboardDetail;
import com.efficaciousIndia.EsmartDemo.entity.OnlineClassDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.OnlineClassDetailsPojo;
import com.efficaciousIndia.EsmartDemo.entity.OnlineClassTimetablePojo;
import com.efficaciousIndia.EsmartDemo.entity.PaymentHistoryResponse;
import com.efficaciousIndia.EsmartDemo.entity.PaymentSuccessResponse;
import com.efficaciousIndia.EsmartDemo.entity.ProfileDetailsPojo;
import com.efficaciousIndia.EsmartDemo.entity.SchoolDetailsPojo;
import com.efficaciousIndia.EsmartDemo.entity.StandardDetail;
import com.efficaciousIndia.EsmartDemo.entity.StandardDetailsPojo;
import com.efficaciousIndia.EsmartDemo.entity.StudentStandardwiseDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.SubjectDetailLibPojo;
import com.efficaciousIndia.EsmartDemo.entity.SupportDetailResponse;
import com.efficaciousIndia.EsmartDemo.entity.SyllabusDetailPDFPojo;
import com.efficaciousIndia.EsmartDemo.entity.SyllabusDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.TeacherDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.TeacherLibDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.TimeTableDetailPojo;
import com.efficaciousIndia.EsmartDemo.entity.UnPaidFeeListResponse;
import com.efficaciousIndia.EsmartDemo.entity.VersionDetailPojo;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface DataService {
    //Login
    @GET("Login")
    Observable<SchoolDetailsPojo> getSchoolDetails(@Query("command") String command,
                                                   @Query("GalleryId") String GalleryId);
    @GET("Login")
    Observable<LoginDetailsPojo> getLoginDetails(@Query("command") String command,
                                                 @Query("intUserType_id") String intUserType_id,
                                                 @Query("vchUser_name") String vchUser_name,
                                                 @Query("vchPassword") String vchPassword,
                                                 @Query("intAcademic_id") String intAcademic_id,
                                                 @Query("intSchool_id") String intSchool_id);
//Dashbaord
    @GET("Dashboard")
    Observable<DashboardDetailsPojo> getDashboardDetails(@Query("command") String command,
                                                         @Query("intAcademic_id") String intAcademic_id,
                                                         @Query("intSchool_id") String intSchool_id);

    @GET("Dashboard")
    Observable<DashboardDetailsPojo> getDashboardDetailsTudent(@Query("command") String command,
                                                               @Query("intstanderd_id") String intstanderd_id,
                                                               @Query("intSchool_id") String intSchool_id,
                                                               @Query("intAcademic_id") String intAcademic_id);

    @GET("Dashboard")
    Observable<DashboardDetailsPojo> getDashboardDetails(@Query("command") String command,
                                                         @Query("intAcademic_id") String intAcademic_id,
                                                         @Query("intDivision_id") String intDivision_id,
                                                         @Query("intstanderd_id") String intstanderd_id);


    @GET("Dashboard")
    Observable<DashboardDetailsPojo> getDashboardDetails(@Query("command") String command,
                                                         @Query("intAcademic_id") String intAcademic_id);

    @GET("OnlineClassSchedule")
    Observable<OnlineClassDetailPojo> getOnlineClassDetails(@Query("command") String command,
                                                            @Query("intAcademic_id") String intAcademic_id,
                                                            @Query("intSchool_id") String intSchool_id,
                                                            @Query("intStandard_id") String intStandard_id,
                                                            @Query("dtLecture_date") String dtLecture_date);
    @GET("OnlineClassSchedule")
    Observable<OnlineClassDetailPojo> getOnlineClassDetailsAdmin(@Query("command") String command,
                                                                 @Query("intAcademic_id") String intAcademic_id,
                                                                 @Query("intSchool_id") String intSchool_id,
                                                                 @Query("dtLecture_date") String dtLecture_date,
                                                                 @Query("intStandard_id") String intStandard_id);
    @GET("OnlineClassSchedule")
    Observable<OnlineClassDetailPojo> getOnlineClassDetailsT(@Query("command") String command,
                                                             @Query("intAcademic_id") String intAcademic_id,
                                                             @Query("intSchool_id") String intSchool_id,
                                                             @Query("intTeacher_id") String intTeacher_id,
                                                             @Query("dtLecture_date") String dtLecture_date, @Query("intStandard_id") String intStandard_id);

    @GET("OnlineClassTimetable")
    Observable<OnlineClassTimetablePojo> getOnlineClassTimetableS(@Query("command") String command,
                                                                  @Query("intAcademic_id") String intAcademic_id,
                                                                  @Query("intSchool_id") String intSchool_id,
                                                                  @Query("intStandard_id") String intStandard_id,
                                                                  @Query("dtLecture_date") String dtLecture_date);

    @GET("OnlineClassTimetable")
    Observable<OnlineClassTimetablePojo> getOnlineClassTimetable(@Query("command") String command,
                                                                 @Query("intTeacher_id") String intTeacher_id,
                                                                 @Query("intAcademic_id") String intAcademic_id,
                                                                 @Query("intSchool_id") String intSchool_id,
                                                                 @Query("dtLecture_date") String dtLecture_date,
                                                                 @Query("intStandard_id") String intStandard_id);

    @GET("OnlineClassTimetable")
    Observable<OnlineClassTimetablePojo> getOnlineClassTimetableAdmin(@Query("command") String command,
                                                                      @Query("intAcademic_id") String intAcademic_id,
                                                                      @Query("intSchool_id") String intSchool_id,
                                                                      @Query("dtLecture_date") String dtLecture_date,
                                                                      @Query("intStandard_id") String intStandard_id);

    @GET("OnlineClassTimetable")
    Observable<OnlineClassDetailsPojo> getOnlineTTdetailById(@Query("command") String command,
                                                             @Query("intAcademic_id") String intAcademic_id,
                                                             @Query("intSchool_id") String intSchool_id,
                                                             @Query("intOnlinelecture_id") String intOnlinelecture_id);

//Standard
    @GET("Standard")
    Observable<StandardDetailsPojo> getStandardDetails(@Query("command") String command,
                                                       @Query("intSchool_id") String intSchool_id,
                                                       @Query("intAcademic_id") String intAcademic_id,
                                                       @Query("intStandard_id") String intStandard_id,
                                                       @Query("intDivision_id") String intDivision_id,
                                                       @Query("intTeacher_id") String intTeacher_id,
                                                       @Query("vchType") String vchType);
    //Student StandardWise details
    @GET("StudentStandardWise")
    Observable<StudentStandardwiseDetailPojo> getStudentStandardWiseDetails(@Query("command") String command,
                                                                            @Query("intSchool_id") String intSchool_id,
                                                                            @Query("intAcademic_id") String intAcademic_id,
                                                                            @Query("intStandard_id") String intStandard_id,
                                                                            @Query("intDivision_id") String intDivision_id
    );
    @GET("Attendance")
    Observable<AttendanceDetailPojo> getAttendanceDetails(@Query("command") String command,
                                                          @Query("intschool_id") String intschool_id,
                                                          @Query("intUserType_id") String intUserType_id,
                                                          @Query("intstanderd_id") String intstanderd_id,
                                                          @Query("intdivision_id") String intdivision_id,
                                                          @Query("intAcademic_id") String intAcademic_id,
                                                          @Query("dtDate") String dtDate,
                                                          @Query("status") String status,
                                                          @Query("intUser_id") String intUser_id);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Attendance")
    Observable<ResponseBody>  MarkAttendence(@Query("command") String command,
                                             @Body MarkAttendence attendanceDetail);

    @GET("Attendance")
    Observable<AttendanceDetailPojo> getAttendancedetails(@Query("command") String command,
                                                          @Query("intschool_id") String intschool_id,
                                                          @Query("intAcademic_id") String intAcademic_id,
                                                          @Query("intUser_id") String intUser_id);
    @GET("Profile")
    Observable<ProfileDetailsPojo> getProfiledetails(@Query("command") String command,
                                                     @Query("intschool_id") String intschool_id,
                                                     @Query("intAcademic_id") String intAcademic_id,
                                                     @Query("intUser_id") String intUser_id);

    @GET("TeacherDetail")
    Observable<TeacherDetailPojo> getTeacherDetails(@Query("command") String command,
                                                    @Query("intSchool_id") String intSchool_id);
    @GET("Staff")
    Observable<TeacherDetailPojo> getStaffDetails(@Query("command") String command,
                                                  @Query("intSchool_id") String intSchool_id);

    @GET("SyllabusDetail")
    Observable<SyllabusDetailPojo> getSyllabusDetails(@Query("command") String command,
                                                      @Query("intSchool_id") String intSchool_id,
                                                      @Query("intSubject_id") String intSubject_id,
                                                      @Query("intSTD_id") String intSTD_id);
    @GET("TimeTable")
    Observable<TimeTableDetailPojo> getTimeTableDetails(@Query("command") String command,
                                                        @Query("intSchool_id") String intSchool_id,
                                                        @Query("Day") String Day,
                                                        @Query("intAcademic_id") String intAcademic_id,
                                                        @Query("intStandard_id") String intStandard_id,
                                                        @Query("intTeacher_id") String intTeacher_id,
                                                        @Query("intDivision_id") String intDivision_id);

    @GET("Library")
    Observable<LibraryDetailPojo> getLibraryDetails(@Query("command") String command,
                                                    @Query("intSchool_id") String intSchool_id,
                                                    @Query("intStandard_id") String intStandard_id,
                                                    @Query("intStudent_id") String intStudent_id);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("SyllabusDetailPDF")
    Observable<SyllabusDetailPDFPojo> getSyllabusDetailPDF(@Query("command") String command,
                                                           @Query("intSchool_id") String intSchool_id,
                                                           @Query("intSubject_id") String intSubject_id,
                                                           @Query("intSTD_id") String intSTD_id);

    @POST("Standard")
    Observable<ResponseBody> UpdateDairyHomework(@Query("command") String command,
                                                 @Body StandardDetail standardDetail);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PUT("Standard")
    Observable<ResponseBody> InsertDairyHomework(@Query("command") String command,
                                                 @Body StandardDetail standardDetail);

    @GET("Leave")
    Observable<LeaveDetailPojo> getLeaveDetailDetails(@Query("command") String command,
                                                      @Query("intAcademic_id") String intAcademic_id,
                                                      @Query("intUserType_id") String intUserType_id,
                                                      @Query("intUser_id") String intUser_id,
                                                      @Query("intSchool_id") String intSchool_id,
                                                      @Query("intTeacher_id") String intTeacher_id);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Leave")
    Observable<ResponseBody> updateLeaveDetail(@Query("command") String command,
                                               @Body LeaveDetail leaveDetail);

    @GET("Event")
    Observable<EventDetailPojo> getEventDetails(@Query("command") String command,
                                                @Query("vchStandard_id") String vchStandard_id,
                                                @Query("intAcademic_id") String intAcademic_id,
                                                @Query("intSchool_id") String intSchool_id,
                                                @Query("intUser_id") String intUser_id
    );
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Event")
    Observable<ResponseBody> InsertEvent(@Query("command") String command,
                                         @Body EventDetail eventDetail);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Noticeboard")
    Observable<ResponseBody> InsertNotice(@Query("command") String command,
                                          @Body NoticeboardDetail noticeboardDetail);

    @Multipart
    @POST("FileUpload")
    Observable<ResponseBody> upload(
            @Part MultipartBody.Part file,
            @Query("extension") String extension,
            @Query("EventDescription") String EventDescription,
            @Query("Folder_id") String Folder_id,
            @Query("intSchool_id") String intSchool_id
    );
    @Multipart
    @POST("FileUpload")
    Observable<ResponseBody> upload(
            @Part MultipartBody.Part file,
            @Query("extension") String extension
    );
    @Multipart
    @POST("Profile")
    Observable<ResponseBody> uploadProfile(
            @Part MultipartBody.Part file,
            @Query("vchProfile") String vchProfile,
            @Query("command") String command,
            @Query("intschool_id") String intschool_id,
            @Query("intAcademic_id") String intAcademic_id,
            @Query("intUser_id") String intUser_id
    );

    @GET("Chat")
    Observable<ChatDetailsPojo> getChatUserDetails(@Query("command") String command,
                                                   @Query("intSchool_id") String intSchool_id,
                                                   @Query("intUserid") String intUserid,
                                                   @Query("intStandard_id") String intStandard_id,
                                                   @Query("intDivision_id") String intDivision_id,
                                                   @Query("intAcademic_id") String intAcademic_id);
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("Chat")
    Observable<ResponseBody> SendChatMessage(@Query("command") String command,
                                             @Body ChatDetail chatDetail);
    @POST("Login")
    Observable<ResponseBody> FCMTokenUpdate(@Query("command") String command,
                                            @Body LoginDetail loginDetail);
    @POST("FileUpload")
    Observable<ResponseBody> delete(
            @Query("id") String id,
            @Query("command") String command,
            @Query("intSchool_id") String intSchool_id
    );


    @GET("ProfileDetails")
    Observable<SyllabusDetailPDFPojo> getResultUrl(@Query("command") String command,
                                                   @Query("intSchool_id") String intSchool_id,
                                                   @Query("intSubject_id") String intSubject_id,
                                                   @Query("intSTD_id") String intSTD_id);

    @GET("Library")
    Observable<SubjectDetailLibPojo> getLibraryDetails(@Query("command") String command,
                                                       @Query("intSchool_id") String intSchool_id,
                                                       @Query("intStandard_id") String intStandard_id);
    @GET("Library")
    Observable<CategoryDetailLibPojo> getLibraryCategoryDetails(@Query("command") String command,
                                                                @Query("intSchool_id") String intSchool_id);

    @GET("Library")
    Observable<BookDetailLibPojo> getLibraryBookDetails(@Query("command") String command,
                                                        @Query("intSchool_id") String intSchool_id,
                                                        @Query("intBookLanguage_id") String intBookLanguage_id,
                                                        @Query("intStandard_id") String intStandard_id,
                                                        @Query("intCategory_id") String intCategory_id);

    @GET("Library")
    Observable<AssignBookDetailLibPojo> getLibraryAsiignBookDetails(@Query("command") String command,
                                                                    @Query("intSchool_id") String intSchool_id,
                                                                    @Query("intStudent_id") String intStudent_id,
                                                                    @Query("intstandard_id") String intStandard_id,
                                                                    @Query("intBookLanguage_id") String ntBookLanguage_id,
                                                                    @Query("dtAssigned_Date") String dtAssigned_Date,
                                                                    @Query("dtReturn_date") String dtReturn_date);

    @GET("Library")
    Observable<AssignBookDetailLibPojo> getLibraryAsiignBookDetailsStudent(@Query("command") String command,
                                                                           @Query("intstandard_id") String intstandard_id,
                                                                           @Query("intSchool_id") String intSchool_id,
                                                                           @Query("intStudent_id") String intStudent_id,
                                                                           @Query("dtAssigned_Date") String dtAssigned_Date,
                                                                           @Query("dtReturn_date") String dtReturn_date,
                                                                           @Query("intBookLanguage_id") String intBookLanguage_id);
    @GET("APKVersion")
    Observable<VersionDetailPojo> getVersionDetails(@Query("command") String command,
                                                    @Query("intschool_id") String intSchool_id);

    @GET("Library")
    Observable<SubjectDetailLibPojo> getLibraryDetailsTeacher(@Query("command") String command,
                                                              @Query("intSchool_id") String intSchool_id);

    @GET("OnlineClassTimetable")
    Observable<DeptDetailPojo> getDepartment(@Query("command") String command,
                                             @Query("intSchool_id") String intSchool_id,
                                             @Query("intusertype_id") String intusertype_id);

    @GET("Library")
    Observable<TeacherLibDetailPojo> getLibraryAsiignBookDetailsTeacher(@Query("command") String command,
                                                                        @Query("intSchool_id") String intSchool_id,
                                                                        @Query("intTeacher_id") String intTeacher_id,
                                                                        @Query("intDepartment_id") String intDepartment_id,
                                                                        @Query("intBookLanguage_id") String intBookLanguage_id,
                                                                        @Query("dtAssigned_Date") String dtAssigned_Date,
                                                                        @Query("dtReturn_date") String dtReturn_date);


    @GET("SchoolFeeDetails")
    Observable<UnPaidFeeListResponse> getUnpaidFeeList(@Query("command") String str, @Query("intStudentID_Number") String str2, @Query("intstandard_id") String str3, @Query("intStudentID") String str4, @Query("intAcademic_id") String str5, @Query("intSchool_id") String str6);
    @GET("SchoolFeeDetails")
    Observable<MonthFeeDetailsResponse> getMonthlyDetailFeeList(@Query("command") String str, @Query("intStudentID_Number") String str2, @Query("intstandard_id") String str3, @Query("intStudentID") String str4, @Query("intAcademic_id") String str5, @Query("intSchool_id") String str6, @Query("intDivision_id") String str7, @Query("Month") String str8);
    @GET("Support")
    Observable<SupportDetailResponse> getSupport(@Query("command") String command,
                                                 @Query("intSchool_id") String intSchool_id);
    @POST("SchoolFeeDetails")
    Observable<PaymentSuccessResponse> insertPayentDetails(@Query("command") String command,
                                                           @Query("intStudentID_Number") String intStudentID_Number,
                                                           @Query("intstandard_id") String intstandard_id,
                                                           @Query("intStudentID") String intStudentID,
                                                           @Query("intAcademic_id") String intAcademic_id,
                                                           @Query("intSchool_id") String intSchool_id,
                                                           @Query("month") String month,
                                                           @Query("intRoll_no") String intRoll_no,
                                                           @Query("intDivision_id") String intDivision_id);

    //Login
    @GET("BankMaster")
    Observable<BamkDetailResponse> getBankDetails(@Query("command") String command,
                                                  @Query("intSchool_id") String intSchool_id);

    @POST("SchoolFeeDetails")
    Observable<PaymentSuccessResponse> insertPayentDetails(@Query("command") String command,
                                                           @Query("feeId") String feeId,
                                                           @Query("intStudentId") String intStudentId,
                                                           @Query("trasactionId") String trasactionId);
    @GET("SchoolFeeDetails")
    Observable<PaymentHistoryResponse> getUnpaidPainFeeList(@Query("command") String str, @Query("intStudentID_Number") String str2, @Query("intstandard_id") String str3, @Query("intStudentID") String str4, @Query("intAcademic_id") String str5, @Query("intSchool_id") String str6, @Query("intDivision_id") String str7);

}
