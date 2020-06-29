package com.archi.architecture.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.archi.architecture.R;
import com.archi.architecture.pager.PagerActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ViewFragment extends Fragment {
    private static final String TAG = "ViewFragment";
    private Unbinder mUnbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_func_layout, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.fragment_bt)
    void handleClick() {
        Log.i(TAG, "handle bt clicked!");
        Intent intent = new Intent(getActivity(), PagerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
