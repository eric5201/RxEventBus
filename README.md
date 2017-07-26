# RxEventBus: 基于`Rxjava`实现

构想源于`EventBus`, 但`EventBus`的调试和来源追溯一直是个诟病, 然后决定自己写一个.

## 参考
-[RxJava](https://github.com/ReactiveX/RxJava)

-[RxAndroid](https://github.com/ReactiveX/RxAndroid)

-[EventBus](https://github.com/greenrobot/EventBus)

## 关于注册

为了完善eventbus的可调试和来源追溯将回调设计成了`listener`形式. 现有设计每个event消息都需要一对一注册, 通常在实际应用过程中不同界面不同逻辑中处理eventbus消息都不多一般只有一到两个, 我想这个情况应该还是能接受的.
`注意: 每个event的register与unregister必需成对出现, 重复注册相同event会引发异常抛出, 只注册不解注册容易造成内存泄漏.`
```java
  RxEventListener<TestEvent> listener = new RxEventListener<TestEvent>(){
    @Override
    public void onPostEvent(TestEvent event) {
        // todo 事件处理
    }
  };
  MyEventBus.register(TestEvent.class, listener)
```

```java
  MyEventBus.unregister(TestEvent.class, listener)
```

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

