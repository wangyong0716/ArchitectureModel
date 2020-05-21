package com.archi.architecture;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.archi.database.TestUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestUtil.log();
    }
}
