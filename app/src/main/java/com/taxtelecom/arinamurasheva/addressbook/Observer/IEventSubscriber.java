package com.taxtelecom.arinamurasheva.addressbook.Observer;

public interface IEventSubscriber {
    void updateSuccess(String eventType);
    void updateFail(String eventType);
}
