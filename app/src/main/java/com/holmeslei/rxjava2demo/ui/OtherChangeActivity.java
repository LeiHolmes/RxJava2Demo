package com.holmeslei.rxjava2demo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.holmeslei.rxjava2demo.R;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class OtherChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_change);
        actionChange();
        functionChange();
        doOnCancel();
        schedulerChange();
        subscriptionChange();
    }


    /**
     * Action更新
     * Action0->Action
     * Action1->Consumer
     * Action2->BiConsumer
     * ActionN->Consumer<Object[]>
     * 去除了Action3 - Action9
     */
    private void actionChange() {
        Flowable.just(1, 2, 3, 4, 5)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("rx2_test", "actionChange：" + integer);
                    }
                });
    }

    /**
     * Function更新
     * Func1->Function
     * Func2->BiFunction
     * Func3-Func9->Function3-Function9
     * FuncN->Function<Object[], R>
     * Action与Function都增加了throws Exception，免去了try-catch
     */
    private void functionChange() {
        Flowable.just(1, 2, 3, 4, 5)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        return "xulei" + integer;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("rx2_test", "functionChange：" + s);
                    }
                });
    }

    /**
     * doOnCancel/doOnDispose/unsubscribeOn
     * 取消订阅时回调
     */
    private void doOnCancel() {
        //take操作符会取消之后未发送的时间，因此会出发doOnCancel()。
        Flowable.just(1, 2, 3, 4, 5)
                .doOnCancel(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e("rx2_test", "回调doOnCancel");
                    }
                })
                .take(2)
                .subscribe((integer) -> Log.e("rx2_test", "doOnCancel：" + integer));
        //注意take与doOnCancel先后顺序，先take后doOnCancel取消时则不会回到doOnCancel()
    }

    /**
     * 线程调度更新
     */
    private void schedulerChange() {
        //去除Schedulers.immediate()与Schedulers.test()
        //io.reactivex.Scheduler这个抽象类支持直接调度自定义线程任务
    }

    /**
     * Subscription更新
     */
    private void subscriptionChange() {
        //Subscription改名为Disposable
        //CompositeSubscription改名为CompositeDisposable
        //SerialSubscription和MultipleAssignmentSubscription被合并到了SerialDisposable里.set()方法会处理掉就的值，而replace()方法不会。
        //RefCountSubscription被移除了
    }
}
