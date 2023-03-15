package com.youxuegu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.youxuegu.MainActivity;
import com.youxuegu.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private TextView tv_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }


    private void init(){
        //获取显示版本号信息的控件
        tv_version = findViewById(R.id.tv_version);
        try {
            //获取程序包信息
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),0);
            //将程序版本号信息系设置到界面控件上
            tv_version.setText("V"+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            tv_version.setText("V");
            throw new RuntimeException(e);
        }
        Timer timer = new Timer();
        //通过TimerTask类实现界跳转功能
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        //设置程序延迟3秒后执行
        timer.schedule(task,3000);
    }
}