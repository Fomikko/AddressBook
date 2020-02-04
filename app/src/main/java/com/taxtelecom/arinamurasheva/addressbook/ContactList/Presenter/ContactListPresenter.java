package com.taxtelecom.arinamurasheva.addressbook.ContactList.Presenter;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.View.IContactListView;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.View.Item;
import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Interactor.IContactListInteractor;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Interactor.ContactListInteractor;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.Observer.IEventSubscriber;
import com.taxtelecom.arinamurasheva.addressbook.UrlBuilder;

import java.util.ArrayList;
import java.util.List;


public class ContactListPresenter implements IContactListPresenter, IEventSubscriber {

    private IContactListView _view;
    private IContactListInteractor _model;

    public ContactListPresenter(IContactListView view) {
        _view = view;
        _model = new ContactListInteractor();

    }

    @Override
    public void onGetDataLoadRequest() {
        _model.events.subscribe(_model.CONTACT_LIST, this);
        _model.fetchContactListData();

        _view.showLoadingIndicator();

    }

    @Override
    public void updateSuccess(String eventType) {

        Item deptItem = deptToItem(_model.getContactListData());

        _view.showContactDataView();
        _view.setContactListLayout(deptItem);

    }

    @Override
    public void updateFail(String eventType) {
        _view.showErrorMessage();
    }

    @Override
    public void onGetLogOutRequest() {

        SharedPreferencesManager.getInstance().removeUserData();

        _view.goToLoginView();

    }

    @Override
    public void onGetContactInfoRequest(List<Integer> routingList) {

        Person person = _model.getContact(routingList);
        _view.goToContactView(person);

    }


    private static Item deptToItem(Department dept) {
        return deptToItem(dept, 0);
    }

    private static Item deptToItem(Department dept, int nestingLevel) {

        Item deptItem = new Item(dept.getName(), nestingLevel);

        List<Person> persons;
        if ((persons = dept.getEmployees()) != null) {

            List<Item> itemList = new ArrayList<>(persons.size());
            for (Person p : persons) {
                Item personItem = new Item(p.toString());
                personItem.setNestingLevel(-1);
                itemList.add(personItem);
            }

            deptItem.setItems(itemList);
        }

        List<Department> innerDepts;
        if ((innerDepts = dept.getDepartments()) != null) {

            List<Item> itemList = new ArrayList<>(innerDepts.size());
            for (Department d : innerDepts) {
                Item innerDeptItem = deptToItem(d, nestingLevel + 1);
                itemList.add(innerDeptItem);
            }

            deptItem.setItems(itemList);
        }

        return deptItem;

    }

}
