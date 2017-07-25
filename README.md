# RxEventBus: 基于`Rxjava`实现

构想源于`EventBus`, 但`EventBus`的调试和来源追溯一直是个诟病, 然后决定自己写一个.

## 参考
-[RxJava](https://github.com/ReactiveX/RxJava)
-[RxAndroid](https://github.com/ReactiveX/RxAndroid)
-[EventBus](https://github.com/greenrobot/EventBus)

## 简单使用说明

默认线程模式
```java
  MyEventBus.post(new TestEvent(true));
```

指定发送线程
```java
  MyEventBus
    .postThreadMode(Schedulers.io())
    .post(new TestEvent(true));
```

指定响应回调时切换到主线程, 不管之前在什么线程做的发送及逻辑操作, 响应时都处于主线程, 所以回调方法内勿做耗时操作以免阻塞.
```java
  MyEventBus
    .responseThreadMode(AndroidSchedulers.mainThread())
    .post(new TestEvent(true));
```

同时指定两头的线程模式
```java
  MyEventBus
    .postThreadMode(Schedulers.io())
    .responseThreadMode(Schedulers.io())
    .post(new TestEvent(true));
```

## LICENSE

    Copyright 2017 The RxEventBus authors

