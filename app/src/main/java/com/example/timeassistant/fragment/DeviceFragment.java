package com.example.timeassistant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeassistant.R;
import com.example.timeassistant.activity.MainActivity;
import com.example.timeassistant.activity.SearchDeviceActivity;
import com.example.timeassistant.adapter.RVDevicesAdapter;
import com.example.timeassistant.entity.DevicesListItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class DeviceFragment extends Fragment {


    @BindView(R.id.rv_connected)
    RecyclerView rvConnected;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_toolbar_right)
    Button btnToolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public DeviceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_devices, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).initToolbar(view, "Device", null, getActivity().getDrawable(R.drawable.ic_search_white_24dp));
        initDevices();
        return view;
    }

    @OnClick(R.id.btn_toolbar_right)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), SearchDeviceActivity.class);
        getActivity().startActivity(intent);
    }


    private void initDevices() {
        List<DevicesListItem> list = new ArrayList<>();
        list.add(new DevicesListItem("Win60-192.168.0.0", DevicesListItem.CONNECTED));
        RVDevicesAdapter adapter = new RVDevicesAdapter(getContext(), list);
        adapter.setOnItemClickListenter(new RVDevicesAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {

            }
        });

//        //实例化ItemTouchHelper
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MessageItemTouchCallBack(adapter));
//        //将ItemTouchHelper和RecyclerView进行关联
//        itemTouchHelper.attachToRecyclerView(rvConnected);

        rvConnected.setAdapter(adapter);
        rvConnected.setLayoutManager(new LinearLayoutManager(getContext()));
        rvConnected.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

}
