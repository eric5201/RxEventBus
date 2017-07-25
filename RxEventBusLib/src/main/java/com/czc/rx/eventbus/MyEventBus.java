package com.czc.rx.eventbus;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

/**
 * Created by Eric on 2017/7/24.
 */

public class MyEventBus {

    public static final void register(@NonNull Class clazz, RxEventListener listener) throws IllegalStateException {
        RxEventBus.getInstance().register(clazz, listener);
    }

    public static final boolean isRegister(@NonNull Class clazz, RxEventListener listener) {
        return RxEventBus.getInstance().isRegister(clazz, listener);
    }

    public static final void unregister(@NonNull Class clazz, RxEventListener listener) {
        RxEventBus.getInstance().unregister(clazz, listener);
    }

    public static final EventBusBuilder postThreadMode(Scheduler threadMode) {
        return new EventBusBuilder().postThreadMode(threadMode);
    }

    public static final EventBusBuilder responseThreadMode(Scheduler threadMode) {
        return new EventBusBuilder().responseThreadMode(threadMode);
    }

    public static final void post(Object event) {
        RxEventBus.getInstance().post(event);
    }

    public static class EventBusBuilder {
        private Scheduler postThreadMode;
        private Scheduler responseThreadMode;

        EventBusBuilder() {

        }

        public EventBusBuilder postThreadMode(Scheduler threadMode) {
            this.postThreadMode = threadMode;
            return this;
        }

        public EventBusBuilder responseThreadMode(Scheduler threadMode) {
            this.responseThreadMode = threadMode;
            return this;
        }

        public void post(Object event) {
            RxEventBus.getInstance().post(event, postThreadMode, responseThreadMode);
        }
    }

}
