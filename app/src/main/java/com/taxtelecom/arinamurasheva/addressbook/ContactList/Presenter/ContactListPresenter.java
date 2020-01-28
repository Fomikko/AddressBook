package com.taxtelecom.arinamurasheva.addressbook.ContactList.Presenter;

import com.taxtelecom.arinamurasheva.addressbook.ContactList.View.IContactListView;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.View.Item;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Contractor.IContactListContractor;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Contractor.ContactListContractor;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.ContactListApp;
import com.taxtelecom.arinamurasheva.addressbook.Observer.ISubscriber;
import com.taxtelecom.arinamurasheva.addressbook.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;


public class ContactListPresenter implements IContactListPresenter, ISubscriber {

    private IContactListView _view;
    private IContactListContractor _model;

    public ContactListPresenter(IContactListView view) {
        _view = view;
        _model = new ContactListContractor();

    }

    @Override
    public void onGetDataLoadRequest() {
        String contactListRequestUrl = NetworkUtils.buildContactListUrl();

        _model.fetchContactListData(contactListRequestUrl);

        _model.subscribe(this);
    }


    private static Item deptToItem(Department dept) {

        Item item = new Item(dept.getName());

        List<Person> persons;
        if ((persons = dept.getEmployees()) != null) {

            List<Item> itemList = new ArrayList<>(persons.size());
            for (Person p : persons) {
                itemList.add(new Item(p.toString()));
            }

            item.setItems(itemList);
        }

        List<Department> innerDepts;
        if ((innerDepts = dept.getDepartments()) != null) {

            List<Item> itemList = new ArrayList<>(innerDepts.size());
            for (Department d : innerDepts) {
                itemList.add(deptToItem(d));
            }

            item.setItems(itemList);
        }

        return item;

    }

    @Override
    public void update() {
        Department dept;

        if ((dept = _model.getRootDepartment()) != null) {

            _view.showContactDataView();
            _view.setContactListLayout(deptToItem(dept));
        }
    }

    @Override
    public void onGetLogOutRequest() {
        ContactListApp.getSharedPreferencesManager().removeUserData();

        _view.goToLoginView();

    }

}
