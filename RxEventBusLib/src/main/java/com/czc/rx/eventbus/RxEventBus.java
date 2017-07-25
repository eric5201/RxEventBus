package com.czc.rx.eventbus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Eric on 2017/7/24.
 */

class RxEventBus {

    private Map<Class, List<RxEventListener>> callList = new LinkedHashMap<>();

    private RxEventBus() {

    }

    static final RxEventBus getInstance() {
        return EventBusHolder.instance;
    }

    public void register(@NonNull Class clazz, RxEventListener listener) throws IllegalStateException {
        if (null != callList.get(clazz)) {
            if (callList.get(clazz).contains(listener)) {
                throw new IllegalStateException("listener is already register");
            } else {
                callList.get(clazz).add(listener);
            }
        } else {
            List<RxEventListener> list = new ArrayList<>();
            list.add(listener);
            callList.put(clazz, list);
        }
    }

    public boolean isRegister(@NonNull Class clazz, RxEventListener listener) {
        if (null != callList.get(clazz)) {
            if (callList.get(clazz).contains(listener)) {
                return true;
            }
        }
        return false;
    }

    public void unregister(@NonNull Class clazz, RxEventListener listener) {
        if (null != callList.get(clazz)) {
            callList.get(clazz).remove(listener);
            if (callList.get(clazz).size() <= 0) {
                callList.remove(clazz);
            }
        }
    }

    public void post(Object event) {
        Observable.just(event).subscribe(getObserver());
    }

    public void post(Object event, Scheduler postThreadMode, Scheduler responseThreadMode) {
        Observable bus = Observable.just(event);
        if (null != postThreadMode) {
            bus = bus.subscribeOn(postThreadMode);
        }
        if (null != responseThreadMode) {
            bus = bus.observeOn(responseThreadMode);
        }
        bus.subscribe(getObserver());
    }

    protected Observer getObserver() {
        return new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                if (null != callList.get(o.getClass())) {
                    for (int i = (callList.get(o.getClass()).size() - 1); i >= 0; i--) {
                        callList.get(o.getClass()).get(i).onPostEvent(o);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    static class EventBusHolder {
        static final RxEventBus instance = new RxEventBus();
    }
}
