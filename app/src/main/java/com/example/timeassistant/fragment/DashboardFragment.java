package com.example.timeassistant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeassistant.R;
import com.example.timeassistant.activity.MainActivity;
import com.example.timeassistant.adapter.DashboardRVAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends Fragment {

    @BindView(R.id.iv_dashboard_ad)
    ImageView ivDashboardAd;
    @BindView(R.id.rv_dashboard_gird)
    RecyclerView rvDashboardGird;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).initToolbar(view, "Dashboard", null, null);
        initRecycler();
        return view;
    }

    private void initRecycler() {
        DashboardRVAdapter adapter=new DashboardRVAdapter(getContext());
        rvDashboardGird.setLayoutManager(new GridLayoutManager(getActivity(),3, RecyclerView.VERTICAL,false));
        rvDashboardGird.setAdapter(adapter);

    }

}
