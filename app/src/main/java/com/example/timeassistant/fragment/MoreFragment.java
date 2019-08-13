package com.example.timeassistant.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timeassistant.R;
import com.example.timeassistant.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreFragment extends Fragment {


    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.rv_more_menu)
    RecyclerView rvMoreMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).initToolbar(view, "More", null, null);


        return view;
    }


}
