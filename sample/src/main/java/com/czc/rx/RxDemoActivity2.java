package com.czc.rx;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.czc.rx.eventbus.MyEventBus;
import com.czc.rx.eventbus.RxEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Eric on 2017/7/21.
 */

public class RxDemoActivity2 extends Activity implements RxEventListener<TestEvent> {

    @BindView(R.id.btn_1)
    Button btn1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rx_demo_activity2);
        ButterKnife.bind(this);

        MyEventBus.register(TestEvent.class, this);
    }

    @Override
    protected void onDestroy() {
        MyEventBus.unregister(TestEvent.class, this);
        super.onDestroy();
    }

    @OnClick(R.id.btn_1)
    public void onPostClick(View view) {
        MyEventBus.postThreadMode(Schedulers.io()).responseThreadMode(AndroidSchedulers.mainThread()).post(new TestEvent(true));
    }

    @Override
    public void onPostEvent(TestEvent event) {
        Log.e("eric-act2", "[onEventPost] => " + event.isBol);
    }
}
