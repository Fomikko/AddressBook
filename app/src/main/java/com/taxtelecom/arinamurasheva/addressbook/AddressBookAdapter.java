package com.taxtelecom.arinamurasheva.addressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.AddressBookAdapterViewHolder> {

    private List<Item> contactList;

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

    private List<Item> getFlatItemsList(List<Item> nestedItemsList) {

        List<Item> flatItemsList = new ArrayList<>();

        for (int i = 0; i < nestedItemsList.size(); i++) {

            Item curItem = nestedItemsList.get(i);
            flatItemsList.add(curItem);

            List<Item> innerItems;

            if (curItem.isExpanded() && (innerItems = curItem.getItems()) != null) {
                flatItemsList.addAll(getFlatItemsList(innerItems));

            }
        }

        return flatItemsList;
    }


    @Override
    public int getItemCount() {
        try {
            return getFlatItemsList(contactList).size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookAdapterViewHolder holder, int position) {

        final Item contactListItemData = getFlatItemsList(contactList).get(position);

        View.OnClickListener headerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeaderClicked(contactListItemData);
            }
        };

        holder.mDeptTextView.setOnClickListener(headerListener);
        holder.mDeptTextView.setText(contactListItemData.toString());
    }


    private void onHeaderClicked(Item header) {

        int index = getFlatItemsList(contactList).indexOf(header);

        if (header.isExpanded()) {
            header.collapse();
            notifyItemRangeRemoved(index + 1, getFlatItemsList(header.getItems()).size());

        } else {
            if (header.getItems() != null) {
                header.expand();
                notifyItemRangeInserted(index + 1, getFlatItemsList(header.getItems()).size());
            }
        }
    }

    /*Метод адаптирует список отделов для отображения на экране в виде раскрывающегося списка.*/
    public void setContactListData(Item item) {

        List<Item> itemsList = new ArrayList<>(1);
        itemsList.add(item);

        contactList = itemsList;
        notifyDataSetChanged();

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
