package com.aucloud.event.event;

import org.springframework.context.ApplicationEvent;

public class GenericEvent<T> extends ApplicationEvent {

    private final T payload;

    public GenericEvent(Object source, T payload) {
        super(source);
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }
}