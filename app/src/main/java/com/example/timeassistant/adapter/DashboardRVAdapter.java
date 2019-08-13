package com.example.timeassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeassistant.R;
import com.example.timeassistant.activity.BaseApplication;
import com.example.timeassistant.activity.RuleActivity;
import com.example.timeassistant.activity.UserInfoActivity;

import java.util.List;

public class DashboardRVAdapter extends RecyclerView.Adapter<DashboardRVAdapter.ViewHolder> {

    private Context mContext;

    private String[] names;
    private int[] images;

    public DashboardRVAdapter(Context mContext) {
        this.mContext = mContext;
        names = mContext.getResources().getStringArray(R.array.dashboardBtnsTitle);
        images = new int[names.length];
        TypedArray typedArray = mContext.getResources().obtainTypedArray(R.array.dashboardBtnsIcon);//获得任意类型
        for (int i = 0; i < typedArray.length(); i++) {
            images[i] = typedArray.getResourceId(i, 0);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dashboard_gird, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivImage.setImageResource(images[position]);
        holder.tvName.setText(names[position]);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(mContext, UserInfoActivity.class);
                        break;
                    case 1:
                        intent.setClass(mContext, RuleActivity.class);
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View item;
        ImageView ivImage;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvName = itemView.findViewById(R.id.tv_name);
            item = itemView;
        }
    }

}
