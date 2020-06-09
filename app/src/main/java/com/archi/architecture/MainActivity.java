package com.archi.architecture;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.archi.database.DbConfig;
import com.archi.database.DbManager;
import com.archi.database.TaskManager;
import com.archi.database.async.AsyncThreadTask;
import com.archi.database.common.CommonInfo;
import com.archi.database.common.CommonTask;
import com.archi.database.info.IInfo;

import org.json.JSONException;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView displayArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.add).setOnClickListener(onClickListener);

        findViewById(R.id.display).setOnClickListener(onClickListener);

        displayArea = findViewById(R.id.display_area);
        DbManager.getInstance().setDbConfig(new DbConfig.DBConfigBuilder().setAppContext(getApplicationContext()).build());
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add:
                    CommonTask.getInstance().save("test at " + System.currentTimeMillis());
                    break;
                case R.id.display:
                    display();
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
                    List<IInfo> infos = CommonTask.getInstance().getAll();
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
