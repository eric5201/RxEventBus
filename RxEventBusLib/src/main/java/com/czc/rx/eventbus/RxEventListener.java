package com.czc.rx.eventbus;

/**
 * Created by Eric on 2017/7/24.
 */

public interface RxEventListener<T> {

    void onPostEvent(T event);
}
