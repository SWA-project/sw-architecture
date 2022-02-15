package swa.order.handler;

import swa.order.dto.Event;

public interface EventHandler<T extends Event, R extends Event> {
    R handleEvent(T event);
}
