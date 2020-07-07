package com.archi.architecture;

import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.archi.architecture.lottery.net.LotteryFetcher;
import com.archi.architecture.math.SortUtil;
import com.archi.architecture.time.TimeMonitorConfig;
import com.archi.architecture.time.TimeMonitorManager;
import com.archi.architecture.view.LotteryActivity;
import com.archi.database.async.AsyncThreadTask;
import com.archi.database.common.CommonStoreTask;
import com.archi.database.info.IInfo;

import org.json.JSONException;

import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    Fragment mFragment;
    private TextView displayArea;
    private int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_MainActivityTheme2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(MainActivity.this);

        findViewById(R.id.add).setOnClickListener(onClickListener);

        findViewById(R.id.display).setOnClickListener(onClickListener);

        findViewById(R.id.lottery).setOnClickListener(onClickListener);

        //mFragment = getSupportFragmentManager().findFragmentById(R.id.bt_fragment);

        displayArea = findViewById(R.id.display_area);

        displayArea.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (mCount == 0) {
                    TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("MainActivity-onPreDraw-Over");
                    mCount++;
                }
                return true;
            }
        });

        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("MainActivity-onCreate-Over");
    }

    @Override
    protected void onStart() {
        super.onStart();
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("MainActivity-onStart-Over");
    }

    @Override
    protected void onResume() {
        super.onResume();
        SortUtil.test();
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("MainActivity-onResume-Over");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("MainActivity-onWindowFocusChanged-Over");
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add:
                    LotteryFetcher.testSSData();
//                    CommonStoreTask.getInstance().save("test at " + System.currentTimeMillis());
                    break;
                case R.id.display:
                    display();
                    break;
                case R.id.lottery:
                    Intent intent = new Intent(MainActivity.this, LotteryActivity.class);
                    startActivity(intent);
                default:
                    break;
            }
        }
    };

    private void display() {
        AsyncThreadTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final StringBuilder stringBuilder = new StringBuilder();
                    List<IInfo> infos = CommonStoreTask.getInstance().getAll();
                    for (IInfo info : infos) {
                        stringBuilder.append(info.toJson()).append("\n");
                    }

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            displayArea.setText(stringBuilder);
                        }
                    });
                }catch(JSONException js) {
                    js.printStackTrace();
                }
            }
        });
    }

}
