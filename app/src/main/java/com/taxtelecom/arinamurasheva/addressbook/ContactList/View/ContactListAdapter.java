package com.taxtelecom.arinamurasheva.addressbook.ContactList.View;

import android.content.Context;
import android.content.SyncAdapterType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taxtelecom.arinamurasheva.addressbook.R;

import java.util.ArrayList;
import java.util.List;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.AddressBookAdapterViewHolder> {

    private final List<Item> mContactList = new ArrayList<>();

    public ContactListAdapter() {

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

    private void flattenContactList() {
        synchronized (mContactList) {
            mContactList.clear();
            mContactList.addAll(getFlatItemsList(mContactList));
        }
    }

    @Override
    public int getItemCount() {
        try {
            return getFlatItemsList(mContactList).size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookAdapterViewHolder holder, int position) {

        final Item contactListItemData = getFlatItemsList(mContactList).get(position);

        View.OnClickListener headerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHeaderClicked(contactListItemData);
            }
        };

        holder.mDeptTextView.setOnClickListener(headerListener);
        holder.mDeptTextView.setText(contactListItemData.toString());
    }

    //TODO привести в порядок методы onHeaderClicked и collapseAllExceptOne

    private void onHeaderClicked(Item header) {

        synchronized (mContactList) {
            int headerIndex = getFlatItemsList(mContactList).indexOf(header);

            if (header.isExpanded()) {
                notifyItemRangeRemoved(headerIndex + 1, getFlatItemsList(header.getItems()).size());
                collapseWithChildren(header);

            } else {

                if (header.getItems() != null) {

                    header.expand();
                    notifyItemRangeInserted(headerIndex + 1, header.getItems().size());

                    collapseAllExceptOne(header);
                }
            }
        }

    }

    private void collapseWithChildren(Item header) {

        synchronized (mContactList) {
            header.collapse();

            List<Item> childrenList;
            if ((childrenList = header.getItems()) != null) {
                for (Item item : childrenList) {
                    if (item.isExpanded()) {
                        collapseWithChildren(item);
                    }
                }
            }


        }

        System.out.println("collapse with children " + header);
    }

    private void collapseAllExceptOne(Item changingItem) {
        synchronized (mContactList) {

            int changingItemNestingLevel = changingItem.getNestingLevel();

            List<Item> flatItemsList = getFlatItemsList(mContactList);
            int numOfItems = flatItemsList.size();

            for (int i = 0; i < numOfItems; i++) {

                Item item = flatItemsList.get(i);

                if (item.isExpanded() && item.getNestingLevel() == changingItemNestingLevel && !item.equals(changingItem)) {

                    notifyItemRangeRemoved(i + 1, getFlatItemsList(item.getItems()).size());
                    collapseWithChildren(item);

                }
            }
        }

        System.out.println("collapse all except one " + changingItem);
    }



/*    public List<Integer> getRoutingList(Item header) {

        List<Integer> routingList = new ArrayList<>(3);
        int numOfItems = getItemCount();

        for (int i = 0; i < numOfItems; i++) {

        }
    }

    private Integer getIndex(List<Integer> list, Item item) {

    }*/


    /*Метод адаптирует список отделов для отображения на экране в виде раскрывающегося списка.*/
    public void setContactListData(Item item) {

        mContactList.addAll(item.getItems());

        notifyDataSetChanged();

    }
/*
    public List<Integer> getRoutingList(Item selectedItem) {
        List<Integer> routingList = new ArrayList<>(5);
        int selectedItemNestingLevel = selectedItem.getNestingLevel();

        List<Item> flatItemsList = getFlatItemsList(mContactList);

        int curCount = 0;
        for (Item iterItem : flatItemsList) {
            int iterItemnestingLevel = iterItem.getNestingLevel();


            if (iterItemnestingLevel < selectedItemNestingLevel) {
                curCount++;
            }
            if (iterItemnestingLevel)
        }
    }*/

}
