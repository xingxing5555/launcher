package com.example.admin.launcherdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.admin.launcherdemo.adpter.AppAdapter;
import com.example.admin.launcherdemo.utils.AppInfo;
import com.example.admin.launcherdemo.utils.AppUtils;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyApp = (RecyclerView) findViewById(R.id.recy_app);
        recyApp.setLayoutManager(new GridLayoutManager(this, 3));
        List<AppInfo> appInfos = AppUtils.scanInstallApp(getPackageManager());
        recyApp.setAdapter(new AppAdapter(this, appInfos));
    }

    @Override
    public void onBackPressed() {
//            super.onBackPressed();
    }
}
