package swa.credit.handler;

import swa.credit.model.Event;

public interface EventHandler<T extends Event, R extends Event> {

    R handleEvent(T event);
}
