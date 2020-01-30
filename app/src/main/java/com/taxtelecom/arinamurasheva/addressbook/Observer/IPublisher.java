package com.taxtelecom.arinamurasheva.addressbook.Observer;

public interface IPublisher {
    void subscribe(ISubscriber subscriber);
    void notifySubscribers();
}
