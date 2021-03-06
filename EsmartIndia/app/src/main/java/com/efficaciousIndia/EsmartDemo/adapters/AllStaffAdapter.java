package com.efficaciousIndia.EsmartDemo.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.efficaciousIndia.EsmartDemo.R;
import com.efficaciousIndia.EsmartDemo.Tab.Attendence_sliding_tab;
import com.efficaciousIndia.EsmartDemo.activity.MainActivity;
import com.efficaciousIndia.EsmartDemo.entity.TeacherDetail;
import com.efficaciousIndia.EsmartDemo.webApi.RetrofitInstance;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllStaffAdapter extends RecyclerView.Adapter<AllStaffAdapter.TeacherListHolder> implements Filterable {

    private final Context mcontext;
    ArrayList<TeacherDetail> menus = new ArrayList<TeacherDetail>();
    public ArrayList<TeacherDetail> categories;
    public ArrayList<TeacherDetail> orig;
    String url = "";
    private static final String PREFRENCES_NAME = "myprefrences";
    SharedPreferences settings;
    String page_selected, role_id;
    public AllStaffAdapter(Context context, ArrayList<TeacherDetail> Menus, String page) {
        this.mcontext = context;
        this.menus = Menus;
        this.page_selected = page;
    }

    @Override
    public TeacherListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.allteacher_name, parent, false);
        return new TeacherListHolder(view);
    }

    @Override
    public void onBindViewHolder(final TeacherListHolder holder, final int position) {
        try {
            settings = mcontext.getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
            role_id = settings.getString("TAG_USERTYPEID", "");
            holder.id.setText(menus.get(position).getDesignation());
            holder.name.setText(menus.get(position).getName());

            holder.teacher_image.setVisibility(View.VISIBLE);
            String url = RetrofitInstance.Image_URL + menus.get(position).getVchProfile();
            Glide.with(mcontext)
                    .load(url)
                    .fitCenter()// image url
                    .error(R.mipmap.profile)
                    .into(holder.teacher_image);

            holder.linear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (page_selected.contentEquals("attendence")) {
                        try {
                            Toast.makeText(mcontext, "Selected list view", Toast.LENGTH_SHORT).show();
                            Attendence_sliding_tab attendence_sliding_tab = new Attendence_sliding_tab();
                            Bundle args = new Bundle();
                            args.putString("Staffname", menus.get(position).getName());
                            args.putString("Staff_id", String.valueOf(menus.get(position).getStaffid()));
                            if (role_id.contentEquals("6") || role_id.contentEquals("7") || role_id.contentEquals("3")) {
                                args.putString("intSchool_id", String.valueOf(menus.get(position).getIntSchoolId()));
                            }
                            args.putString("attendence", "staff_attendence");
                            attendence_sliding_tab.setArguments(args);
                            MainActivity.fragmentManager.beginTransaction().replace(R.id.content_main, attendence_sliding_tab).commitAllowingStateLoss();

                        } catch (Exception ex) {

                        }

                    }
                }
            });
        } catch (Exception ex) {

        }
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<TeacherDetail> results = new ArrayList<TeacherDetail>();
                if (orig == null)
                    orig = menus;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final TeacherDetail g : orig) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString().toLowerCase()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                try {
                    menus = (ArrayList<TeacherDetail>) results.values;
                    notifyDataSetChanged();
                } catch (Exception ex) {

                }

            }
        };
    }

    class TeacherListHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView name;
        CircleImageView teacher_image;
        LinearLayout linear;
        TeacherListHolder(View itemView) {
            super(itemView);
            teacher_image = (CircleImageView)itemView.findViewById(R.id.teacher_image);
            id = (TextView) itemView.findViewById(R.id.Dept_allteacher1);
            name = (TextView) itemView.findViewById(R.id.name_allteacher1);
            linear = (LinearLayout) itemView.findViewById(R.id.Linear_allteacher1);

        }


    }

}