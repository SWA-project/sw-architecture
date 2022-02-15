package swa.credit.handler;

import swa.credit.dto.Event;

public interface EventHandler<T extends Event, R extends Event> {

    R handleEvent(T event);
}
