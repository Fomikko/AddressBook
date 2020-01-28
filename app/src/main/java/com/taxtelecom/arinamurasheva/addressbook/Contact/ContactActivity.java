package com.taxtelecom.arinamurasheva.addressbook.Contact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.taxtelecom.arinamurasheva.addressbook.R;

public class ContactActivity extends AppCompatActivity {

    private TextView mContactName;

    private TextView mContactTitle;

    private TextView mContactEmail;

    private TextView mContactPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


    }
}
