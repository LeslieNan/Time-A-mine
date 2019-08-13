package com.example.timeassistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeassistant.R;
import com.example.timeassistant.entity.DevicesListItem;

import java.util.List;

public class RVDevicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    public final int TYPE_HEADER = 0;
    public final int TYPE_NORMAL = 1;

    private Context mContext;
    private List<DevicesListItem> mList;

    public RVDevicesAdapter(Context mContext, List<DevicesListItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new HeaderHolder(LayoutInflater.from(mContext).inflate(R.layout.item_header, parent, false));
        } else {
            return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_devices_list, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.tvConnectedDevice.setText("");
        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.tvDevice.setText(mList.get(position - 1).getName());
            viewHolder.tvDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_NORMAL;
        }
    }


    /**
     * 实现点击事件设置的外抛
     */
    public interface OnItemClickListener{
        void onItemClickListener(int position);
    }

    public OnItemClickListener listener;

    public void setOnItemClickListenter(OnItemClickListener listenter){
        this.listener=listenter;
    }





    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDevice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDevice = itemView.findViewById(R.id.tv_devices);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        TextView tvConnectedDevice;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            tvConnectedDevice = itemView.findViewById(R.id.tv_connected_device);
        }
    }
}
