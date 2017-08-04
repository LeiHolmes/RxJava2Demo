package com.holmeslei.rxjava2demo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.holmeslei.rxjava2demo.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description:   RxJava2中弥补了RxJava1中背压问题的不足，将观察者模式分为两种
 * 1. Observable/Observer：不支持背压
 * 2. Flowable/Subscriber：支持背压
 * author         xulei
 * Date           2017/8/3 14:07
 */
public class ObserveModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observe_mode);
        range();
        create();
        maybeObserver();
    }

    private void range() {
        Flowable.range(0, 10).subscribe(new MySubscription());
    }

    /**
     * Flowable通过create创建需指定背压策略
     */
    private void create() {
        Flowable.create((FlowableOnSubscribe<Integer>) e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
            e.onNext(4);
            e.onComplete();
        }, BackpressureStrategy.BUFFER)
                .subscribe(new MySubscription());
    }

    /**
     * 注意：onSubscribe方法中由于调用request(n)后会立刻执行onNext()，故尽量保证request(n)为最后执行
     */
    private class MySubscription implements Subscriber<Integer> {
        Subscription subscription;

        //订阅之后首先调用此方法，传入Subscription参数可用于请求数据或取消订阅
        @Override
        public void onSubscribe(Subscription s) {
            Log.e("rx2_test", "onSubscribe");
            subscription = s;
            //这里可进行一些初始化操作
            subscription.request(1);
        }

        @Override
        public void onNext(Integer integer) {
            Log.e("rx2_test", "onNext：" + integer);
            subscription.request(1);
        }

        @Override
        public void onError(Throwable t) {
            Log.e("rx2_test", "onError：" + t.getMessage());
        }

        @Override
        public void onComplete() {
            Log.e("rx2_test", "onComplete");
        }
    }

    /**
     * 还有一类观察者模式
     * 1. Single/SingleObserver：订阅后只能接收到一次
     * 2. Completable/CompletableObserver：只能接收到完成(onComplete)和错误(onError)
     * 3. Maybe/MaybeObserver：只能接收到一次true或false的数据
     * 不用于发送大量数据，主要用于判断单一状态
     */
    private void maybeObserver() {
        //以MaybeObserver模拟判断登录状态为例子
        Maybe.just(isLoginSuccess())
                .observeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("rx2_test", "maybeObserver：onSubscribe");
                    }

                    @Override
                    public void onSuccess(@NonNull Boolean aBoolean) {
                        if (aBoolean) {
                            Log.e("rx2_test", "maybeObserver：登录成功");
                        } else {
                            Log.e("rx2_test", "maybeObserver：登录失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("rx2_test", "maybeObserver：onError：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("rx2_test", "maybeObserver：onComplete");
                    }
                });
    }

    private boolean isLoginSuccess() {
        //这里可去服务器请求登录状态
        return true;
    }
}
