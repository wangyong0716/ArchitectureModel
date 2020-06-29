package com.archi.architecture.pager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.archi.architecture.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PagerActivity extends FragmentActivity {
    @BindView(R.id.vp)
    ViewPager mViewPager;

    private List<PagerFragment> mViewFragments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_layout);
        ButterKnife.bind(this);

        initFragments();

        mViewPager.setAdapter(new VPAdapter(getSupportFragmentManager(), 1));
    }

    @OnClick(R.id.bt1)
    void handleClick1() {
        mViewPager.setCurrentItem(0);
    }

    @OnClick(R.id.bt2)
    void handleClick2() {
        mViewPager.setCurrentItem(1);
    }

    @OnClick(R.id.bt3)
    void handleClick3() {
        mViewPager.setCurrentItem(2);
    }

    @OnClick(R.id.bt4)
    void handleClick4() {
        mViewPager.setCurrentItem(3);
    }

    private void initFragments() {
        mViewFragments = new ArrayList<>();

        mViewFragments.add(getFragment(0));
        mViewFragments.add(getFragment(1));
        mViewFragments.add(getFragment(2));
        mViewFragments.add(getFragment(3));
    }

    private PagerFragment getFragment(int i) {
        PagerFragment fragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("arg", "pager" + i);
        fragment.setArguments(bundle);
        return fragment;
    }


    class VPAdapter extends FragmentStatePagerAdapter{
        public VPAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mViewFragments.get(position);
        }

        @Override
        public int getCount() {
            return mViewFragments.size();
        }
    }

}
