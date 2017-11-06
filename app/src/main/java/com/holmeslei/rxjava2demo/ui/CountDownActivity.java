package com.holmeslei.rxjava2demo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.holmeslei.rxjava2demo.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 利用RxJava2实现倒计时，获取验证码的功能
 */
public class CountDownActivity extends AppCompatActivity {
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    private Disposable mdDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get_code)
    public void onViewClicked() {
        //点击后置为不可点击状态
        btnGetCode.setEnabled(false);
        btnGetCode.setClickable(false);
        //从0开始发射11个数字为：0-10依次输出，延时0s执行，每1s发射一次。
        mdDisposable = Flowable.intervalRange(0, 11, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        btnGetCode.setText("重新获取(" + (10 - aLong) + ")");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        //倒计时完毕置为可点击状态
                        btnGetCode.setEnabled(true);
                        btnGetCode.setClickable(true);
                        btnGetCode.setText("获取验证码");
                    }
                })
                .subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
    }
}
