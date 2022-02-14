package swa.order.consumer;

import swa.order.model.Event;

public interface EventConsumer<T extends Event> {

    void consumeEvent(T event);
}
