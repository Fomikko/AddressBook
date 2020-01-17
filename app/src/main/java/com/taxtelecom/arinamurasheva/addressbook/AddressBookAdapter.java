package com.taxtelecom.arinamurasheva.addressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taxtelecom.arinamurasheva.addressbook.model.Department;
import com.taxtelecom.arinamurasheva.addressbook.model.Person;

import java.util.ArrayList;
import java.util.List;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.AddressBookAdapterViewHolder> {

    private List<Item> mContactList;

    public AddressBookAdapter() {

    }

    public class AddressBookAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mDeptTextView;

        public AddressBookAdapterViewHolder(View view) {
            super(view);
            mDeptTextView = view.findViewById(R.id.tv_dept_name);
        }

    }

    @NonNull
    @Override
    public AddressBookAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.contact_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParent =  false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, attachToParent);
        return new AddressBookAdapterViewHolder(view);
    }

    private List<Item> getFlatItemsList() {

        List<Item> items = new ArrayList<>();

        for (Item item : mContactList) {
            items.add(item);

            List<Item> innerItems;

            if (item.isExpanded() && (innerItems = item.getItems()) != null) {
                items.addAll(innerItems);
            }
        }

        return items;
    }

    @Override
    public int getItemCount() {
        try {
            return getFlatItemsList().size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookAdapterViewHolder holder, int position) {

        mContactList = getFlatItemsList();

        final Item contactListItemData = mContactList.get(position);

        View.OnClickListener headerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeaderClicked(contactListItemData);
            }
        };

        holder.mDeptTextView.setOnClickListener(headerListener);
        holder.mDeptTextView.setText(contactListItemData.toString());

    }

    /*Метод адаптирует список отделов для отображения на экране в виде раскрывающегося списка.*/
    public void setContactListData(List<Item> itemsList) {

        mContactList = itemsList;
        notifyDataSetChanged();

    }

    public static List<Item> deptsToItemsList(List<Department> deptsList) {

        int numOfItems = deptsList.size();
        List<Item> result = new ArrayList<>(numOfItems);

        for (int i = 0; i < numOfItems; i++) {
            Department dept = deptsList.get(i);

            Item item = new Item(dept.getName());

            List<Department> innerDepts;
            List<Person> personsList;

            if ((innerDepts = dept.getDepartments()) != null) {

                item.setItems(deptsToItemsList(innerDepts));

            }/* else if ((personsList = dept.getEmployees()) != null) {

                List<Item> itemList = deptsToItemsList(personsList);
                item.setItems(personsList);

            }*/

            result.add(item);
        }

        //printItemsList(result);
        return result;
    }

    private void onHeaderClicked(Item header) {

        int index = getFlatItemsList().indexOf(header);

        if (header.isExpanded()) {
            header.collapse();
            notifyItemRangeRemoved(index + 1, header.getItems().size());

        } else {
            if (header.getItems() != null) {
                header.expand();
                notifyItemRangeInserted(index + 1, header.getItems().size());
            }
        }
    }

    public static void printItemsList(List<Item> itemsList) {
        for (Item item : itemsList) {
            System.out.println(item.getName());

            List<Item> innerItems;
            if ((innerItems = item.getItems()) != null) {
                System.out.println("SubItems of " + item.getName());
                printItemsList(innerItems);
            }
        }
    }
}
