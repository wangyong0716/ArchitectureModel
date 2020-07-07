package com.archi.architecture;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.archi.architecture.util.DeviceUtil;

public class PreLoadDexActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);//取消掉系统默认的动画。
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.predexlayout);

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    long time = System.currentTimeMillis();
                    MultiDex.install(getApplication());
                    Log.i("lz", "PreLoadDexActivityCostTime:" + (System.currentTimeMillis() - time));
                    SharedPreferences sp = getSharedPreferences(
                            DeviceUtil.getVersionName(PreLoadDexActivity.this), MODE_MULTI_PROCESS);
                    sp.edit().putBoolean("dexoptdone", true).commit();
                    finish();
                } catch (Exception e) {
                    Log.e("loadDex", e.getLocalizedMessage());
                    finish();
                }
            }
        }.start();
    }

    private void killCurrentProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
