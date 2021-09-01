package com.efficaciousIndia.EsmartDemo.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.efficaciousIndia.EsmartDemo.Interface.DataService;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.adapters.BookListAdapter;
import com.efficaciousIndia.EsmartDemo.adapters.CategoryAdapter;
import com.efficaciousIndia.EsmartDemo.common.ConnectionDetector;
import com.efficaciousIndia.EsmartDemo.entity.BookDetailLibPojo;
import com.efficaciousIndia.EsmartDemo.entity.CategoryDetailLibPojo;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rahul on 20,May,2020
 */
public class LibCategoryBooklist_fragment extends AppCompatActivity {

    RecyclerView category_list,book_list;
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String Schooli_id;
    ConnectionDetector cd;
    private ProgressDialog progress;
    String value, academic_id, role_id, userid, Standard_id;
    CategoryAdapter madapter;
    BookListAdapter bookListAdapter;
    TextView sub_tv;
    String SubjectName,SubjectId;
    ImageView arrow_img;
    RelativeLayout std_relative;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_categorywise_book_fragment);
        category_list=findViewById(R.id.category_list);
        book_list=findViewById(R.id.book_list);
        sub_tv=findViewById(R.id.sub_tv);
        arrow_img=findViewById(R.id.arrow_img);
        std_relative=findViewById(R.id.std_relative);
        settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        Schooli_id = settings.getString("TAG_SCHOOL_ID", "");
        userid = settings.getString("TAG_USERID", "");
        role_id = settings.getString("TAG_USERTYPEID", "");
        academic_id = settings.getString("TAG_ACADEMIC_ID", "");
        cd = new ConnectionDetector(this);
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.setMessage("loading...");
        if(role_id.contentEquals("1")||role_id.contentEquals("2")){
            Standard_id = settings.getString("TAG_STANDERDID", "");
        }else {
            Standard_id=getIntent().getExtras().getString("Standard_id");
        }
        SubjectId = getIntent().getExtras().getString("inBookLangId");
        SubjectName = getIntent().getExtras().getString("SubjectName");
        sub_tv.setText(SubjectName);

        std_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(book_list.getVisibility()== View.VISIBLE){
                    book_list.setVisibility(View.GONE);
                    arrow_img.animate().rotation(180).start();
                }else {
                    book_list.setVisibility(View.VISIBLE);
                    arrow_img.animate().rotation(0).start();
                }
            }
        });

        CategoryAsync ();
    }
    public void  CategoryAsync (){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
           Observable<CategoryDetailLibPojo> call = service.getLibraryCategoryDetails("select",Schooli_id);
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<CategoryDetailLibPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(CategoryDetailLibPojo body) {
                    try {
                        generateCategoryList((ArrayList<CategoryDetailLibPojo.LibraryDetail>) body.getLibraryDetail());
                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(LibCategoryBooklist_fragment.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(LibCategoryBooklist_fragment.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
            progress.dismiss();
        }
    }

    public void generateCategoryList(ArrayList<CategoryDetailLibPojo.LibraryDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                madapter = new CategoryAdapter(this,taskListDataList);
                category_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                category_list.setAdapter(madapter);
                CategoryWiseBookAsync (taskListDataList.get(0).getIntCategoryId());
            } else {
                madapter = new CategoryAdapter(this,taskListDataList);
                category_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                category_list.setAdapter(madapter);
                Toast toast = Toast.makeText(this,
                        "No Data Available",
                        Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.no_data_available);
                toast.show();
            }
            madapter.setOnClickListener(new CategoryAdapter.OptionDataClick() {
                @Override
                public void onOptionDataClick(int id) {
                    CategoryWiseBookAsync (id);
                }
            });

        } catch (Exception ex) {
            progress.dismiss();
            Toast.makeText(this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

    public void  CategoryWiseBookAsync(int id){
        try {
            DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
            Observable<BookDetailLibPojo> call;
            if(role_id.contentEquals("5")){
                call = service.getLibraryBookDetails("GetCategoryWiseDataForAdmin",Schooli_id,SubjectId,Standard_id, String.valueOf(id));
            }else {
                call = service.getLibraryBookDetails("GetCategoryWiseData",Schooli_id,SubjectId,Standard_id, String.valueOf(id));
            }

            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<BookDetailLibPojo>() {
                @Override
                public void onSubscribe(Disposable disposable) {
                    progress.show();
                }

                @Override
                public void onNext(BookDetailLibPojo body) {
                    try {
                        generateBookList((ArrayList<BookDetailLibPojo.LibraryDetail>) body.getLibraryDetail());
                    } catch (Exception ex) {
                        progress.dismiss();
                        Toast.makeText(LibCategoryBooklist_fragment.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(Throwable t) {
                    progress.dismiss();
                    Toast.makeText(LibCategoryBooklist_fragment.this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {
                    progress.dismiss();
                }
            });
        } catch (Exception ex) {
            ex.getMessage();
            progress.dismiss();
        }
    }
    public void generateBookList(ArrayList<BookDetailLibPojo.LibraryDetail> taskListDataList) {
        try {
            if ((taskListDataList != null && !taskListDataList.isEmpty())) {
                bookListAdapter = new BookListAdapter(this,taskListDataList);
                book_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                book_list.setAdapter(bookListAdapter);
            } else {
                bookListAdapter = new BookListAdapter(this,taskListDataList);
                book_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                book_list.setAdapter(bookListAdapter);
                Toast toast = Toast.makeText(this,
                        "No Data Available",
                        Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toastView.setBackgroundResource(R.drawable.no_data_available);
                toast.show();
            }

        } catch (Exception ex) {
            progress.dismiss();
            Toast.makeText(this, "Response taking time seems Network issue!", Toast.LENGTH_SHORT).show();
        }
    }

}
