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

    private List<ItemsGroup> mContactList = new ArrayList<>();

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

    private List<?> getFlatItemsList() {
        List<Object> items = new ArrayList<>();

        for (ItemsGroup item : mContactList) {
            items.add(item);
            if (item.isExpanded()) {
                items.addAll(item.getItems());
            }
        }

        return items;
    }

    @Override
    public int getItemCount() {
        return getFlatItemsList().size();
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookAdapterViewHolder holder, int position) {
        ItemsGroup contactListItemData = mContactList.get(position);
        holder.mDeptTextView.setText(contactListItemData.getName());
    }

    public void setContactListData(List<String> deptsList) {

        List<ItemsGroup> result = new ArrayList<>(deptsList.size());

        for (String item : deptsList) {
            result.add(new ItemsGroup(item));
        }

        mContactList = result;
        notifyDataSetChanged();
    }

    private void OnHeaderClicked(ItemsGroup header) {
        int index = getFlatItemsList().indexOf(header);
        if (header.isExpanded()) {
            header.collapse();
            notifyItemRangeRemoved(index + 1, header.getItems().size());
        } else {
            header.expand();
            notifyItemRangeInserted(index + 1, header.getItems().size());
        }
    }
}
