package com.taxtelecom.arinamurasheva.addressbook.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private final Map<String, List<IEventSubscriber>> allSubscribers = new HashMap<>();

    public EventManager(String... events) {
        for (String event : events) {
            this.allSubscribers.put(event, new ArrayList<IEventSubscriber>());
        }
    }

    public void subscribe(String eventType, IEventSubscriber subscriber) {

        synchronized (allSubscribers) {
            List<IEventSubscriber> concreteSubscribers = allSubscribers.get(eventType);
            concreteSubscribers.add(subscriber);
        }

    }

    public void unsubscribe(String eventType, IEventSubscriber subscriber) {

        synchronized (allSubscribers) {
            List<IEventSubscriber> concreteSubscribers = allSubscribers.get(eventType);
            concreteSubscribers.remove(subscriber);
        }

    }

    public void notifySuccess(String eventType) {

        synchronized (allSubscribers) {
            List<IEventSubscriber> concreteSubscribers = allSubscribers.get(eventType);
            for (IEventSubscriber subscriber : concreteSubscribers) {
                subscriber.updateSuccess(eventType);

            }
        }
    }

    public void notifyFail(String eventType) {

        synchronized (allSubscribers) {
            List<IEventSubscriber> concreteSubscribers = allSubscribers.get(eventType);
            for (IEventSubscriber subscriber : concreteSubscribers) {
                subscriber.updateFail(eventType);

            }
        }
    }
}
