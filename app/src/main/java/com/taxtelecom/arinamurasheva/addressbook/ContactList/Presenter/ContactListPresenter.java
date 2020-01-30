package com.taxtelecom.arinamurasheva.addressbook.ContactList.Presenter;

import com.taxtelecom.arinamurasheva.addressbook.Authenticator.SharedPreferencesManager;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.View.IContactListView;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.View.Item;
import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Contractor.IContactListContractor;
import com.taxtelecom.arinamurasheva.addressbook.ContactList.Contractor.ContactListContractor;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.Observer.ISubscriber;
import com.taxtelecom.arinamurasheva.addressbook.utilities.NetworkUtils;

import java.io.Serializable;
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

    @Override
    public void update() {
        Department dept;

        if ((dept = _model.getRootDepartment()) != null) {

            Item deptItem = deptToItem(dept);

            _view.showContactDataView();
            _view.setContactListLayout(deptItem);

            //printItemsList(deptItem);
        }
    }

    public static void printItemsList(Item item) {
        System.out.println(item.getName() + " LVL " + item.getNestingLevel());

        List<Item> innerItems;
        if ((innerItems = item.getItems()) != null) {
            System.out.println("Внутрянка " + item.getName());
            for (Item i : innerItems) {
                printItemsList(i);
            }
        }
    }

    @Override
    public void onGetLogOutRequest() {
        SharedPreferencesManager.getInstance().removeUserData();

        _view.goToLoginView();

    }

    @Override
    public void onGetContactInfoRequest(List<Integer> routingList) {

        Person person = _model.getUserId(routingList);
        _view.goToContactView(person);

    }
}
