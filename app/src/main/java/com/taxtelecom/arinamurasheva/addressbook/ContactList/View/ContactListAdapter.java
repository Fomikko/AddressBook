package com.taxtelecom.arinamurasheva.addressbook.ContactList.View;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.taxtelecom.arinamurasheva.addressbook.R;

import java.util.ArrayList;
import java.util.List;


public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.AddressBookAdapterViewHolder> {

    private List<Item> mContactList;

    private IContactListView listener;

    public ContactListAdapter() {

    }

    public class AddressBookAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mDeptTextView;

        public AddressBookAdapterViewHolder(View view) {
            super(view);
            mDeptTextView = view.findViewById(R.id.tv_dept_name);
        }

    }

    public void setUserInfoListener(ContactListActivity activity) {
        listener = activity;
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

    @Override
    public void onBindViewHolder(@NonNull final AddressBookAdapterViewHolder holder, int position) {

        final Item itemData = getFlatItemsList(mContactList).get(position);

        int itemNestingLevel = itemData.getNestingLevel();

        if (itemNestingLevel == 1) {

            holder.mDeptTextView.setPadding(64, 32, 64, 32);
            holder.mDeptTextView.setTextSize(22);
            holder.mDeptTextView.setAllCaps(true);
            holder.mDeptTextView.setTypeface(null, Typeface.BOLD);

        } else if (itemNestingLevel == 2) {

            holder.mDeptTextView.setPadding(64,16,64,16);
            holder.mDeptTextView.setTextSize(22);
            holder.mDeptTextView.setAllCaps(false);
            holder.mDeptTextView.setTypeface(null, Typeface.NORMAL);

        } else if (itemNestingLevel == 3) {

            holder.mDeptTextView.setPadding(100,16,64,16);
            holder.mDeptTextView.setTextSize(22);
            holder.mDeptTextView.setAllCaps(false);
            holder.mDeptTextView.setTypeface(null, Typeface.NORMAL);

        } else if (itemNestingLevel == -1) {

            holder.mDeptTextView.setPadding(140,8,64,8);
            holder.mDeptTextView.setTextSize(20);
            holder.mDeptTextView.setAllCaps(false);
            holder.mDeptTextView.setTypeface(null, Typeface.NORMAL);

        }

        View.OnClickListener headerListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.mDeptTextView.getContext();
                onHeaderClicked(context, itemData);
            }
        };

        holder.mDeptTextView.setOnClickListener(headerListener);
        holder.mDeptTextView.setText(itemData.toString());


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

    private void onHeaderClicked(Context context, Item header) {

            int headerIndex = getFlatItemsList(mContactList).indexOf(header);

            if (header.isExpanded()) {
                notifyItemRangeRemoved(headerIndex + 1, getFlatItemsList(header.getItems()).size());
                collapseWithChildren(header);

            } else if (!header.isExpanded() && (header.getItems() != null)) {

                    header.expand();
                    notifyItemRangeInserted(headerIndex + 1, header.getItems().size());
                    collapseAllExceptOne(header);

            } else if (header.getNestingLevel() != -1) {

                Toast toast = Toast.makeText(
                        context,
                        "Отдел \u00AB" + header.getName() + "\u00BB пуст.",
                        Toast.LENGTH_SHORT);
                toast.show();

            } else {

                listener.requestContactInfo(getRoutingList(headerIndex));

            }

    }

    private void collapseWithChildren(Item header) {

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

    private void collapseAllExceptOne(Item changingItem) {

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

    /*Метод адаптирует список отделов для отображения на экране в виде раскрывающегося списка.*/
    void setContactListData(Item item) {

        mContactList = item.getItems();
        notifyDataSetChanged();

    }

    private List<Integer> getRoutingList(int headerIndex) {

        List<Integer> routingList = new ArrayList<>(5);

        List<Item> flatItemsList = getFlatItemsList(mContactList);

        int count = -1;
        int curNestingLevel;
        int bufferNestingLevel = 1;

        for (int i = 0; i <= headerIndex; i++) {

            Item iterItem = flatItemsList.get(i);

            curNestingLevel = iterItem.getNestingLevel();

            if (curNestingLevel == bufferNestingLevel) {
                count++;
            } else {
                bufferNestingLevel= curNestingLevel;

                routingList.add(count);
                count = 0;
            }
        }

        routingList.add(count);

        return routingList;
    }

}
