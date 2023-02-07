package com.jon.javareactor;

import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

public class MyObserver {
    public static void main(String[] args) {
        Flux<Object> flux = new Flux<Object>() {
            @Override
            public void subscribe(CoreSubscriber<? super Object> coreSubscriber) {
                System.out.println(coreSubscriber);
            }
        };
    }

}
