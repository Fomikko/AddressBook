package com.taxtelecom.arinamurasheva.addressbook.ContactList.View;

import android.content.Context;
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


            int index = getFlatItemsList(mContactList).indexOf(header);

            if (header.isExpanded()) {

                synchronized (mContactList) {
                    header.collapse();

                    List<Item> flatItemsList = getFlatItemsList(header.getItems());
                    int numOfCollapsingItems = flatItemsList.size();
                    notifyItemRangeRemoved(index + 1, numOfCollapsingItems);

                    for (int i = 0; i < numOfCollapsingItems; i++) {
                        Item curItem = flatItemsList.get(i);
                        if (curItem.isExpanded()) {
                            curItem.collapse();
                        }
                    }
                }

                //collapseAllExceptOne(header);



            } else {
                if (header.getItems() != null) {

                    synchronized (mContactList) {
                        header.expand();
                        notifyItemRangeInserted(index + 1, header.getItems().size());
                        System.out.println(header + " expanded");
                    }

                    collapseAllExceptOne(header);
                }
            }

    }

    private void collapseAllExceptOne(Item changingItem) {

        synchronized (mContactList) {

            List<Item> flatItemsList = getFlatItemsList(mContactList);
            int numOfItems = flatItemsList.size();

            int changingItemNestingLevel = changingItem.getNestingLevel();

            for (int iterNestingLevel = changingItemNestingLevel; iterNestingLevel >= changingItemNestingLevel; iterNestingLevel--) {

                for (int i = 0; i < numOfItems; i++) {

                    Item curItem = flatItemsList.get(i);
                    int curNestingLevel = curItem.getNestingLevel();

                    if (curItem.isExpanded() && curNestingLevel == iterNestingLevel && !curItem.equals(changingItem)) {

                        curItem.collapse();
                        notifyItemRangeRemoved(i + 1, curItem.getItems().size());

                        System.out.println(curItem + " here!!!");
                        flatItemsList = getFlatItemsList(flatItemsList);
                        numOfItems = flatItemsList.size();


                    }
                }
            }
        }
    }

    public void collapseChildren(Item header) {

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

        mContactList.add(item);

        notifyDataSetChanged();

    }



}
