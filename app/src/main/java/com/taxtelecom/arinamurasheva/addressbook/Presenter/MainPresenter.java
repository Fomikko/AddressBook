package com.taxtelecom.arinamurasheva.addressbook.Presenter;

import android.util.Log;

import com.taxtelecom.arinamurasheva.addressbook.IMainView;
import com.taxtelecom.arinamurasheva.addressbook.Item;
import com.taxtelecom.arinamurasheva.addressbook.model.Department;
import com.taxtelecom.arinamurasheva.addressbook.model.IContactListModel;
import com.taxtelecom.arinamurasheva.addressbook.model.ContactListModel;
import com.taxtelecom.arinamurasheva.addressbook.model.IJsonResponseSubscriber;
import com.taxtelecom.arinamurasheva.addressbook.model.Person;
import com.taxtelecom.arinamurasheva.addressbook.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;


public class MainPresenter implements IMainPresenter, IJsonResponseSubscriber {

    private IMainView _view;
    private IContactListModel _model;

    public MainPresenter (IMainView view) {
        _view = view;
        _model = new ContactListModel();

    }

    @Override
    public void onGetDataLoadRequest() {
        String login = "test_user";
        String password = "test_pass";

        String contactListRequestUrl = NetworkUtils.buildUrl(login, password);

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
}
