package com.taxtelecom.arinamurasheva.addressbook.Contact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.R;

import java.io.Serializable;

public class ContactActivity extends AppCompatActivity {

    private TextView mContactName;

    private TextView mContactTitle;

    private TextView mContactEmail;

    private TextView mContactPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        mContactName = findViewById(R.id.tv_contact_name);

        mContactTitle = findViewById(R.id.tv_contact_title);

        mContactEmail = findViewById(R.id.tv_contact_email);

        mContactPhone = findViewById(R.id.tv_contact_phone);

        setContactData();
    }

    private void setContactData() {
        Person person = (Person) getIntent().getSerializableExtra("person");

        String personName;
        String personTitle;
        String personEmail;
        String personPhone;

        personName = person.getId() + " " + person.getName();
        mContactName.setText(personName);

        if ((personTitle = person.getTitle()) != null) {
            mContactTitle.setText(personTitle);

        } else {
            mContactTitle.setText(getString(R.string.no_contact_title));
        }

        if ((personEmail = person.getEmail()) != null) {
            mContactEmail.setText(personEmail);

        } else {
            mContactEmail.setText(getString(R.string.no_contact_email));
        }

        if ((personPhone = person.getPhone()) != null) {
            mContactPhone.setText(personPhone);

        } else {
            mContactPhone.setText(getString(R.string.no_contact_phone));
        }

    }
}
