package com.taxtelecom.arinamurasheva.addressbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.AddressBookAdapterViewHolder> {

    private String[] mContactData;

    public AddressBookAdapter() {

    }

    public class AddressBookAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContactTextView;

        public AddressBookAdapterViewHolder(View view) {
            super(view);
            mContactTextView = view.findViewById(R.id.tv_contact_data);
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

    @Override
    public int getItemCount() {
        if (mContactData == null) {
            return 0;
        }
        return mContactData.length;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookAdapterViewHolder holder, int position) {
        String contactListItemData = mContactData[position];
        holder.mContactTextView.setText(contactListItemData);


    }

    public void setContactData(String[] contactData) {
        mContactData = contactData;
        notifyDataSetChanged();
    }
}
