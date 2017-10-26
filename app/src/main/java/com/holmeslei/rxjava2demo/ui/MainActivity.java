package com.holmeslei.rxjava2demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.holmeslei.rxjava2demo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:   RxJava2
 * 本demo通过与RxJava1的比较来学习RxJava2的更新之处
 * author         xulei
 * Date           2017/7/29 16:00
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_observe_mode_change, R.id.bt_other_change, R.id.bt_count_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_observe_mode_change:
                startActivity(new Intent(this, ObserveModeActivity.class));
                break;
            case R.id.bt_other_change:
                startActivity(new Intent(this, OtherChangeActivity.class));
                break;
            case R.id.bt_count_down:
                startActivity(new Intent(this, CountDownActivity.class));
                break;
        }
    }
}
