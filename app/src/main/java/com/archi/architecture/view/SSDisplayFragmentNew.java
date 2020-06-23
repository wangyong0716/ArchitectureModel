package com.archi.architecture.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.archi.architecture.R;
import com.archi.architecture.lottery.LotteryDataKeeper;
import com.archi.architecture.lottery.SSLottery;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SSDisplayFragmentNew extends Fragment {
    @BindView(R.id.display)
    RecyclerView mDisplayList;
    private RecycleLotteryAdapter mLotteryAdapter;
    private Unbinder mUnbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_ssq_layout_new, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mDisplayList.setLayoutManager(new LinearLayoutManager(getContext()));
        //添加默认分割线：高度为2px，颜色为灰色
        mDisplayList.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL,
                getResources().getDimensionPixelSize(R.dimen.divider_height), Color.DKGRAY));
        mLotteryAdapter = new RecycleLotteryAdapter(getContext());
        mDisplayList.setAdapter(mLotteryAdapter);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mLotteryAdapter.update(LotteryDataKeeper.getInstance().getAfter(2020, 1, 1));
            }
        }, 1000);
    }

    public class RecycleLotteryAdapter extends RecyclerView.Adapter<RecycleLotteryAdapter.RecycleLotteryViewHolder> {
        private Context mContext;
        private List<SSLottery> mLotteries = new ArrayList<>();

        public RecycleLotteryAdapter(Context context) {
            mContext = context;
        }

        private void update(List<SSLottery> lotteries) {
            mLotteries.clear();
            mLotteries.addAll(lotteries);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecycleLotteryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecycleLotteryViewHolder(View.inflate(mContext, R.layout.item_ssl_layout, null));
        }

        @Override
        public void onBindViewHolder(@NonNull RecycleLotteryViewHolder viewHolder, int position) {
            SSLottery lottery = mLotteries.get(position);
            viewHolder.date.setText(lottery.getDateString());
            viewHolder.seriesNum.setText(String.valueOf(lottery.getSeriesNum()));
            viewHolder.reds.setText(lottery.getRedsStr());
            viewHolder.blue.setText(String.valueOf(lottery.getBlue()));
            viewHolder.poolBonus.setText(String.valueOf(lottery.getPoolBonus()));
            viewHolder.firstCount.setText(String.valueOf(lottery.getFirstPot().getCount()));
            viewHolder.firstMoney.setText(String.valueOf(lottery.getFirstPot().getMoney()));
            viewHolder.secondCount.setText(String.valueOf(lottery.getSecondPot().getCount()));
            viewHolder.secondMoney.setText(String.valueOf(lottery.getSecondPot().getMoney()));
            viewHolder.sellMoney.setText(String.valueOf(lottery.getSellMoney()));
        }

        @Override
        public int getItemCount() {
            return mLotteries.size();
        }

        public class RecycleLotteryViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.date) TextView date;
            @BindView(R.id.series_num) TextView seriesNum;
            @BindView(R.id.reds) TextView reds;
            @BindView(R.id.blue) TextView blue;
            @BindView(R.id.pool_bonus) TextView poolBonus;
            @BindView(R.id.win1_count) TextView firstCount;
            @BindView(R.id.win1_money) TextView firstMoney;
            @BindView(R.id.win2_count) TextView secondCount;
            @BindView(R.id.win2_money) TextView secondMoney;
            @BindView(R.id.sell_money) TextView sellMoney;
            public RecycleLotteryViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
