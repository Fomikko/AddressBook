package com.taxtelecom.arinamurasheva.addressbook.Contact.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.taxtelecom.arinamurasheva.addressbook.Contact.Presenter.ContactPresenter;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;
import com.taxtelecom.arinamurasheva.addressbook.R;

public class ContactActivity extends AppCompatActivity implements IContactView {

    ContactPresenter presenter;

    private ImageView mContactPhoto;

    private TextView mContactName;

    private TextView mContactTitle;

    private TextView mContactEmail;

    private TextView mContactPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        if (presenter == null) {
            presenter = new ContactPresenter(this);
        }

        mContactPhoto = findViewById(R.id.iv_contact_photo);

        mContactName = findViewById(R.id.tv_contact_name);

        mContactTitle = findViewById(R.id.tv_contact_title);

        mContactEmail = findViewById(R.id.tv_contact_email);

        mContactPhone = findViewById(R.id.tv_contact_phone);

        View.OnClickListener telListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhoneClicked(v);
            }
        };

        mContactPhone.setOnClickListener(telListener);

        View.OnClickListener emailListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmailClicked(v);
            }
        };

        mContactEmail.setOnClickListener(emailListener);

        setContactData();
    }

    private void setContactData() {

        Person person = (Person) getIntent().getSerializableExtra("person");

        String contactName;
        String contactTitle;
        String contactEmail;
        String contactPhone;

        requestContactPhoto(person.getId());

        contactName = person.getId() + " " + person.getName();
        mContactName.setText(contactName);

        if ((contactTitle = person.getTitle()) != null) {
            mContactTitle.setText(contactTitle);

        } else {
            mContactTitle.setText(getString(R.string.no_contact_title));
        }

        if ((contactEmail = person.getEmail()) != null) {
            mContactEmail.setText(contactEmail);

        } else {
            mContactEmail.setText(getString(R.string.no_contact_email));
        }

        if ((contactPhone = person.getPhone()) != null) {
            mContactPhone.setText(contactPhone);

        } else {
            mContactPhone.setText(getString(R.string.no_contact_phone));
        }

    }

    @Override
    public void setContactPhoto(final Bitmap contactPhoto) {
        ContactActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mContactPhoto.setImageBitmap(contactPhoto);

            }
        });
    }

    @Override
    public void onEmailClicked(View view) {

        CharSequence personEmail = mContactEmail.getText();

        if (!personEmail.equals(getString(R.string.no_contact_email))) {

            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, personEmail);

            try {

                startActivity(Intent.createChooser(i, "Отправка email..."));

            } catch (android.content.ActivityNotFoundException ex) {

                Toast.makeText(ContactActivity.this, "Нет установленных приложений для отправки email.", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public void onPhoneClicked(View view) {

        String personPhone = mContactPhone.getText().toString();

        if (!personPhone.equals(getString(R.string.no_contact_phone))) {

            String dial = "tel:" + personPhone;
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(dial));

            try {

                startActivity(Intent.createChooser(dialIntent, "Звонок..."));

            } catch (android.content.ActivityNotFoundException ex) {

                Toast.makeText(ContactActivity.this, "Нет установленных приложений для звонка.", Toast.LENGTH_SHORT).show();

            }
        }

    }

    @Override
    public void requestContactPhoto(String contactId) {
        presenter.onGetPhotoRequest(contactId);
    }



}
