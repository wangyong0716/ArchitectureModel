package com.archi.architecture.pager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.archi.architecture.R;
import com.archi.architecture.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PagerFragment extends Fragment {
    private Context mContext;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.rcv)
    RecyclerView mRecyclerView;

    String arg;

    private Unbinder mUnbinder;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_pager_layout, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        arg = getArguments().getString("arg");
        mTitle.setText(arg);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL,
                getResources().getDimensionPixelSize(R.dimen.divider_height), Color.DKGRAY));
        mRecyclerView.setAdapter(new FAdapter());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    public class FAdapter extends RecyclerView.Adapter<FAdapter.FHolder> {
        private List<String> data = new ArrayList<String>(){
            {
                add(arg + " : item1");
                add(arg + " : 第二条");
            }
        };

        @Override
        public int getItemCount() {
            return data.size();
        }

        @NonNull
        @Override
        public FHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FHolder(View.inflate(mContext, R.layout.item_fragment_rcv_layout, null));
        }

        @Override
        public void onBindViewHolder(@NonNull FHolder holder, int position) {
            holder.id.setText(String.valueOf(position));
            holder.content.setText(data.get(position));
        }

        public class FHolder extends RecyclerView.ViewHolder{
            @BindView(R.id._id)
            TextView id;
            @BindView(R.id.content)
            TextView content;

            public FHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
