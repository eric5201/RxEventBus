package com.czc.rx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.czc.rx.eventbus.MyEventBus;
import com.czc.rx.eventbus.RxEventListener;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Eric on 2017/7/19.
 */

public class RxDemoActivity extends Activity implements RxEventListener<TestEvent> {

    private String TAG = "RxDemoActivity";

    @BindView(R.id.btn_1)
    Button btn1;

    @BindView(R.id.btn_2)
    Button btn2;

    @BindView(R.id.btn_3)
    Button btn3;

    @BindView(R.id.btn_4)
    Button btn4;

    @BindView(R.id.btn_5)
    Button btn5;

    @BindView(R.id.btn_6)
    Button btn6;

    @BindView(R.id.btn_7)
    Button btn7;

    @BindView(R.id.btn_8)
    Button btn8;

    @BindView(R.id.btn_9)
    Button btn9;

    @BindView(R.id.btn_10)
    Button btn10;

    @BindView(R.id.btn_11)
    Button btn11;

    @BindView(R.id.btn_12)
    Button btn12;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rx_demo_activity);
        ButterKnife.bind(this);

        MyEventBus.register(TestEvent.class, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        MyEventBus.unregister(TestEvent.class, this);
        super.onDestroy();
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
            R.id.btn_8, R.id.btn_9, R.id.btn_10, R.id.btn_11, R.id.btn_12})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                testOne();
                break;

            case R.id.btn_2:
                testTwo();
                break;

            case R.id.btn_3:
                testThree();
                break;

            case R.id.btn_4:
                testFour();
                break;

            case R.id.btn_5:
                testFive();
                break;

            case R.id.btn_6:
                testSix();
                break;

            case R.id.btn_7:
                testSeven();
                break;

            case R.id.btn_8:
                testEight();
                break;

            case R.id.btn_9:
                testNine();
                break;

            case R.id.btn_10:
                testTen();
                break;

            case R.id.btn_11:
                testEleven();
                break;

            case R.id.btn_12:
                testTwelve();
                break;
        }
    }

    public void testOne() {
        Observable.just("one", "two", "three", "four", "five")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "[testOne] -> [onSubscribe].. ");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "[testOne] -> [onNext].. str:" + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "[testOne] -> [onError].. ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "[testOne] -> [onComplete].. ");
                    }
                });
    }

    public void testTwo() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1001);
                e.onNext(1002);
                e.onNext(1003);
                e.onComplete();
                e.onNext(1004);
                e.onNext(1005);
                e.onNext(1006);
            }
        }).subscribe(new Observer<Integer>() {
            Disposable disposable;

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "[testTwo] -> [create] -> [onError].. " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "[testTwo] -> [create] -> [onComplete].. ");
                disposable.dispose();
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "[testTwo] -> [create] -> [onSubscribe].. ");
                this.disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "[testTwo] -> [create] -> [onNext].. integer:" + integer);

            }
        });
    }

    public void testThree() {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("cbd");
        list.add("lg");
        Flowable.just(list).flatMap(new Function<List<String>, Publisher<String>>() {
            @Override
            public Publisher<String> apply(@NonNull List<String> strings) throws Exception {
                return Flowable.fromIterable(strings);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.d(TAG, "[testThree] -> [accept] -> val:" + s);
            }
        });
    }

    public void testFour() {
        Observable.range(3, 7).filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer != 5;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "[testFour] -> [range] -> [onError].. " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "[testFour] -> [range] -> [onComplete].. ");
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "[testFour] -> [range] -> [onSubscribe].. ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "[testFour] -> [range] -> [onNext].. integer:" + integer);

            }
        });
    }

    public void testFive() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1001);
                e.onNext(1002);
                e.onNext(1003);
                e.onComplete();
                e.onNext(1004);
                e.onNext(1005);
                e.onNext(1006);
            }
        }).filter(new AppendOnlyLinkedArrayList.NonThrowingPredicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer != 1003;
            }
        }).subscribe(new Observer<Integer>() {
            Disposable disposable;

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "[testFive] -> [create] -> [onError].. " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "[testFive] -> [create] -> [onComplete].. ");
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "[testFive] -> [create] -> [onSubscribe].. ");
                this.disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "[testFive] -> [create] -> [onNext].. integer:" + integer);

            }
        });
    }

    public void testSix() {
        Flowable.just("abc").map(new Function<String, Integer>() {
            @Override
            public Integer apply(@NonNull String s) throws Exception {
                return s.hashCode();
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(@NonNull Integer integer) throws Exception {
                Log.d(TAG, "[testSix] -> [accept]  integer:" + integer);
            }
        });
    }

    public void testSeven() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(1);
        list.add(5);

        Flowable.just(list).flatMap(new Function<List<Integer>, Publisher<Integer>>() {
            @Override
            public Publisher<Integer> apply(List<Integer> integers) throws Exception {
                return Flowable.fromIterable(integers);
            }
        })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "[testSeven] -> [accept]  integer:" + integer);
                    }
                });
    }

    public void testEight() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("exception:" + (1 / 0));
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "[testEight] -> [create] -> [onSubscribe].. ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "[testEight] -> [create] -> [onNext].. integer:" + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        Log.d(TAG, "[testEight] -> [create] -> [onError].. " + t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "[testEight] -> [create] -> [onComplete].. ");
                    }
                });
    }

    public void testNine() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("将会在3秒后显示");
                e.onNext("ittianyu");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(RxDemoActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void testTen() {
        Observable.just("abc").map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String s) throws Exception {
                return "file://sdcard/aios/bitmap/" + s;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "[testTen] -> [onSubscribe] -> ");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d(TAG, "[testTen] -> [onNext] -> " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "[testTen] -> [onError] -> " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "[testTen] -> [onComplete] -> ");
                    }
                });
    }

    public void testEleven() {
//        Observable.just("abc").map(new Function<String, String>() {
//            @Override
//            public String apply(@NonNull String s) throws Exception {
//                return "file://sdcard/aios/bitmap/" + s;
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//                        Log.d(TAG, "[testTen] -> [accept] -> " + s);
//                    }
//                });

        MyEventBus.postThreadMode(Schedulers.io()).post(new TestEvent(false));
    }

    private void testTwelve() {
        startActivity(new Intent(this, RxDemoActivity2.class));
    }

    @Override
    public void onPostEvent(TestEvent event) {
        Log.e("eric-act1", "[onEventPost] => " + event.isBol);
    }
}
