package swa.order.handler;

import swa.order.dto.Event;

public interface EventConsumer<T extends Event> {

    void consumeEvent(T event);
}
