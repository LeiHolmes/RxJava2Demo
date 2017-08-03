package com.holmeslei.rxjava2demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.holmeslei.rxjava2demo.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:   使用RxJava2解决背压问题
 * author         xulei
 * Date           2017/7/2 9 16:17
 */
public class BackPressureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_pressure);
    }

    
}
